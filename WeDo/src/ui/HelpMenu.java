/**
 * @author Andy Hsu Wei Qiang 
 * This class creates the Help Menu when 
 * user hotkey <F1> is pressed
 * 
 */
package ui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JFrame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
 
/*
 * This class is the help menu the shows
 * different command to the user
 */
public class HelpMenu {
    public static JFrame frame;
	JTextArea output;
    JScrollPane scrollPane;
    String newline = "\n";
    
	private static final int Xcoordinate = 310;
	private static final int Ycoordinate = 225;
	
//    public Container createHelpPane() {
//        //Create the content-pane-to-be.
//        JPanel contentPane = new JPanel(new BorderLayout());
//        contentPane.setOpaque(true);
// 
//        //Create a scrolled text area.
//        output = new JTextArea(5, 30);
//        output.setEditable(false);
//        scrollPane = new JScrollPane(output);
// 
//        //Add the text area to the content pane.
//        contentPane.add(scrollPane, BorderLayout.CENTER);
// 
//        return contentPane;
//    }
    
    public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        //Create the menu bar.
        menuBar = new JMenuBar();
        
        JLabel lblHelp = new JLabel("");
        lblHelp.setBounds(0, 0, 434, 183);
        lblHelp.setBackground(new Color(224, 255, 255));
        lblHelp.setOpaque(true);
 
        //Build the <Add> menu.
        JMenu menuAdd;
        menuAdd = new JMenu("<Add Command>");
        menuBar.add(menuAdd);
        //createHelpLabel().setText("testing");
        lblHelp.setText("This is the Add command!");
        //menuAdd.add(lblHelp);
        
 
        //Build second menu <View> menu in the menu bar.
        JMenu menuView;
        menuView = new JMenu("<View Command>");
        menuBar.add(menuView);
        
        //Build third menu <Edit> menu in the menu bar.
        JMenu menuEdit;
        menuEdit = new JMenu("<Edit Command>");
        menuBar.add(menuEdit);
        
        //Build forth menu <Delete> menu in the menu bar.
        JMenu menuDelete;
        menuDelete = new JMenu("<Delete Command>");
        menuBar.add(menuDelete);
 
        return menuBar;
    }
    
    private static JLabel createHelpLabel(){
        JLabel lblHelp = new JLabel("");
        lblHelp.setBounds(0, 0, 434, 183);
        lblHelp.setBackground(new Color(224, 255, 255));
        lblHelp.setOpaque(true);
        
        return lblHelp;
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Help Menu");
        frame.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent arg0) {
        		if(arg0.getKeyCode() == KeyEvent.VK_F1){
        			frame.dispose();
        		}
        	}
        });
   		
        //Create and set up the content pane.
        HelpMenu menu = new HelpMenu();
        frame.setJMenuBar(menu.createMenuBar());
        
        //Display the window.
        frame.setSize(450, 261);
        frame.setVisible(true);
        
        //Set the location of the Help Menu
        frame.setLocation(Xcoordinate, Ycoordinate);
        
        //Set the focus to the main frame
        frame.setFocusable(true);
        frame.getContentPane().setLayout(null);
        
        JLabel lblEnter = new JLabel("   Press <F1> again to exit the Help Menu");
        lblEnter.setFont(new Font("Times New Roman", Font.BOLD, 12));
        lblEnter.setBackground(new Color(127, 255, 212));
        lblEnter.setBounds(0, 179, 434, 21);
        lblEnter.setOpaque(true);
        frame.getContentPane().add(lblEnter);
        
        frame.getContentPane().add(createHelpLabel());
    }
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}