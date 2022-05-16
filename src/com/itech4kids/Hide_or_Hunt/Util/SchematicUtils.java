package com.itech4kids.Hide_or_Hunt.Util;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class SchematicUtils {

    public static boolean airDrops(Player sendPlayer) {
        Random rand = new Random();
        int i = rand.nextInt(5);
        int f = rand.nextInt((int) (sendPlayer.getWorld().getWorldBorder().getSize() / 2));
        int g = rand.nextInt((int) (sendPlayer.getWorld().getWorldBorder().getSize() / 2));
        int s = rand.nextInt(2);
        int h = rand.nextInt(2);
        if (s == 1 || h == 2) {
            f = -f;
        }
        if (h == 1 || h == 2) {
            g = -g;
        }

        Vector loc = new Vector(f, 100, g);
        if (i == 1) {
            WorldEditPlugin we = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
            File schematic = new File("plugins/Hide_or_Hunt/air_drop1.schematic");
            EditSession session = we.getWorldEdit().getEditSessionFactory().getEditSession(new BukkitWorld(sendPlayer.getWorld()), 1000000);
            try {
                MCEditSchematicFormat.getFormat(schematic).load(schematic).paste(session, loc, false);
                return true;
            } catch (MaxChangedBlocksException
                    | com.sk89q.worldedit.data.DataException | IOException e2) {
                e2.printStackTrace();
            }
        } else if (i == 2) {
            WorldEditPlugin we = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
            File schematic = new File("plugins/Hide_or_Hunt/air_drop2.schematic");
            EditSession session = we.getWorldEdit().getEditSessionFactory().getEditSession(new BukkitWorld(sendPlayer.getWorld()), 1000000);
            try {
                MCEditSchematicFormat.getFormat(schematic).load(schematic).paste(session, loc, false);
                return true;
            } catch (MaxChangedBlocksException
                    | com.sk89q.worldedit.data.DataException | IOException e2) {
                e2.printStackTrace();
            }
        } else if (i == 3) {
            WorldEditPlugin we = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
            File schematic = new File("plugins/Hide_or_Hunt/air_drop3.schematic");
            EditSession session = we.getWorldEdit().getEditSessionFactory().getEditSession(new BukkitWorld(sendPlayer.getWorld()), 1000000);
            try {
                MCEditSchematicFormat.getFormat(schematic).load(schematic).paste(session, loc, false);
                return true;
            } catch (MaxChangedBlocksException
                    | com.sk89q.worldedit.data.DataException | IOException e2) {
                e2.printStackTrace();
            }
        } else if (i == 4) {
            WorldEditPlugin we = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
            File schematic = new File("plugins/Hide_or_Hunt/air_drop4.schematic");
            EditSession session = we.getWorldEdit().getEditSessionFactory().getEditSession(new BukkitWorld(sendPlayer.getWorld()), 1000000);
            try {
                MCEditSchematicFormat.getFormat(schematic).load(schematic).paste(session, loc, false);
                return true;
            } catch (MaxChangedBlocksException
                    | com.sk89q.worldedit.data.DataException | IOException e2) {
                e2.printStackTrace();
            }
        }
        return false;
    }

    public static boolean adminBase(Player sendPlayer){
        Random rand = new Random();
        int i = rand.nextInt(6);
        int f = rand.nextInt((int) (sendPlayer.getWorld().getWorldBorder().getSize()/2));
        int g = rand.nextInt((int) (sendPlayer.getWorld().getWorldBorder().getSize()/2));
        int s = rand.nextInt(2);
        int h = rand.nextInt(2);
        if (s == 1||s==2){
            f = -f;
        }
        if (h == 1||h==2){
            g = -g;
        }
        Vector loc = new Vector(f,11,g);
        if (i == 1) {
            WorldEditPlugin we = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
            File schematic = new File("plugins/Hide_or_Hunt/admin_base1.schematic");
            EditSession session = we.getWorldEdit().getEditSessionFactory().getEditSession(new BukkitWorld(sendPlayer.getWorld()), 1000000);
            try {
                MCEditSchematicFormat.getFormat(schematic).load(schematic).paste(session, loc, false);
                return true;
            } catch (MaxChangedBlocksException
                    | com.sk89q.worldedit.data.DataException | IOException e2) {
                e2.printStackTrace();
            }
        }else if (i == 2){
            WorldEditPlugin we = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
            File schematic = new File("plugins/Hide_or_Hunt/admin_base2.schematic");
            EditSession session = we.getWorldEdit().getEditSessionFactory().getEditSession(new BukkitWorld(sendPlayer.getWorld()), 1000000);
            try {
                MCEditSchematicFormat.getFormat(schematic).load(schematic).paste(session, loc, false);
                return true;
            } catch (MaxChangedBlocksException
                    | com.sk89q.worldedit.data.DataException | IOException e2) {
                e2.printStackTrace();
            }
        }else if (i == 3){
            WorldEditPlugin we = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
            File schematic = new File("plugins/Hide_or_Hunt/admin_base3.schematic");
            EditSession session = we.getWorldEdit().getEditSessionFactory().getEditSession(new BukkitWorld(sendPlayer.getWorld()), 1000000);
            try {
                MCEditSchematicFormat.getFormat(schematic).load(schematic).paste(session, loc, false);
                return true;
            } catch (MaxChangedBlocksException
                    | com.sk89q.worldedit.data.DataException | IOException e2) {
                e2.printStackTrace();
            }
        }else if (i == 4){
            WorldEditPlugin we = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
            File schematic = new File("plugins/Hide_or_Hunt/admin_base4.schematic");
            EditSession session = we.getWorldEdit().getEditSessionFactory().getEditSession(new BukkitWorld(sendPlayer.getWorld()), 1000000);
            try {
                MCEditSchematicFormat.getFormat(schematic).load(schematic).paste(session, loc, false);
                return true;
            } catch (MaxChangedBlocksException
                    | com.sk89q.worldedit.data.DataException | IOException e2) {
                e2.printStackTrace();
            }
        }else if (i == 5){
            WorldEditPlugin we = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
            File schematic = new File("plugins/Hide_or_Hunt/admin_base5.schematic");
            EditSession session = we.getWorldEdit().getEditSessionFactory().getEditSession(new BukkitWorld(sendPlayer.getWorld()), 1000000);
            try {
                MCEditSchematicFormat.getFormat(schematic).load(schematic).paste(session, loc, false);
                return true;
            } catch (MaxChangedBlocksException
                    | com.sk89q.worldedit.data.DataException | IOException e2) {
                e2.printStackTrace();
            }
        }
        return false;
    }

}
