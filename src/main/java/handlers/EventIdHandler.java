package handlers;
import java.io.*;
import java.net.*;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;
import dao.DataAccessException;
import helpers.AuthTokenService;
import helpers.HandlerHelper;
import request.EventIdRequest;
import result.EventIdResult;
import result.PersonIdResult;
import services.EventIdService;

public class EventIdHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                // Get the HTTP request headers
                Headers reqHeaders = exchange.getRequestHeaders();
                // Check to see if an "Authorization" header is present
                if (reqHeaders.containsKey("Authorization")) {
                    // Extract the auth token from the "Authorization" header
                    String authToken = reqHeaders.getFirst("Authorization");
                    AuthTokenService authtokenService = new AuthTokenService();
                    if (authtokenService.verify(authToken)) {
                        String urlString = exchange.getRequestURI().toString();
                        String eventId = urlString.split("/")[2];
                        HandlerHelper handlerHelper = new HandlerHelper();
                        EventIdRequest eventIdRequest = new EventIdRequest();
                        eventIdRequest.setEventID(eventId);
                        EventIdService eventIdService = new EventIdService();
                        EventIdResult eventIdResult = eventIdService.eventIdService(eventIdRequest, authToken);

                        if (eventIdResult.isSuccess()) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        }
                        else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        }

                        Gson gson = new Gson();
                        String respData = gson.toJson(eventIdResult);
                        OutputStream respBody = exchange.getResponseBody();
                        // Write the JSON string to the output stream.
                        handlerHelper.writeString(respData, respBody);
                        // function to write the string of response data back to server
                        // Close the output stream.  This is how Java knows we are done
                        // sending data and the response is complete.
                        respBody.close();
                    }
                    else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

                        EventIdResult eventIdResult = new EventIdResult();
                        HandlerHelper handlerHelper = new HandlerHelper();
                        Gson gson = new Gson();

                        eventIdResult.setSuccess(false);
                        eventIdResult.setMessage("Error: Authtoken not valid");
                        String respData = gson.toJson(eventIdResult);

                        OutputStream respBody = exchange.getResponseBody();
                        handlerHelper.writeString(respData, respBody);
                        respBody.close();
                    }
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

                    PersonIdResult personIdResult = new PersonIdResult();
                    HandlerHelper handlerHelper = new HandlerHelper();
                    Gson gson = new Gson();

                    personIdResult.setSuccess(false);
                    personIdResult.setMessage("Error: Authtoken not found in request");
                    String respData = gson.toJson(personIdResult);

                    OutputStream respBody = exchange.getResponseBody();
                    handlerHelper.writeString(respData, respBody);
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
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
    
}
