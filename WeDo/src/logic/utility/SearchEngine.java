package logic.utility;

import java.util.ArrayList;

import logic.exception.InvalidCommandException;

import com.google.common.collect.Multimap;

import dataStorage.BasicDataHandler;

//@author A0112862L
/**
 * Search the lists of tasks.
 */
public class SearchEngine {

	BasicDataHandler dataHandler;

	public SearchEngine(BasicDataHandler dataHandler) {
		this.dataHandler = dataHandler;
	}

	/**
	 * @param source
	 *            the input source that need to be searched
	 * @param searchInput
	 *            the searchInput to be searched
	 * @return the result that matched with the searchInput(case insensitive)
	 */
	public ArrayList<Task> searchCaseInsensitive(String description) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Task> searchCaseInsensitive(
			Multimap<String, Task> multimap, String searchInput) {
		ArrayList<Task> searchList = new ArrayList<Task>();
		if (invalidList(multimap) || invalidSearchInput(searchInput)) {
			return searchList;
		}

		searchInput = searchInput.trim().toLowerCase();

		for (String key : multimap.keys()) {
			for (Task task : multimap.get(key)) {

				if (task.getDescription().toLowerCase().contains(searchInput)) {
					searchList.add(task);
				}
			}
		}
		return searchList;
	}

	/**
	 * @param source
	 * @return whether the list consist anything
	 */
	private boolean invalidList(Multimap<String, Task> source) {
		return source == null || source.isEmpty();
	}

	/**
	 * @param searchInput
	 * @return whether the searchInput consist anything
	 */
	private boolean invalidSearchInput(String searchInput) {
		return searchInput == null || searchInput.trim().isEmpty();
	}

	public ArrayList<Task> search(String str1) {
		return searchWagnerList(str1);
	}

	public ArrayList<Task> search(Task task) throws InvalidCommandException {
		if (task.getDescription() != null) {
			return searchWagnerList(task.getDescription());

		} else {
			dataHandler.view(task);
			return dataHandler.getObservableList().getList();
		}
	}


	/**
	 * get the list of tasks that their descriptions match with the string user
	 * wants to search.
	 * 
	 * @param str1
	 *            the string user wants to search.
	 * @return the list of the tasks that match the user's search.
	 */
	public ArrayList<Task> searchWagnerList(String str1) {

		int distance;
		ArrayList<Task> tmp = new ArrayList<Task>();

		for (Task t : dataHandler.getAllTasks()) {
			String[] str2 = t.getDescription().split(" ");
			for (String s : str2) {

				distance = getDist(str1, s);

				if (distance < s.length() / 2) {
					tmp.add(t);
					break;

				}
			}
		}

		dataHandler.setDisplayedTasks(tmp);

		return tmp;
	}

	/**
	 * get the Wagner Fischer distance between two strings.
	 * 
	 * @param str1
	 * @param str2
	 * @return the distance
	 */
	public int getDist(String str1, String str2) {

		int len1 = str1.length();
		int len2 = str2.length();
		int[][] arr = new int[len1 + 1][len2 + 1];
		for (int i = 0; i <= len1; i++)
			arr[i][0] = i;
		for (int i = 1; i <= len2; i++)
			arr[0][i] = i;
		for (int i = 1; i <= len1; i++) {
			for (int j = 1; j <= len2; j++) {
				int m = (str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1;
				arr[i][j] = Math.min(
						Math.min(arr[i - 1][j] + 1, arr[i][j - 1] + 1),
						arr[i - 1][j - 1] + m);
			}
		}

		return arr[len1][len2];
	}

	/**
	 * @param source
	 *            the input source that need to be searched
	 * @param searchInput
	 *            the searchInput to be searched
	 * @return the result that matched with the searchInput(case sensitive)
	 */
	public ArrayList<Task> searchSensitive(Multimap<String, Task> multimap,
			String searchInput) {
		ArrayList<Task> searchList = new ArrayList<Task>();
		if (invalidList(multimap) || invalidSearchInput(searchInput)) {
			return searchList;
		}

		for (String key : multimap.keys()) {
			for (Task task : multimap.get(key)) {

				if (task.getDescription().contains(searchInput)) {
					searchList.add(task);
				}
			}
		}
		return searchList;
	}

}
