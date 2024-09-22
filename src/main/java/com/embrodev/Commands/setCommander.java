package com.embrodev.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class setCommander implements CommandExecutor {
    public static Player attack_commander;
    public static Player defense_commander;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        Player target = Bukkit.getPlayerExact(args[0]);


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
            if(args[1].equals("attack")) {
                attack_commander = target;
                p.sendMessage("Игрок " + target + " назначен командиром атакующей стороны!");
                target.sendMessage("Вы назначены командиром атакующей стороны!");

            } else{
                defense_commander = target;
                p.sendMessage("Игрок " + target + "назначен командиром стороны защиты!");
                target.sendMessage("Вы назначены командиром стороны защиты!");
            }
        }


        return true;
    }
}
