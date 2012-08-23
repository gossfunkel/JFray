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

public class client0_0_1 {
	String host;
	
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
	
	public String createNewUser() {
		String usnm = c.readLine("Please enter desired username: ");
    	char [] passwd = c.readPassword("Please enter desired password: ");
    	char [] passwd2 = c.readPassword("Please re-enter password: ");
    	if (passwd == passwd2) {
	    	String data = usnm + ":" + passwd + ".\n";
    		FileOutputStream logIn;
			PrintStream p;
			try {
				logIn = new FileOutputStream("logIn.fra");
				p = new PrintStream(logIn);
				p.println(data);
				p.close();
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
		int tries;
		tries = 5;
		while (tries > 0) {
			String usnm = c.readLine("Username: ");
    		char [] passwd = c.readPassword("Password: ");
    		logIn = new FileInputStream("logIn.fra");
    		DataInputStream dis = new DataInputStream(logIn);
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
    		}
		}
		System.exit(2);
		return usnm;
	}
	
	public void loginToServer(String host, String usnm ) {
		try {
			Socket testClient;
			testClient = new Socket(host,1013);
			System.out.println ("Connected to host at " + host);
			logString = ("CONNECTED: " + host);
		}
		catch (Exception e) {
			System.err.println ("Error connecting to host at " + host + ".\n Reason: " + e);
			logString = ("CONNECT FAILED: " + host + ": " + e);
		}
		printLog(logString);
		// send server usnm and os.name [System.getProperty(os.name)] ?
	}
	
	public void client0_0_1() throws FileNotFoundException, IOException {
		FileInputStream in = null;
        FileOutputStream out = null;
		c = System.console();
        if (c == null) {
            System.err.println("No console.");
            System.exit(1);
        }
        newusr = c.readLine("New user? ");
        if (newusr == "y") {
        	usnm = createNewUser();
        }
        else {
			usnm = loginToClient();
		}
		host = c.readLine("Enter server address: ");
		loginToServer(host, usnm);
	}
	
	public static void main(String args[]) throws FileNotFoundException, IOException {
		new client0_0_1();
	}
	
	private String usnm;
	private Console c;
	private FileOutputStream out;
	private FileInputStream in;
	private FileInputStream logIn;
	private String logString;
	private String newusr;
	
}

