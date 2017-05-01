package nl.sogyo.webserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class ConnectionHandler implements Runnable {
    private Socket socket;

    public ConnectionHandler(Socket toHandle) {
        this.socket = toHandle;
    }

    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = reader.readLine();
            System.out.println();
            
            HttpRequest request = new HttpRequest();
            
            // put content of each line in http request object
            while (!line.isEmpty()) {
            	request.parseLine(line);
                System.out.println(line);
                line = reader.readLine();
            }
            
            // get header parameters from request for html content
            String headerParametersContent = "";
            for (String headerParameterName : request.getHeaderParameterNames()) {
            	headerParametersContent += headerParameterName + ": " + request.getHeaderParameterValue(headerParameterName) + "</br>";
            }
            
            // get parameters from request for html content
            String parametersContent = "";
            for (String parameterName : request.getParameterNames()) {
            	parametersContent += parameterName + ": " +  request.getParameterValue(parameterName) + "<br/>";
            }
            
            // make response message object for client
            ResponseMessage message = new ResponseMessage(HttpStatusCode.OK, 
            		"You did an HTTP " + request.getHTTPMethod() + " request and you requested the following resource: " + request.getResourcePath()
            		+ "<br/><br/>"
            		+ "The following header parameters where passed:<br/>"
            		+ headerParametersContent + "<br/>"
            		+ "The following parameters were passed:<br/>"
            		+ parametersContent);
            
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.write("HTTP/1.1 " + message.getStatus().getCode() + " " + message.getStatus().getDescription() + "\r\n"
            		
            		// header parameters for http response
					+ "Date: " + (DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss zzz").format(message.getDate())) + "\r\n"
            		+ "Server: Apache/2.4.16 (Unix) OpenSSL/1.0.2d PHP/5.4.45\r\n"
            		+ "Connection: close\r\n"
            		+ "Content-Type: text/html; charset=UTF-8\r\n"
            		+ "Content-Length: " + message.getContent().getBytes("UTF-8").length + "\r\n"
            		+ "\r\n"
            		
            		// content of http response
            		+ message.getContent() + "\r\n");
            
            writer.flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    


    public static void main(String... args) {
        try {
            ServerSocket socket = new ServerSocket(9090);
            while(true) {
                Socket newConnection = socket.accept();
                Thread t = new Thread(new ConnectionHandler(newConnection));
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}