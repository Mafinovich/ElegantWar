package com.embrodev.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.embrodev.Commands.setCommander.attack_commander;
import static com.embrodev.Commands.setCommander.defense_commander;

public class listCommander implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;

        if(!(attack_commander == null) && !(defense_commander == null)) {
            p.sendMessage(ChatColor.GOLD + "Командир атаки: " + attack_commander + "\nКомандир обороны: " + defense_commander);
        } else{
            p.sendMessage(ChatColor.GOLD + "Командиры еще не назначены");
        }

        return true;
    }
}
