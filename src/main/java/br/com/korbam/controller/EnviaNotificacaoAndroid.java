package br.com.korbam.controller;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class EnviaNotificacaoAndroid implements Runnable{

	private static String SERVER_KEY = "AIzaSyALswAOMg2ZN0Ku86bSDDm5eOKET4pZI2Y";
    
    private String msg;
    private String token;
    private String title;

	public EnviaNotificacaoAndroid(String mensagem, String deviceToken) {
		super();
		msg = mensagem;
		token = deviceToken;
		title = "Titulo";
	}

	public void run () {
		try {
			String pushMessage = "{\"data\":{\"title\":\"" +
	                title +
	                "\",\"message\":\"" +
	                msg +
	                "\"},\"to\":\"" +
	                token +
	                "\"}";
	        // Create connection to send FCM Message request.
	        URL url = new URL("https://fcm.googleapis.com/fcm/send");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestProperty("Authorization", "key=" + SERVER_KEY);
	        conn.setRequestProperty("Content-Type", "application/json");
	        conn.setRequestMethod("POST");
	        conn.setDoOutput(true);
	// Send FCM message content.
	        OutputStream outputStream = conn.getOutputStream();
	        outputStream.write(pushMessage.getBytes());
	        System.out.println(conn.getResponseCode());
	        System.out.println(conn.getResponseMessage());
   	        System.out.println("The message has been hopefully sent...");
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
			
	}
	
}