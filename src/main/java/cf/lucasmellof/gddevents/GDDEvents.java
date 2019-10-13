package cf.lucasmellof.gddevents;

import cf.lucasmellof.gddevents.events.commandmanager.Manager;
import org.bukkit.plugin.java.JavaPlugin;

public final class GDDEvents extends JavaPlugin {
    private static Manager commandManager;
    @Override
    public void onEnable() {
        commandManager = new Manager(this);
    }

    @Override
    public void onDisable() {

    }
    public static Manager commandManager(){
        return commandManager;
    }
}
