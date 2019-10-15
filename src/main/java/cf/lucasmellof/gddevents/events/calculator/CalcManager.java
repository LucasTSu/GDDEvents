package cf.lucasmellof.gddevents.events.calculator;

import cf.lucasmellof.gddevents.GDDEvents;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Manager implements CommandExecutor {
    GDDEvents gddEvents;

    public Manager(GDDEvents gddEvents) {
        this.gddEvents = gddEvents;
        gddEvents.getCommand("calc").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("§eCalculadora");
        sender.sendMessage("§eExpressão:§f " + String.join(" ", args));
        sender.sendMessage("§e... Calculando ...");
        Calculator calculator = new Calculator();
        try {
            sender.sendMessage("§e -> Resultado: " +calculator.runner(String.join(" ", args)));
        } catch (Exception e) {
            sender.sendMessage("§cHouve um erro ao calcular sua expressão");
        }
        sender.sendMessage("§eCalculado em §a"+ (System.currentTimeMillis() - calculator.milis)+"ms");
        return false;
    }
}
