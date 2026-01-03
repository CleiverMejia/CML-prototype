
import interpreter.Interpreter;
import lexer.Lexer;
import parser.Parser;

public class App {

    public static void main(String[] args) throws Exception {
        /* if (args.length == 0) {
            throw new Exception("Missing arguments");
        }

        Lexer lexer = new Lexer(args[0]); */
        Lexer lexer = new Lexer("./assets/helloWorld.cml");
        lexer.run();

        Parser parser = new Parser(lexer.getTokens());
        parser.run();

        /* Semantic semantic = new Semantic();
        semantic.run(parser.getMain()); */

        Interpreter.run(parser.getMain());

    }
}
