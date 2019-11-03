package cf.lucasmellof.gddevents.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class NMSUtil {

    /**
     * Plays the chest animation to display it in an open or closed state.
     *
     * @param loc  chest we want to open or close.
     * @param open true if we want to open the chest, false if we want to close the chest.
     */
    public static void playChestAction(Location loc, boolean enderChest, boolean open, Player p) {
        PacketContainer chest = new PacketContainer(PacketType.Play.Server.BLOCK_ACTION);
        if (enderChest) {
            chest.getBlocks().write(0, Material.ENDER_CHEST);
        }else{
            chest.getBlocks().write(0, Material.CHEST);
        }
        chest.getBlockPositionModifier().write(0, new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
        chest.getIntegers().write(0, 1);
        chest.getIntegers().write(1, open ? 1 : 0); //1 for open, 0 for close

        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(p, chest, true);
        } catch (InvocationTargetException ex) {
            throw new IllegalStateException("Unable to send packet " + chest, ex);
        }
    }
}