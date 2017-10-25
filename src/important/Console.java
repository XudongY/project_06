package important;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Console {
	public static void readLine() {
		Scanner input = new Scanner(System.in);
		String line;
		// Read the file line by line
		List<Indivdual> indivdualList = new ArrayList<Indivdual>();
		List<Family> familiesList = new ArrayList<Family>();
		HashMap<String, List<String>> errors = new HashMap<String, List<String>>();
		while (true) {
			if (!(line = input.nextLine()).equals("quit")) {
				line = Tools.replaceBlank(line);
				Parse.parse(line, indivdualList, familiesList);
			} else {
				break;
			}
		}
		Print.print_arraylist_indivdual((ArrayList<Indivdual>) indivdualList);
		Print.print_arraylist_family((ArrayList<Family>) familiesList);
		Print.print_Error((HashMap<String, List<String>>) errors);
	}
}
