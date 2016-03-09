package graphics;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.undo.*;

import repositories.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.*;

/* Editfile window */
public class MyDialogEdit extends JDialog {
	final UndoManager undoManager = new UndoManager();
	JButton undoButton = new JButton("Undo");
	JButton redoButton = new JButton("Redo");
	public MyDialogEdit(JFrame parent, String title, final RepositoryClass rep) {
		super(parent, title, true);
		/* Get dialog location */
		if(parent != null){
			Dimension parentSize = parent.getSize(); 
		    Point p = parent.getLocation(); 
		    setLocation(p.x + parentSize.width / 2, p.y + parentSize.height / 2);
		}
		setMinimumSize(new Dimension(600, 400));
		setPreferredSize(new Dimension (700, 500));
		setMaximumSize(new Dimension(800, 600));
		/* Text area */
		JPanel textPanel = new JPanel();
		final JTextArea textEdit = new JTextArea(rep.getData());
		JScrollPane textScroll = new JScrollPane(textEdit,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		textScroll.setMinimumSize(new Dimension(400, 200));
		textScroll.setPreferredSize(new Dimension(500, 300));
		textScroll.setMaximumSize(new Dimension(700, 400));
		textPanel.add(textScroll);
		getContentPane().add(textPanel);
		/* Save and exit buttons */
		JPanel buttonPanel = new JPanel();
		JButton saveButton = new JButton ("Save"); 
		saveButton.addActionListener(new ActionListener(){
			@Override 
			public void actionPerformed(ActionEvent e){
				rep.setData(textEdit.getText());
			}
		});
		JButton exitButton = new JButton("Exit"); 
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false); 
				dispose();
			}
		});
		/* Undo - redo buttons */
		undoButton.setEnabled(false);
		redoButton.setEnabled(false);
		textEdit.getDocument().addUndoableEditListener(new UndoableEditListener(){
			@Override
			public void undoableEditHappened(UndoableEditEvent e){
				undoManager.addEdit(e.getEdit());
				updateButtons();
			}
		});
		undoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try{
					undoManager.undo();
				} catch (CannotRedoException redoExp) {
					//Cannot undo
				}
				updateButtons();
			}
		});
		redoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					undoManager.redo();
				} catch (CannotRedoException redoExp) {
					//Cannot redo
				}
				updateButtons();
			}
		});
		/* Add buttons */
		buttonPanel.add(undoButton);
		buttonPanel.add(redoButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(exitButton); 
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		/* Closing dialog */
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		}
	public void updateButtons() {
		undoButton.setEnabled(undoManager.canUndo());
		redoButton.setEnabled(undoManager.canRedo());
	}
}

