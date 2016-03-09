package graphics;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.jtattoo.*;
import users.*;
import exceptions.*;
import repositories.*;

/* My graphical user interface */
public class GUI {
	public JFrame mainFrame;
	/* History panel */
	public JPanel historyConsole;
	public JScrollPane historyConsoleScroll;
	/* Command console panel */
	public JPanel commandConsole;
	public JLabel currentUser;
	public JTextField commandLine;
	public JScrollPane commandLineScroll;
	
	public GUI(){
		/* JTattoo - HiFi Look And Feel */
		try {
//			UIManager.set LookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
			UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
		/* Main frame */
		mainFrame = new JFrame("AAA Cloud");
		mainFrame.setLayout(new GridBagLayout());
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainFrame.addWindowListener(new WindowAdapter(){//add exit listener
			@Override
			public void windowClosing(WindowEvent e){
				if(JOptionPane.showConfirmDialog(mainFrame, 
			            "Are you sure to close the AAA Cloud?", "Exit", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					Logger systemLogger = new Logger();
					systemLogger.writeToFile();
					System.exit(0);
				}
			}
		});
		mainFrame.setMinimumSize(new Dimension(500, 300));
		mainFrame.setSize(810, 410);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrame.setLocation(dim.width/2 - 410, dim.height - 810);//center frame
		ImageIcon img = new ImageIcon(getClass().getResource("/resources/cloudAAAicon.png"));
		mainFrame.setIconImage(img.getImage());
		/* First panel - History console */
		historyConsole = new JPanel();
		historyConsole.setLayout(new BoxLayout(historyConsole, BoxLayout.Y_AXIS));
		historyConsole.setAlignmentX(Component.LEFT_ALIGNMENT);
		//scroll
		historyConsoleScroll = new JScrollPane(historyConsole,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		historyConsoleScroll.setPreferredSize(new Dimension(700, 320));
		historyConsoleScroll.setMinimumSize(new Dimension(400, 200));
		historyConsoleScroll.getVerticalScrollBar().setUnitIncrement(10);
		/* Second panel - current user / command line */
		commandConsole = new JPanel();
		commandConsole.setLayout(new GridBagLayout());
		// current user
		currentUser = new JLabel("guest", SwingConstants.CENTER);
		currentUser.setPreferredSize(new Dimension(100, 50));
		currentUser.setIcon(new ImageIcon(getClass().getResource("/resources/cloudAAAuser70.png")));
		currentUser.setHorizontalTextPosition(JLabel.CENTER);
		currentUser.setVerticalTextPosition(JLabel.CENTER);
		commandConsole.add(currentUser);
		//command line
		commandLine = new JTextField();
		commandLine.setBorder(null);
		//command line - scroll
		commandLineScroll = new JScrollPane(commandLine, 
				JScrollPane.VERTICAL_SCROLLBAR_NEVER, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		commandLineScroll.setBorder(null);
		commandLineScroll.setPreferredSize(new Dimension(692, 40));
		commandLineScroll.setMinimumSize(new Dimension(400, 40));
		commandConsole.add(commandLineScroll);
		/* Add historyConsoleScroll to mainFrame */
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 700;
		c.weighty = 320;
		mainFrame.add(historyConsoleScroll, c);
		/* Add commandConsole to mainFrame */
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 700;
		mainFrame.add(commandConsole, c);
		mainFrame.setVisible(true);
	}
	/* Add command to historyConsole */
	public void addCommand(String input){
		JTextArea newCommand = new JTextArea(1,1);
		newCommand.setText(">" + input);
		newCommand.setEditable(false);
		newCommand.getCaret().setSelectionVisible(true);
		newCommand.setBorder(null);
		historyConsole.add(newCommand);
		/* Update scroll position */
		JScrollBar vertical = historyConsoleScroll.getVerticalScrollBar();
		vertical.setValue(vertical.getMaximum());
		mainFrame.setVisible(true);
	}
	/* Add text to the historyConsole */
	public void addText(String input){
		JTextArea newCommand = new JTextArea(1,1);
		newCommand.setText(input);
		newCommand.setEditable(false);
		newCommand.getCaret().setSelectionVisible(true);
		newCommand.setBorder(null);
		historyConsole.add(newCommand);
		/* Update scroll position */
		JScrollBar vertical = historyConsoleScroll.getVerticalScrollBar();
		vertical.setValue(vertical.getMaximum());
		mainFrame.setVisible(true);
	}
	/* Add intro */
	public void addIntro(){
		/* Intro image */
		JPanel myImage = new JPanel();
		JLabel cloudHeader = new JLabel();
		cloudHeader.setIcon(new ImageIcon(getClass().getResource("/resources/cloudAAAheader.png")));
		cloudHeader.setBounds(0, 0, 0, 0);
		myImage.add(cloudHeader);
		historyConsole.add(myImage);
		/* Intro message */
		String intro = new String();
		intro += "AAA Cloud V1.0 " +
				"\u00a9 Alexandru-Adrian Achiritoaei, 325CC, 2015-2016\n" +
				"Welcome, guest, to my personalized version of a cloud!\n" +
				"For help, use command 'man'!\n" +
				"Enjoy!";
		/* Update scroll position */
		addText(intro);
		JScrollBar vertical = historyConsoleScroll.getVerticalScrollBar();
		vertical.setValue(vertical.getMaximum());
	}
	/* Editfile windows */
	public void addEditfile(RepositoryClass rep){
		MyDialogEdit editDialog = new MyDialogEdit(mainFrame, "EDIT", rep);
	}
	/* Echo -POO */
	public void addEchoPOO(String message){
		MyDialog echoDialog = new MyDialog(mainFrame, "ECHO", message);
	}
	/* Userinfo -POO */
	public void addUserinfoPOO(User user){
		/* List model */
		String[] components = new String[5];
		components[0] = "Username : " + user.getUsername();
		components[1] = "FirstName : " + user.getFirstName();
		components[2] = "Lastname : " + user.getLastName();
		components[3] = "Created : " + user.getCreationDate();
		components[4] = "Last login : " + user.getLastLogin();
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		for(int i = 0; i < 5; i++)
			listModel.addElement(components[i]);
		/* JList */
		JList<String> userList = new JList<String>(listModel);
		userList.setVisibleRowCount(5);
		userList.setPreferredSize(new Dimension(50, 80));
		JScrollPane listScroll = new JScrollPane(userList, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		historyConsole.add(listScroll);
		addCommand("");
		/* Update scroll position */
		JScrollBar vertical = historyConsoleScroll.getVerticalScrollBar();
		vertical.setValue(vertical.getMaximum());
	}
	/* LS -POO */
	/* For file */
	public void addLsPOO(File file){
		String[] columnNames = {"Name",
								"Size",
								"Owner",
								"Read",
								"Write",
								"Creation date",
								"Type"
		};
		String[][] data = {
				{file.getName(),
				Integer.toString(file.getSize()),
				file.getOwner().getUsername(),
				Boolean.toString(file.isReadable()),
				Boolean.toString(file.isWriteable()),
				file.getCreationDate().toString(),
				"file"
				}
		};
		JTable lsTable = new JTable(data, columnNames);
		lsTable.setEnabled(false);
		historyConsole.add(lsTable);
	}
	/* For directory */
	public void addLsPOO(Directory dir){
		String[] columnNames = {"Name",
				"Size",
				"Owner",
				"Read",
				"Write",
				"Creation date",
				"Type"
		};
		String[][] data = {
				{dir.getName(),
					Integer.toString(dir.getSize()),
					dir.getOwner().getUsername(),
					Boolean.toString(dir.isReadable()),
					Boolean.toString(dir.isWriteable()),
					dir.getCreationDate().toString(),
					"directory"
				}
		};
		JTable lsTable = new JTable(data, columnNames);
		lsTable.setEnabled(false);
		historyConsole.add(lsTable);
	}
	/* For -lPOO */
	public void addLsLPOO(Directory dir){
		String[] columnNames = {"Name",
				"Size",
				"Owner",
				"Read",
				"Write",
				"Creation date",
				"Type"
		};
		String [][] data= new String[dir.getChildren().size()][7];
		for(int i = 0; i < dir.getChildren().size(); i++){
			RepositoryClass rep = dir.getChildren().get(i);
			data[i][0] = rep.getName();
			data[i][1] = Integer.toString((rep.getSize()));
			data[i][2] = rep.getOwner().getUsername();
			data[i][3] = Boolean.toString(rep.isReadable());
			data[i][4] = Boolean.toString(rep.isWriteable());
			data[i][5] = rep.getCreationDate().toString();
			if(rep instanceof File)
				data[i][6] = "file";
			else
				data[i][6] = "directory";
		}
		JTable lsTable = new JTable(data, columnNames);
		lsTable.setEnabled(false);
		historyConsole.add(lsTable);
	}
}
