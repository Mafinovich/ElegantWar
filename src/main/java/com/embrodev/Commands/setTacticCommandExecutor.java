package com.embrodev.Commands;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class setTacticCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        // Проверка аргументов
        if (args.length < 2) {
            player.sendMessage("Пожалуйста, укажите тактику и команду (attack, defense).");
            return false;
        }

        String tactic = args[0];
        String team = args[1];

        // Вызов метода установки тактики
        setTactic.setTactic(player, tactic, team);
        return true;
    }
}
