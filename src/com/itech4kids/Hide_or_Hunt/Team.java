package com.itech4kids.Hide_or_Hunt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Team {

    public Player player1;
    public Player player2;
    private String nonAbbreviatedName;
    private String name;
    public Block beacon;
    public boolean placed;
    public int aliveTeamPlayers;
    public boolean beaconPlaceConfirmation;
    public boolean beaconPlaced;
    public boolean baseSet;
    public Location respawnLoc;
    public boolean isDead;

    public Team(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
        this.name = player1.getName().substring(0, 4) + " & " + player2.getName().substring(0, 4);
        this.nonAbbreviatedName = player1.getName() + " & " + player2.getName();
        this.beacon = null;
        this.placed = false;
        this.beaconPlaceConfirmation = false;
        this.beaconPlaced = false;
        this.baseSet = false;
        this.respawnLoc = Bukkit.getWorld("world").getSpawnLocation();
        this.isDead = false;

        if (player2 == player1){
            this.aliveTeamPlayers = 1;
        }else{
            this.aliveTeamPlayers = 2;
        }
    }

    public String getName(){
        return name;
    }
    public String getNonAbbreviatedName() {return nonAbbreviatedName;}
}

