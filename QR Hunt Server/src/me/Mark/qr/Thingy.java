package me.Mark.qr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

public class Thingy {
	
	public static HashMap<String, String> nick = new HashMap<String, String>();
	public static HashMap<String, String> score = new HashMap<String, String>();
	public static HashMap<String, String> qr = new HashMap<String, String>();
	public static ArrayList<String> colors = new ArrayList<String>();
	
	//Console input
	public static boolean handel(String in) throws Exception {
		sendAll(in);
		if (in.startsWith("go")) {
			String[] args = in.split(",");
			score.put(args[1], args[2]);
		} else if (in.startsWith("del")) {
			String[] args = in.split(",");
			sendAll("kick," + args[1]);
			score.remove(args[1]);
			for (String n : nick.keySet())
				if (nick.get(n).equals(args[1]))
					nick.remove(n);
		}
		return true;
	}
	
	public static void join(WebSocket conn, ClientHandshake handshake) {
		conn.send(getColors());
		String ip = conn.getRemoteSocketAddress().getAddress().toString();
		for (String s : score.keySet())
			conn.send("go," + s + "," + score.get(s));
		if (!nick.containsKey(ip))
			conn.send("no");
	}
	
	public static void start() {
		qr.put("nMntWa6H","A");
		qr.put("Ux8ZZaUm","B");
		qr.put("yBHUGnjR","C");
		qr.put("ME4kusfF","D");
		qr.put("Wqk6YgKp","E");
		qr.put("ApYhBTFe","F");
		qr.put("BrXSkqt2","G");
		qr.put("YmtKX5Wr","H");
		qr.put("ZqvECqWW","I");
		qr.put("ds3YpTAn","J");
		
		colors.add("#f44292");
		colors.add("#426bf4");
		colors.add("#cb42f4");
		colors.add("#42f1f4");
		colors.add("#42f48f");
		colors.add("#f1f442");
		colors.add("#f4b642");
		colors.add("#fc431e");
		colors.add("#d438f7");
		colors.add("#8efff1");
		
		//Shuffle colors
		long seed = System.nanoTime();
		Collections.shuffle(colors, new Random(seed));
		
	}
	
	public static String getColors() {
		String all = "";
		for (String c : colors)
			all = all + c;
		return all;
	}
	
	public static void leave(WebSocket conn, int code, String reason, boolean remote) {}
	
	//Handel stuff from client
	public static void stuff(WebSocket conn, String msg) throws IOException {
		log(msg);
		String[] args = msg.split(",");
		String ip = conn.getRemoteSocketAddress().getAddress().toString();
		if (msg.startsWith("login")) {
			String name = msg.substring(6);
			//String name = args[1];
			//log(ip + " " + name);
			nick.put(ip, name);
		} else if (args[0].equals("qr")) {
			//log(msg);
			String n = nick.get(ip);
			if (n != null) {
			String code = args[1];
			//log(n + " " + code);
			if (qr.containsKey(code)) {
				String c = qr.get(code);
				if (score.get(n) == null || !score.get(n).contains(c)) {
					String old = score.get(n) + ",";
					if (score.get(n) == null) old = "";
					score.put(n, old + c);
					sendAll("go," + n + "," + score.get(n));
				}
			}
			}
		} else if (args[0].equals("kick")) {
			sendAll("kick," + args[1]);
			score.remove(args[1]);
			for (String n : nick.keySet())
				if (nick.get(n).equals(args[1]))
					nick.remove(n);
		} else if (args[0].equals("ping")) {
			if (!nick.containsKey(ip))
				conn.send("no");
		} else log(nick.get(ip) + " " + msg);
	}
	
	public static void send(WebSocket user, String msg) {
		user.send(msg);
	}
	
	public static void sendAll(String msg) {
		for (WebSocket user : WebServer.online)
			user.send(msg);
		log(msg);
	}

	public static void log(String msg) {
		
		if (msg.startsWith("go")) msg = msg.substring(3);
		
		Main.chat(msg);
	}
	
}
