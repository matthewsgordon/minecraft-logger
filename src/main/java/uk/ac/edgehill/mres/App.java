package uk.ac.edgehill.mres;

import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class App extends JavaPlugin implements Listener {
     Logger trackerLogger = Bukkit.getLogger();

    boolean stopRepeater = true;

    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        this.trackerLogger.info("TRACKING PLUGIN INITIALISING");
    }

    public void onDisable() {
        this.trackerLogger.info("SHUTTING DOWN TRACKER...");
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        final Player thePlayer = event.getPlayer();
        this.stopRepeater = true;
        Location playerSpawnLocation = new Location(thePlayer.getWorld(), thePlayer.getLocation().getBlockX(), thePlayer.getLocation().getBlockY(), thePlayer.getLocation().getBlockZ());
        this.trackerLogger.info(event.getPlayer().getName() + " has logged in");
        this.trackerLogger.info("Welcome " + event.getPlayer().getName() + "!" + "Your current position is: " + playerSpawnLocation);
        thePlayer.sendMessage("Welcome to the TseLab Event Arena Simulation");
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            if (App.this.stopRepeater)
                App.this.logToFile(thePlayer, thePlayer.getLocation());
            App.this.scoreboardlogtoFile(thePlayer);
        }, 0L, 20L);
        ScoreboardManager sm = Bukkit.getScoreboardManager();
        Scoreboard s = sm.getMainScoreboard();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        this.stopRepeater = false;
        this.trackerLogger.info(event.getPlayer().getName() + " has left the game");
    }

    public void scoreboardlogtoFile(Player currentPlayer) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();
        Objective objectiveD = board.getObjective("distanceWalked");
        Objective objectiveE = board.getObjective("Error");
        Objective objectiveT = board.getObjective("Info");
        Objective objectiveTN = board.getObjective("Trial_Tracker");
        Objective objectiveDWELL = board.getObjective("explore_time");
        Score scoreD = objectiveD.getScore("Distance_Travelled");
        Score scoreE = objectiveE.getScore("Error_Tracker");
        Score scoreT = objectiveT.getScore("EA_TimeSECS");
        Score scoreTN = objectiveTN.getScore("Trial_Tracker");
        Score scoreDWELL_A_L1 = objectiveDWELL.getScore("A_L1");
        Score scoreDWELL_A_L2 = objectiveDWELL.getScore("A_L2");
        Score scoreDWELL_A_L3 = objectiveDWELL.getScore("A_L3");
        Score scoreDWELL_A_L4 = objectiveDWELL.getScore("A_L4");
        Score scoreDWELL_A_L5 = objectiveDWELL.getScore("A_L5");
        Score scoreDWELL_A_L6 = objectiveDWELL.getScore("A_L6");
        Score scoreDWELL_B_L1 = objectiveDWELL.getScore("B_L1");
        Score scoreDWELL_B_L2 = objectiveDWELL.getScore("B_L2");
        Score scoreDWELL_B_L3 = objectiveDWELL.getScore("B_L3");
        Score scoreDWELL_B_L4 = objectiveDWELL.getScore("B_L4");
        Score scoreDWELL_B_L5 = objectiveDWELL.getScore("B_L5");
        Score scoreDWELL_B_L6 = objectiveDWELL.getScore("B_L6");
        Score scoreDWELL_C_L1 = objectiveDWELL.getScore("C_L1");
        Score scoreDWELL_C_L2 = objectiveDWELL.getScore("C_L2");
        Score scoreDWELL_C_L3 = objectiveDWELL.getScore("C_L3");
        Score scoreDWELL_C_L4 = objectiveDWELL.getScore("C_L4");
        Score scoreDWELL_C_L5 = objectiveDWELL.getScore("C_L5");
        Score scoreDWELL_C_L6 = objectiveDWELL.getScore("C_L6");


        try {
            File scoreboardFolder = getDataFolder();
            if (!scoreboardFolder.exists())
                scoreboardFolder.mkdir();
            File scoreboardsaveTo = new File(getDataFolder(), currentPlayer.getPlayer().getDisplayName() + "_scoreboard.log");
            if (!scoreboardsaveTo.exists())
                scoreboardsaveTo.createNewFile();
            Date nowDate = new Date();
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            FileWriter fw = new FileWriter(scoreboardsaveTo, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(format.format(nowDate) + " Error: " + scoreE.getScore() + " Time: " + scoreT.getScore() + " Trial_Tracker: " + scoreTN.getScore() + " Distance: " + scoreD.getScore() + " Explore_A1: " + scoreDWELL_A_L1.getScore() + " Explore_A2: " + scoreDWELL_A_L2.getScore() + " Explore_A3: " + scoreDWELL_A_L3.getScore() + " Explore_A4: " + scoreDWELL_A_L4.getScore() + " Explore_A5: " + scoreDWELL_A_L5.getScore() + " Explore_A6: " + scoreDWELL_A_L6.getScore() + " Explore_B1: " + scoreDWELL_B_L1.getScore() + " Explore_B2: " + scoreDWELL_B_L2.getScore() + " Explore_B3: " + scoreDWELL_B_L3.getScore() + " Explore_B4: " + scoreDWELL_B_L4.getScore() + " Explore_B5: " + scoreDWELL_B_L5.getScore() + " Explore_B6: " + scoreDWELL_B_L6.getScore() + " Explore_C1: " + scoreDWELL_C_L1.getScore() + " Explore_C2: " + scoreDWELL_C_L2.getScore() + " Explore_C3: " + scoreDWELL_C_L3.getScore() + " Explore_C4: " + scoreDWELL_C_L4.getScore() + " Explore_C5: " + scoreDWELL_C_L5.getScore() + " Explore_C6: " + scoreDWELL_C_L6.getScore());
            // pw.println(format.format(nowDate) + "EA_Distance: " + Bukkit.getScoreboardManager().getMainScoreboard().getObjective("EA_Distance").getScore("NeuronSafari") + " Errors: " + currentPlayer.getScoreboard().getObjective("Errors").getScore("NeuronSafari") + " Time_Taken: " + currentPlayer.getScoreboard().getObjective("Time_Taken").getScore("NeuronSafari"));
            //pw.println(format.format(nowDate) + "EA_Distance: " + currentPlayer.getPlayer().getScoreboard().getScores("EA_Distance") + " Errors: " + currentPlayer.getPlayer().getScoreboard().getScores("Errors") + " Time_Taken: " + currentPlayer.getPlayer().getScoreboard().getScores("Time_Taken"));
            pw.close();
        }   catch (IOException e) {
            e.printStackTrace();
        }       
    }
    public void logToFile(Player currentPlayer, Location playerCurrentLocation) {
        try {
            File dataFolder = getDataFolder();
            if (!dataFolder.exists())
                dataFolder.mkdir();
            File saveTo = new File(getDataFolder(), currentPlayer.getPlayer().getName() + "_location.log");
            if (!saveTo.exists())
                saveTo.createNewFile();
            int groundHeight = currentPlayer.getWorld().getHighestBlockYAt(playerCurrentLocation);
            Block currentWalkingBlock = currentPlayer.getWorld().getBlockAt(playerCurrentLocation.getBlockX(), playerCurrentLocation.getBlockY() - 1, playerCurrentLocation.getBlockZ());

            Date nowDate = new Date();
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            FileWriter fw = new FileWriter(saveTo, true);
            PrintWriter pw = new PrintWriter(fw);

            String lookingAtInfo = "None";
            Block targetBlock = currentPlayer.getTargetBlock(null, 100);
            if (targetBlock != null) {
                lookingAtInfo = targetBlock.getType() + " at (" +
                        targetBlock.getX() + ", " + targetBlock.getY() + ", " + targetBlock.getZ() + ")";
            }

            pw.println(format.format(nowDate) + " CurrentLocation(x,y,z): " + playerCurrentLocation.getBlockX() + " " +
                    playerCurrentLocation.getBlockY() + " " + playerCurrentLocation.getBlockZ() + " Head_Direction: " +
                    playerCurrentLocation.getPitch() + " " + playerCurrentLocation.getYaw() + " Looking At: " + lookingAtInfo);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();
        Objective objectiveD = board.getObjective("distanceWalked");
        Objective objectiveE = board.getObjective("Error");
        Objective objectiveT = board.getObjective("Info");
        Score scoreD = objectiveD.getScore("Distance_Travelled");
        Score scoreE = objectiveE.getScore("Error_Tracker");
        Score scoreT = objectiveT.getScore("EA_TimeSECS");
        // Check if the action is a right-click on a block
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block clickedBlock = event.getClickedBlock();
            // Check if the block is a button
            if (clickedBlock != null && (clickedBlock.getType() == Material.STONE_BUTTON
                    || clickedBlock.getType() == Material.OAK_BUTTON)) {
                getLogger().info(System.currentTimeMillis() + ": " + event.getPlayer().getName());
                try {
                    File scoreboardFolder = getDataFolder();
                    if (!scoreboardFolder.exists())
                        scoreboardFolder.mkdir();
                    File scoreboardsaveTo = new File(getDataFolder(), event.getPlayer().getDisplayName() + "_quickstats.csv");
                    if (!scoreboardsaveTo.exists())
                        scoreboardsaveTo.createNewFile();
                    Date nowDate = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    FileWriter fw = new FileWriter(scoreboardsaveTo, true);
                    PrintWriter pw = new PrintWriter(fw);
                    pw.println(format.format(nowDate) + " Error: " + scoreE.getScore() + " Time: " + scoreT.getScore() + " Distance: " + scoreD.getScore());
                    pw.close();
                }   catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}