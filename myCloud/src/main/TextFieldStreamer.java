package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;

import javax.swing.JTextField;
/* Used when redirecting System.in to a JTextField */
class JTexfFieldStream extends InputStream implements ActionListener {
    private JTextField inputTextField;
    private String text = null;
    private int position = 0;
    /* Constructor */
    public JTexfFieldStream(JTextField inputTextField) {
        this.inputTextField= inputTextField;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        text = inputTextField.getText() + "\n";
        position = 0;
        inputTextField.setText("");
        /* Notify all threads */
        synchronized (this) { 
            this.notifyAll();
        }
    }
    @Override
    public int read() {
        if(text != null && position == text.length()){
            text = null;
            return java.io.StreamTokenizer.TT_EOF;
        }
        /* Wait until a char is introduced */
        while (text == null || position >= text.length()) {
            try {
                synchronized (this) {
                    this.wait();
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        /* Read an additional character */
        return text.charAt(position++);
    }
}