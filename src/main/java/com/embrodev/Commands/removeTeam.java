package com.embrodev.Commands;

import com.embrodev.ElegantWar;
import com.embrodev.Managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

//Импорт списков игроков с обеих команд
import java.util.ArrayList;

import static com.embrodev.Commands.setTeam.attack;
import static com.embrodev.Commands.setTeam.defense;
import static com.embrodev.Managers.ConfigManager.updateConfigWithTeams;

public class removeTeam implements CommandExecutor {
    private Scoreboard scoreboard;
    private OfflinePlayer target;
    private String targetName;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ElegantWar plugin = (ElegantWar) Bukkit.getPluginManager().getPlugin("ElegantWar");
        ConfigManager configManager = plugin.getConfigManager();

        Player p = (Player) sender;
        scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        //Проверка на синтаксис
        if(p.hasPermission("elegantwar.removeteam")) {
            if(args.length < 1) {
                p.sendMessage(ChatColor.DARK_RED + "Укажите ник игрока или команду");
                return false;
            }
            if(args[0].equals("attack")){
                removePlayerTeam("attack", attack, true);

                p.sendMessage(ChatColor.GREEN + "Команда атакующих была удалена");
            } else if (args[0].equals("defense")) {
                removePlayerTeam("defense", defense, true);

                p.sendMessage(ChatColor.GREEN + "Команда защиты была удалена");
            } else {
                target = Bukkit.getOfflinePlayer(args[0]);
                String targetName = target.getName();

                //Проверка на null
                if (target == null || !target.hasPlayedBefore()) {
                    p.sendMessage(ChatColor.DARK_RED + "Игрок не найден или никогда не заходил на сервер.");
                    return false;
                }

                //Если игрок найден в списке атакующих удаляем его
                if (attack.contains(targetName)) {
                    removePlayerTeam("attack", attack, false);

                    p.sendMessage(ChatColor.GREEN + "Игрок " + targetName + " удален из команды атакующих");
                    //Если игрок найден в списке защиты тоже удаляем
                } else if (defense.contains(targetName)) {
                    removePlayerTeam("defense", defense, false);

                    p.sendMessage(ChatColor.GREEN + "Игрок " + targetName + " удален из команды защиты");


                    //Если игрока нет в списках выдаем ошибку
                } else {
                    p.sendMessage(ChatColor.DARK_RED +  "Игрок не существует или не состоит в команде");
                    return false;
                }
            }

        }

        return true;
    }
    private void removePlayerTeam(String teamName, ArrayList<String> teamList, boolean AllTeam){
        Team team = scoreboard.getTeam(teamName);

        if(team == null){
            team = scoreboard.registerNewTeam(teamName);
            team.setAllowFriendlyFire(false);
        }

        if(AllTeam){
            for(String targetName : teamList){
                team.removeEntry(targetName);
            }
            teamList.clear();
        } else{
            team.removeEntry(targetName);
            teamList.remove(targetName);
        }
        updateConfigWithTeams();
    }
}
