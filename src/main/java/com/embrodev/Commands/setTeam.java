package com.embrodev.Commands;

import com.embrodev.ElegantWar;
import com.embrodev.Managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.embrodev.Managers.ConfigManager.updateConfigWithTeams;

public class setTeam implements CommandExecutor {
    //Создание списков для хранения всех участников команды
    public static ArrayList<String> attack = new ArrayList<String>();
    public static ArrayList<String> defense = new ArrayList<String>();
    private Scoreboard scoreboard;

    private Player p;


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Проверка на игрока
        if (!(sender instanceof Player)) return false;

        p = (Player) sender;
        String args0 = args[0].toString();
        String args1 = args[1].toString();
        Player target = Bukkit.getPlayerExact(args0);
        scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        if (target == null) {
            //Проверка на валидность ника
            p.sendMessage("Данного игрока не существует");
            return false;
        }

        //Проверки на синтаксис
        if (args.length < 1) {
            p.sendMessage("Пожалуйста, укажите игрока и команду(attack, defense)");
            return false;
        } else if (args.length == 1) {
            p.sendMessage("Пожалуйста, укажите команду(attack, defense)");
            return false;
        } else if (!(args1.equals("attack") || args1.equals("defense"))) {
            p.sendMessage("Укажите команду в правильном формате!(attack, defense)");
            return false;
        }

        if (p.hasPermission("elegantwar.setteam")) {

            //Проверяем нет ли игрока в списках и проверяем тип команды
            if (args1.equals("attack") && !attack.contains(target) && !defense.contains(target)) {
                setPlayerTeam(target, "attack", ChatColor.RED, attack);

            //Проверяем нет ли игрока в списках и проверяем тип команды
            } else if (args1.equals("defense") && !attack.contains(target) && !defense.contains(target)) {
                setPlayerTeam(target, "defense", ChatColor.BLUE, defense);

            } else {
                p.sendMessage(ChatColor.RED + "Данный игрок уже состоит в команде!");
            }
        }

    return true;

    }
    private void setPlayerTeam (Player player, String teamName, ChatColor color, ArrayList<String> teamList){
        Team team = scoreboard.getTeam(teamName);
        if(team == null){
            team = scoreboard.registerNewTeam(teamName);
            team.setPrefix(color.toString());
            team.setAllowFriendlyFire(false);
        }

        for(Team t : scoreboard.getTeams()){
            t.removeEntry(player.getName());
        }

        teamList.add(player.getName());
        team.addEntry(player.getName());
        team.setColor(color);
        updateConfigWithTeams();
        Bukkit.broadcastMessage(ChatColor.GOLD + "Игрок " + player.getName() + " добавлен в команду " + teamName);

    }
}
