package cf.lucasmellof.gddevents;

import cf.lucasmellof.gddevents.events.calculator.CalcManager;
import cf.lucasmellof.gddevents.events.commandmanager.CMDManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class GDDEvents extends JavaPlugin {
    //private static CMDManager commandManager;
    @Override
    public void onEnable() {
        //commandManager = new CMDManager(this);
        new CalcManager(this);
    }

    @Override
    public void onDisable() {

    }
    /*public static CMDManager commandManager(){
        return commandManager;
    }
    */
}
