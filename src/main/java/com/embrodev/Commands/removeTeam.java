package com.embrodev.Commands;

import com.embrodev.ElegantWar;
import com.embrodev.Managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

//Импорт списков игроков с обеих команд
import static com.embrodev.Commands.setTeam.attack;
import static com.embrodev.Commands.setTeam.defense;
import static com.embrodev.Managers.ConfigManager.updateConfigWithTeams;

public class removeTeam implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ElegantWar plugin = (ElegantWar) Bukkit.getPluginManager().getPlugin("ElegantWar");
        ConfigManager configManager = plugin.getConfigManager();

        Player p = (Player) sender;
        Player target = Bukkit.getPlayerExact(args[0]);
        String targetName = target.getName();


        //Проверка на синтаксис
        if(p.hasPermission("elegantwar.removeteam")) {
            if(args.length < 1) {
                p.sendMessage("Укажите ник игрока или команду");
                return false;
            }
            if(args[0].equals("attack")){
                attack.clear();
                updateConfigWithTeams();

                p.sendMessage("Команда атакующих была удалена");
            } else if (args[0].equals("defense")) {
                defense.clear();
                updateConfigWithTeams();

                p.sendMessage("Команда защиты была удалена");
            } else {
                //Если игрок найден в списке атакующих удаляем его
                if (attack.contains(targetName)) {
                    attack.remove(targetName);
                    updateConfigWithTeams();

                    p.sendMessage("Игрок " + targetName + " удален из команды атакующих");
                    //Если игрок найден в списке защиты тоже удаляем
                } else if (defense.contains(targetName)) {
                    defense.remove(targetName);
                    updateConfigWithTeams();

                    p.sendMessage("Игрок " + targetName + " удален из команды защиты");

                    //Если игрока нет в списках выдаем ошибку
                } else {
                    p.sendMessage("Игрок не существует или не состоит в команде");
                    return false;
                }
            }

        }

        return true;
    }
}
