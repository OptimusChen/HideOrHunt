package com.itech4kids.Hide_or_Hunt.Commands;

import com.itech4kids.Hide_or_Hunt.ActivePlayer;
import com.itech4kids.Hide_or_Hunt.Main;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;

public class GuiCommand implements CommandExecutor {
    private Main main;
    public GuiCommand(Main main){
        this.main = main;
        initializeKits();
    }
    private ArrayList<ItemStack> kitsItems;
    Inventory kits;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.isOp()){
                ActivePlayer activePlayer = main.getActiveInstance(player.getName());
                activePlayer.getBukkitPlayer().openInventory(kits);
                return true;
            }

        }
        return false;
    }

    private void initializeKits() {
        kitsItems = new ArrayList<ItemStack>();
        ItemStack teamsList = new ItemStack(Material.WOOL, 1, DyeColor.GRAY.getData());
        ItemStack space = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData());
        ItemStack space2 = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData());
        ItemStack start = new ItemStack(Material.WOOL, 1, DyeColor.GREEN.getData());
        ItemStack kill = new ItemStack(Material.WOOL, 1, DyeColor.RED.getData());
        ItemStack tp = new ItemStack(Material.ENDER_PORTAL_FRAME, 1);
        ItemStack pvp = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemStack bases = new ItemStack(Material.BRICK, 1);
        Potion potion = new Potion(PotionType.INVISIBILITY);
        potion.setHasExtendedDuration(true);
        ItemStack invisPotion = potion.toItemStack(1);
        ItemStack close = new ItemStack(Material.BARRIER, 1);
        ItemMeta teamsListMeta = teamsList.getItemMeta();
        teamsListMeta.setDisplayName(ChatColor.GRAY + "List Teams");
        teamsList.setItemMeta(teamsListMeta);
        kitsItems.add(teamsList);
        ItemMeta startMeta = start.getItemMeta();
        startMeta.setDisplayName(ChatColor.GREEN + "Start Game");
        start.setItemMeta(startMeta);
        kitsItems.add(start);
        ItemMeta killMeta = kill.getItemMeta();
        killMeta.setDisplayName(ChatColor.RED + "Kill all Teams");
        kill.setItemMeta(killMeta);
        kitsItems.add(kill);
        ItemMeta tpMeta = tp.getItemMeta();
        tpMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Teleport to your beacon");
        tp.setItemMeta(tpMeta);
        kitsItems.add(tp);
        ItemMeta basesMeta = bases.getItemMeta();
        basesMeta.setDisplayName(ChatColor.AQUA + "Spawn Admin bases");
        bases.setItemMeta(basesMeta);
        kitsItems.add(bases);
        ItemMeta pvpMeta = pvp.getItemMeta();
        pvpMeta.setDisplayName(ChatColor.YELLOW + "Enable PvP");
        pvp.setItemMeta(pvpMeta);
        kitsItems.add(pvp);
        ItemMeta potionMeta = invisPotion.getItemMeta();
        potionMeta.setDisplayName(ChatColor.WHITE + "Toggle Vanish");
        invisPotion.setItemMeta(potionMeta);
        kitsItems.add(invisPotion);
        kitsItems.add(space);
        ItemMeta spaceMeta = space.getItemMeta();
        spaceMeta.setDisplayName(ChatColor.RED + "Coming Soon!");
        space.setItemMeta(spaceMeta);
        ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName(ChatColor.RED + "Close");
        close.setItemMeta(closeMeta);
        kitsItems.add(close);

        /*
        ItemMeta spaceMeta = space.getItemMeta();
        spaceMeta.setDisplayName(ChatColor.GRAY + " ");
        space.setItemMeta(spaceMeta);
        kitsItems.add(space);
        ItemMeta spaceMeta2 = space2.getItemMeta();
        spaceMeta2.setDisplayName(ChatColor.GRAY + " ");
        space2.setItemMeta(spaceMeta2);
        kitsItems.add(space2);
         */

        kits = Bukkit.createInventory(null, 9, "Admin Gui");
        for (ItemStack item: kitsItems) {
            kits.setItem(kits.firstEmpty(), item);
        }

    }

}
