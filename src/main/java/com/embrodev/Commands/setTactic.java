package com.embrodev.Commands;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class setTactic {
    // Глобальные переменные для командующих
    private static Player attack_commander;
    private static Player defense_commander;

    // Метод для установки тактики
    public static void setTactic(Player player, String tactic, String team) {
        switch (tactic) {
            case "Блицкриг":
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600000, 1)); // Скорость II
                break;
            case "Глубокая_оборона":
                player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 600000, 1)); // Сопротивление II
                break;
            case "Активное_наступление":
                player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 600000, 0)); // Сила I
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600000, 0)); // Скорость I
                break;
            case "Оперативное_взаимодействие":
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600000, 0)); // Скорость I
                player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 600000, 0)); // Сопротивление I
                break;
            default:
                player.sendMessage(ChatColor.RED + "Неизвестная тактика: " + tactic);
        }

        // Назначаем командующего в зависимости от команды
        if (team.equalsIgnoreCase("attack")) {
            attack_commander = player;
            player.sendMessage("Вы назначены командующим команды атаки!");
        } else if (team.equalsIgnoreCase("defense")) {
            defense_commander = player;
            player.sendMessage("Вы назначены командующим команды защиты!");
        } else {
            player.sendMessage(ChatColor.RED + "Неизвестная команда: " + team);
        }
    }

    // Методы для получения командующих
    public static Player getAttackCommander() {
        return attack_commander;
    }

    public static Player getDefenseCommander() {
        return defense_commander;
    }
}
