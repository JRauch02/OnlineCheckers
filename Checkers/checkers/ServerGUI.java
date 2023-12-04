package checkers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetAddress;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;



public class ServerGUI extends JFrame
{
  private JLabel status;
  private JButton listen;
  private JButton close;
  private JButton stop;
  private JButton quit;
  private String[] labels = {"Port #:","Timeout:"};
  private JTextField[] textFields = new JTextField[labels.length];
  private JLabel[] textFieldLabels = new JLabel[labels.length];
  private JTextArea serverLogTextArea;
  JPanel centerWrapper = new JPanel(new FlowLayout());
  JPanel centerInnerWrapper = new JPanel(new GridLayout(2,1));
  JPanel southCenterWrapper = new JPanel(new FlowLayout());
  JPanel southCenter = new JPanel(new BorderLayout());
  JPanel north = new JPanel(new FlowLayout());
  JPanel center = new JPanel(new GridLayout(labels.length,1,10,10));
  JPanel south = new JPanel(new FlowLayout());
  private CheckersServer server;
  private boolean listening = false;
  private InetAddress ip;
  private String hostAdress;
  
  
  public JTextField[] getTextFields() {
	  return textFields;
  }
  
  public JTextArea getServerLog() {
	  return serverLogTextArea;
  }
  public ServerGUI()
  {
    
    this.setTitle("Server");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    
    
    
    JLabel statusLabel = new JLabel("Status:",JLabel.RIGHT);
    status = new JLabel("Not Connected",JLabel.LEFT);
    status.setForeground(Color.red);
    north.add(statusLabel);
    north.add(status);
    
    
    int i = 0;
    for (i = 0; i < labels.length; i++)
    {
      
    	textFieldLabels[i] = new JLabel(labels[i],JLabel.RIGHT);
    	if (labels[i].equals("Client ID:")) {
    		textFields[i] = new JTextField("",1);
    		textFields[i].setEditable(false);
    	}
    	else if (labels[i].equals("Port #:")) {
    		textFields[i] = new JTextField("8300", 1);
    		textFields[i].setEditable(false);
    	}
    	else {
    		textFields[i] = new JTextField("500", 1);
    	}
    	

      
      center.add(textFieldLabels[i]);
      center.add(textFields[i]);
     
    }
    

    JLabel logLabel = new JLabel("Sever Log Below :");
    serverLogTextArea = new JTextArea(7,25);
    
    
    JScrollPane clientScrollPane = new JScrollPane(serverLogTextArea);

    server = new CheckersServer(status, serverLogTextArea);
    server.setDatabase(new Database());
    
    southCenter.add(logLabel,BorderLayout.NORTH);
    southCenter.add(clientScrollPane,BorderLayout.CENTER);


    
    //event = new EventHandler(connect, submit, stop, clientArea, serverArea);
    
    
    
    centerInnerWrapper.add(center,BorderLayout.NORTH);
    centerInnerWrapper.add(southCenter,BorderLayout.SOUTH);
    
    //south.add(connect);
    //south.add(submit);
    //south.add(stop);
    
    listen = new JButton("Listen");
    close = new JButton("Close");
    stop = new JButton("Stop");
    quit = new JButton("Quit");
    
    //event = new EventHandler(listen, close, stop, quit, textFields, serverLogTextArea, server,status);
    
    listen.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(textFields[0].getText().isEmpty() || textFields[1].getText().isEmpty()) {
				serverLogTextArea.append("Port Number/timeout not entered before pressing listen.\n");
			}
			else {
				//System.out.println("Listen Button Pressed");
				try {
					server.setPort(Integer.parseInt(textFields[0].getText()));
					server.setTimeout(Integer.parseInt(textFields[1].getText()));
					server.listen();
					ip = InetAddress.getLocalHost();
					serverLogTextArea.append(ip + "\n");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				listening = true;
			}
		}
	});
    
    close.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (listening) {
				try {
					server.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else {
				serverLogTextArea.append("Server not currently started\n");
			}
			System.out.println("Close Button Pressed");
		}
	});
    
    stop.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (listening) {
				server.stopListening();
			}
			else {
				serverLogTextArea.append("Server not currently started\n");
			}
			System.out.println("Stop Button Pressed");
		}
	});
    
    quit.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			JFrame frame = (JFrame)SwingUtilities.getRoot(quit);
			frame.dispose();
		}
	});
    south.add(listen);
    south.add(close);
    south.add(stop);
    south.add(quit);
    

    centerWrapper.add(centerInnerWrapper);
    this.add(north,BorderLayout.NORTH);
    this.add(centerWrapper,BorderLayout.CENTER);
    this.add(south,BorderLayout.SOUTH);
    
    setSize(350,400);
    setVisible(true);
    
    this.addWindowListener(new WindowAdapter() {
    	public void windowClosing(WindowEvent e) {
    		try {
				server.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}
    });
  }
  
  public static void main(String[] args)
  {
    new ServerGUI(); //args[0] represents the title of the GUI
  }
}


