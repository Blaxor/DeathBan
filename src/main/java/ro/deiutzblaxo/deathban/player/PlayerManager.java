package ro.deiutzblaxo.deathban.player;

import org.bukkit.entity.Player;
import ro.deiutzblaxo.deathban.DeathBan;

import java.util.HashMap;

public class PlayerManager {
    public static HashMap<String,PlayerData> players = new HashMap<>();

    public void loadPlayer(String uuid){
        players.put(uuid, DeathBan.getConfigManager().getPlayer(uuid));
    }
    public void banPlayer(Player players){
        PlayerData player = getPlayer(players.getUniqueId().toString());
        player.banned = true;
        if(player.maxSeconds < 60*6){
            player.maxSeconds += 60;
            player.decreseeRemain = 60*60*2;
        }
        DeathBan.getConfigManager().savePlayer(player);
        player.startBanCounter();



    }
    public PlayerData getPlayer(String uuid){
        if(!players.containsKey(uuid))
            DeathBan.getPlayerManager().loadPlayer(uuid);
        return  players.get(uuid);
    }
    public void unloadPlayer(PlayerData player){
        DeathBan.getConfigManager().savePlayer(player);
        players.remove(player);
    }

}
