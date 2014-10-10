/**
 * 
 */
package logic.command.commandList;

import definedEnumeration.TaskFeedBack;

/**
 * @author TienLong This class makes use of the Command interface to
 *         implement execute function for undoCommand
 */
public class UndoCommand extends Command {

    public TaskFeedBack execute() {
        System.out.println("undo");
        
        if(undoHandler.undo())
        {
            return TaskFeedBack.FEEDBACK_VALID;
        }
        else
        {
            return TaskFeedBack.FEEDBACK_INVALID;
        }
        
    }

    /*
     * (non-Javadoc)
     * 
     * @see logic.Command#undo()
     */
    @Override
    public void undo() {
        // TODO Auto-generated method stub

    }
}