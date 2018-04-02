package se.ade.minecraft.adeplugin;

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
        s.sendMessage("§6God mode §c" + (enabled ? "enabled" : "disabled") + "§6.");
        return true;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
    public void onDamage(EntityDamageEvent e) {
        if (enabled && e.getEntity() instanceof Player) {
            Player player = (Player)e;
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