package uk.ac.edgehill.mres;

import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
// import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin implements Listener {

    private enum TrialStatus {
        TRIAL_STOPPED,
        TRIAL_IN_PROGRESS;
    }

    private TrialStatus trialStatus;
    private long trialStartTime;
    private GameEventRepository gameRepo;

    private String getTrialStartLocation(double x) {
        switch ((int)(x)) {
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
        trialStatus = TrialStatus.TRIAL_STOPPED;
        getLogger().info("Connecting to game event repository...");
        gameRepo = new GameEventRepository();
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
                trialStartTime = System.currentTimeMillis();
                trialStatus = TrialStatus.TRIAL_IN_PROGRESS;
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
            if (clickedBlock != null && (clickedBlock.getType() == Material.STONE_BUTTON || clickedBlock.getType() == Material.OAK_BUTTON)) {
                double trialTime = (double)(System.currentTimeMillis() - trialStartTime) / 1000.0;
                getLogger().info(System.currentTimeMillis() + ": " + event.getPlayer().getName() + " trail took " + trialTime);
                trialStatus = TrialStatus.TRIAL_STOPPED;
            }
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("Stopping event logging service...");
    }
}