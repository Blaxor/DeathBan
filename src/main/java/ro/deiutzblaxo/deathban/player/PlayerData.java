package ro.deiutzblaxo.deathban.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.inventivetalent.bossbar.BossBar;
import org.inventivetalent.bossbar.BossBarAPI;
import ro.deiutzblaxo.deathban.DeathBan;

import java.util.ArrayList;

public class PlayerData {

    public long maxSeconds;
    public long secondNow;
    public long decreseeRemain;
    public String playerUUID;
    public boolean banned;
    public boolean waitingTP;
    public int taskId;
    public int remainTaskId;
    public Player player;

    public PlayerData(String playerUUID , long maxSeconds , long secondNow,long decreseeRemain , boolean banned,boolean waitingTP){
    this.playerUUID = playerUUID;
    this.maxSeconds = maxSeconds;
    this.secondNow = secondNow;
    this.decreseeRemain = decreseeRemain;
    this.banned= banned;
    this.waitingTP = waitingTP;
    startRemainCounter();
    if(secondNow > 0)
        startBanCounter();
    }
    public void startRemainCounter(){
        BukkitTask task = Bukkit.getScheduler().runTaskTimer(DeathBan.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(maxSeconds <= 120)
                    return;
                if(decreseeRemain == 0){
                    decreseeRemain = 60*60*2;
                    maxSeconds-= 60;
                }
                decreseeRemain -=1;

            }
        },20,20);
        remainTaskId = task.getTaskId();
    }

    public void startBanCounter(){

    if(!banned) {
     secondNow = 0;
        return;
    }
    if(secondNow <= 0) {
        secondNow = maxSeconds;
    }


        BukkitTask task = Bukkit.getScheduler().runTaskTimer(DeathBan.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (secondNow <= 0) {
                    if(player != null) {
                        BossBarAPI.removeAllBars(player);
                        player.teleport(DeathBan.getConfigManager().getUnbanLocation());
                        waitingTP = false;
                    }else
                        waitingTP = true;

                    banned = false;
                    Bukkit.getScheduler().cancelTask(taskId);
                    return;

                }
                secondNow -= 1;
                System.out.println(secondNow);
                if(player != null) {
                    BossBarAPI.removeAllBars(player);
                    BossBarAPI.setMessage(player, secondNow + "", secondNow * 100 / maxSeconds, 2);
                }


            }
        }, 20, 20);
        taskId =task.getTaskId();



    }
}
