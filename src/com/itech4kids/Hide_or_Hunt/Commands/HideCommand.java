package com.itech4kids.Hide_or_Hunt.Commands;

import com.itech4kids.Hide_or_Hunt.ActivePlayer;
import com.itech4kids.Hide_or_Hunt.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HideCommand implements CommandExecutor {

    private Main main;
    public HideCommand(Main main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        Player player = (Player) sender;
        if (player.isOp()){
            ActivePlayer activePlayer = main.getActiveInstance(player.getName());
            main.vanish(player);
        }
        return false;
    }

}
