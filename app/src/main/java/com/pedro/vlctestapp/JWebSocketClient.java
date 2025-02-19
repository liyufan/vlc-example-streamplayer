package com.pedro.vlctestapp;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class JWebSocketClient extends WebSocketClient {
    private static final String TAG = "JWebSocketClient";

    public JWebSocketClient(URI serverUri) {
        super(serverUri, new Draft_6455());
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
       //Log.e(TAG, "onOpen()");
    }

    @Override
    public void onMessage(String message) {
       //Log.e(TAG, "OnMessage()");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
       //Log.e(TAG, "onClose()");
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
       //Log.e(TAG, "onError()");
    }
}


