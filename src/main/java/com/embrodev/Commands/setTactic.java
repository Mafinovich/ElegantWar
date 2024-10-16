package com.embrodev.Commands;


import com.embrodev.ElegantWar;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

import static com.embrodev.Commands.TeamTactics.setTeamTactic;
import static com.embrodev.Commands.setCommander.attack_commander;
import static com.embrodev.Commands.setCommander.defense_commander;
import static com.embrodev.Commands.setTeam.attack;
import static com.embrodev.Commands.setTeam.defense;

public class setTactic implements CommandExecutor {
    //Подтягиваем конфиг
    private static FileConfiguration config = ElegantWar.getInstance().getConfig();

    //количество использований тактики для каждого правителя
    private static HashMap<Player, Integer> tacticUseCount = new HashMap<>();

    // Максимальное количество тактик
    private static final int MAX_TACTICS = config.getInt("tactic-limit");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        String tactic = args[0];

        // Получаем количество использований этого правителя
        int currentCount = tacticUseCount.getOrDefault(player, 0);

        // Проверка аргументов
        if (args.length < 1) {
            player.sendMessage(ChatColor.DARK_RED + "Пожалуйста, укажите тактику");
            player.sendMessage("Список существующих тактик: \n blitzkrieg - Скорость II \n defense - Сопротивление II \n offensive - Сила I, Скорость I \n operational_interaction - Скорость I, Сопротивление I");
            return false;
        }
        if (player.getName().equals(attack_commander)) {
            if (currentCount >= MAX_TACTICS) {
                player.sendMessage(ChatColor.DARK_RED + "Вы достигли максимального лимита выбора тактики (" + MAX_TACTICS + ")!");
            } else {
                // Вызов метода установки тактики
                for (String targetName : attack) {
                    OfflinePlayer targetOffline = Bukkit.getOfflinePlayer(targetName);
                    if(targetOffline.isOnline()) {
                        Player target = Bukkit.getPlayer(targetOffline.getName());
                        setTactic(target, tactic);
                        setTeamTactic("attack", tactic);
                    }
                }
            }
        } else if (player.getName().equals(defense_commander)) {
            if (currentCount >= MAX_TACTICS) {
                player.sendMessage(ChatColor.DARK_RED + "Вы достигли максимального лимита выбора тактики (" + MAX_TACTICS + ")!");
            } else {
                for (String targetName : defense) {
                    OfflinePlayer targetOffline = Bukkit.getOfflinePlayer(targetName);
                    if(targetOffline.isOnline()) {
                        Player target = Bukkit.getPlayer(targetOffline.getName());
                        setTactic(target, tactic);
                        setTeamTactic("defense", tactic);
                    }
                }
            }
        } else {
            player.sendMessage(ChatColor.DARK_RED + "Вы не являетесь командиром!");
            return false;
        }
        //счётчик использований + 1
        tacticUseCount.put(player, currentCount + 1);

        return true;
    }

    // Метод для установки тактики
    public static void setTactic(Player player, String tactic) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
            switch (tactic) {
            case "blitzkrieg":
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600000, 1)); // Скорость II
                player.sendMessage("Тактика blitzkrieg установлена!");
                break;
            case "defense":
                player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 600000, 1)); // Сопротивление II
                player.sendMessage("Тактика defense установлена!");
                break;
            case "offensive":
                player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 600000, 0)); // Сила I
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600000, 0)); // Скорость I
                player.sendMessage("Тактика offensive установлена!");
                break;
            case "operational_interaction":
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600000, 0)); // Скорость I
                player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 600000, 0)); // Сопротивление I
                player.sendMessage("Тактика operational_interaction установлена!");
                break;
            default:
                player.sendMessage(ChatColor.DARK_RED + "Неверная тактика!");
                player.sendMessage("Список существующих тактик: \n blitzkrieg - Скорость II \n defense - Сопротивление II \n offensive - Сила I, Скорость I \n operational_interaction - Скорость I, Сопротивление I");
                break;
        }

    }

}
