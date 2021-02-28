package ro.deiutzblaxo.deathban.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ro.deiutzblaxo.deathban.ConfigManager;
import ro.deiutzblaxo.deathban.DeathBan;

import java.util.Locale;

public class AdminCommands implements CommandExecutor {
    /**
     * Executes the given command, returning its success
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0){
            sendHelp(sender);
            return true;
        }
        String subcommand = args[0].toLowerCase(Locale.ROOT);
        switch(subcommand){
            case "setspawn":
                if(sender instanceof Player){
                    if(!sender.hasPermission("deathban.admin.setspawn")){
                        noPermission(sender);
                        return true;
                    }
                    Player player = (Player) sender;
                    DeathBan.getConfigManager().setUnbanLocation(new String[]{player.getWorld().getName(),
                            String.valueOf(player.getLocation().getX()),
                            String.valueOf(player.getLocation().getY()),
                            String.valueOf(player.getLocation().getZ())});
                sender.sendMessage(ChatColor.YELLOW+"The unban location has been setted!");

                }else{
                    sender.sendMessage(ChatColor.RED + "This command can be used just as a player!");
                }
                break;
            case "setban":
                if(sender instanceof Player){
                    if(!sender.hasPermission("deathban.admin.setban")){
                        noPermission(sender);
                        return true;
                    }
                    Player player = (Player) sender;
                    DeathBan.getConfigManager().setBanLocation(new String[]{player.getWorld().getName(),
                            String.valueOf(player.getLocation().getX()),
                            String.valueOf(player.getLocation().getY()),
                            String.valueOf(player.getLocation().getZ())});
                    sender.sendMessage(ChatColor.YELLOW+"The ban location has been setted!");

                }else{
                    sender.sendMessage(ChatColor.RED + "This command can be used just as a player!");
                }
                break;

            default:
                sendHelp(sender);
                break;
        }
        return false;
    }
    private void sendHelp(CommandSender sender){
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&eHelp admin commands DeathBan by JDeiutz"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&f/deathban setspawn - &eset where to spawn at unban"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&f/deathban setban - &eset where to spawn at ban"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&f/deathban unban <player> - &eunban an online player!"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&f/deathban ban <player> - &eban an online player!"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&f/deathban lives <set/get> <value> - &eset/get/reset the lives of a online player"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&f/deathban price <double> - &eset the price per life!"));
    }
    private void noPermission(CommandSender sender){
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',DeathBan.getInstance().getConfig().getString("noPermission")));
    }
    //permission setspawn -> deathban.admin.setspawn
    //permission setunban -> deathban.admin.setban
}
