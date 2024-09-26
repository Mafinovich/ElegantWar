package com.embrodev.Commands;

import com.embrodev.ElegantWar;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if(args[0].equals("reload")){
            ElegantWar.getInstance().reloadConfig();
            p.sendMessage(ChatColor.AQUA + "Конфигурация плагина перезагружена!");
        } else if (args[0].equals("show-config")) {
            FileConfiguration config = ElegantWar.getInstance().getConfig();

            p.sendMessage("tactic-limit: " + config.getInt("tactic-limit") + "");
            p.sendMessage("rebirth-tactic-interval: " + config.getInt("rebirth-tactic-interval") + "");
            List<Integer> list = config.getIntegerList("spawn-after-loose");

            p.sendMessage("spawn-after-loose:");
            for(int i : list){
                p.sendMessage(i + "");
            }
        } else{
            p.sendMessage(ChatColor.DARK_RED + "Введите корректное значение!");
            return false;
        }
        return true;
    }
}
