package com.itech4kids.Hide_or_Hunt;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getPlayerExact;

public class DeniedCommand implements CommandExecutor {

    private Main main;

    public DeniedCommand(Main main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        Player player = (Player) sender;
        Player partner = getPlayerExact(args[0]);

        ActivePlayer activePartnerPlayer = main.getActiveInstance(partner.getName());
        activePartnerPlayer.pendingTeamWith = null;


        player.sendMessage(main.prefix + ChatColor.RED + "You denied " + partner.getName() + "'s team request.");
        partner.sendMessage(main.prefix + ChatColor.RED + player.getName() + " denied your team request!");

        

        return false;
    }

}
