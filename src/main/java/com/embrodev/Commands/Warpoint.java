package com.embrodev.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

//Импорт списков игроков с обеих команд
import static com.embrodev.Commands.setTeam.attack;
import static com.embrodev.Commands.setTeam.defense;

public class Warpoint implements CommandExecutor {

    Player p;
    String playerUUID;
    String playerName;
    //Создание двух словарей для хранения количества жизней и прошлого спавнопоинта по ключу UUID
    public static HashMap<String, Integer> war_dict = new HashMap<String, Integer>();
    public static HashMap<String, Location> spawnpoint_dict = new HashMap<String, Location>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Проверка на игрока
        if(!(sender instanceof Player)) return false;

        String count_dead = args[0];
        p = (Player) sender;
        playerUUID = p.getUniqueId().toString();
        playerName = p.getName();
        String args1 = args[1].toString();

        int CountDeadValue = Integer.parseInt(count_dead);
        Location loc = p.getLocation();


        //Проверки на синтаксис
        if (args.length < 1){
            p.sendMessage(ChatColor.DARK_RED + "Укажите количество респавнов и команду.");
            return false;
        } else if (args.length == 1) {
            p.sendMessage(ChatColor.DARK_RED + "Укажите команду(attack, defense)");
            return false;
        } else if (!(args1.equals("attack") || args1.equals("defense") )) {
            p.sendMessage(ChatColor.DARK_RED + "Укажите команду в правильном формате!(attack, defense)");
            return false;
        }
        //Назначение варпоинта для атаки
        if(args1.equals("attack")){
            //Цикл для перебора участников стороны атаки
            for(String targetName : attack){
                Player target = Bukkit.getPlayer(targetName);
                //Проверка онлайн ли игрок
                if(target.isOnline()) {
                    if(p.hasPermission("elegantwar.warpoint")) {
                        //Провевка на минимальное количество жизней
                        if(CountDeadValue >= 2) {
                            //Запись значений в словари для дальнейшей работы в классе isPlayerDead
                            spawnpoint_dict.put(target.getUniqueId().toString(), target.getRespawnLocation());
                            war_dict.put(target.getUniqueId().toString(), CountDeadValue);
                            //Телепорт всех участников команды на варпоинт
                            target.teleport(p.getLocation());
                            //Замена точки спавна всем участнкиам команды
                            target.setRespawnLocation(loc, true);
                            target.sendMessage(ChatColor.AQUA + "Варпоинт команды атакуюших установлен!");
                        } else{
                            p.sendMessage(ChatColor.DARK_RED + "Введите количество респавнов больше двух");
                        }
                    }
                } else{
                    //Если игрок не в сети удаляем из списка участников команды
                    attack.remove(target);
                    p.sendMessage(ChatColor.GREEN + "Участник " + target.getName() + " был удален из команды атакующих, поскольку не в сети");
                }
            }
          //Назначение варпоинта для защиты
        } else if (args1.equals("defense")) {
            //Цикл для перебора участников стороны защиты
            for (String targetName : defense) {
                OfflinePlayer targetOffline = Bukkit.getOfflinePlayer(targetName);
                //Проверка онлайн ли игрок
                if(targetOffline.isOnline()) {
                    Player target = Bukkit.getPlayer(targetOffline.getName());
                    if(p.hasPermission("nnna.warpoint")) {
                        //Проверка на минимальное количество жизней
                        if(CountDeadValue >= 2) {
                            //Запись значений в словари для дальнейшей работы в классе isPlayerDead
                            spawnpoint_dict.put(target.getUniqueId().toString(), target.getRespawnLocation());
                            war_dict.put(target.getUniqueId().toString(), CountDeadValue);
                            //Телепорт всех участников команды на варпоинт
                            target.teleport(p.getLocation());
                            //Замена точки спавна всем участнкиам команды
                            target.setRespawnLocation(loc, true);
                            target.sendMessage(ChatColor.AQUA + "Варпоинт команды защитников установлен!");
                        } else{
                            p.sendMessage(ChatColor.DARK_RED + "Введите количество респавнов больше двух");
                        }
                    }
                } else{
                    //Если игрок не в сети удаляем из списка участников команды
                    defense.remove(targetOffline.getName());
                    p.sendMessage(ChatColor.GREEN + "Участник " + targetName + " был удален из команды атакующих, поскольку не в сети");
                }
            }
        }


        return true;
    }


}
