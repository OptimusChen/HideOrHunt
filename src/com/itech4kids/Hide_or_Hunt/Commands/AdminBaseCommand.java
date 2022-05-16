package com.itech4kids.Hide_or_Hunt.Commands;

import com.itech4kids.Hide_or_Hunt.Main;
import com.itech4kids.Hide_or_Hunt.Util.SchematicUtils;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class AdminBaseCommand implements CommandExecutor {

    private Main main;
    public AdminBaseCommand(Main main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player sendPlayer = (Player) sender;
        SchematicUtils.adminBase(sendPlayer);
        return false;
    }
}
