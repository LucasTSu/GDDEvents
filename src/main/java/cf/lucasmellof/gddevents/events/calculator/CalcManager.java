package cf.lucasmellof.gddevents.events.calculator;

import cf.lucasmellof.gddevents.GDDEvents;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CalcManager implements CommandExecutor {
    GDDEvents gddEvents;

    public CalcManager(GDDEvents gddEvents) {
        this.gddEvents = gddEvents;
        gddEvents.getCommand("calc").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String math = String.join(" ", args);
        sender.sendMessage("§eCalculadora");
        if(math.isEmpty() || math == null){
            sender.sendMessage("§c ! Você precisa inserir valores para que a calculadora funcionar");
        }else{
            sender.sendMessage("§eExpressão:§f " + math);
            sender.sendMessage("§e... Calculando ...");
            Calculator calculator = new Calculator();
            try {
                sender.sendMessage("§e -> Resultado: " +calculator.runner(math));
            } catch (Exception e) {
                sender.sendMessage("§c ! Houve um erro ao calcular sua expressão");
                e.printStackTrace();
            }
            sender.sendMessage("§eCalculado em §a"+ (System.currentTimeMillis() - calculator.milis)+"ms");
        }
        return false;
    }
}
