package com.embrodev.Managers;

import com.embrodev.Commands.setCommander;
import com.embrodev.ElegantWar;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static com.embrodev.Commands.setTeam.attack;
import static com.embrodev.Commands.setTeam.defense;


public class ConfigManager {

    private File warCacheFile;
    private FileConfiguration warCache;
    private ElegantWar plugin;
    private final String patch = "/plugins/ElegantWar/";

    public ConfigManager(ElegantWar plugin) {
        this.plugin = plugin;
        createWarCache();
    }

    public void createWarCache() {
        warCacheFile = new File(plugin.getDataFolder(), "warCache.yml");
        if (!warCacheFile.exists()) {
            plugin.saveResource("warCache.yml", false);
        }
        warCache = YamlConfiguration.loadConfiguration(warCacheFile);
    }

    public FileConfiguration getCustomConfig() {
        return this.warCache;
    }

    public void saveCustomConfig() {
        try {
            warCache.save(warCacheFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Не удалось сохранить конфиг!", e);
        }
    }

    public void saveTeamsToConfig(ConfigManager configManager) {
        // Сохранение командиров
        String attackCommanderName = setCommander.attack_commander != null ? setCommander.attack_commander : null;
        String defenseCommanderName = setCommander.defense_commander != null ? setCommander.defense_commander : null;

        // Сохраняем в конфиг
        configManager.getCustomConfig().set("teams.attack", attack);
        configManager.getCustomConfig().set("teams.defense", defense);
        configManager.getCustomConfig().set("commanders.attack", attackCommanderName);
        configManager.getCustomConfig().set("commanders.defense", defenseCommanderName);

        // Сохраняем файл
        configManager.saveCustomConfig();
    }
    public void loadTeamsFromConfig(ConfigManager configManager) {
        // Чтение списка атакующих
        List<String> attackNames = configManager.getCustomConfig().getStringList("teams.attack");
        for(String p : attackNames) {
            attack.add(p);
        }
        /*attack = attackNames.stream()
                .map(Bukkit::getPlayer)
                .collect(Collectors.toCollection(ArrayList::new));*/

        // Чтение списка защитников
        List<String> defenseNames = configManager.getCustomConfig().getStringList("teams.defense");
        for(String p : defenseNames){
            defense.add(p);
        }
        /*defense = defenseNames.stream()
                .map(Bukkit::getPlayer)
                .collect(Collectors.toCollection(ArrayList::new));*/

        // Чтение командиров
        String attackCommanderName = configManager.getCustomConfig().getString("commanders.attack");
        String defenseCommanderName = configManager.getCustomConfig().getString("commanders.defense");

        if (attackCommanderName != null) {
            setCommander.attack_commander = attackCommanderName;
        }

        if (defenseCommanderName != null) {
            setCommander.defense_commander = defenseCommanderName;
        }
    }

    public void removeTeamsFromConfig(ConfigManager configManager) {
        // Удаляем все записи о командах и командирах
        configManager.getCustomConfig().set("teams.attack", null);
        configManager.getCustomConfig().set("teams.defense", null);
        configManager.getCustomConfig().set("commanders.attack", null);
        configManager.getCustomConfig().set("commanders.defense", null);

        // Сохраняем изменения
        configManager.saveCustomConfig();
    }

    // Метод для обновления конфигурационного файла
    public static void updateConfigWithTeams() {
        // Получаем доступ к вашему плагину
        ElegantWar plugin = (ElegantWar) Bukkit.getPluginManager().getPlugin("ElegantWar");
        ConfigManager configManager = plugin.getConfigManager();

        // Сохраняем текущие списки команд в конфиг
        configManager.getCustomConfig().set("teams.attack", attack);
        configManager.getCustomConfig().set("teams.defense", defense);

        // Сохраняем изменения
        configManager.saveCustomConfig();
    }
}