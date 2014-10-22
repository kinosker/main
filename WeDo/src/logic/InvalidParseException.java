/**
 * 
 */
package logic;

/**
 * @author Kuan Tien Long
 *
 */
@SuppressWarnings("serial")
public class InvalidParseException extends Exception 
{

        public InvalidParseException() { super(); }
        public InvalidParseException(String message) { super(message); }
        public InvalidParseException(String message, Throwable cause) { super(message, cause); }
        public InvalidParseException(Throwable cause) { super(cause); }
      
}