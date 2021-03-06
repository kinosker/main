package ui;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Toolkit;

import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

import userInterface.UserIntSwing;

/**
 // @author A0112636M
  * This window shows the authors of WeDo Task Manager and
  * the acknowledements 
 */
public class AboutWeDo {

	private static JDialog frame;
	private static final int Xcoordinate = 5;
	private static final int Ycoordinate = 5;
	private static final String TAG_WRAP_STRING = "%s%s%s";
	private static final String HTML_OPEN = "<html>";
	private static final String HTML_CLOSE = "</html>";
	private static final String HTML_BREAK = "<br>";

	/**
	 * Initialize the contents of the frame.
	 */
	private static void AboutWeDoGUI() {
		frame = new JDialog();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(
				UserIntSwing.class.getResource("/ui/icon/WeDo_logo.png")));
		frame.setBounds(100, 100, 334, 300);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocation(Xcoordinate, Ycoordinate);
		
		JLabel lblAbout = new JLabel(buildAboutMessage());
		lblAbout.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAbout.setVerticalAlignment(SwingConstants.TOP);
		lblAbout.setBounds(10, 11, 308, 249);
		frame.getContentPane().add(lblAbout);
		
		JButton btnClose = new JButton("Okay");
		btnClose.setBounds(229, 237, 89, 23);
		frame.getContentPane().add(btnClose);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
	
		UserIntSwing.lblIcon.setIcon(new ImageIcon(UserIntSwing.class.getResource("/ui/icon/WeDo_logo.png")));
		UserIntSwing.lblIcon.setBounds(199, 11, 103, 98);
		frame.getContentPane().add(UserIntSwing.lblIcon);
	}
	
	/**
	 *This operation returns message the "About WeDo" menu item
	 */
	private static String buildAboutMessage() {
		StringBuilder message = new StringBuilder();

		message.append("<b> About WeDo Task Manager: </b>" + HTML_BREAK);
		message.append("(CS2103T   T17/C05   t17-2j)" + HTML_BREAK);
		message.append("Team Members:" + HTML_BREAK);
		message.append("Andy Hsu Wei Qiang" + HTML_BREAK);
		message.append("Kuan Tien Long" + HTML_BREAK);
		message.append("Wai Min" + HTML_BREAK);
		message.append("Sitti Maryam Binte Rashid Ridza" + HTML_BREAK);
		message.append(HTML_BREAK + HTML_BREAK);
		message.append("<b> Acknowledements: </b>" + HTML_BREAK);
		message.append("BalloonTip dependency - https://balloontip.java.net/");

		return wrapWithHtmlTag(message.toString());
	}
	
	/**
	 * @param text String to be wrapped in HTML
	 * @return String wrapped with HTML format
	 */
	private static String wrapWithHtmlTag(String text) {
		return String.format(TAG_WRAP_STRING, HTML_OPEN, text, HTML_CLOSE);
	}
	
	/**
	 * Launch the application.
	 */
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	AboutWeDoGUI();
            }
        });
    }
}
