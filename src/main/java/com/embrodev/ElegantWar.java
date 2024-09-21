package com.embrodev;

import com.embrodev.Commands.*;
import com.embrodev.Listeners.isPlayerDead;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class ElegantWar extends JavaPlugin {

    @Override
    public void onEnable() {
        Objects.requireNonNull(this.getCommand("warpoint")).setExecutor(new Warpoint());
        Objects.requireNonNull(this.getCommand("setteam")).setExecutor(new setTeam());
        Objects.requireNonNull(this.getCommand("listteam")).setExecutor(new listTeam());
        Objects.requireNonNull(this.getCommand("removeteam")).setExecutor(new removeTeam());
        Objects.requireNonNull(this.getCommand("settactic")).setExecutor(new setTactic());
        Objects.requireNonNull(this.getCommand("setcommander")).setExecutor(new setCommander());
        Objects.requireNonNull(this.getCommand("listcommander")).setExecutor(new listCommander());

        getServer().getPluginManager().registerEvents(new isPlayerDead(), this);

    }
}
