package com.embrodev.Listeners;

import com.embrodev.Commands.TeamTactics;
import com.embrodev.ElegantWar;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import static com.embrodev.Commands.Warpoint.*;
import static com.embrodev.Commands.setTactic.setTactic;
import static com.embrodev.Commands.setTeam.attack;
import static com.embrodev.Commands.setTeam.defense;

public class isPlayerDead implements Listener {
    String sumonnerUUID;
    Player sumonner;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        sumonnerUUID = event.getEntity().getUniqueId().toString();
        sumonner = event.getEntity();
        //Фикс двух сообщений о смерти
        event.setDeathMessage(null);

        //Если такой UUID есть в словаре war_dict
        if (war_dict.containsKey(sumonnerUUID)) {
            //Замена количества жизней по ключу UUID на -1
            war_dict.replace(sumonnerUUID, war_dict.get(sumonnerUUID) - 1);
            //хз добавил для удобства
            if (war_dict.get(sumonnerUUID) > 0){
                Bukkit.broadcastMessage(ChatColor.DARK_AQUA +"У "+ sumonner.getName() + " осталось "+war_dict.get(sumonnerUUID) + " жизней");
            }

            //Если жизни закончились
            if (war_dict.get(sumonnerUUID) <= 0) {
                //Назначаем спавнпоинт по сохраненному значению перед установкой варпоинта
                sumonner.setRespawnLocation(spawnpoint_dict.get(sumonnerUUID), true);
                Bukkit.broadcastMessage(ChatColor.GOLD + sumonner.getName() + " выбывает из команды и покидает битву!");
                //Удаляем участника из команды
                war_dict.remove(sumonnerUUID);
            }
        }
    }
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event){
        sumonnerUUID = event.getPlayer().getUniqueId().toString();
        sumonner = event.getPlayer();
        // Запускаем отложенное выполнение через 2 секунды (40 тиков)
        Bukkit.getScheduler().runTaskLater(ElegantWar.getInstance(), () -> {
            if (war_dict.containsKey(sumonnerUUID)) {
                if (attack.contains(sumonner)) {
                    String tactic = TeamTactics.getTeamTactic("attack");
                    setTactic(sumonner, tactic);
                } else if (defense.contains(sumonner)) {
                    String tactic = TeamTactics.getTeamTactic("defense");
                    setTactic(sumonner, tactic);
                }
            }
        }, 40L); // 40 тиков = 2 секунды
    }
}