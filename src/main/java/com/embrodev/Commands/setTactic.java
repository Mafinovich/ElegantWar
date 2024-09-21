package com.embrodev.Commands;


import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static com.embrodev.Commands.setCommander.attack_commander;
import static com.embrodev.Commands.setCommander.defense_commander;
import static com.embrodev.Commands.setTeam.attack;
import static com.embrodev.Commands.setTeam.defense;

public class setTactic implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        String tactic = args[0];
        String cmdName = command.getName().toLowerCase();

        if(cmdName.equals("settactic")) {
            // Проверка аргументов
            if (args.length < 1) {
                player.sendMessage("Пожалуйста, укажите тактику");
                player.sendMessage("Список существующих тактик: \n blitzkrieg - Скорость II \n defense - Сопротивление II \n offensive - Сила I, Скорость I \n operational_interaction - Скорость I, Сопротивление I");
                return false;
            }
            if(player == attack_commander) {
                // Вызов метода установки тактики
                for(Player target : attack) {
                    setTactic(target, tactic);
                    player.sendMessage("Тактика " + tactic + " установлена!");
                }
            } else if (player == defense_commander) {
                for(Player target : defense){
                    setTactic(target, tactic);
                }
            } else{
                player.sendMessage("Вы не являетесь командиром!");
                return false;
            }
        }
        return true;
    }

    // Метод для установки тактики
    public static void setTactic(Player player, String tactic) {
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
                player.sendMessage("Неверная тактика!");
                player.sendMessage("Список существующих тактик: \n blitzkrieg - Скорость II \n defense - Сопротивление II \n offensive - Сила I, Скорость I \n operational_interaction - Скорость I, Сопротивление I");
                break;
        }

    }

}
