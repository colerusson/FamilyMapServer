package handlers;
import java.io.*;
import java.net.*;
import java.nio.file.Files;

import com.sun.net.httpserver.*;

public class FileHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                // Get the HTTP request headers
                String urlPath = exchange.getRequestURI().toString();
                String filePath;
                if (urlPath == null || urlPath.equals("/") || urlPath.equals("")) {
                    filePath = "web" + "/index.html";
                }
                else {
                    filePath = "web" + urlPath;
                }
                File file = new File(filePath);
                // Check to see if an "Authorization" header is present
                if (file.exists()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                    OutputStream respBody = exchange.getResponseBody();

                    Files.copy(file.toPath(), respBody);

                    // Close the output stream.  This is how Java knows we are done
                    // sending data and the response is complete.
                    respBody.close();
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    String errorFilePath = "web/HTML/404.html";
                    file = new File(errorFilePath);
                    Files.copy(file.toPath(), respBody);
                    respBody.close();
                }
            }
        }
        catch (IOException e) {
            // Some kind of internal error has occurred inside the server (not the
            // client's fault), so we return an "internal server error" status code
            // to the client.
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            // Since the server is unable to complete the request, the client will
            // not receive the list of games, so we close the response body output stream,
            // indicating that the response is complete.
            exchange.getResponseBody().close();

            // Display/log the stack trace
            e.printStackTrace();
        }
    }

}
