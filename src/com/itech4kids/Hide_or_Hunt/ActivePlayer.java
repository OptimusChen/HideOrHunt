package com.itech4kids.Hide_or_Hunt;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ActivePlayer  {

    public Player player;
    public Player pendingTeamWith;
    public Team team;
    public boolean isVanished;
    public boolean canGetIron;

    public ActivePlayer(Player player){

        this.player = player;
        this.pendingTeamWith = null;
        this.team = null;
        this.isVanished = true;
        this.canGetIron = true;
    }
    public Player getBukkitPlayer() {
        return player;
    }
}
