package com.itech4kids.Hide_or_Hunt;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import javax.swing.*;
import java.util.Map;

public class BeaconCommand implements CommandExecutor {

    private Main main;
    public BeaconCommand(Main main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        Player player = (Player) sender;
        ActivePlayer activePlayer = main.getActiveInstance(player.getName());
        if (activePlayer.team.beacon == null && activePlayer.team.placed == true){
            player.sendMessage(main.prefix + ChatColor.RED + "Your beacon was destroyed :(");
        }else{
            player.sendMessage(main.prefix + ChatColor.GREEN + "Your beacon is at: " + activePlayer.team.beacon.getLocation().getX() + " " + activePlayer.team.beacon.getLocation().getY() + " " + activePlayer.team.beacon.getLocation().getZ());
        }

        return false;
    }

}
