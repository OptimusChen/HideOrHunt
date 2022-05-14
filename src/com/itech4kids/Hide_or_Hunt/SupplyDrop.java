package com.itech4kids.Hide_or_Hunt;

import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SupplyDrop extends BukkitRunnable {

	private Main main;
	
	public SupplyDrop(Main plugin) {
		this.main = plugin;
		runTaskTimer(plugin, 60 * 60 * 20L
				, 10 * 60 * 20L);
	}

	@Override
	public void run() {
	    for (Player player : Bukkit.getOnlinePlayers()){
            Location borderCenter = player.getWorld().getWorldBorder().getCenter();
            int borderRadius = (int) player.getWorld().getWorldBorder().getSize() / 2;
            int x = getRandomNumberInRange(borderCenter.getBlockX() - borderRadius, borderCenter.getBlockX() + borderRadius);
            int z = getRandomNumberInRange(borderCenter.getBlockZ() - borderRadius, borderCenter.getBlockZ() + borderRadius);
            Location higestBlock = borderCenter.getWorld().getHighestBlockAt(x, z).getLocation().clone().add(0.0, 60.0, 0.0);
            createDrop(higestBlock);
        }
	}

    public void createDrop(Location loc) {
        Random rand = new Random();
        for (Player player : Bukkit.getOnlinePlayers()) {
            int j = rand.nextInt((int) player.getWorld().getWorldBorder().getSize()/2);
            int h = rand.nextInt((int) player.getWorld().getWorldBorder().getSize()/2);
            World workWorld = player.getWorld();
            workWorld.getBlockAt(loc.clone().add(0.0, 4.0, -1.0)).setTypeIdAndData(35, DyeColor.LIGHT_BLUE.getData(), true);
            workWorld.getBlockAt(loc.clone().add(0.0, 1.0, 0.0)).setType(Material.CHEST);
            workWorld.getBlockAt(loc.clone().add(0.0, 5.0, 0.0)).setType(Material.WOOL);
            workWorld.getBlockAt(loc.clone().add(0.0, 4.0, -1.0)).setTypeIdAndData(35, DyeColor.LIGHT_BLUE.getData(), true);
            workWorld.getBlockAt(loc.clone().add(0.0, 4.0, -1.0)).setTypeIdAndData(35, DyeColor.LIGHT_BLUE.getData(), true);
            workWorld.getBlockAt(loc.clone().add(0.0, 4.0, -1.0)).setTypeIdAndData(35, DyeColor.LIGHT_BLUE.getData(), true);
            workWorld.getBlockAt(loc.clone().add(0.0, 4.0, -1.0)).setTypeIdAndData(35, DyeColor.LIGHT_BLUE.getData(), true);
            workWorld.getBlockAt(loc.clone().add(1.0, 1.0, 0.0)).setType(Material.WOOD);
            workWorld.getBlockAt(loc.clone().add(-1.0, 1.0, 0.0)).setType(Material.WOOD);
            workWorld.getBlockAt(loc.clone().add(0.0, 1.0, 1.0)).setType(Material.WOOD);
            workWorld.getBlockAt(loc.clone().add(0.0, 1.0, -1.0)).setType(Material.WOOD);
            for (int i = 1; i < 4; ++i) {
                workWorld.getBlockAt(loc.clone().add(1.0, i, 1.0)).setType(Material.FENCE);
                workWorld.getBlockAt(loc.clone().add(1.0, i, 1.0)).setType(Material.FENCE);
                workWorld.getBlockAt(loc.clone().add(1.0, i, 1.0)).setType(Material.FENCE);
                workWorld.getBlockAt(loc.clone().add(1.0, i, 1.0)).setType(Material.FENCE);
            }
            workWorld.getBlockAt(loc.clone().add(1.0, 4.0, 1.0)).setType(Material.WOOL);
            workWorld.getBlockAt(loc.clone().add(1.0, 4.0, -1.0)).setType(Material.WOOL);
            workWorld.getBlockAt(loc.clone().add(-1.0, 4.0, 1.0)).setType(Material.WOOL);
            workWorld.getBlockAt(loc.clone().add(-1.0, 4.0, -1.0)).setType(Material.WOOL);
            Bukkit.broadcastMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "Supply drop at X:" + loc.getBlockX() + " Z:" + loc.getBlockZ());
            Chest chest = (Chest) workWorld.getBlockAt(loc.clone().add(0.0, 1.0, 0.0)).getState();
            ItemStack itemStack = new ItemStack(Material.DIAMOND, 1);
            ItemStack itemStack2 = new ItemStack(Material.IRON_INGOT, 4);
            ItemStack itemStack3 = new ItemStack(Material.ENDER_PEARL, 1);
            ItemStack itemStack4 = new ItemStack(Material.GOLD_BLOCK, 2);
            chest.getBlockInventory().addItem(itemStack);
            chest.getBlockInventory().addItem(itemStack2);
            chest.getBlockInventory().addItem(itemStack3);
            chest.getBlockInventory().addItem(itemStack4);

        }
	}

	 
    public int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }
}
