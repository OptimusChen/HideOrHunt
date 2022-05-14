package com.itech4kids.Hide_or_Hunt;

import com.sk89q.worldedit.*;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;
import com.sk89q.worldedit.schematic.SchematicFormat;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.*;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

public class StartCommand implements CommandExecutor {

    private Main main;
    public int timerTask;
    public int getTimerTask;

    public StartCommand(Main main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        Player sendPlayer = (Player) sender;

        if (sendPlayer.isOp()){
            System.out.println("Step 1" + main.players.toString());
            for (Map.Entry mapElement : main.players.entrySet()) {
                String name = (String) mapElement.getKey();
                Potion potion = new Potion(PotionType.INVISIBILITY);
                potion.setHasExtendedDuration(true);
                ItemStack invisPotion = potion.toItemStack(1);
                ItemStack pick = new ItemStack(Material.STONE_PICKAXE);
                pick.addEnchantment(Enchantment.DURABILITY, 3);
                ActivePlayer activePlayer = (ActivePlayer) mapElement.getValue();
                Player player = activePlayer.player;
                Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
                System.out.println("Step 2" + player.toString());
                player.getInventory().clear();
                //player.setOp(false);
                player.setHealth(20);
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 400, 4));
                player.getInventory().addItem(new ItemStack(Material.STONE_SWORD, 1));
                player.getInventory().addItem(new ItemStack(Material.STONE_AXE, 1));
                player.getInventory().addItem(new ItemStack(Material.STONE_SPADE, 1));
                player.getInventory().addItem(pick);
                player.getInventory().addItem(new ItemStack(Material.APPLE, 5));
                player.getInventory().addItem(new ItemStack(Material.BREAD, 64));
                player.getInventory().addItem(new ItemStack(Material.BEACON, 1));
                player.getInventory().addItem(new ItemStack(Material.SIGN, 1));
                player.getInventory().addItem(invisPotion);
                player.getInventory().addItem(new ItemStack(Material.BED, 1));
                block.getRelative(BlockFace.NORTH).setType(Material.AIR);
                block.getRelative(BlockFace.SOUTH).setType(Material.AIR);
                block.getRelative(BlockFace.EAST).setType(Material.AIR);
                block.getRelative(BlockFace.WEST).setType(Material.AIR);
                block.getRelative(BlockFace.NORTH_EAST).setType(Material.AIR);
                block.getRelative(BlockFace.NORTH_WEST).setType(Material.AIR);
                block.getRelative(BlockFace.SOUTH_EAST).setType(Material.AIR);
                block.getRelative(BlockFace.SOUTH_WEST).setType(Material.AIR);
                block.setType(Material.AIR);
            }
            Bukkit.broadcastMessage(main.prefix + ChatColor.DARK_AQUA+ "The game has started!");
            //main.skyCrates(sendPlayer);
            for (int i = 1; i < 10; ++i){
             main.adminBase(sendPlayer);
            }
            main.timer = 600;
            timerTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable(){
                public void run(){
                    if(main.timer > 0) {
                        main.timer = main.timer - 1;
                    }
                    if(main.timer == 0){
                        for (Map.Entry<String, ActivePlayer> mapElement : main.players.entrySet()) {
                            String name = mapElement.getKey();
                            ActivePlayer activePlayer = mapElement.getValue();
                            Player player = activePlayer.player;
                        }
                        Bukkit.getScheduler().cancelTask(timerTask);
                    }

                    main.updateScoreBoard();
                }

            }, 0, 20l);
            main.lootTimer = 660;
            getTimerTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable(){
                Random rand = new Random();
                ItemStack itemStack = new ItemStack(Material.DIAMOND_SWORD);
                ItemMeta itemMeta = itemStack.getItemMeta();
                ItemStack itemStack2 = new ItemStack(Material.DIAMOND_CHESTPLATE);
                ItemMeta itemMeta2 = itemStack2.getItemMeta();
                int x = rand.nextInt(15);
                int z = rand.nextInt(15);
                int h = rand.nextInt(2);
                int j = h + 1;
                int x2 = rand.nextInt(15);
                int z2 = rand.nextInt(15);
                Location loc = new Location(sendPlayer.getWorld(), x, 100, z);
                Location loc2 = new Location(sendPlayer.getWorld(), x2, 100, z2);
                public void run(){
                    if(main.lootTimer > 0) {
                        main.lootTimer = main.lootTimer - 1;
                    }

                    if(main.lootTimer == 0){
                        for (Map.Entry mapElement : main.players.entrySet()) {
                            String name = (String) mapElement.getKey();
                            ActivePlayer activePlayer = (ActivePlayer) mapElement.getValue();
                            Player player = activePlayer.player;
                            PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + "§bLoot Drop!" + "\"}"));
                            PacketPlayOutTitle subTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + "§e" + x + " ~ " + z + "\"}"));
                            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                            PacketPlayOutTitle length = new PacketPlayOutTitle(5, 60, 5);
                            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(subTitle);
                            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(length);
                            //player.sendTitle(ChatColor.AQUA + "Loot Drop!", ChatColor.YELLOW + "" + x + " ~ " + z);
                        }
                        Bukkit.getScheduler().cancelTask(getTimerTask);
                        itemMeta.addEnchant(Enchantment.DAMAGE_ALL, j, true);
                        itemMeta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
                        itemMeta2.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);

                        itemStack2.setItemMeta(itemMeta2);
                        itemStack.setItemMeta(itemMeta);
                        // sendPlayer.getWorld().dropItem(loc, itemStack);
                        // sendPlayer.getWorld().dropItem(loc2, itemStack2);
                        for (int i = 1; i < 4; ++i){
                            main.airDrops(sendPlayer);
                        }

                    }

                    main.updateScoreBoard();
                }
            }, 0, 20l);

        }else{
            sendPlayer.sendMessage(main.prefix + ChatColor.RED + "This command is for operators only!");
            return false;
        }

        main.started = true;

        return true;
    }

}