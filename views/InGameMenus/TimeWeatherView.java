package com.untildawn.views.InGameMenus;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.untildawn.Enums.GameConsts.Seasons;
import com.untildawn.Enums.GameConsts.WeatherStates;
import com.untildawn.models.Game;
import com.untildawn.models.States.DateTime;
import com.untildawn.models.States.Weather;

import java.util.HashMap;
import java.util.Map;

public class TimeWeatherView implements Disposable {

    private Stage stage;
    private Group hudGroup; // به جای Stack یا Table از Group استفاده می‌کنیم
    private Image timeDialImage; // دایره زمان را به صورت جداگانه نگه می‌داریم

    // اجزای UI
    private Label dateLabel;
    private Label timeLabel;
    private Image seasonIcon;
    private Image weatherIcon;

    // منابع گرافیکی
    private Skin skin;
    private Map<String, Texture> iconTextures;
    private Texture hudFrameTexture;

    private Label[] digitLabels;
    private final int MAX_DIGITS = 8;
    private Image energyBarBackground;
    private Image energyBarForeground;

    private float uiScale;
    private float frameWidth;
    private float frameHeight;

    public TimeWeatherView(SpriteBatch batch, Skin skin) {
        this.skin = skin;
        stage = new Stage(new ScreenViewport(), batch);

        // --- ۱. تعریف مقیاس نهایی ---
         uiScale = 2.8f;

        // --- ۲. بارگذاری منابع ---
        hudFrameTexture = new Texture(Gdx.files.internal("Images/sprites/UI/Clock.png"));
        iconTextures = new HashMap<>();
        iconTextures.put(Seasons.SPRING.name(), new Texture(Gdx.files.internal("Images/sprites/UI/Clock3.png")));
        iconTextures.put(Seasons.SUMMER.name(), new Texture(Gdx.files.internal("Images/sprites/UI/Clock8.png")));
        iconTextures.put(Seasons.FALL.name(), new Texture(Gdx.files.internal("Images/sprites/UI/Clock9.png")));
        iconTextures.put(Seasons.WINTER.name(), new Texture(Gdx.files.internal("Images/sprites/UI/Clock6.png")));
        iconTextures.put(WeatherStates.SUNNY.name(), new Texture(Gdx.files.internal("Images/sprites/UI/Clock13.png")));
        iconTextures.put(WeatherStates.RAIN.name(), new Texture(Gdx.files.internal("Images/sprites/UI/Clock4.png")));
        iconTextures.put(WeatherStates.STORM.name(), new Texture(Gdx.files.internal("Images/sprites/UI/Clock10.png")));
        iconTextures.put(WeatherStates.SNOWY.name(), new Texture(Gdx.files.internal("Images/sprites/UI/Clock6.png")));
        iconTextures.put("TIME_DIAL", new Texture(Gdx.files.internal("Images/sprites/UI/Clock2.png")));

        // --- ۳. ساخت اجزا ---
        Image frameImage = new Image(hudFrameTexture);

         frameWidth = hudFrameTexture.getWidth() * uiScale;
         frameHeight = hudFrameTexture.getHeight() * uiScale;
        frameImage.setSize(frameWidth, frameHeight);

        float fontScale = 0.5f * uiScale;
        dateLabel = new Label("MON. 1", skin, "hud-style");
        dateLabel.setFontScale(fontScale);
        timeLabel = new Label("6:00 am", skin, "hud-style");
        timeLabel.setFontScale(fontScale);
        seasonIcon = new Image(iconTextures.get(Seasons.SPRING.name()));
        weatherIcon = new Image(iconTextures.get(WeatherStates.SUNNY.name()));
        timeDialImage = new Image(iconTextures.get("TIME_DIAL")); // <-- این خط را اضافه کنید


        // --- ۴. ساخت Group و تنظیم دستی موقعیت‌ها ---
        hudGroup = new Group();
        hudGroup.setSize(frameWidth, frameHeight);

        // قاب در نقطه (۰,۰) گروه قرار می‌گیرد
        hudGroup.addActor(frameImage);

        // **اینجا نقطه کلیدی است: موقعیت‌ها را دستی و با آزمون و خطا تنظیم کنید**
        // مختصات (۰,۰) گوشه پایین-چپ کادر HUD است.

        // تنظیم موقعیت تاریخ (Date)
        dateLabel.setPosition(frameWidth * 0.45f, frameHeight * 0.8f);
        hudGroup.addActor(dateLabel);

        // تنظیم موقعیت آیکون فصل
        float iconSize = 9 * uiScale;
        seasonIcon.setSize(iconSize, iconSize);
        seasonIcon.setPosition(frameWidth * 0.4f, frameHeight * 0.6f);
        hudGroup.addActor(seasonIcon);

        // تنظیم موقعیت آیکون آب و هوا
        weatherIcon.setSize(iconSize, iconSize);
        weatherIcon.setPosition(frameWidth * 0.8f, frameHeight * 0.6f);
        hudGroup.addActor(weatherIcon);

        // تنظیم موقعیت زمان (Time)
        timeLabel.setPosition(frameWidth * 0.45f, frameHeight * 0.41f);
        hudGroup.addActor(timeLabel);
// تنظیمات دایره زمان (Dial)
        // --- تنظیمات دایره زمان (Dial) ---
        float dialSize = frameHeight * 0.85f; // اندازه دایره، ۸۵٪ ارتفاع قاب (این عدد را برای تغییر اندازه تغییر دهید)
        timeDialImage.setSize(dialSize, dialSize);
        timeDialImage.setOrigin(Align.center); // نقطه چرخش را در مرکز قرار می‌دهد
// موقعیت‌دهی نهایی و دقیق در متد resize انجام می‌شود

// موقعیت دایره (این اعداد را باید با سعی و خطا تنظیم کنید تا دقیقاً کنار قاب قرار بگیرد)
        timeDialImage.setPosition(frameWidth * 0.02f, frameHeight * 0.25f);

        // --- ساخت و موقعیت‌دهی نمایشگر پول ---
        digitLabels = new Label[MAX_DIGITS];
        Table digitTable = new Table(); // جدولی برای نگهداری منظم ارقام

        for (int i = 0; i < MAX_DIGITS; i++) {
            digitLabels[i] = new Label("", skin, "hud-style");
            digitLabels[i].setFontScale(fontScale * 0.9f); // فونت پول کمی کوچکتر
            // هر لیبل را به یک سلول اضافه می‌کنیم
            digitTable.add(digitLabels[i]).width(14 * uiScale).expandX(); // عرض هر خانه رقم
        }

// موقعیت جدول ارقام را نسبت به قاب اصلی تنظیم می‌کنیم
// این اعداد را برای تنظیم دقیق جایگاه پول تغییر دهید
        float moneyX = frameWidth * 0.24f;
        float moneyY = frameHeight * 0.13f;
        digitTable.setPosition(moneyX, moneyY);

        hudGroup.addActor(digitTable); // جدول ارقام را به گروه اصلی اضافه می‌کنیم


        // --- ساخت نوار انرژی ---
// با استفاده از Pixmap، یک تکسچر ۱x۱ پیکسلی برای هر رنگ می‌سازیم
        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        bgPixmap.setColor(0.3f, 0.3f, 0.3f, 1); // رنگ خاکستری تیره برای پس‌زمینه
        bgPixmap.fill();
        iconTextures.put("ENERGY_BG", new Texture(bgPixmap));

        Pixmap fgPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        fgPixmap.setColor(0.9f, 0.2f, 0.2f, 1);
        fgPixmap.fill();
        iconTextures.put("ENERGY_FG", new Texture(fgPixmap));

        bgPixmap.dispose(); // دیگر به pixmap ها نیازی نداریم
        fgPixmap.dispose();

// ساخت Image ها از روی تکسچرهای رنگی
        energyBarBackground = new Image(iconTextures.get("ENERGY_BG"));
        energyBarForeground = new Image(iconTextures.get("ENERGY_FG"));

// اضافه کردن نوارها به Group اصلی
        hudGroup.addActor(energyBarBackground);
        hudGroup.addActor(energyBarForeground);
// موقعیت‌دهی اولیه در متد update انجام می‌شود

// این خط از قبل در انتهای constructor شما وجود دارد
        stage.addActor(hudGroup);
        stage.addActor(timeDialImage);
    }

