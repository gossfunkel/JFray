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
import java.security.*;
import java.text.DateFormat;

public class server0_0_1 {

// printing log:

	public void printLog(String data) {
		data = data + "\n";
		if (debugMode) {
			System.out.println(data);
		}
		FileOutputStream log;
		PrintStream p;
		try {
			log = new FileOutputStream("log.sfra");
			p = new PrintStream(log);
			p.println(data);
			p.close();
		}
		catch (Exception e) {
			System.err.println ("WriteError: log: " + e);
		}
	}

	public void loginToServer() throws FileNotFoundException , IOException {
		// taking in password as characted array
		char [] passwd = c.readPassword("Password: ");

		// reading encrypted password from pass.sfra file
		FileInputStream pass = new FileInputStream("pass.sfra");
    	DataInputStream dis = new DataInputStream(pass);
		BufferedReader br = new BufferedReader(new InputStreamReader(dis));

    	String passdat = br.readLine();
		String Passwd = new String(passwd);
		
		// comparing encrypted password with data from pass file
    	if (BCrypt.checkpw(Passwd,passdat)) {
    		System.out.println("Logged in.");
			printLog("LOGIN");
    	}
    	else {
			// quit if login fails (for now...)
    		System.out.println("login failed.");
			printLog("LOGIN FAIL");
    		System.exit(2);
    	}
	}
	
// Socket:

	public void runServer() {
		boolean looping = true;
		// attempt to create a server listening on port 1042
		try{
			server = new ServerSocket(1042);
		}
		catch (IOException e) {
			// if listen fails, throw error and shut down with unusual error code
			printLog("LISTEN FAIL on 1042: " + e);	
			System.err.println("Could not listen on port 1042.");
			System.exit(-1);
		}

		printLog("LISTENING");

		while(looping == true){
			try{
				client = server.accept();
				System.out.println(client);
			}
			catch (IOException e) {
				printLog("ACCEPT FAIL on 1042: " + e);	
				System.err.println("Accept failed: 1042");
				System.exit(-1);
			}

			printLog("ACCEPTING");

			try{
				// open bufferedreader for incoming data from client
				inComing = new BufferedReader(new InputStreamReader(client.getInputStream()));
				// open printwriter for outgoing data to client
				outGoing = new PrintWriter(client.getOutputStream(), true);
			}
			catch (IOException e) {
				// if error occurs creating inputs/outputs, call error and quit
				printLog("READ/WRITE FAIL on 1042: " + e);
				System.err.println("Read/write failed (BufferedReader/Printwriter inComing Outgoing declaration error)");
				System.exit(-1);
			}

			printLog(

			try{
				clientData = inComing.readLine();
				// if client sents empty string, complain
				if (clientData.equals(null)) {
					System.err.println("NULL INPUT");
					outGoing.println("ILLEGAL INPUT: NULL");
				}
				// shutdown trigger phrase breaks loop
				if (clientData.equals("EXIT SERVER LOOP NOW")) {
					System.out.println("Exiting loop under client command");
					looping = false;
					break;
				}
				else {
					// default reaction to connection
					System.out.println(clientData);
					//processingUnit(clientData, client);
					outGoing.println("Thank you for connecting to " + server.getLocalSocketAddress() + ". You said: '" + str(clientData) + "'.\nGoodbye!");
				}
			}
			catch (IOException e) {
				printLog("READ FAIL on 1042: " + e);
				System.out.println("Read failed");
				System.exit(-1);
			}
		// print "looping" every cycle of loop
		System.out.println("looping");
		}
	}
	
// main gaming processing:

	/*public String processingUnit(inData, client) {
		String mainVal = clientData.substring(0,3);
		if (mainVal == "LOGI") {
			if (logInClient(inData)) {
				// logged in
			}
		}
	}*/
	
// main method:

	public void general() throws FileNotFoundException, IOException {
		// create console input
		FileInputStream in = null;
		FileOutputStream out = null;
		c = System.console();
		if (c == null) {
			System.err.println("No console.");
			System.exit(1);
		}
		
		// get Operating System name property in lower case
		os = System.getProperty("os.name").toLowerCase();
		
		// check for existence of password file. If it does not exist, create one.
		pass = new File("pass.sfra");
		if (!pass.exists()) {
			// user names the server
			String servName = c.readLine("Please enter a name for the server: ");

			// user inputs password as character array to muted command line
			char [] passwd = c.readPassword("Please enter desired password: ");
    		char [] passwd2 = c.readPassword("Please re-enter password: ");
			
			// checks if passwords are equal (typo check)
    		if (Arrays.equals(passwd, passwd2)) {
    			try {
					// create a logfile to contain info about the running of the program
    				File log = new File("log.sfra");
    				log.createNewFile();
    				FileOutputStream logStream;
					PrintStream p;
					logStream = new FileOutputStream("log.sfra");
					p = new PrintStream(logStream);
					String newLog = "CREATED " + DateFormat.getDateInstance() + os + ". \n";

					// log this data	
					p.println(newLog);
					p.close();
    			}
    			catch (Exception e) {
    				System.err.println("Could not create log file.");
    				System.exit(1);
    			}
    			try {
    				File pass = new File("pass.sfra");
    				pass.createNewFile();
    				FileOutputStream passStream;
					PrintStream s;
					passStream = new FileOutputStream("pass.sfra");
					s = new PrintStream(passStream);
					String brown = new String(passwd);
					String hashedBrown = BCrypt.hashpw(brown, BCrypt.gensalt());
					s.println(hashedBrown);
					s.close();
    			}
    			catch (Exception e) {
    				System.err.println("Could not create password file.");
    				System.exit(1);
    			}
    		}	
		}	
		log = new File("log.sfra");
		if (log.exists()) {
        	System.out.println("Logfile exists.");
        }
    }
    
	server0_0_1() throws FileNotFoundException, IOException {
		general();
    	loginToServer();
		runServer();
	}
	
// Constructor:
	
	public static void main(String args[]) throws FileNotFoundException, IOException 	{
		System.out.println("Startup initiated");
		if (args[0] == "-d") {
			debugMode = true;
		}
		server0_0_1 server = new server0_0_1();
	}
	
// Variables:
	
	private boolean debugMode;
	private Console c;
	private FileOutputStream out;
	private FileInputStream in;
	private File pass;
	private File log;
	public String servName; 
	private String os;
	private FileInputStream logIn;
	private String logString;
	private String newusr;
	private boolean newserver;
	private ServerSocket server;
	private Socket client;
	private BufferedReader inComing;
	private PrintWriter outGoing;
	private String clientData;
}
