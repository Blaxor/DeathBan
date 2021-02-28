package ro.deiutzblaxo.deathban;

import org.bukkit.plugin.java.JavaPlugin;
import org.inventivetalent.bossbar.BossBarPlugin;
import ro.deiutzblaxo.deathban.commands.AdminCommands;
import ro.deiutzblaxo.deathban.player.PlayerJoin;
import ro.deiutzblaxo.deathban.player.PlayerManager;


import java.lang.reflect.Field;

public final class DeathBan extends JavaPlugin {


    private static DeathBan instance;
    private static PlayerManager playerManager;
    private static ConfigManager configManager;

    @Override
    public void onEnable() {
        instance =this;
        saveDefaultConfig();
        reloadConfig();
        configManager = new ConfigManager();
        playerManager = new PlayerManager();


        configManager.loadALLPlayer();
        getServer().getPluginManager().registerEvents(new PlayerJoin(),this);
        getCommand("deathban").setExecutor(new AdminCommands());

        try {
            Field field = BossBarPlugin.class.getDeclaredField("instance");
            field.setAccessible(true);
            field.set(null,this);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDisable() {

        PlayerManager.players.forEach((string , player) ->{

            getConfigManager().savePlayer(player);
        });
        
    }
    public static DeathBan getInstance(){
        return instance;
    }
    public static ConfigManager getConfigManager(){
        return configManager;
    }
    public static PlayerManager getPlayerManager(){
        return playerManager;
    }
}
