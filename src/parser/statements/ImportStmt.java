package parser.statements;

import interpreter.Interpreter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import lexer.Lexer;
import parser.Block;
import parser.Parser;
import parser.interfaces.Stmt;

public class ImportStmt implements Stmt {

    private final String path;

    public ImportStmt(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public Block resolve() {
        if (Interpreter.imports.stream().anyMatch(imp -> imp.equals(path))) {
            return null;
        }

        Interpreter.imports.add(path);

        try {
            String pathParent = Paths.get(path).getParent().toString();
            String filePath = Paths.get(path).getFileName().toString();
            File dir = new File(Interpreter.sourcePath + "/" + pathParent);
            URL url = dir.toURI().toURL();

            Block block = new Block();
            Stmt stmt;

            try (URLClassLoader loader = new URLClassLoader(new URL[]{url})) {
                Class<?> clazz = loader.loadClass(filePath);
                stmt = (Stmt) clazz.getDeclaredConstructor().newInstance();

                block.add(stmt);
            }

            return block;
        } catch (MalformedURLException | ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            throw new Error(e);
        } catch (IOException e) {
        }

        try {
            Lexer lexer = new Lexer(String.format("%s/%s.cml", Interpreter.sourcePath, path));
            lexer.run();

            Parser parser = new Parser(lexer.getTokens());
            parser.run();

            return parser.getMain();
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    @Override
    public String toString() {
        return "Stmt<Import>";
    }
}
