package com.itech4kids.Hide_or_Hunt.Commands;

import com.itech4kids.Hide_or_Hunt.Main;
import com.itech4kids.Hide_or_Hunt.Team;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class StatsCommand implements CommandExecutor {

    private Main main;

    public StatsCommand(Main main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        Player player = (Player) sender;
        Team playerTeam = main.getActiveInstance(player.getName()).team;

        player.sendMessage(main.prefix + ChatColor.GOLD + "Teams Alive: " + ChatColor.GREEN + main.teams.size());
        player.sendMessage(main.prefix + ChatColor.GOLD + "Beacons Alive: " + ChatColor.GREEN + main.beacons.size());
        if (playerTeam.beacon != null){
         player.sendMessage(main.prefix + ChatColor.AQUA + "You beacon is still standing!");

        }else if(playerTeam.placed){

            player.sendMessage(main.prefix + ChatColor.RED + "Your beacon was destroyed!");

        }else{

            player.sendMessage(main.prefix + ChatColor.BLUE + "Place your beacon in your base!");

        }

        return false;
    }

}

