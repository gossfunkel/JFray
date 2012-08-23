/*
GUI libs:
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
*/

import java.io.*;
import java.lang.*;
import java.net.*;
import java.util.*;
import javax.crypto.*;
//import java.security.GeneralSecurityException;
import java.text.DateFormat;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class client0_0_2 {
	
// printing log:

	public void printLog(String data) {
		data = data + "\n";
		FileOutputStream log;
		PrintStream p;
		try {
			log = new FileOutputStream("log.fra");
			p = new PrintStream(log);
			p.println(data);
			p.close();
		}
		catch (Exception e) {
			System.err.println ("WriteError: log: " + e);
		}
	}

// SECURITY:
	public void encryptUsers() {
		
	}
	
	public void decryptUsers() {
		
	}
	
// logins:
	
	public String createNewUser() {
		String usnm = c.readLine("Please enter desired username: ");
    	char [] passwd = c.readPassword("Please enter desired password: ");
    	char [] passwd2 = c.readPassword("Please re-enter password: ");
    	if (Arrays.equals(passwd, passwd2)) {
	    	String data = usnm + ":" + passwd + ".\n";
    		FileOutputStream users;
			PrintStream p;
			try {
				users = new FileOutputStream("users.fra");
				p = new PrintStream(users);
				p.println(data);
				p.close();
				//encryptUsers();
			}
			catch (Exception e) {
				System.err.println ("WriteError: log");
			}
		}
		else {
			System.err.println ("Passwords do not match!");
			System.exit(1);
		}
		return usnm;
	}
	
	public String loginToClient() throws FileNotFoundException, IOException {
		//decryptUsers();
		int tries;
		tries = 5;
		while (tries > 0) {
			System.out.println("LOGIN");
			String usnm = c.readLine("Username: ");
    		char [] passwd = c.readPassword("Password: ");
    		users = new FileInputStream("users.fra");
    		DataInputStream dis = new DataInputStream(users);
			BufferedReader br = new BufferedReader(new InputStreamReader(dis));
    		String logindat = br.readLine();
    		int startUsnm = logindat.indexOf(usnm);
    		String logdat = logindat.substring(startUsnm, -1);
			int tendUsnm = logdat.indexOf(':'); 
			int endUsnm = startUsnm + tendUsnm;
			int startPass = endUsnm + 1;
			int tendPass = logdat.indexOf('.');
			int endPass = startPass + tendPass;
			String Usnm = logindat.substring(startUsnm, endUsnm);
			String Pass = logindat.substring(startPass, endPass);
			char [] Passwd = Pass.toCharArray();
			if (usnm.equals(Usnm)) {
				if (Arrays.equals(passwd,Passwd)) {
					System.out.println ("Logged in. Welcome, " + usnm + ".");
					String data = "LOGIN: " + usnm;
					printLog(data);
					//encryptUsers();
					return usnm;
				}
				else {
					System.out.println ("Incorrect password, please try again.");
					String data = "PASWFAIL: " + usnm;
					printLog(data);
					tries -= 1;
				}
			}
			else {
				System.out.println ("Username not recognised.");
				printLog("USNAMFAIL");
				//encrytUsers();
    		}
		}
		//encryptUsers();
		System.exit(2);
		return usnm;
	}
	
// Sockets:
	
	public void loginToServer(String host, String usnm ) {
		try {
			Socket testClient = new Socket(host,1042);
			System.out.println ("Connected to host at " + host);
			logString = ("CONNECTED: " + host);
         	OutputStream outToServer = testClient.getOutputStream();
         	DataOutputStream out = new DataOutputStream(outToServer);
			out.writeUTF("Hello from " + testClient.getLocalSocketAddress());
         	InputStream inFromServer = testClient.getInputStream();
         	DataInputStream in = new DataInputStream(inFromServer);
         	System.out.println("Server says " + in.readUTF());
         	testClient.close();
		}
		catch (Exception e) {
			System.err.println ("Error connecting to host at " + host + ":1042.\n Reason: " + e);
			logString = ("CONNECT FAILED: " + host + ":1042: " + e);
		}
		printLog(logString);
		// send server usnm and os.name [System.getProperty(os.name)] ?
	}
	
	public static void exitClient() {
		System.out.println("Client exiting properly");
	}
	
// main method:
	
	public void general() throws FileNotFoundException, IOException {
		FileInputStream in = null;
        FileOutputStream out = null;
		c = System.console();
        if (c == null) {
            System.err.println("No console.");
            System.exit(1);
        }
		os = System.getProperty("os.name").toLowerCase();
        try {
        	File log = new File("log.fra");
        	if (log.exists()) {
        		System.out.println("Logfile exists.");
        	}
        	else {
    			log.createNewFile();
    			FileOutputStream logStream;
				PrintStream p;
				logStream = new FileOutputStream("log.fra");
				p = new PrintStream(logStream);
				String newLog = "CREATED " + DateFormat.getDateInstance() + os + ". \n";	
				p.println(newLog);
				p.close();
    		}
    		// decryptUsers();
        	File users = new File("users.fra");
    		if (users.exists()) {
        		System.out.println("Users file exists.");
        	}
        	else {
    			users.createNewFile();
    		}
    	}
    	catch (Exception e) {
    		System.err.println("Could not access files.");
    		System.exit(1);
    	}
        newusr = c.readLine("New user? ");
        if (newusr == "y") {
        	System.out.println("IF");
        	usnm = createNewUser();
        	usnm = loginToClient();
        }
        else {
        	System.out.println("ELSE");
			usnm = loginToClient();
		}
		host = c.readLine("Enter server address: ");
		loginToServer(host, usnm);
		exitClient();
	}
	
	client0_0_2() throws FileNotFoundException, IOException {
		general();
	}
	
// Constructor:
	
	public static void main(String args[]) throws FileNotFoundException, IOException {
		System.out.println("Startup initiated.");
		client0_0_2 client = new client0_0_2();
	}
	
// Variables:
	
	private String host;
	private String usnm;
	private Console c;
	private String os;
	private FileOutputStream out;
	private FileInputStream in;
	private FileInputStream users;
	private String logString;
	private String newusr;
	
}


