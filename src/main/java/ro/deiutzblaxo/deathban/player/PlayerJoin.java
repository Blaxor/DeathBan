package ro.deiutzblaxo.deathban.player;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.inventivetalent.bossbar.BossBarAPI;
import ro.deiutzblaxo.deathban.DeathBan;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){

            PlayerData player =  DeathBan.getPlayerManager().getPlayer(event.getPlayer().getUniqueId().toString());
            player.player = event.getPlayer();
            if(player.waitingTP){
                event.getPlayer().teleport(DeathBan.getConfigManager().getUnbanLocation());
                player.waitingTP = false;
            }
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        PlayerData player =  DeathBan.getPlayerManager().getPlayer(event.getPlayer().getUniqueId().toString());
        player.player = null;
        BossBarAPI.removeAllBars(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDeath(PlayerDeathEvent event){
        event.getEntity().spigot().respawn();
        event.getEntity().teleport(DeathBan.getConfigManager().getBanLocation());
        DeathBan.getPlayerManager().banPlayer(event.getEntity());
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onRespawn(PlayerRespawnEvent event){
        System.out.println(DeathBan.getPlayerManager().getPlayer(event.getPlayer().getUniqueId().toString()).banned);
        if(DeathBan.getPlayerManager().getPlayer(event.getPlayer().getUniqueId().toString()).banned){
            Bukkit.getScheduler().runTaskLater(DeathBan.getInstance(), new Runnable() {
                @Override
                public void run() {
                    event.getPlayer().teleport(DeathBan.getConfigManager().getBanLocation());
                }
            },2);

        }
    }
}
