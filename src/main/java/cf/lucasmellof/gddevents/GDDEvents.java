package cf.lucasmellof.gddevents;

import cf.lucasmellof.gddevents.events.mysterybox.BoxManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class GDDEvents extends JavaPlugin {
    //private static CMDManager commandManager;
    @Override
    public void onEnable() {
        //commandManager = new CMDManager(this);
        //new CalcManager(this);
        new BoxManager().load(this);
    }

    @Override
    public void onDisable() {

    }
    /*public static CMDManager commandManager(){
        return commandManager;
    }
    */
}
