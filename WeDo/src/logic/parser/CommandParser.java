/**
 * 
 */
package logic.parser;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import logic.command.commandList.AddCommand;
import logic.command.commandList.CompleteCommand;
import logic.command.commandList.Command;
import logic.command.commandList.DeleteCommand;
import logic.command.commandList.EditCommand;
import logic.command.commandList.ExitCommand;
import logic.command.commandList.UncompleteCommand;
import logic.command.commandList.RedoCommand;
import logic.command.commandList.SearchCommand;
import logic.command.commandList.UndoCommand;
import logic.command.commandList.ViewCommand;
import logic.utility.KeyWordMappingList;
import logic.utility.MultiMapMatcher;
import logic.utility.StringHandler;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;

/**
 * @author Kuan Tien Long
 *
 */
public class CommandParser {
    
    private Command command;
    private String wordUsed;
    private String wordRemaining;
    private boolean lastWordUsed;
  
    

    /**
     * <p> The source will be parsed to see if it contains date.
     * @param source the String to be parsed
     * @return if source contains valid command 
     */
    public boolean tryParse(String source) {
        if (source == null) {
            return false;
        }

        source = source.trim();

        if (source.isEmpty()) 
        {
            return false;    
        }
        
        String firstWord = StringHandler.getFirstWord(source);               
        String lastWord = StringHandler.getLastWord(source);
        
        command = getCommand(firstWord);
        if(command != null)
        {
            wordUsed = firstWord;
            source = handleEditCommandforFirstWord(source);
            setWordRemaining(StringHandler.removeFirstMatched(source,wordUsed));
            return true;
        }
 
       command = getCommand(lastWord);
       if(command != null)
       {
           wordUsed = lastWord;
           source = handleEditCommandForLastWord(source);
           setWordRemaining(StringHandler.removeLastMatch(source,wordUsed));
           lastWordUsed = true;
           return true;
       }
       
        return false;
    }
    
    /**
     * Remove the index for edit and append it to the front if valid
     * return source if invalid
     * @param source
     * @return
     */
    private String handleEditCommandforFirstWord(String source)
    {
        String index = StringHandler.getDigitAfterFirstWord(source);    
        if(handleFlexibleEditCommand(source,index))
        {
            source = StringHandler.removeDigitAfterFirstWord(source);
            return  index + " " + source ;

        }
        else
        {
            return source;
        }

    }
    
    /**
     * Remove the index for edit and append it to the front if valid
     * return source if invalid
     * @param source
     * @return
     */
    private String handleEditCommandForLastWord(String source)
    {
        String index = StringHandler.getDigitAfterLastWord(source);  
        if(handleFlexibleEditCommand(source,index))
        {
            source = StringHandler.removeDigitAfterLastWord(source);
            return  index + " " + source ;
        }
        else
        {
            return source;
        }

    }


