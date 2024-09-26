package com.embrodev.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.embrodev.Commands.setTeam.attack;
import static com.embrodev.Commands.setTeam.defense;

public class setCommander implements CommandExecutor {
    public static String attack_commander;
    public static String defense_commander;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);


        if(args.length < 1){
            p.sendMessage("Укажите ник командира и команду(attack, defense)");
            return false;
        } else if (args.length == 1) {
            p.sendMessage("Укажите команду(attack, defense)");
            return false;
        } else if (!(args[1].equals("attack") || args[1].equals("defense") )) {
            p.sendMessage("Укажите команду в правильном формате!(attack, defense)");
            return false;
        } else if (target == null) {
            p.sendMessage("Игрок не обнаружен");
            return false;
        }

        if(p.hasPermission("elegantwar.setcommander")){
            if(args[1].equals("attack") && attack.contains(target.getName())) {
                attack_commander = target.getName();
                p.sendMessage(ChatColor.GOLD + target.getName() + " назначен командиром атакующей стороны!");

            } else if(args[1].equals("defense") && defense.contains(target.getName())){
                defense_commander = target.getName();
                p.sendMessage(ChatColor.GOLD + "Игрок " + target.getName() + " назначен командиром стороны защиты!");
            } else{
                p.sendMessage(ChatColor.RED +"Неверная команда или игрок не состоит в данной команде!");
            }
        }


        return true;
    }
}
