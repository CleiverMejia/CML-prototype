package lib;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import interpreter.Frame;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import parser.Block;
import parser.expresions.FuncExpr;
import parser.expresions.NumberExpr;
import parser.statements.ExternStmt;
import parser.statements.FunctionStmt;

public class Http extends FunctionStmt {

    public Http() {
        Block block;
        block = new Block(new ExternStmt() {
            @Override
            public void exec() {
                int port = (int) ((NumberExpr) Frame.get("port")).value;

                try {
                    HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

                    server.createContext("/", (HttpExchange exchange) -> {
                        String response = "Hola mundo";
                        exchange.sendResponseHeaders(200, response.length());

                        try (OutputStream os = exchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                    });

                    server.setExecutor(null);
                    server.start();
                    System.out.println("Servidor HTTP en http://localhost:" + port);
                } catch (IOException ex) {
                    System.getLogger(Http.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            }
        });

        FuncExpr function = new FuncExpr("http", block, "port");

        setFunction(function);
    }
}