    public void update(Game game) {
        // دریافت اطلاعات لازم از آبجکت game
        DateTime dateTime = game.getDateTime();
        Weather weather = game.getWeather();

        // --- ۱. آپدیت متن‌ها و آیکون‌های اصلی ---
        String dayOfWeekStr = dateTime.getDayOfWeek().toString().substring(0, 3).toUpperCase();
        dateLabel.setText(dayOfWeekStr + ". " + dateTime.getDay());

        String ampm = "am";
        int hour = dateTime.getHour();
        if (hour >= 12) {
            ampm = "pm";
            if (hour > 12) {
                hour -= 12;
            }
        }
        if (hour == 0) {
            hour = 12; // برای نمایش صحیح ساعت ۱۲ شب
        }
        timeLabel.setText(String.format("%d:00 %s", hour, ampm));

        // نسخه صحیح و بهینه‌تر
        seasonIcon.setDrawable(new TextureRegionDrawable(iconTextures.get(dateTime.getSeason().name())));
        weatherIcon.setDrawable(new TextureRegionDrawable(iconTextures.get(weather.getCurrentWeather().name())));
        // --- ۲. محاسبه و اعمال چرخش صحیح (از پایین به بالا) ---
        int currentHour = dateTime.getHour();
        int startHour = 9;
        int endHour = 22;

        float progress = (float)(currentHour - startHour) / (endHour - startHour);
        if (progress < 0) progress = 0;
        if (progress > 1) progress = 1;

        // فرمول صحیح: چرخش از -90 درجه (پایین) به سمت +90 درجه (بالا)
        float rotation = -90 + (progress * 180.0f);
        timeDialImage.setRotation(rotation);

        // --- ۳. آپدیت نمایشگر پول ---
        int coins = game.getCurrentPlayer().getWallet().getCoin();
        String coinString = String.valueOf(coins);
        int len = coinString.length();

        // ابتدا تمام لیبل‌ها را پاک می‌کنیم
        for (int i = 0; i < MAX_DIGITS; i++) {
            digitLabels[i].setText("");
        }

        // سپس لیبل‌ها را از راست به چپ با ارقام جدید پر می‌کنیم
        for (int i = 0; i < len; i++) {
            int labelIndex = MAX_DIGITS - len + i;
            if (labelIndex >= 0 && labelIndex < MAX_DIGITS) {
                digitLabels[labelIndex].setText(String.valueOf(coinString.charAt(i)));
            }
        }

        // --- آپدیت نوار انرژی ---
        float currentEnergy = game.getCurrentPlayer().getEnergy();
        float maxEnergy = game.getCurrentPlayer().getEnergyLimit();
        float energyPercent = currentEnergy / maxEnergy;

// ابعاد و موقعیت نوار انرژی را اینجا تنظیم کنید
        float barWidth = 6 * uiScale;
        float barMaxHeight = 40 * uiScale;
        float barCurrentHeight = barMaxHeight * energyPercent;

// موقعیت نسبت به گوشه پایین-چپ قاب اصلی
        float barX = frameWidth * 0.9f; // کمی سمت راست قاب
        float barY = frameHeight * -0.7f;  // کمی بالاتر از پایین قاب
        float borderThickness = 2 * uiScale;
        energyBarBackground.setBounds(barX, barY, barWidth, barMaxHeight);

        float foregroundX = barX + borderThickness;
        float foregroundY = barY + borderThickness;
        float foregroundWidth = barWidth - (2 * borderThickness);
        float foregroundMaxHeight = barMaxHeight - (2 * borderThickness);
        float foregroundCurrentHeight = foregroundMaxHeight * energyPercent;

        energyBarForeground.setBounds(foregroundX, foregroundY, foregroundWidth, foregroundCurrentHeight);
    }

