import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//import java.io.*;
//import java.net.*;

public class Frame0_0_1 extends JFrame {
	JPanel pane = new JPanel();
	JButton pressme = new JButton("Press Me");
	Frame0_0_1() {
		super("Client"); setBounds(100,100,300,200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container con = this.getContentPane();
		con.add(pane);
    	pressme.setMnemonic('P');
    	pane.add(pressme); pressme.requestFocus();
		setVisible(true);
	}
	public static void main(String args[]) {
	new Frame0_0_1();
	}
}

/*class client {
	String host;
		// input host here
		Socket testClient;
		testClient = new Socket(host,
	}
}*/
