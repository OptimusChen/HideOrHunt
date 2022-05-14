package com.itech4kids.Hide_or_Hunt;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class BaseCommand implements CommandExecutor {

    private Main main;
    public BaseCommand(Main main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        Player player = (Player) sender;
        ActivePlayer activePlayer = main.getActiveInstance(player.getName());
        Team team = activePlayer.team;
        if (activePlayer.team.baseSet == true){
            player.sendMessage(main.prefix + ChatColor.RED + "You have already set your base!");
        }else{
            player.sendMessage(main.prefix + ChatColor.GREEN + "Base Set!");
            team.respawnLoc = player.getLocation();
            activePlayer.team.baseSet = true;
        }
        return false;
    }
}
