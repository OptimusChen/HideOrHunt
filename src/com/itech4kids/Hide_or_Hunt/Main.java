package com.itech4kids.Hide_or_Hunt;

import com.itech4kids.Hide_or_Hunt.Commands.*;
import com.itech4kids.Hide_or_Hunt.Listeners.EventListener;
import com.itech4kids.Hide_or_Hunt.Listeners.SideBarListener;
import com.itech4kids.Hide_or_Hunt.Listeners.VanishListener;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main extends JavaPlugin {

    private static Main main;
    public HashMap<Block, Team> beacons;
    public HashMap<String, ActivePlayer> players;
    public ArrayList<Team> teams;
    public int timer;
    public int lootTimer;
    public int combatTimer;
    public int maxNumTeams;
    public boolean running;
    public boolean started;
    public int combatTask;
    public String prefix;

    public ActivePlayer getActiveInstance(String playerName) {
        return players.get(playerName);
    }

    @Override
    public void onEnable() {
        main = this;

        beacons = new HashMap<>();
        players = new HashMap<>();
        teams = new ArrayList<>();
        timer = -1;
        lootTimer = -1;
        combatTimer = -1;
        maxNumTeams = 6;
        running = true;
        started = false;
        prefix = ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "Hide" + ChatColor.WHITE + " or " + ChatColor.RED + "Hunt" + ChatColor.DARK_GRAY + "]" + ChatColor.RESET + " ";

        System.out.println(prefix + "Hide Or Hunt Enabled!");

        getCommand("teamwith").setExecutor(new TeamCommand(this));
        getCommand("start").setExecutor(new StartCommand(this));
        getCommand("deny").setExecutor(new DeniedCommand(this));
        getCommand("Stats").setExecutor(new StatsCommand(this));
        getCommand("resume").setExecutor(new ResumeCommand(this));
        getCommand("pause").setExecutor(new PauseCommand(this));
        getCommand("beacon").setExecutor(new BeaconCommand(this));
        getCommand("hide").setExecutor(new HideCommand(this));
        getCommand("unhide").setExecutor(new unHideCommand(this));
        getCommand("setbase").setExecutor(new BaseCommand(this));
        getCommand("broadcast").setExecutor(new BroadcastCommand(this));
        getCommand("adminbase").setExecutor(new AdminBaseCommand(this));
        getCommand("gui").setExecutor(new GuiCommand(this));

        this.getConfig().options().copyDefaults();
        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(new EventListener(this), this);
        Bukkit.getPluginManager().registerEvents(new SideBarListener(this), this);
        Bukkit.getPluginManager().registerEvents(new VanishListener(this), this);
    }

    public static Main getMain() { return main; }

    public void BuildSideBar(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("test", "dummy");
        obj.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD + "Hide or Hunt");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        int scoreIndex = 1;

        Score score = obj.getScore("----------");
        score.setScore(scoreIndex);
        scoreIndex = scoreIndex + 1;

        for (int i = 1; i <= maxNumTeams; i++) {
            String teamName = "Team " + i;
            if (i > teams.size()) {
                score = obj.getScore(ChatColor.YELLOW + teamName + " " + ChatColor.RED + "- ✖");
            } else {
                Team team = teams.get(i - 1);
                if (!team.placed) {
                    score = obj.getScore(ChatColor.WHITE + team.getName() + " " + ChatColor.AQUA + "~ " + ChatColor.WHITE + team.aliveTeamPlayers);
                } else if (team.beacon == null) {
                    score = obj.getScore(ChatColor.WHITE + team.getName() + " " + ChatColor.RED + "✖ " + ChatColor.WHITE + team.aliveTeamPlayers);
                } else {
                    score = obj.getScore(ChatColor.WHITE + team.getName() + " " + ChatColor.GREEN + "✔ " + ChatColor.WHITE + team.aliveTeamPlayers);
                }
            }
            score.setScore(scoreIndex);
            scoreIndex = scoreIndex + 1;
        }

        score = obj.getScore("  ");
        score.setScore(scoreIndex);
        scoreIndex = scoreIndex + 1;

        if (timer > 0) {
            String time = String.format("%d:%02d", timer / 60, timer % 60);
            score = obj.getScore(ChatColor.YELLOW + "PvP: " + ChatColor.AQUA + time + "");
        } else if (timer == 0) {
            score = obj.getScore(ChatColor.YELLOW + "PvP: " + ChatColor.AQUA + "Now");
        } else if (timer == -1) {
            score = obj.getScore(ChatColor.YELLOW + "PvP: " + ChatColor.AQUA + "Waiting");
        }
        score.setScore(scoreIndex);
        scoreIndex = scoreIndex + 1;

        if (lootTimer > 0) {
            String time = String.format("%d:%02d", lootTimer / 60, lootTimer % 60);
            score = obj.getScore(ChatColor.YELLOW + "Loot: " + ChatColor.AQUA + time + "");
        } else if (lootTimer == 0) {
            score = obj.getScore(ChatColor.YELLOW + "Loot: " + ChatColor.AQUA + "Done");
        } else if (lootTimer == -1) {
            score = obj.getScore(ChatColor.YELLOW + "Loot: " + ChatColor.AQUA + "Waiting");
        }
        score.setScore(scoreIndex);
        scoreIndex = scoreIndex + 1;

        player.setScoreboard(board);
    }

    public void ShowPlayers(Player player) {
        for (Map.Entry mapElement : players.entrySet()) {
            String name = (String) mapElement.getKey();
            player.sendMessage(name + ":" + mapElement.getKey().toString());
        }
    }

    public void updateScoreBoard() {
        for (Map.Entry mapElement : players.entrySet()) {
            String name = (String) mapElement.getKey();
            ActivePlayer activePlayer = (ActivePlayer) mapElement.getValue();
            Player player = activePlayer.player;
            if (player == null) {
                continue;
            }

            BuildSideBar(player);
        }
    }

    public void AddTeam(Team team) {
        teams.add(team);
        updateScoreBoard();
    }

    public void gameOver() {
        for (Map.Entry mapElement : players.entrySet()) {
            String name = (String) mapElement.getKey();
            ActivePlayer activePlayer = (ActivePlayer) mapElement.getValue();
            Player player = activePlayer.player;
            PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + "§2" + teams.get(0).getNonAbbreviatedName() + "" + "\"}"));
            PacketPlayOutTitle subTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + "§2have won" + "\"}"));
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
            PacketPlayOutTitle length = new PacketPlayOutTitle(5, 60, 5);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(subTitle);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(length);
            player.sendMessage(ChatColor.DARK_GREEN + teams.get(0).getNonAbbreviatedName() + " have won!");
            player.playSound(player.getLocation(), Sound.FIREWORK_LARGE_BLAST2, 10, 10);
            player.playSound(player.getLocation(), Sound.FIREWORK_LARGE_BLAST, 10, 10);
            player.playSound(player.getLocation(), Sound.FIREWORK_TWINKLE, 10, 10);
        }
    }

    public void removePlayerFromTeam(Team team) {
        team.aliveTeamPlayers = team.aliveTeamPlayers - 1;
        updateScoreBoard();
        if (team.aliveTeamPlayers == 0) {
            team.isDead = true;
            if (getLiveTeamsSize() == 1) {
                gameOver();
            }
        }
    }

    public int getLiveTeamsSize(){
        int count = 0;
        for (Team team : teams){
            if (team.isDead == false){
                count = count + 1;
            }
        }
        return count;
    }

    public void vanish(Player player) {
        PacketPlayOutPlayerInfo info = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, ((CraftPlayer) player).getHandle());
        ActivePlayer activePlayer = getActiveInstance(player.getName());
        player.setGameMode(GameMode.ADVENTURE);
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 99999999, 4, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999999, 4, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 99999999, 4, true));
        player.setCanPickupItems(false);
        player.setDisplayName(ChatColor.GRAY + "[Spec] " + player.getName());
        activePlayer.isVanished = true;
        player.setAllowFlight(true);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) player1).getHandle().playerConnection.sendPacket(info);
        }
    }

    public void unVanish(Player player) {
        PacketPlayOutPlayerInfo info = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ((CraftPlayer) player).getHandle());
        ActivePlayer activePlayer = getActiveInstance(player.getName());
        player.setGameMode(GameMode.SURVIVAL);
        player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        player.removePotionEffect(PotionEffectType.INVISIBILITY);
        player.removePotionEffect(PotionEffectType.SATURATION);
        player.setCanPickupItems(true);
        player.setDisplayName(player.getName());
        activePlayer.isVanished = false;
        player.setAllowFlight(false);
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) player1).getHandle().playerConnection.sendPacket(info);
        }
    }

    public void updateHealth(Player player, double d) {
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        if (player.hasPotionEffect(PotionEffectType.ABSORPTION)) {
            double health = Math.round(player.getHealth() + d + entityPlayer.getAbsorptionHearts());
            player.setPlayerListName(ChatColor.RED + player.getName() + "   " + ChatColor.YELLOW + health);
        } else {
            double health = Math.round(player.getHealth() + d + entityPlayer.getAbsorptionHearts());
            player.setPlayerListName(ChatColor.RED + player.getName() + "   " + ChatColor.YELLOW + health);
        }
    }

}