    /**
     * determine is action needed to be done
     * @param source
     * @param index
     * @return true if there's a valid index and command is EditCommand
     */
    private boolean handleFlexibleEditCommand(String source, String index) {
        if(command instanceof EditCommand)
        {
            if(index != null)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    private Command getCommand(String firstWord) {
       return MultiMapMatcher.getMatchedKey(KeyWordMappingList.getCommandMultiMap(), firstWord);
    }
    
    public Command getCommand()
    {
        return command;
    }
    

    /**
     * This function create a map of commands and add them to the multi map. The
     * real multi map will be generated by reading a file which consist the
     * available action word to match
     */
    public static Multimap<Command, String> createFakeMultiMapForCommand() {
        Multimap<Command, String> availableActions = ArrayListMultimap.create();

        Command addCommand = new AddCommand();
        Command completeCommand = new CompleteCommand();
        Command uncompleteCommand = new UncompleteCommand();
        Command deleteCommand = new DeleteCommand();
        Command exitCommand = new ExitCommand();
        Command searchCommand = new SearchCommand();
        Command editCommand = new EditCommand();
        Command viewCommand = new ViewCommand();
        Command undoCommand = new UndoCommand();
        Command redoCommand = new RedoCommand();

        final Map<Command, Collection<String>> addActions = ImmutableMap
                .<Command, Collection<String>> of(addCommand,
                        Arrays.asList("add", "new", "create", "cre8"));
        final Map<Command, Collection<String>> completeActions = ImmutableMap
                .<Command, Collection<String>> of(completeCommand, Arrays.asList(
                        "complete", "done", "finish", "tick"));
        final Map<Command, Collection<String>> uncompleteActions = ImmutableMap
                .<Command, Collection<String>> of(uncompleteCommand, Arrays.asList(
                        "incomplete", "undone", "cancel", "uncomplete", "decomplete", "untick"));
     
        final Map<Command, Collection<String>> deleteActions = ImmutableMap
                .<Command, Collection<String>> of(deleteCommand, Arrays.asList(
                        "delete", "d", "delete", "remove", "remove"));
        final Map<Command, Collection<String>> exitActions = ImmutableMap
                .<Command, Collection<String>> of(exitCommand, Arrays.asList(
                        "exit", "e", "exit", "quit", "q", "quit", "alt qq",
                        "leave", "leave", "bye"));
        final Map<Command, Collection<String>> searchActions = ImmutableMap
                .<Command, Collection<String>> of(searchCommand, Arrays.asList(
                        "search", "search", "s", "find", "f", "find"));
        final Map<Command, Collection<String>> editActions = ImmutableMap
                .<Command, Collection<String>> of(editCommand, Arrays.asList(
                        "edit", "edit", "e", "modify", "m", "modify"));
        final Map<Command, Collection<String>> viewActions = ImmutableMap
                .<Command, Collection<String>> of(viewCommand, Arrays.asList(
                        "view", "viewed", "viewing", "read", "reading", "see", "saw", "get"));
        final Map<Command, Collection<String>> undoActions = ImmutableMap
                .<Command, Collection<String>> of(undoCommand,
                        Arrays.asList("undo", "undo"));
        final Map<Command, Collection<String>> redoActions = ImmutableMap
                .<Command, Collection<String>> of(redoCommand,
                        Arrays.asList("redo", "redo"));

        addMapToMultiMap(addActions, availableActions);
        addMapToMultiMap(completeActions, availableActions);
        addMapToMultiMap(uncompleteActions, availableActions);
        addMapToMultiMap(deleteActions, availableActions);
        addMapToMultiMap(exitActions, availableActions);
        addMapToMultiMap(searchActions, availableActions);
        addMapToMultiMap(editActions, availableActions);
        addMapToMultiMap(viewActions, availableActions);
        addMapToMultiMap(undoActions, availableActions);
        addMapToMultiMap(redoActions, availableActions);

        return availableActions;
    }

    private static void addMapToMultiMap(final Map<Command, Collection<String>> map,
            Multimap<Command, String> availableActions) {
        for (Command key : map.keySet()) {
            availableActions.putAll(key, map.get(key));
        }
    }

    /**
     * @return the wordRemaining
     */
    public String getWordRemaining() {
        return wordRemaining;
    }

    /**
     * @param wordRemaining the wordRemaining to set
     */
    public void setWordRemaining(String wordRemaining) {
        this.wordRemaining = wordRemaining;
    }

    /**
     * @return the wordUsed
     */
    public String getWordUsed() {
        return wordUsed;
    }

    /**
     * @param wordUsed the wordUsed to set
     */
    public void setWordUsed(String wordUsed) {
        this.wordUsed = wordUsed;
    }

    /**
     * @return the lastWordUsed
     */
    public boolean isLastWordUsed() {
        return lastWordUsed;
    }

    /**
     * @param lastWordUsed the lastWordUsed to set
     */
    public void setLastWordUsed(boolean lastWordUsed) {
        this.lastWordUsed = lastWordUsed;
    }

}
