package ro.deiutzblaxo.deathban;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import ro.deiutzblaxo.deathban.player.PlayerData;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    FileConfiguration playerData;
    File playerDataFile;

    public ConfigManager(){
        playerDataFile = new File(DeathBan.getInstance().getDataFolder(),"data.yml");
        if(!playerDataFile.exists()){
            try {
                playerDataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        playerData = YamlConfiguration.loadConfiguration(playerDataFile);
    }
    public FileConfiguration loadData(){
       playerData = YamlConfiguration.loadConfiguration(playerDataFile);
        return playerData;
    }
    public File fileData(){
        return playerDataFile;
    }
    public PlayerData getPlayer(String uuid){
        loadData();
        PlayerData playerData;
        if(this.playerData.contains(uuid)){
            playerData = new PlayerData(uuid,this.playerData.getLong(uuid+".maxSeconds"),
                    this.playerData.getLong(uuid+".secondNow"),this.playerData.getLong(uuid+".decreseRemain"),
                    this.playerData.getBoolean(uuid+".banned"),this.playerData.getBoolean(uuid+".waitingTP"));
        }else{
            playerData = new PlayerData(uuid,2*60,0,0,false, false);
        }
        return playerData;
    }
    public void savePlayer(PlayerData player){
        loadData();
        playerData.set(player.playerUUID+".maxSeconds" , player.maxSeconds);
        playerData.set(player.playerUUID+".secondNow" , player.secondNow);
        playerData.set(player.playerUUID+".decreseRemain" , player.decreseeRemain);
        playerData.set(player.playerUUID+".banned" , player.banned);
        playerData.set(player.playerUUID+".waitingTP" , player.waitingTP);
        try {
            playerData.save(playerDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadALLPlayer(){
        loadData();
        for (String key : playerData.getKeys(true)){
            DeathBan.getPlayerManager().loadPlayer(key);
        }
    }
    public Location getBanLocation(){
        String[] args = DeathBan.getInstance().getConfig().getString("ban-teleport").split(",");
        return getLocation(args);
    }
    public Location getUnbanLocation(){
        String[] args = DeathBan.getInstance().getConfig().getString("unban-teleport").split(",");
        return getLocation(args);
    }
    public void setBanLocation(String[] args){
        DeathBan.getInstance().getConfig().set("ban-teleport",args[0]+","+args[1]+","+args[2]+","+args[3]);
        DeathBan.getInstance().saveConfig();

    }
    public void setUnbanLocation(String[] args){
        DeathBan.getInstance().getConfig().set("unban-teleport",args[0]+","+args[1]+","+args[2]+","+args[3]);
        DeathBan.getInstance().saveConfig();

    }

    private Location getLocation(String[] args) {
        World world = Bukkit.getWorld(args[0]);
        double x = Double.parseDouble(args[1]);
        double y = Double.parseDouble(args[2]);
        double z = Double.parseDouble(args[3]);

        return new Location(world,x,y,z);
    }

}