    public void draw() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        float padding = 15f;

        // ۱. موقعیت قاب اصلی (مثل قبل)
        if (hudGroup != null) {
            hudGroup.setPosition(width - hudGroup.getWidth() - padding, height - hudGroup.getHeight() - padding);
        }

        // ۲. تنظیمات نهایی و دستی دایره زمان (Dial)
        if (timeDialImage != null && hudGroup != null) {
            // **برای بزرگ/کوچک کردن دایره، فقط این عدد را تغییر دهید**
            float dialSize = 40.0f; // یک اندازه ثابت بر حسب پیکسل.
            timeDialImage.setSize(dialSize, dialSize);

            // **نکته کلیدی: نقطه چرخش را دقیقاً در مرکز تنظیم می‌کند**
            timeDialImage.setOrigin(dialSize / 2, dialSize / 2);

            // **برای جابجایی دایره، این دو عدد را تغییر دهید**
            // این‌ها فاصله (offset) از گوشه پایین-چپ قاب اصلی هستند.
            float dialOffsetX = 35.0f; // فاصله افقی از لبه چپ قاب
            float dialOffsetY = 95.0f;  // فاصله عمودی از لبه پایین قاب

            // محاسبه موقعیت نهایی بر اساس موقعیت قاب و فاصله دلخواه
            float dialX = hudGroup.getX() + dialOffsetX;
            float dialY = hudGroup.getY() + dialOffsetY;

            timeDialImage.setPosition(dialX, dialY);
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
        hudFrameTexture.dispose();
        for (Texture texture : iconTextures.values()) {
            texture.dispose();
        }
    }
}
