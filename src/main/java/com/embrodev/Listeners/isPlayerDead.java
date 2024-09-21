package com.embrodev.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import static com.embrodev.Commands.Warpoint.*;

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
            sumonner.sendMessage(war_dict.get(sumonnerUUID) + " жизней осталось");

            //Если жизни закончились
            if (war_dict.get(sumonnerUUID) <= 0) {
                //Назначаем спавнпоинт по сохраненному значению перед установкой варпоинта
                sumonner.setRespawnLocation(spawnpoint_dict.get(sumonnerUUID), true);
                sumonner.sendMessage("Вы выбываете из битвы.");
                //Удаляем участника из команды
                war_dict.remove(sumonnerUUID);
            }
        }
    }
}