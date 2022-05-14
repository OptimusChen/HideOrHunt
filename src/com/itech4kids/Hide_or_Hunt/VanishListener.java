package com.itech4kids.Hide_or_Hunt;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class VanishListener implements Listener {

    private Main main;

    public VanishListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onSpecDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player){
            Player victim = (Player) e.getEntity();
            Player damager = (Player) e.getDamager();
            ActivePlayer activeVictim = main.getActiveInstance(victim.getName());
            ActivePlayer activeDamager = main.getActiveInstance(damager.getName());
            if (activeDamager.isVanished == true || activeVictim.isVanished == true) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onSpecInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ActivePlayer activePlayer = main.getActiveInstance(player.getName());
        if (activePlayer.isVanished == true) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e) {
        ActivePlayer activePlayer = main.getActiveInstance(e.getPlayer().getName());
        if (activePlayer.isVanished == true) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onXp(PlayerExpChangeEvent e) {
        ActivePlayer activePlayer = main.getActiveInstance(e.getPlayer().getName());
        if (activePlayer.isVanished == true) {
            e.setAmount(0);
            for (int i = 1; i < e.getAmount(); ++i) {
                e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation(), EntityType.EXPERIENCE_ORB);
            }
        }
    }

    @EventHandler
    public void onEntityDmg(EntityDamageByEntityEvent e){
        Player player = (Player) e.getDamager();
        ActivePlayer activePlayer = main.getActiveInstance(player.getName());
        if (activePlayer.isVanished == true){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onSpecDrop(PlayerDropItemEvent e){
        ActivePlayer activePlayer = main.getActiveInstance(e.getPlayer().getName());
        if (activePlayer.isVanished == true){
            e.setCancelled(true);
        }
    }

}
