package com.embrodev.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import static com.embrodev.Commands.Warpoint.war_dict;

public class RegenerationListener  implements Listener {
    String sumonnerUUID;
    Player sumonner;

    @EventHandler
    public void onRegainHealth(EntityRegainHealthEvent event){
        if(!(event.getEntity() instanceof Player)) return;
        if(!(event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED || event.getRegainReason() == EntityRegainHealthEvent.RegainReason.EATING)) return;

        sumonner = (Player) event.getEntity();
        sumonnerUUID = String.valueOf(sumonner.getUniqueId());

        if(war_dict.containsKey(sumonnerUUID)) {

            event.setCancelled(true);
        }
    }
}
