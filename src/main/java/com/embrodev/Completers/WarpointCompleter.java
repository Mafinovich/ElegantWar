package com.embrodev.Completers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class WarpointCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1) {
            return List.of("1", "2", "3", "4", "5", "6", "7", "8", "9");
        } else if (args.length == 2) {
            return List.of("attack", "defense");
        }
        return null;

    }
}
