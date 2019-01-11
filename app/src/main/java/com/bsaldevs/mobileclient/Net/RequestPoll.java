package com.bsaldevs.mobileclient.Net;

import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;

public class RequestPoll {

    private List<Request> requests;
    private Gson gson;
    private static RequestPoll requestPoll;
    private TCPConnection connection;

    private RequestPoll(TCPConnection connection) {
        requests = new ArrayList<>();
        this.connection = connection;
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void execute(Request request) {
        requests.add(request);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final String jsonRequest = gson.toJson(request);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                connection.sendString(jsonRequest);
            }
        });
        thread.start();
    }

    public void onReceiveResponse(String stringResponse) {

        Response response = gson.fromJson(stringResponse, Response.class);

        for (Request request : requests) {
            if (request.getId() == response.getRequestId()) {
                request.getServerCallback().onComplete(response);
            }
        }

    }

    public static RequestPoll getInstance(TCPConnection connection) {
        if (requestPoll != null)
            return requestPoll;
        else
            return new RequestPoll(connection);
    }

}
