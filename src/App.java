
import interpreter.Interpreter;
import lexer.Lexer;
import parser.Parser;

public class App {

    public static void main(String[] args) throws Exception {
        /* if (args.length == 0) {
            throw new Exception("Missing arguments");
        }

        Lexer lexer = new Lexer(args[0]); */

        long inicio = System.nanoTime();
        Lexer lexer = new Lexer("./examples/helloWorld.cml");
        lexer.run();

        Parser parser = new Parser(lexer.getTokens());
        parser.run();

        Interpreter.run(parser.getMain());

        long fin = System.nanoTime();
        long duracionNs = fin - inicio;
        double duracionMs = duracionNs / 1_000_000.0;

        System.out.println("time: " + duracionMs + " ms");
    }
}
