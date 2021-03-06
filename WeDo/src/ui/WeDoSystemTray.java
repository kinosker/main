package ui;

import java.awt.AWTException;
import java.awt.Frame;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import userInterface.UserIntSwing;

/**
 // @author A0112636M 
  * This class handles the "Minimise To Tray" operation.
  */
public class WeDoSystemTray {
	private static TrayIcon trayIcon;
	private static SystemTray tray;
	
	private static final String MAIN_FRAME_NAME = "Wedo";
	private static final String SYSTEMTRAY_MENU_ABOUT = "About Wedo";
	private static final String SYSTEMTRAY_MENU_OPEN = "Open Application";
	private static final String SYSTEMTRAY_MENU_EXIT = "Exit Application";
	
	public static void Minimise(WindowEvent arg) {
		
		 /* If export as Executable Jar file use "/WeDo_logo.png" 
		  * and put the logo in the same root*/
		Image image = Toolkit.getDefaultToolkit().getImage(
				UserIntSwing.class.getClass().getResource("/ui/icon/WeDo_logo.png"));
		PopupMenu popup = new PopupMenu();
		trayIcon = new TrayIcon(image, MAIN_FRAME_NAME, popup);
		trayIcon.setImageAutoSize(true);

		if(SystemTray.isSupported()){
			System.out.println("SystemTray supported");
			tray = SystemTray.getSystemTray();
			
			MenuItem popupItemAbout = new MenuItem(SYSTEMTRAY_MENU_ABOUT);
			popup.add(popupItemAbout);
			/*This listener opens up the AboutWeDo Window*/
			popupItemAbout.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					aboutWeDo();
				}
			});
			
			MenuItem popupItemOpen = new MenuItem(SYSTEMTRAY_MENU_OPEN);
			popup.add(popupItemOpen);
			/*This listener opens up the main Window*/
			popupItemOpen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					openMainFrame();
					UserInterfaceMain.setupFrameLocation();
				}
			});
			addTrayIconMouseListener();
			
			MenuItem popupItemExit = new MenuItem(SYSTEMTRAY_MENU_EXIT);
			popup.add(popupItemExit);
			/*This listener when pressed directly exit the application*/
			popupItemExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					exitMainFrame();
				}
			});
		}
		else{
			System.out.println("system tray not supported");
		}
		
		// Pass the 'iconified' parameter to minimise the frame
		if(arg.getNewState() == Frame.ICONIFIED){
			try{
				tray.add(trayIcon);
				UserIntSwing.frame.setVisible(false);
				System.out.println("added to System Tray");
			} catch (AWTException ex){
				System.out.println("unable to add to System Tray");
			}
		}
	}
	
	/**
	 * This operation adds listener to the tray icon
	 * to maximize the program when mouse is double clicked
	 */
	private static void addTrayIconMouseListener() {
		trayIcon.addMouseListener(new MouseAdapter() {
			boolean isAlreadyOneClick;
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
			    if (isAlreadyOneClick) {
			    	openMainFrame();
			    	UserInterfaceMain.setupFrameLocation();
			        System.out.println("double click");
			        isAlreadyOneClick = false;
			    } else {
			        isAlreadyOneClick = true;
			        Timer t = new Timer("doubleclickTimer", false);
			        t.schedule(new TimerTask() {

			            @Override
			            public void run() {
			                isAlreadyOneClick = false;
			            }
			        }, 500);
			    }
			}
		});
	}

	/**
	 * This operation maximize the main frame
	 */
	public static void openMainFrame() {
		UserIntSwing.frame.setVisible(true);
		UserIntSwing.frame.setExtendedState(JFrame.NORMAL);
		tray.remove(trayIcon);
		System.out.println("Tray icon removed");
	}
	
	/**
	 * This operation closes the main frame when
	 * the "Exit" menu on the SystemTray is pressed
	 */
	private static void exitMainFrame() {
		System.out.println("Exiting......");
		System.exit(0);
	}
	
	/**
	 * This operation opens the aboutWeDo
	 */
	private static void aboutWeDo() {
		AboutWeDo.main(null);
	}
}
