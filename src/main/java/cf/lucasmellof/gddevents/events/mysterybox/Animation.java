package cf.lucasmellof.gddevents.events.mysterybox;

import cf.lucasmellof.gddevents.utils.NMSUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class Animation extends BukkitRunnable {

    private final Player player;

    private ArmorStand armorStand;

    private Location loc;
    private final Location chestLocation;

    private int tick;
    private double t;
    private double r;

    public Animation(Player player, Location loc) {
        chestLocation = new Location(loc.getWorld(), loc.getBlockX() + 0.5D, loc.getBlockY() + 0.0D, loc.getBlockZ() + 0.5D);
        this.player = player;
        this.loc = new Location(loc.getWorld(), loc.getBlockX() + 0.5D, loc.getBlockY() + 0.0D, loc.getBlockZ() + 0.5D);
        loc.setYaw(player.getLocation().getYaw() + (player.getLocation().getYaw() < 0.0F ? -180.0F : 180.0F));
        armorStand = spawnHead(chestLocation, new ItemStack(Material.CHEST));
        tick = 0;
        t = 0;
        r = 1;
    }

    public void run() {
        tick += 1;
        playCircle();
        if (tick == 1) {
            NMSUtil.playChestAction(chestLocation, true, true, player);
        } else if (tick > 1 && tick < 80) {
            Particle.FIREWORKS_SPARK.builder().offset(0.0F, 0.0F, 0.0F).count(2).location(armorStand.getEyeLocation()).spawn();
            loc.getWorld().playSound(armorStand.getEyeLocation(), Sound.BLOCK_NOTE_PLING, 1.0F, 2.0F);
            armorStand.teleport(armorStand.getLocation().subtract(0.0D, 0.025D, 0.0D));
        } else if (tick >= 80 && tick < 130) {
            NMSUtil.playChestAction(chestLocation, true, false, player);
            Particle.CLOUD.builder().offset(0.0F, 0.0F, 0.0F).count(60).location(chestLocation).spawn();
        } else if (tick == 135) {
            loc.getWorld().playSound(armorStand.getEyeLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0F, 2.0F);
            Particle.EXPLOSION_LARGE.builder().offset(0.0F, 0.0F, 0.0F).count(60).location(chestLocation).spawn();
        } else if (tick >= 150 && tick < 220) {
            Particle.CRIT_MAGIC.builder().offset(0.0F, 0.0F, 0.0F).count(5).location(chestLocation).spawn();
            Particle.EXPLOSION_NORMAL.builder().offset(1.0F, 0.5F, 1.0F).count(1).location(chestLocation).spawn();
        } else if (tick == 220) {
            NMSUtil.playChestAction(chestLocation, true, true, player);
            armorStand.setHelmet(new ItemStack(Material.DIAMOND_SWORD));
        } else if (tick > 220 && tick < 250) {
            armorStand.teleport(armorStand.getLocation().add(0.0D, 0.05D, 0.0D));
            armorStand.setCustomName("§fUm Item qualquer");
            armorStand.setCustomNameVisible(true);
        } else if (tick >= 310) {
            armorStand.remove();
            NMSUtil.playChestAction(chestLocation, true, false, player);
            cancel();
        }
    }

    private void playCircle() {
        if (tick >= 150 && t <= Math.PI * 4) {
            t = t + Math.PI / 16;
            double x = r * Math.cos(t);
            double y = t * 0.05;
            double z = r * Math.sin(t);
            Particle.VILLAGER_HAPPY.builder().offset(0.0F, 0.0F, 0.0F).count(1).location(chestLocation.clone().add(x, y, z)).spawn();
        }
    }

    private ArmorStand spawnHead(Location location, ItemStack itemStack) {
        ArmorStand stand = location.getWorld().spawn(location, ArmorStand.class);
        stand.setHelmet(itemStack);
        stand.setGravity(false);
        stand.setVisible(false);
        return stand;
    }
}