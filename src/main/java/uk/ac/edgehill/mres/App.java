package uk.ac.edgehill.mres;

import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class App extends JavaPlugin implements Listener {

    private boolean stopRepeater = true;

    private String getTrialStartLocation(double x) {
        switch ((int) (x)) {
            case 91:
                return "N";
            case 102:
                return "E";
            case 104:
                return "W";
            case 114:
                return "S";
            default:
                return null;
        }
    }

    @Override
    public void onEnable() {
        getLogger().info("Starting event logging service...");
        // Register the event listener
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        final Player thePlayer = event.getPlayer();
        this.stopRepeater = true;
        Location playerSpawnLocation = new Location(thePlayer.getWorld(), thePlayer.getLocation().getBlockX(),
                thePlayer.getLocation().getBlockY(), thePlayer.getLocation().getBlockZ());
        getLogger().info(event.getPlayer().getName() + " has logged in");
        getLogger().info(
                "Welcome " + event.getPlayer().getName() + "!" + "Your current position is: " + playerSpawnLocation);
        thePlayer.sendMessage("Welcome to the TseLab Event Arena Simulation");
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            if (this.stopRepeater)
                logToFile(thePlayer, thePlayer.getLocation());
            scoreboardlogtoFile(thePlayer);
        }, 0L, 20L);
        ScoreboardManager sm = Bukkit.getScoreboardManager();
        Scoreboard s = sm.getMainScoreboard();
    }

    public void logToFile(Player currentPlayer, Location playerCurrentLocation) {
        try {
            File dataFolder = getDataFolder();
            if (!dataFolder.exists())
                dataFolder.mkdir();
            File saveTo = new File(getDataFolder(), currentPlayer.getPlayer().getName() + "_location.csv");
            if (!saveTo.exists()) {
                saveTo.createNewFile();
                FileWriter fw = new FileWriter(saveTo, true);
                PrintWriter pw = new PrintWriter(fw);
                pw.println(
                        "Date Time,Player Location X,Player Location Y,Player Location Z," +
                                "Player Pitch,Player Yaw,Looking At,Looking X,Looking Y,Looking Z," +
                                "Ground Height");
                pw.close();
            }

            int groundHeight = currentPlayer.getWorld().getHighestBlockYAt(playerCurrentLocation);
            Block currentWalkingBlock = currentPlayer.getWorld().getBlockAt(playerCurrentLocation.getBlockX(),
                    playerCurrentLocation.getBlockY() - 1, playerCurrentLocation.getBlockZ());

            LocalDateTime timenow = LocalDateTime.now();
            FileWriter fw = new FileWriter(saveTo, true);
            PrintWriter pw = new PrintWriter(fw);

            String lookingAtInfo = ",,,";
            Block targetBlock = currentPlayer.getTargetBlock(null, 100);
            if (targetBlock != null) {
                lookingAtInfo = targetBlock.getType().toString() + "," +
                        targetBlock.getX() + "," + targetBlock.getY() + "," + targetBlock.getZ();
            }

            pw.println(timenow + "," + playerCurrentLocation.getBlockX() + "," +
                    playerCurrentLocation.getBlockY() + "," + playerCurrentLocation.getBlockZ() + "," +
                    playerCurrentLocation.getPitch() + "," + playerCurrentLocation.getYaw() + "," +
                    lookingAtInfo + "," + groundHeight);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            File scoreboardsaveTo = new File(getDataFolder(),
                    currentPlayer.getPlayer().getDisplayName() + "_scoreboard.csv");
            if (!scoreboardsaveTo.exists()) {
                scoreboardsaveTo.createNewFile();
                FileWriter fw = new FileWriter(scoreboardsaveTo, true);
                PrintWriter pw = new PrintWriter(fw);
                pw.println(
                        "Date Time,No. of Errors,Time Errors,Trial Tracker,Distance," +
                                "Explore A1,Explore A2,Explore A3,Explore A4,Explore A5,Explore A6," +
                                "Explore B1,Explore B2,Explore B3,Explore B4,Explore B5,Explore B6," +
                                "Explore C1,Explore C2,Explore C3,Explore C4,Explore C5,Explore C6");
                pw.close();
            }

            LocalDateTime timenow = LocalDateTime.now();
            FileWriter fw = new FileWriter(scoreboardsaveTo, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(timenow + "," + scoreE.getScore() + "," + scoreT.getScore() + ","
                    + scoreTN.getScore() + "," + scoreD.getScore() + "," + scoreDWELL_A_L1.getScore() + ","
                    + scoreDWELL_A_L2.getScore() + "," + scoreDWELL_A_L3.getScore() + "," + scoreDWELL_A_L4.getScore()
                    + "," + scoreDWELL_A_L5.getScore() + "," + scoreDWELL_A_L6.getScore() + ","
                    + scoreDWELL_B_L1.getScore() + "," + scoreDWELL_B_L2.getScore() + "," + scoreDWELL_B_L3.getScore()
                    + "," + scoreDWELL_B_L4.getScore() + "," + scoreDWELL_B_L5.getScore() + ","
                    + scoreDWELL_B_L6.getScore() + "," + scoreDWELL_C_L1.getScore() + ","
                    + scoreDWELL_C_L2.getScore() + "," + scoreDWELL_C_L3.getScore() + ","
                    + scoreDWELL_C_L4.getScore() + "," + scoreDWELL_C_L5.getScore() + ","
                    + scoreDWELL_C_L6.getScore());
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        // Get the player who teleported
        Player player = event.getPlayer();
        // Get the from and to locations
        Location to = event.getTo();
        String startLocation = getTrialStartLocation(to.getX());
        // Log the teleport event
        getLogger().info(player.getName() + " teleported at " + startLocation);
    }

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        // Check if the entity is a player
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            Item item = event.getItem();
            if (item.getName().contains("Concrete Powder")) {
                getLogger().info("Picked up " + item.getName());
            }
            getLogger().info(System.currentTimeMillis() + ": " + player.getName() + " picked up " + item.getName());
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // Check if the action is a right-click on a block
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block clickedBlock = event.getClickedBlock();
            // Check if the block is a button
            if (clickedBlock != null && (clickedBlock.getType() == Material.STONE_BUTTON
                    || clickedBlock.getType() == Material.OAK_BUTTON)) {
                getLogger().info(
                        System.currentTimeMillis() + ": " + event.getPlayer().getName());
            }
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("Stopping event logging service...");
    }
}