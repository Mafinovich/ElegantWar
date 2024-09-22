package com.embrodev.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class setTeam implements CommandExecutor {
    //Создание списков для хранения всех участников команды
    public static ArrayList<Player> attack = new ArrayList<Player>();
    public static ArrayList<Player> defense = new ArrayList<Player>();

    private Player p;


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Проверка на игрока
        if(!(sender instanceof Player)) return false;

        p = (Player) sender;
        String args0 = args[0].toString();
        String args1 = args[1].toString();
        Player target = Bukkit.getPlayerExact(args0);

        if(target == null){
            //Проверка на валидность ника
            p.sendMessage("Данного игрока не существует");
            return false;
        }

        //Проверки на синтаксис
        if(args.length < 1){
            p.sendMessage("Пожалуйста, укажите игрока и команду(attack, defense)");
            return false;
        } else if (args.length == 1) {
            p.sendMessage("Пожалуйста, укажите команду(attack, defense)");
            return false;
        } else if (!(args1.equals("attack") || args1.equals("defense") )) {
            p.sendMessage("Укажите команду в правильном формате!(attack, defense)");
            return false;
        }

        if(p.hasPermission("elegantwar.setteam")) {

            //Проверяем нет ли игрока в списках и проверяем тип команды
            if (args1.equals("attack") && !attack.contains(target) && !defense.contains(target)) {
                attack.add(target);
                p.sendMessage("Игрок " + args0 + " добавлен в команду атакующих");
            //Проверяем нет ли игрока в списках и проверяем тип команды
            } else if (args1.equals("defense") && !attack.contains(target) && !defense.contains(target)) {
                defense.add(target);
                p.sendMessage("Игрок " + args0 + " добавлен в команду защиты");
            } else{
                p.sendMessage("Данный игрок уже состоит в команде!");
            }
        }



    return true;
}


}
