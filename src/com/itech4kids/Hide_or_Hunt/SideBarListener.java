package com.itech4kids.Hide_or_Hunt;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class SideBarListener implements Listener {

    private Main main;

    public SideBarListener(Main main){
        this.main = main;
    }

    @EventHandler
    public  void  onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        main.BuildSideBar(player);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {

        Player player = e.getPlayer();


    }

}
