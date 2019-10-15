package cf.lucasmellof.gddevents.events.commandmanager;

import cf.lucasmellof.gddevents.GDDEvents;
import cf.lucasmellof.gddevents.utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Manager implements Listener, CommandExecutor {
    public HashMap<String, Integer> commandtop = new HashMap<>();
    GDDEvents main;
    ConfigManager configdata;

    public Manager(GDDEvents main) {
        main = main;
        loadConfig();
        main.getCommand("ctop").setExecutor(this);
        Bukkit.getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onServerCommand(ServerCommandEvent e) {
        String cmd = "";
        if (e.getCommand().contains(" ")) {
            cmd = e.getCommand().toLowerCase().split(" ")[0].trim();
        } else {
            cmd = e.getCommand().toLowerCase().trim();
        }
        cmd += "/";
        commandtop.put(cmd, commandtop.getOrDefault(cmd, 0) + 1);
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
        String cmd = "";
        if (e.getMessage().contains(" ")) {
           cmd = e.getMessage().toLowerCase().split(" ")[0].trim();
        } else {
            cmd = e.getMessage().toLowerCase().trim();
        }
        commandtop.put(cmd, commandtop.getOrDefault(cmd, 0) + 1);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            Map<String, Integer> collect = commandtop.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(10).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (cmd, vzs) -> {
                throw new IllegalStateException();
            }, LinkedHashMap::new));
            AtomicInteger i = new AtomicInteger(0);
            p.sendMessage("§e§lComandos top:");
            collect.forEach((cmd, vzs) -> {
                i.getAndAdd(1);
                p.sendMessage("§f" + i + "§eº - §f" + cmd.toLowerCase() + "§e - §f" + vzs + " §evzs");
            });
        } else {
            sender.sendMessage("Comando apenas para players");
            return true;
        }
        return false;
    }
    public void loadConfig(){
        configdata = new ConfigManager(main,"commands-data.yml");
        if(!configdata.existsConfig()) configdata.saveDefaultConfig();
        configdata.getConfig().getStringList("Commands").forEach(data -> {
            String[] commands = data.split(" ");
            commandtop.put(commands[0], Integer.valueOf(commands[1]));
        });
    }
}
