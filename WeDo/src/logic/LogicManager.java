/**
 * 
 */
package logic;

import logic.command.CommandExecutor;
import logic.exception.InvalidCommandException;
import logic.parser.DynamicParseResult;
import logic.parser.ParseResult;
import logic.parser.ParserManager;
import logic.utility.Task;
import dataStorage.DataHandler;

//@author A0112887X
/**
 *
 */
public class LogicManager {

    private DataHandler dataHandler;
    private CommandExecutor commandExecutor;

    /**
     * Constructor for CommandHandler
     * 
     * @param dataHandler
     *            the handler which contains of all the data
     */
    public LogicManager(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
        this.commandExecutor = new CommandExecutor(this.dataHandler);
    }

    public DynamicParseResult dynamicParse(String userInput) {
        ParserManager parserManager = new ParserManager();
        return parserManager.dynamicParsing(userInput);
    }

    public void setComplete(int index, boolean prevCompleteStatus)
            throws InvalidCommandException {
        String Command;

        if (prevCompleteStatus) {
            Command = "Uncomplete " + index;
        } else {
            Command = "Complete " + index;
        }
        ParseResult parseResult = interpret(Command);
        executeCommand(parseResult);
    }

    /**
     * Interpret what the user had entered
     * @param userInput
     *            the input that the user entered which will be decipher into
     *            task and command
     * @throws InvalidCommandException
     */
    public ParseResult interpret(String userInput) {

        ParserManager parserManager = new ParserManager();
        return parserManager.interpret(userInput);
    }

    /**
     * Execute the command based on parseResult
     * @param parseResult the result parsed
     * @throws InvalidCommandException
     */
    public void executeCommand(ParseResult parseResult)
            throws InvalidCommandException {
        commandExecutor
                .execute(parseResult.getCommand(), parseResult.getTask());
    }

    /**
     * @param index
     *            the index to get the task
     * @return the task at the index or null if it is not valid
     */
    public Task getTaskToBeEdited(int index) {
        if (dataHandler.indexValid(index)) {
            return dataHandler.getTask(index);
        } else {
            return null;
        }
    }
}
