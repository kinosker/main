package ui;

import java.awt.event.KeyEvent;
import java.util.Stack;

import ui.logic.command.FeedbackHandler;
import userInterface.UserIntSwing;

/**
 // @author A0112636M 
 * This class create 2 Stacks which store the user
 * input. 
 * Up Arrow Listener - Display the history of what the user input
 * Down Arrow Listener - Opposite operation of the Up Arrow Key.
 */
public class TextfieldHistory{
	private static final Stack<String> userInputStack = new Stack<String>();
	private static final Stack<String> undoStack = new Stack<String>();
	private static String getText;
	
	/**
	 * This operation checks if "undoStack" is empty. If it is not empty, pushes
	 * all the items to "userInputStack"
	 * Tt gets the text from what the user type
	 * and store it in "userInputStack" 
	 */
	public static void getTextfieldString(String getText){
		
		if(!getText.isEmpty()){
			while(!undoStack.isEmpty()) {
				userInputStack.push(undoStack.pop());
			}
			userInputStack.push(getText);
		}
	}
	
	/**
	 * This operation has keylistener of up and down arrow keys
	 * Up arrow keylistener - Pushes the text from the user to "undoStack"
	 * Down arrow keylistener - Pushes the text previously stored to "userInputStack"  
	 */
	public static void showTextfieldHistory(KeyEvent arg1) {
		if(arg1.getKeyCode() == KeyEvent.VK_UP) {
			if(!userInputStack.isEmpty()) {
				pushToUndoStack();
			}
			else{
				FeedbackHandler.emptyHistoryStringOperation();
			}
		}
		else if(arg1.getKeyCode() == KeyEvent.VK_DOWN) {
			
			if(!undoStack.isEmpty()) {
				pushToUserInputStack();
			}
			else{
				FeedbackHandler.emptyInputStringOperation();
				UserIntSwing.textField.setText(null);
			}
		}
	}
	
	private static void pushToUndoStack() {
		getText = userInputStack.pop();
		undoStack.push(getText);
		UserIntSwing.textField.setText(getText);
	}
	
	private static void pushToUserInputStack() {
		getText = undoStack.pop();
		userInputStack.push(getText);
		UserIntSwing.textField.setText(getText);
	}
}
