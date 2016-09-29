package br.com.korbam.controller;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;

public class EnviaNotificacaoIOS implements Runnable{

	//private static String DEVICE_TOKEN = "ab313a052e88707095fbc0c800b4841a97160d4750f8fbb9244e195449059819";
	//private static String PATH_TO_P12_CERT = "/Users/lincolncaetano/Desktop/certificadoapndev.p12";
	private static String PATH_TO_P12_CERT = "/home/risidev/appservers/apache-tomcat-8x/webapps/certificado/certificadoapndev.p12";
    private static String CERT_PASSWORD = "saoJorge2";
    
    private String msg;
    private String token;

	public EnviaNotificacaoIOS(String mensagem, String deviceToken) {
		super();
		msg = mensagem;
		token = deviceToken;
	}

	public void run () {
		try {
	   		 ApnsService service =
	   	                APNS.newService()
	   	                        .withCert(PATH_TO_P12_CERT, CERT_PASSWORD)
	   	                        .withSandboxDestination()
	   	                        .build();

	   	        String payload = APNS.newPayload()
	   	                .alertBody(msg)
	   	                .sound("default")
	   	                .build();

	   	        service.push(token, payload);
	   	        System.out.println("The message has been hopefully sent...");
			} catch (Exception e) {
				System.out.println(e.getStackTrace());
			}
			
	}
	
}