package lib.file;

import interpreter.CallStack;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import parser.Block;
import parser.expresions.FuncExpr;
import parser.expresions.StringExpr;
import parser.statements.ExternStmt;
import parser.statements.FunctionStmt;

public class File extends FunctionStmt {

    public File() {
        Block block = new Block(new ExternStmt() {
            @Override
            public void exec() {
                String path = resolveArgString("path");

                try {
                    String file = new String(
                            Files.readAllBytes(Path.of(path)),
                            StandardCharsets.UTF_8
                    );

                    CallStack.setReturn(new StringExpr(file));
                } catch (IOException ex) {
                    System.getLogger(File.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            }
        });

        FuncExpr function = new FuncExpr("file", block, "path");

        setFunction(function);
    }
}
