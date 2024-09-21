package com.embrodev.Commands;

import java.util.HashMap;

public class TeamTactics {
    // Словрь хранения тактик группировок
    public static HashMap<String, String> tacticsDict = new HashMap<String, String>();

    // добавление тактики для команды
    public static void setTeamTactic(String team, String tactic) {
        tacticsDict.put(team, tactic);
    }

    // получение тактики команды
    public static String getTeamTactic(String team) {
        return tacticsDict.getOrDefault(team, "Тактика не установлена");
    }
}
