package com.itech4kids.Hide_or_Hunt;

import com.sun.deploy.uitoolkit.ui.ConsoleController;
import com.sun.org.apache.xml.internal.security.c14n.helper.C14nHelper;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.ChannelNameTooLongException;

import java.io.Console;
import java.util.Map;

public class ResumeCommand implements CommandExecutor {

    private Main main;
    public ResumeCommand(Main main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        System.out.println(sender.toString());
        main.running = true;

        return false;
    }
}
