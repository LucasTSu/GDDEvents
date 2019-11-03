package cf.lucasmellof.gddevents.events.mysterybox;

import cf.lucasmellof.gddevents.GDDEvents;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class BoxManager implements Listener {
    GDDEvents gddEvents;
    public boolean stop = false;

    public void load(GDDEvents events) {
        gddEvents = events;
        gddEvents.getServer().getPluginManager().registerEvents(this, gddEvents);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (stop && e.getClickedBlock().getType() == Material.CHEST) {
            e.setCancelled(true);
            new Animation(p, e.getClickedBlock().getLocation()).runTaskTimer(gddEvents, 0, 1);
        }
    }
}
