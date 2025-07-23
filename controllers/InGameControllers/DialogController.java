package com.untildawn.controllers.InGameControllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DialogController {
    private static Map<String, Map<String, List<String>>> dialogData;
    private static String jsonFilePath;

    static {
        jsonFilePath = "src/main/java/org/example/Enums/NPCConsts/Dialogs.json";
        ObjectMapper mapper = new ObjectMapper();
        try {
            dialogData = mapper.readValue(new File(jsonFilePath),
                    new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getDialog(String NPCName, String situation) {
        Map<String, List<String>> characterDialog = dialogData.get(NPCName.toLowerCase());
        if (characterDialog == null) return "NPC not found.";

        List<String> dialogList = characterDialog.get(situation);
        if (dialogList == null || dialogList.isEmpty()) return "No dialog found for this situation.";

        return dialogList.get(new Random().nextInt(dialogList.size()));
    }
}
