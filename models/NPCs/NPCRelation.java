package com.untildawn.models.NPCs;

/*
    A class to handle relations between a player and an NPC.
 */

import com.untildawn.models.Players.Player;


public class NPCRelation {
    private Player player;
    private NPC npc;
    private int friendShipLevel;
    private int friendShipPoints;
    private boolean firstMeetInDay;
    private boolean firstGiftInDay;
    private int availableQuestNumber;
    private int daysToLastQuest;

    public NPCRelation(Player player, NPC npc) {
        this.player = player;
        this.npc = npc;
        this.friendShipLevel = 0;
        this.friendShipPoints = 0;
        this.firstMeetInDay = true;
        this.firstGiftInDay = true;
        this.availableQuestNumber = 1;
        this.daysToLastQuest = npc.getDaysToLastQuest();
    }

    public Player getPlayer() {
        return player;
    }

    public NPC getNpc() {
        return npc;
    }

    public int getFriendShipLevel() {
        return this.friendShipPoints / 200;
    }

    public int getFriendShipPoints() {
        return friendShipPoints;
    }

    public void setFriendShipPoints(int friendShipPoints) {
        this.friendShipPoints = Math.min(friendShipPoints, 799);
    }

    public boolean areMarried() {
        return areMarried();
    }

    public void setFirstMeetInDay(boolean firstMeetInDay) {
        this.firstMeetInDay = firstMeetInDay;
    }

    public void setFirstGiftInDay(boolean firstGiftInDay) {
        this.firstGiftInDay = firstGiftInDay;
    }

    public boolean isFirstMeetInDay() {
        return firstMeetInDay;
    }

    public boolean isFirstGiftInDay() {
        return firstGiftInDay;
    }

    public void setAvailableQuestNumber(int availableQuestNumber) {
        this.availableQuestNumber = availableQuestNumber;
    }

    public int getAvailableQuestNumber() {
        return availableQuestNumber;
    }

    public void setDaysToLastQuest(int daysToLastQuest) {
        this.daysToLastQuest = Math.max(daysToLastQuest, 0);
    }

    public int getDaysToLastQuest() {
        return daysToLastQuest;
    }
}
