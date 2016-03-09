package graphics;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.*;

public class MyDialog extends JDialog implements ActionListener {
	public MyDialog(JFrame parent, String title, String message) {
		super(parent, title, true);
		/* Get dialog location */
		if(parent != null){
			Dimension parentSize = parent.getSize(); 
		    Point p = parent.getLocation(); 
		    setLocation(p.x + parentSize.width / 2, p.y + parentSize.height / 2);
		}
		/* Message */
		JPanel messagePanel = new JPanel();
		messagePanel.add(new JLabel(message));
		getContentPane().add(messagePanel);
		/* Button */
		JPanel buttonPanel = new JPanel();
		JButton button = new JButton("OK"); 
		buttonPanel.add(button); 
		button.addActionListener(this);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack(); 
		setVisible(true);
		}
	public void actionPerformed(ActionEvent e) {
		setVisible(false); 
		dispose(); 
	}
}
