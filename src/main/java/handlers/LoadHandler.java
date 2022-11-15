package handlers;
import java.io.*;
import java.net.*;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;
import dao.DataAccessException;
import helpers.HandlerHelper;
import request.LoadRequest;
import result.LoadResult;
import services.LoadService;

public class LoadHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                Gson gson = new Gson();
                HandlerHelper handlerHelper = new HandlerHelper();
                String reqData = handlerHelper.readString(exchange.getRequestBody());
                LoadRequest loadRequest = gson.fromJson(reqData, LoadRequest.class);
                LoadService loadService = new LoadService();
                LoadResult loadResult = loadService.load(loadRequest);

                if (loadResult.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }

                OutputStream resBody = exchange.getResponseBody();
                String respData = gson.toJson(loadResult);
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
