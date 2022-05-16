package com.itech4kids.Hide_or_Hunt.Commands;

import com.itech4kids.Hide_or_Hunt.ActivePlayer;
import com.itech4kids.Hide_or_Hunt.Main;
import com.sun.deploy.uitoolkit.ui.ConsoleController;
import com.sun.org.apache.xml.internal.security.c14n.helper.C14nHelper;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.Console;
import java.util.Map;

public class PauseCommand implements CommandExecutor {

    private Main main;
    public PauseCommand(Main main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        System.out.println(sender.toString());
        main.running = false;
        if (sender instanceof ConsoleCommandSender) {
            for (Map.Entry mapElement : main.players.entrySet()) {
                System.out.println("Hi test");
                String name = (String) mapElement.getKey();
                ActivePlayer activePlayer = (ActivePlayer) mapElement.getValue();
                Player player = activePlayer.player;
                if (player != null){
                    player.kickPlayer(ChatColor.GREEN + "Event is paused");
                }
            }
        }

    return false;
    }
}
