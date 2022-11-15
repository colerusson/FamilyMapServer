package handlers;
import java.io.*;
import java.net.*;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;
import dao.DataAccessException;
import helpers.HandlerHelper;
import request.FillRequest;
import result.FillResult;
import services.FillService;

public class FillHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {

            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                HandlerHelper handlerHelper = new HandlerHelper();
                String urlString = exchange.getRequestURI().toString();
                String username = urlString.split("/")[2];
                int generations = Integer.parseInt(urlString.split("/")[3]);

                FillRequest fillRequest = new FillRequest();
                fillRequest.setUsername(username);
                fillRequest.setGenerations(generations);
                FillService fillService = new FillService();
                FillResult fillResult = fillService.fill(fillRequest);

                if (fillResult.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }

                Gson gson = new Gson();
                OutputStream resBody = exchange.getResponseBody();
                String respData = gson.toJson(fillResult);
                handlerHelper.writeString(respData, resBody);
                resBody.close();
            }
        }
        catch (IOException e) {
            // Some kind of internal error has occurred inside the server (not the
            // client's fault), so we return an "internal server error" status code
            // to the client.
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);

            // We are not sending a response body, so close the response body
            // output stream, indicating that the response is complete.
            exchange.getResponseBody().close();

            // Display/log the stack trace
            e.printStackTrace();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
