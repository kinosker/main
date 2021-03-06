/**
 * 
 */
package testCases.deprecated;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;

import logic.deprecated.TaskParserBasic;
import logic.utility.Task;

import org.junit.Test;

import definedEnumeration.Priority;

//@author A0112887X -unused
/**
 *
 */
public class TaskParserTest {

    @Test
    public void test() {

        Task expectedTask = new Task();
        TaskParserBasic taskParser = new TaskParserBasic();

        expectedTask.setDescription("floating task");
        assertEquals(expectedTask,
                taskParser.buildTask(new StringBuilder("-add floating task")));

        expectedTask.setStartDate(LocalDate.of(2014, 9, 12));
        expectedTask.setDescription("momo");
        expectedTask.setPriority(Priority.PRIORITY_HIGH);
        assertEquals(expectedTask, taskParser.buildTask(new StringBuilder(
                "-add momo -date 12 sept -priority high")));

        expectedTask.setStartDate(LocalDate.of(2014, 9, 12));
        expectedTask.setEndDate(LocalDate.of(2014, 9, 18));
        expectedTask.setDescription("buy for me something");
        expectedTask.setPriority(Priority.PRIORITY_LOW);
        assertEquals(
                expectedTask,
                taskParser
                        .buildTask(new StringBuilder(
                                "-add buy for me something -date 12 sept to 18 sept -priority low")));

        expectedTask.setStartDate(LocalDate.of(2014, 9, 18));
        expectedTask.setStartTime(LocalTime.of(14, 0));
        expectedTask.setEndTime(LocalTime.of(2, 0));
        expectedTask.setEndDate(LocalDate.of(2014, 9, 22));
        expectedTask.setDescription("buy for me something");
        expectedTask.setPriority(Priority.PRIORITY_MEDIUM);
        assertEquals(
                expectedTask,
                taskParser
                        .buildTask(new StringBuilder(
                                "-add buy for me something -date 18 sept 2pm to 22 sept 2am -priority med")));

    }

}
