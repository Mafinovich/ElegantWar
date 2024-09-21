package com.embrodev.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

//Импорт списков игроков с обеих команд
import static com.embrodev.Commands.setTeam.attack;
import static com.embrodev.Commands.setTeam.defense;

public class removeTeam implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmdName = command.getName().toLowerCase();
        Player p = (Player) sender;
        Player target = Bukkit.getPlayerExact(args[0]);


        if(cmdName.equals("removeteam")){
            //Проверка на синтаксис
            if(args.length < 1) {
                p.sendMessage("Укажите ник игрока");
                return false;
            }
            if(p.hasPermission("elegantwar.removeteam")) {
                //Если игрок найден в списке атакующих удаляем его
                if (attack.contains(target)) {
                    attack.remove(target);
                    p.sendMessage("Игрок " + target.getName() + " удален из команды атакующих");
                //Если игрок найден в списке защиты тоже удаляем
                } else if (defense.contains(target)) {
                    defense.remove(target);
                    p.sendMessage("Игрок " + target.getName() + " удален из команды защиты");

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
