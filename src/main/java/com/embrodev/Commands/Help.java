package com.embrodev.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Help implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;

        Player p = (Player) sender;

        if(args.length > 1){
            p.sendMessage(ChatColor.RED + "Данная команда не принимает аргументов");
            return false;
        }

        if(p.hasPermission("elegantwar.help")){
            p.sendMessage("Доступные команды:\n " +
                    "/warpoint [кол-во респавнов] [команда(attack, defense)] - ставит варпоинт команде\n " +
                    "/setteam[или steam] [игрок] [команда] - добавляет игрока в команду\n " +
                    "/listteam[или lteam] [команда] - выводит список участников команды\n " +
                    "/removeteam[или rteam] [команда или игрок] - удаляет игрока или всю команду\n " +
                    "/settactic[или stac] [тактика] - выводит список тактик \n " +
                    "/setcommander[или scom] [игрок] [команда] - назначает игрока командиром команды\n " +
                    "/listcommander[или lcom] - выводит список командиров\n +" +
                    "Доступные тактики:\n " +
                    "blitzkrieg - Скорость 2\n " +
                    "offensive - Сила 1, Скорость 1\n " +
                    "defense - Сопротивление 2\n " +
                    "operational_interaction - Скорость 1, Сопротивление 1");
        }

        return true;
    }
}
