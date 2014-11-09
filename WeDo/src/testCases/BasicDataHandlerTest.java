package testCases;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;

import logic.exception.InvalidCommandException;
import logic.utility.Task;

import org.junit.Before;
import org.junit.Test;

import dataStorage.BasicDataHandler;

// @author A0112862L
/**
 * This JUnit test class test some functions of BasicDatahandler class.
 */

public class BasicDataHandlerTest {

	BasicDataHandler datahandler;
	ArrayList<Task> mainList, displayList;
	int taskNum = 0;

	@Before
	public void setUp() throws Exception {
		datahandler = new BasicDataHandler();
		mainList = new ArrayList<Task>();
		displayList = new ArrayList<Task>();

	}

	/**
	 * Retrieve observableList (currently displaying list) and mainlist from
	 * BasicDataHandler class and update the local lists.
	 */
	private void updateLists() {
		mainList = datahandler.getAllTasks();
		displayList = datahandler.getObservableList().getList();
	}

	/**
	 * @param daysFromToday
	 *            set the deadline to the days from today
	 * @return new created deadLine task
	 */
	private Task createDeadline(int daysFromToday) {
		Task task = new Task();
		task.setDescription("Task " + (taskNum++));
		task.setEndDate(LocalDate.now().plusDays(daysFromToday));

		return task;
	}

	/**
	 * @return a floating task
	 */
	private Task createFloat() {
		Task task = new Task();
		task.setDescription("Task " + (++taskNum));

		return task;
	}

	/**
	 * @param daysBefore
	 *            set the start date of the task to days before today
	 * @param daysAfter
	 *            set the deadline of the task to days from today
	 * @return a timed task
	 */
	private Task createTimed(int daysBefore, int daysAfter) {
		Task task = new Task();
		task.setDescription("Task " + (++taskNum));
		task.setEndDate(LocalDate.now().plusDays(daysAfter));
		task.setStartDate(LocalDate.now().minusDays(daysBefore));

		return task;
	}

	/**
	 * @throws InvalidCommandException
	 */
	@Test
	public void testAddAndRemove() throws InvalidCommandException {

		testDeadLine();

		testFloat();

		testTimed();

	}

	/**
	 * Test the adding and removing of a timed task. If the task is not in the
	 * list after adding, Or if the task is still there after removing, assert
	 * fails.
	 * 
	 * @throws InvalidCommandException
	 */
	private void testTimed() throws InvalidCommandException {
		Task task4 = createTimed(3, 5);
		datahandler.addTask(task4);
		updateLists();

		assertTrue(mainList.contains(task4));
		datahandler.view(createTimed(3, 5));
		updateLists();

		assertTrue(displayList.contains(task4));

		datahandler.removeTask(task4);
		updateLists();

		assertFalse(displayList.contains(task4));
		assertFalse(mainList.contains(task4));
	}

	/**
	 * Test the adding and removing of a floating task. If the task is not in
	 * the list after adding, Or if the task is still there after removing,
	 * assert fails.
	 * 
	 * @throws InvalidCommandException
	 */
	private void testFloat() throws InvalidCommandException {
		Task task3 = createFloat();
		datahandler.addTask(task3);

		updateLists();
		Task viewFloat = createFloat();
		viewFloat.setDescription("someday");
		datahandler.view(viewFloat);

		assertTrue(displayList.contains(task3));
		assertTrue(mainList.contains(task3));

		datahandler.view(createDeadline(0));
		updateLists();

		assertFalse(displayList.contains(task3));

		datahandler.removeTask(task3);
		updateLists();
		assertFalse(mainList.contains(task3));
	}

	/**
	 * Test the adding and removing of a deadLine task. If the task is not in
	 * the list after adding, Or if the task is still there after removing,
	 * assert fails.
	 * 
	 * @throws InvalidCommandException
	 */
	private void testDeadLine() throws InvalidCommandException {
		Task task1 = createDeadline(0); // create a deadline task with Today's
										// date

		datahandler.addTask(task1);
		updateLists();
		datahandler.view(createDeadline(0)); // change view to today's

		assertTrue(displayList.contains(task1));
		assertTrue(mainList.contains(task1));

		datahandler.view(createDeadline(1));
		updateLists();
		assertFalse(displayList.contains(task1));

		datahandler.removeTask(task1);
		updateLists();
		assertFalse(mainList.contains(task1));

		Task task2 = createDeadline(1); // create a task due tomorrow

		datahandler.addTask(task2);
		updateLists();
		datahandler.view(createDeadline(1));

		assertTrue(displayList.contains(task2));
		assertTrue(mainList.contains(task2));

		datahandler.view(createDeadline(0));
		updateLists();

		assertFalse(displayList.contains(task2));
		assertTrue(mainList.contains(task2));

		datahandler.removeTask(task2);
		updateLists();
		assertFalse(mainList.contains(task2));
	}

	@Test
	public void testView() {
	}

}
