package com.itech4kids.Hide_or_Hunt.Util;

import com.itech4kids.Hide_or_Hunt.Main;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class GameUtils {

    public static void enablePvp(Player player){
        Bukkit.broadcastMessage(Main.getMain().prefix + ChatColor.YELLOW + "PvP has been enabled!");
        PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + "§4PvP Enabled!" + "\"}"));
        PacketPlayOutTitle subTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + "§2Beware!" + "\"}"));
        PacketPlayOutTitle length = new PacketPlayOutTitle(5, 60, 5);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(subTitle);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(length);
        player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 10, 10);
        Main.getMain().updateScoreBoard();
        player.closeInventory();
    }

}
