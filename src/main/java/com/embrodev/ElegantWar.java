package com.embrodev;

import com.embrodev.Commands.*;
import com.embrodev.Completers.setTacticCompleter;
import com.embrodev.Listeners.isPlayerDead;
import org.bukkit.plugin.java.JavaPlugin;

public final class ElegantWar extends JavaPlugin {

    @Override
    public void onEnable() {
        //Executors
        this.getCommand("warpoint").setExecutor(new Warpoint());
        this.getCommand("setteam").setExecutor(new setTeam());
        this.getCommand("listteam").setExecutor(new listTeam());
        this.getCommand("removeteam").setExecutor(new removeTeam());
        this.getCommand("settactic").setExecutor(new setTactic());
        this.getCommand("setcommander").setExecutor(new setCommander());
        this.getCommand("listcommander").setExecutor(new listCommander());

        //TabCompleters
        this.getCommand("settactic").setTabCompleter(new setTacticCompleter());

        //Events
        getServer().getPluginManager().registerEvents(new isPlayerDead(), this);

    }
}
