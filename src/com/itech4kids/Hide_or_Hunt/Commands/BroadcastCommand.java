package com.itech4kids.Hide_or_Hunt.Commands;

import com.itech4kids.Hide_or_Hunt.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BroadcastCommand implements CommandExecutor {

    private Main main;

    public BroadcastCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage(main.prefix + ChatColor.RED + "Type in a message!");
        }else{
            String message = "";
            for(int i = 0; i < args.length; i++){
                String arg = args[i] + " ";
                message = message + arg;
            }
            Bukkit.broadcastMessage(main.prefix + message);
        }
        return false;
    }
}
