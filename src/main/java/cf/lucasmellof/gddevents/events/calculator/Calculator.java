package cf.lucasmellof.gddevents.events.calculator;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Calculator {

    //Using https://github.com/EngineHub/WorldEdit/blob/master/worldedit-core/src/main/java/com/sk89q/worldedit/command/UtilityCommands.java and https://github.com/EngineHub/WorldEdit/blob/master/worldedit-core/src/main/java/com/sk89q/worldedit/internal/expression/Expression.java
    // to know how to start

    // Using Executors because is a better framework to work with Threads
    ExecutorService executor;
    long milis;
    static ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("JavaScript");

    public Calculator() {
        milis = System.currentTimeMillis();
        executor = Executors.newSingleThreadExecutor();
        scriptEngine.put("pi", Math.PI);
        scriptEngine.put("e", Math.E);
    }

    public String runner(String math) throws Exception {
        Future<String> submit = executor.submit(new CalculatorCallable(math));
        return submit.get();
    }
}

class CalculatorCallable implements Callable<String> {
    String math;

    public CalculatorCallable(String math) {
        this.math = math;
    }

    @Override
    public String call() throws Exception {
        Object result = Calculator.scriptEngine.eval(math);
        return result.toString();
    }
}
