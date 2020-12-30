package me.Mark.qr;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class WebServer extends WebSocketServer {
	
	public static ArrayList<WebSocket> online = new ArrayList<WebSocket>();
	
	public WebServer(int port) throws UnknownHostException {
		super(new InetSocketAddress(port));
	}

	public WebServer(InetSocketAddress address) {
		super(address);
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		online.add(conn);
		Thingy.join(conn, handshake);
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		online.remove(conn);
		try {
			Thingy.leave(conn, code, reason, remote);
		} catch (Exception e) {}
	}

	@Override
	public void onMessage(WebSocket conn, String msg) {
		try {
			Thingy.stuff(conn, msg);
		} catch (FileNotFoundException e) {} catch (UnsupportedEncodingException e) {} catch (IOException e) {}
	}

	static WebServer server;
	
	public static void start(String[] args) throws Exception {
		WebSocketImpl.DEBUG = true;
		WebServer s = new WebServer(8887);
		server = s;
		s.start();
		Thingy.log("Server started on port: " + s.getPort());
		BufferedReader sysin = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			String in = sysin.readLine();
			Thingy.handel(in);
		}
	}
	
	
	public void onFragment(WebSocket conn, Framedata fragment) {
		
	}
	
	@Override
	public void onError(WebSocket conn, Exception ex) {
		
	}
	
}