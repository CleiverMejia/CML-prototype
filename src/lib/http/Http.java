package lib.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import parser.Block;
import parser.expresions.ClassExpr;
import parser.expresions.FuncExpr;
import parser.statements.ClassStmt;
import parser.statements.ExternStmt;
import parser.statements.FunctionStmt;

public class Http extends ClassStmt {

    HttpServer server;
    ArrayList<PathInfo> pathInfos = new ArrayList<>();

    public Http() {
        Block block = new Block(
                get(),
                listen()
        );

        ClassExpr clss = new ClassExpr("Http", block);

        setClss(clss);
    }

    private FunctionStmt get() {
        Block block = new Block(new ExternStmt() {
            @Override
            public void exec() {
                String path = resolveArgString("path");
                String resp = resolveArgString("resp");
                String type = resolveArgString("type");
                int code = (int) resolveArgNumber("status");

                pathInfos.add(new PathInfo(path, resp, type, code));
            }
        });

        FuncExpr function = new FuncExpr("get", block, "path", "resp", "type", "status");

        return new FunctionStmt(function);
    }

    private FunctionStmt listen() {
        Block block = new Block(new ExternStmt() {
            @Override
            public void exec() {
                try {
                    int port = (int) resolveArgNumber("port");
                    server = HttpServer.create(new InetSocketAddress(port), 0);

                    for (PathInfo pathInfo : pathInfos) {
                        server.createContext(pathInfo.path, (HttpExchange exchange) -> {
                            String response = pathInfo.respose;
                            exchange.sendResponseHeaders(pathInfo.statusCode, response.length());
                            exchange.getResponseHeaders().set("Content-Type", pathInfo.contentType + "; charset=UTF-8");

                            try (OutputStream os = exchange.getResponseBody()) {
                                os.write(response.getBytes());
                            }
                        });
                    }

                    server.setExecutor(null);
                    server.start();
                    System.out.printf("Servidor HTTP en http://localhost:%d\n", port);
                } catch (IOException e) {
                    System.getLogger(Http.class.getName()).log(System.Logger.Level.ERROR, (String) null, e);
                }

            }
        });

        FuncExpr function = new FuncExpr("listen", block, "port");

        return new FunctionStmt(function);
    }

    private class PathInfo {

        public final String path;
        public final String respose;
        public final String contentType;
        public final int statusCode;

        public PathInfo(String path, String response, String contentType, int statusCode) {
            this.path = path;
            this.respose = response;
            this.contentType = contentType;
            this.statusCode = statusCode;
        }
    }
}
