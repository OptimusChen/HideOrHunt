package com.itech4kids.Hide_or_Hunt;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.session.PasteBuilder;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

import static org.bukkit.Material.*;

public class EventListener implements Listener {

    private Main main;
    public int beaconTask;
    public int potionTask;
    public int ironTask;
    public int itemTask;

    public EventListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player player = e.getPlayer();

        if (main.running == false) {
            player.kickPlayer(ChatColor.RED + "DON'T YOU TRY TO CHEAT...");
            return;
        } else {
            player.sendMessage(main.prefix + ChatColor.GREEN + "Welcome! Use /teamwith <username> to form a team!");
            e.setJoinMessage(ChatColor.YELLOW + player.getName() + " has joined Hide or Hunt!");
            PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + "§l§bHide §for §cHunt" + "\"}"));
            PacketPlayOutTitle subTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + "§2Welcome §a" + player.getName() + " §fto §l§bHide §for §cHunt" + "\"}"));
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
            PacketPlayOutTitle length = new PacketPlayOutTitle(5, 60, 5);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(subTitle);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(length);
        }

        if (main.players.get(player.getName()) == null) {
            ActivePlayer activePlayer = new ActivePlayer(player);
            main.players.put(player.getName(), activePlayer);
        } else {
            ActivePlayer activePlayer = main.players.get(player.getName());
            activePlayer.player = player;
            if (activePlayer.team != null) {
                if (activePlayer.team.player1 == null) {
                    activePlayer.team.player1 = player;
                } else {
                    activePlayer.team.player2 = player;
                }
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {

        Player player = e.getPlayer();

        if (main.players.get(player.getName()) != null) {
            ActivePlayer activePlayer = main.players.get(player.getName());
            if (activePlayer.team != null) {
                if (activePlayer.team.player1 == activePlayer.player) {
                    activePlayer.team.player1 = null;
                }
                if (activePlayer.team.player2 == activePlayer.player) {
                    activePlayer.team.player2 = null;
                }
                if ((activePlayer.team.beacon == null)
                        && (activePlayer.team.placed == true)) {
                    if (main.running == true) {
                        main.vanish(player);
                        main.removePlayerFromTeam(activePlayer.team);
                    }
                }
            }
            activePlayer.player = null;
        }
    }


   /* @EventHandler
    public void onPickUp(PlayerPickupItemEvent e){
        Player player = e.getPlayer();
        if (e.getItem().getType().equals(BEACON)){
            e.setCancelled(true);
            player.sendMessage("Cant pick up beacon");
        }

    }
*/


    @EventHandler
    public void onBreak(BlockBreakEvent e) {

        Player player = (Player) e.getPlayer();
        Team breakingTeam = main.getActiveInstance(player.getName()).team;

        if (e.getBlock().getType().toString().equals("BEACON")) {
            Team beaconOwner = this.main.beacons.get(e.getBlock());
            if (main.timer != 0) {
                e.setCancelled(true);
                player.sendMessage(main.prefix + ChatColor.GOLD + "You cannot break beacons during grace period!");
                return;
            }

            if (beaconOwner != null) {
                if (breakingTeam == beaconOwner) {
                    e.setCancelled(true);
                    player.sendMessage(main.prefix + ChatColor.GOLD + "You cannot break your own beacon");

                } else {
                    e.setCancelled(true);
                    e.getBlock().setType(AIR);
                    beaconOwner.beacon = null;
                    main.beacons.remove(e.getBlock(), beaconOwner);

                    main.updateScoreBoard();
                    player.sendMessage(main.prefix + ChatColor.RED + "You Broke " + beaconOwner.getNonAbbreviatedName() + "'s Beacon!");
                    Bukkit.broadcastMessage(main.prefix + ChatColor.RED + beaconOwner.getNonAbbreviatedName() + "'s beacon was destroyed by: " + player.getName() + "!");
                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                        PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + "§4Beacon Destroyed!" + "\"}"));
                        PacketPlayOutTitle subTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + "" + beaconOwner.getNonAbbreviatedName() + "\"}"));
                        PacketPlayOutTitle length = new PacketPlayOutTitle(5, 60, 5);
                        ((CraftPlayer) player1).getHandle().playerConnection.sendPacket(packet);
                        ((CraftPlayer) player1).getHandle().playerConnection.sendPacket(subTitle);
                        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(length);
                        player1.playSound(player1.getLocation(), Sound.ANVIL_BREAK, 10, 10);
                    }
                    if ((beaconOwner.player1 == null) && (beaconOwner.player2 == null)) {
                        beaconOwner.isDead = true;
                        if (main.getLiveTeamsSize() == 1) {
                            main.gameOver();
                        }
                    }
                }
            }
        }

    }

    /*
        @EventHandler
        public void onDrop(BlockDropItemEvent e){
            Block block = e.getBlock();
            if (e.getBlock().equals(BEACON)){
                block.setDr

            }

        }
    */
    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player player = (Player) e.getPlayer();
        ActivePlayer activePlayer = main.getActiveInstance(player.getName());

        if (e.getBlock().getType().toString().equals("BEACON")) {
            if (activePlayer.team.beaconPlaced) {
                player.sendMessage(main.prefix + ChatColor.RED + "You have already placed your beacon!");
                e.setCancelled(true);
                activePlayer.team.beacon = null;
                main.updateScoreBoard();
                return;
            }

            if (!activePlayer.team.beaconPlaceConfirmation) {
                e.setCancelled(true);
                player.sendMessage(main.prefix + ChatColor.RED + "Are you SURE you want to place your beacon? Place again to confirm. This action will expire in 10 seconds.");
                activePlayer.team.beaconPlaceConfirmation = true;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        activePlayer.team.beaconPlaceConfirmation = false;
                    }
                }.runTaskLater(Main.getMain(), 20*10);
            } else {
                main.beacons.put(e.getBlockPlaced(), activePlayer.team);
                activePlayer.team.beacon = e.getBlockPlaced();
                activePlayer.team.placed = true;
                activePlayer.team.beaconPlaced = true;
                activePlayer.team.beaconPlaceConfirmation = false;
                main.updateScoreBoard();
            }
        }

    }

    @EventHandler
    public void onCraft(CraftItemEvent craft) {
        Player player = (Player) craft.getWhoClicked();
        if (craft.getRecipe().getResult().getType() == WORKBENCH) {

            craft.setCancelled(true);
            player.sendMessage(main.prefix + ChatColor.RED + "You cannot craft this! Use the beacon to craft things");

        } else if (craft.getRecipe().getResult().getType() == BEACON) {

            craft.setCancelled(true);
            player.sendMessage(main.prefix + ChatColor.RED + "You cannot craft a beacon!");

        }

    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ActivePlayer activePlayer = main.getActiveInstance(player.getName());
        if (e.getClickedBlock() != null) {
            if (e.getClickedBlock().getType().equals(Material.BEACON) &&
                    e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (activePlayer.team.beaconPlaced == true) {
                    e.setCancelled(true);
                    player.openWorkbench(null, true);
                }
            }

        }

    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        ActivePlayer activePlayer = main.getActiveInstance(player.getName());
        Team playerTeam = main.getActiveInstance(player.getName()).team;
        if (playerTeam != null) {
            if ((playerTeam.beacon == null)
                    && (playerTeam.placed)) {
                player.sendMessage(main.prefix + ChatColor.RED + "Your beacon was destroyed!");
                main.removePlayerFromTeam(playerTeam);
                main.unVanish(player);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        main.vanish(player);
                    }
                }.runTaskLater(Main.getMain(), 1);
            } else {
                player.sendMessage(main.prefix + ChatColor.GREEN + "You have respawned!");
            }
        }
    }

    @EventHandler
    public void onBeaconPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        Team playerTeam = main.getActiveInstance(player.getName()).team;
        if (playerTeam.beacon != null) {
            playerTeam.player1.getInventory().removeItem(new ItemStack(BEACON, 1));
            playerTeam.player2.getInventory().removeItem(new ItemStack(BEACON, 1));
        }

    }


    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent e) {
        Entity victim = e.getEntity();
        Entity damager = e.getDamager();
        Player damagerPlayer = (Player) e.getDamager();

        if (main.timer != 0) {
            if (victim instanceof Player) {
                e.setCancelled(true);
                damagerPlayer.sendMessage(main.prefix + ChatColor.GOLD + "You cannot damage players during grace period!");
                return;
            }
        }

        if (damager instanceof Player) {
            damagerPlayer = (Player) damager;
        } else if (damager instanceof Projectile) {
            Projectile projectile = (Projectile) damager;
            if (projectile.getShooter() instanceof Player) {
                damagerPlayer = (Player) projectile.getShooter();
            }
        }
        if (damagerPlayer != null) {
            if (main.getActiveInstance(damagerPlayer.getName()) == null) {
                return;
            }
            Team damagerTeam = main.getActiveInstance(damagerPlayer.getName()).team;
            if (victim instanceof Player) {
                Player victimPlayer = (Player) victim;
                Team victimTeam = main.getActiveInstance(victimPlayer.getName()).team;
                if (main.getActiveInstance(victimPlayer.getName()) == null) {
                    return;
                }
                if (damagerTeam == victimTeam) {
                    if (damagerTeam != null) {
                        e.setCancelled(true);
                        damagerPlayer.sendMessage(main.prefix + ChatColor.GOLD + "You cannot damage your own teammate!");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        Random rand = new Random();
        if (!(e.getEntity() instanceof  Player)) return;

        Player victim = (Player) e.getEntity();
        Entity damager = e.getDamager();

        if (e.getEntity() instanceof Player) {
            final Player entity = (Player) e.getEntity();
            final double health = Math.max(Math.ceil(entity.getHealth() - e.getFinalDamage()), 0);
            ItemStack skull = victim.getInventory().getItemInHand();

            if (damager instanceof Projectile) {
                if (((Projectile) damager).getShooter() instanceof Player) {
                    if (victim.getHealth() - e.getDamage() < 0) {
                        ((Player) ((Projectile) damager).getShooter()).sendMessage(ChatColor.GRAY +
                                victim.getName() + " is on " + ChatColor.RED + health + "❤" + ChatColor.GRAY + "!");
                    } else {
                        ((Player) ((Projectile) damager).getShooter()).sendMessage(ChatColor.GRAY +
                                victim.getName() + " is on " + ChatColor.RED + health + "❤" + ChatColor.GRAY + "!");
                    }

                }

            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Random rand = new Random();
        int r = rand.nextInt(6);
        Player player = (Player) e.getEntity();
        player.setPlayerListName(ChatColor.RED + player.getName() + ChatColor.YELLOW + "   " + player.getMaxHealth());
        if (r == 1) {
            e.setKeepInventory(true);
            player.sendMessage(main.prefix + ChatColor.GREEN + "You got lucky and kept your items!");
        }
    }

    @EventHandler
    public void onInventory(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ActivePlayer activePlayer = main.getActiveInstance(player.getName());
        if (activePlayer.team.beaconPlaced) {
            player.getInventory().remove(new ItemStack(BEACON));
        }
    }

    @EventHandler
    public void giveIronDeath(PlayerRespawnEvent e) {
        Random rand = new Random();
        ActivePlayer activePlayer = main.getActiveInstance(e.getPlayer().getName());
        int r = rand.nextInt(3);
        if (r == 1) {

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (activePlayer.canGetIron) {
                        e.getPlayer().getInventory().addItem(new ItemStack(IRON_INGOT, 20));
                        e.getPlayer().sendMessage(main.prefix + ChatColor.GREEN + "You got lucky and got iron!");
                        activePlayer.canGetIron = false;
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                activePlayer.canGetIron = true;
                            }
                        }.runTaskLater(Main.getMain(), 20*300);
                    }
                }
            }.runTaskLater(Main.getMain(), 1);
        }
    }

    @EventHandler
    public void onEat(PlayerItemConsumeEvent e) {
        Player player = e.getPlayer();
        ItemStack itemStack = e.getItem();
        if (itemStack.getType() == Material.GOLDEN_APPLE) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 160, 1));
        }
    }

    @EventHandler
    public void onJoin2(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        double health = player.getHealth();
        main.updateHealth(player, 0);
        //player.getPlayer().setPlayerListName(ChatColor.RED + player.getName() + ChatColor.YELLOW + "   " + health);
    }

    @EventHandler
    public void onDmg(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            main.updateHealth(player, -e.getFinalDamage());
            //final long health = Math.round(player.getHealth() - e.getFinalDamage());
            //player.setPlayerListName(ChatColor.RED + player.getName() + "   " + ChatColor.YELLOW + health);
        }
    }

    @EventHandler
    public void onHeal(EntityRegainHealthEvent e) {
        Player player = (Player) e.getEntity();
        main.updateHealth(player, e.getAmount());
        //long health = Math.round(player.getHealth() + e.getAmount());
        //if (health <= player.getMaxHealth()){
        //    player.setPlayerListName(ChatColor.RED + player.getName() + "   " + ChatColor.YELLOW + health);
        //}

    }

    @EventHandler
    public void onBeaconRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        ActivePlayer activePlayer = main.getActiveInstance(player.getName());
        Location location = activePlayer.team.respawnLoc;
        e.setRespawnLocation(location);
    }

    @EventHandler
    public void onSpecRejoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (player.getGameMode().equals(GameMode.SPECTATOR)) {
        }
    }

    @EventHandler
    public void onWorld(PlayerChangedWorldEvent e) {
        org.bukkit.Location location = new Location(Bukkit.getWorld("world"), 0, 100, 0);
        e.getPlayer().teleport(location);
    }

    @EventHandler
    public void onPlayerKilled(PlayerDeathEvent e) {
        if (e.getEntity() == null) return;

        Player player = e.getEntity();
        Player killer = player.getKiller();
        killer.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 1, true));
        killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 2, true));

    }

    @EventHandler
    public void onKill(EntityDeathEvent e) {
        if (e.getEntity().getType().equals(EntityType.ENDERMAN)) {
            e.getDrops().clear();
            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(ENDER_PEARL, 1));
        }
    }

    /*@EventHandler
    public void tabListDmg(EntityDamageByEntityEvent e){
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player){
            Player victim = (Player) e.getEntity();
            Player damager = (Player) e.getDamager();
            damager.sendMessage("Dmg Test");
            main.yellowName(victim, damager);
        }
    }

     */


    @EventHandler
    public void onSleep(PlayerBedEnterEvent e) {
        e.getPlayer().getWorld().setTime(0);
    }

    @EventHandler
    public void onBookshelfBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Random rand = new Random();
        int r = rand.nextInt(10);
        if (e.getBlock().getType().toString().equals("BOOKSHELF")) {
            e.setCancelled(true);
            e.getBlock().setType(AIR);
            if (r == 1) {
                player.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(BOOK));
            }
        }
    }

    @EventHandler
    public void onGui(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ActivePlayer activePlayer = main.getActiveInstance(player.getName());
        ItemStack item = e.getCurrentItem();
        Inventory inv = e.getClickedInventory();
        InventoryView view = e.getView();

        if (view.getTitle().equals("Admin Gui")) {
            if (e.getCurrentItem() != null) {
                e.setCancelled(true);

                switch (e.getCurrentItem().getType()) {
                    case WOOL:
                        ItemStack teamsList = new ItemStack(Material.WOOL, 1, DyeColor.GRAY.getData());
                        ItemStack start = new ItemStack(Material.WOOL, 1, DyeColor.GREEN.getData());
                        if (item.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "List Teams")) {
                            for (int i = 1; i <= main.maxNumTeams; i++) {
                                String teamName = "Team " + i;
                                if (i > main.teams.size()) {
                                    player.sendMessage(ChatColor.YELLOW + teamName + " " + ChatColor.RED + "- ✖, Not Formed");
                                } else {
                                    Team team = main.teams.get(i - 1);
                                    if (!team.placed) {
                                        player.sendMessage(ChatColor.WHITE + team.getNonAbbreviatedName() + " " + ChatColor.AQUA + "~ " + ChatColor.WHITE + team.aliveTeamPlayers);
                                        player.sendMessage(ChatColor.GRAY + teamName + " - " + "Beacon: " + ChatColor.AQUA + "Not Placed");
                                    } else if (team.beacon == null) {
                                        player.sendMessage(ChatColor.WHITE + team.getNonAbbreviatedName() + " " + ChatColor.RED + "✖ " + ChatColor.WHITE + team.aliveTeamPlayers);
                                        player.sendMessage(ChatColor.GRAY + teamName + " - " + "Beacon: " + ChatColor.RED + "Destroyed");
                                    } else {
                                        player.sendMessage(ChatColor.WHITE + team.getNonAbbreviatedName() + " " + ChatColor.GREEN + "✔ " + ChatColor.WHITE + team.aliveTeamPlayers);
                                        player.sendMessage(ChatColor.GRAY + teamName + " - " + "Beacon: " + ChatColor.GREEN + "Placed at: " + team.beacon.getLocation().getX() + " " + team.beacon.getLocation().getY() + " " + team.beacon.getLocation().getZ());
                                    }
                                }
                            }
                            } else if (item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Start Game")) {
                                player.performCommand("start");
                            }else if (item.getItemMeta().getDisplayName().equals(ChatColor.RED + "Kill all Teams")){
                            for (Team team : main.teams) {
                                team.isDead = true;
                                main.beacons.remove(team.beacon, team);
                                team.beacon = null;
                                main.updateScoreBoard();
                                Bukkit.broadcastMessage(main.prefix + ChatColor.RED + "All beacon's were destroyed by: " + player.getName() + "!");
                                for (Player player1 : Bukkit.getOnlinePlayers()) {
                                    PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + "§4All Beacons Destroyed!" + "\"}"));
                                    PacketPlayOutTitle subTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + "§4by " + player.getName() + "\"}"));
                                    PacketPlayOutTitle length = new PacketPlayOutTitle(5, 60, 5);
                                    ((CraftPlayer) player1).getHandle().playerConnection.sendPacket(packet);
                                    ((CraftPlayer) player1).getHandle().playerConnection.sendPacket(subTitle);
                                    ((CraftPlayer) player1).getHandle().playerConnection.sendPacket(length);
                                    player1.playSound(player1.getLocation(), Sound.ANVIL_BREAK, 10, 10);
                                }
                                if ((team.player1 == null) && (team.player2 == null)) {
                                    team.isDead = true;
                                    if (main.getLiveTeamsSize() == 1) {
                                        main.gameOver();
                                    }
                                }
                            }
                        }
                            player.closeInventory();
                            break;
                    case STAINED_GLASS_PANE:
                        player.sendMessage(main.prefix + ChatColor.RED + "Coming Soon!");
                        player.closeInventory();
                        break;
                    case ENDER_PORTAL_FRAME:
                        if (activePlayer.team.beacon == null && activePlayer.team.placed){
                            player.sendMessage(main.prefix + ChatColor.RED + "Your beacon was destroyed!");
                        }else if (activePlayer.team.beacon == null){
                            player.sendMessage(main.prefix + ChatColor.AQUA + "Please place down your beacon!");
                        }else{
                            Location location = new Location(activePlayer.team.beacon.getLocation().getWorld(), activePlayer.team.beacon.getLocation().getX(), activePlayer.team.beacon.getLocation().getY() + 1, activePlayer.team.beacon.getLocation().getZ());
                            player.teleport(location);
                            player.sendMessage(main.prefix + ChatColor.GREEN + "Whoosh!");
                            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 10, 10);
                        }
                        player.closeInventory();
                        break;
                    case DIAMOND_SWORD:
                        main.timer = 0;
                        Bukkit.broadcastMessage(main.prefix + ChatColor.YELLOW + "PvP has been enabled!");
                        PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + "§4PvP Enabled!" + "\"}"));
                        PacketPlayOutTitle subTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + "§2Beware!" + "\"}"));
                        PacketPlayOutTitle length = new PacketPlayOutTitle(5, 60, 5);
                        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(subTitle);
                        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(length);
                        player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 10, 10);
                        main.updateScoreBoard();
                        player.closeInventory();
                        break;
                    case BRICK:
                        player.performCommand("adminbase");
                        player.closeInventory();
                        break;
                    case POTION:
                        if (activePlayer.isVanished){
                            main.unVanish(player);
                        }else {
                            main.vanish(player);
                        }
                        player.closeInventory();
                        break;
                    case BARRIER:
                        player.closeInventory();
                        break;
                }
            }
        }
    }
}