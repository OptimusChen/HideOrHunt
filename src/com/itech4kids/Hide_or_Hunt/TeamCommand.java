package com.itech4kids.Hide_or_Hunt;

import javafx.scene.CacheHint;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getPlayerExact;

public class TeamCommand implements CommandExecutor {

    private Main main;

    public TeamCommand(Main main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player = (Player) sender;
        ActivePlayer activePlayer = main.getActiveInstance(player.getName());

        main.unVanish(player);

        if(args.length==0){
            player.sendMessage(main.prefix + ChatColor.RED + "Type in a username!");
        }else{
            Player partner = getPlayerExact(args[0]);
            if (partner == null){
                player.sendMessage(main.prefix + ChatColor.RED + "Invalid player name!");
                return false;
            }

            ActivePlayer partnerPlayer = main.getActiveInstance(partner.getName());
            if (partnerPlayer.team != null){
                player.sendMessage(main.prefix + ChatColor.DARK_BLUE + "This player already has a team!");
                return false;
            }

            if (partnerPlayer.pendingTeamWith != null){
                if (partnerPlayer.pendingTeamWith == player){
                    Team team = new Team(player, partner);
                    activePlayer.team = team;
                    partnerPlayer.team = team;
                    main.AddTeam(team);
                    player.sendMessage(main.prefix + ChatColor.GREEN + "You are now in a team with " + partner.getName().toString() + "!");
                    partner.sendMessage(main.prefix + ChatColor.GREEN + "You are now in a team with " + player.getName().toString() + "!");
                    return true;
                }else{
                    player.sendMessage(main.prefix + ChatColor.RED + "Player already has a pending team request with someone else.");
                    return false;
                }
            }else{
                activePlayer.pendingTeamWith = partner;
                partner.sendMessage(main.prefix + ChatColor.GREEN + player.getName() + " send you a team request! Type /teamwith " + player.getName().toString() + " to accept it or type /deny " + player.getName().toString() + " to deny it.");

            }
        }

        return true;
    }

}
