package com.example.tvreceiver;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import java.net.InetSocketAddress;

import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

public class MainActivity extends Activity {

    private TextView status;
    private WebSocketServer server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(24, 24, 24, 24);
        layout.setBackgroundColor(Color.rgb(20, 25, 35));

        TextView title = new TextView(this);
        title.setText("TCL WiFi Gamepad Receiver");
        title.setTextColor(Color.WHITE);
        title.setTextSize(24);
        title.setGravity(Gravity.CENTER);

        status = new TextView(this);
        status.setTextColor(Color.GREEN);
        status.setTextSize(18);
        status.setGravity(Gravity.CENTER);

        layout.addView(title);
        layout.addView(status);
        setContentView(layout);

        startServer();
    }

    private void startServer() {
        try {
            WifiManager wifi = (WifiManager) getApplicationContext()
                .getSystemService(WIFI_SERVICE);

            int ip = wifi.getConnectionInfo().getIpAddress();
            String address = Formatter.formatIpAddress(ip);

            server = new WebSocketServer(new InetSocketAddress(8765)) {

                @Override
                public void onOpen(WebSocket conn, ClientHandshake handshake) {
                    runOnUiThread(() -> status.setText("Phone connected!"));
                }

                @Override
                public void onClose(WebSocket conn, int code, String reason, boolean remote) {
                    runOnUiThread(() -> status.setText("Waiting for phone..."));
                }

                @Override
                public void onMessage(WebSocket conn, String message) {
                    runOnUiThread(() -> status.setText("Received: " + message));
                }

                @Override
                public void onError(WebSocket conn, Exception ex) {
                    runOnUiThread(() -> status.setText("Server error: " + ex.getMessage()));
                }

                @Override
                public void onStart() {
                    runOnUiThread(() -> status.setText(
                        "Wi-Fi Server Ready\nIP: " + address + "\nPort: 8765"
                    ));
                }
            };

            server.start();

        } catch (Exception e) {
            status.setText("Error: " + e.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        if (server != null) {
            try {
                server.stop();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        super.onDestroy();
    }
}
