package se.ade.minecraft.opgodmode;

import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class OpGodMode extends JavaPlugin implements CommandExecutor, Listener {
    private boolean enabled = false;

    @Override
    public void onEnable() {
        getCommand("god").setExecutor(this);
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("AdminGodMode running");
    }

    @Override
    public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
        if(!s.isOp()) {
            return true;
        }

        enabled = !enabled;
        s.sendMessage(ChatColor.WHITE + "God mode " + (enabled ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled") + ChatColor.WHITE + ".");
        return true;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
    public void onDamage(EntityDamageEvent e) {
        if (enabled && e.getEntity() instanceof Player) {
            Player player = (Player)e.getEntity();
            if(player.isOp()) {
                e.setCancelled(true);
                player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {

    }
}