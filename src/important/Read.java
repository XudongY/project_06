package important;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import sprints.Sprint1_Checkout;
import sprints.Sprint2_Checkout;

public class Read {
	public static void openFile() throws IOException {
		try {
			JFileChooser jfc = new JFileChooser(FileSystemView
					.getFileSystemView().getHomeDirectory());

			int returnValue = jfc.showOpenDialog(null);
			File selectedFile = null;
			String suffix = "";
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				selectedFile = jfc.getSelectedFile();
				String fileName = selectedFile.getName();
				suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			}
			if (selectedFile != null && suffix.equals("txt")) {
				FileInputStream fis = new FileInputStream(
						selectedFile.getAbsolutePath());

				BufferedReader br = new BufferedReader(new InputStreamReader(
						fis));
				String line;
				// Read the file line by line
				List<Indivdual> indivdualList = new ArrayList<Indivdual>();
				List<Family> familiesList = new ArrayList<Family>();
				HashMap<String, List<String>> errors_sprint1 = new HashMap<String, List<String>>();
				HashMap<String, List<String>> errors_sprint2 = new HashMap<String, List<String>>();

				while ((line = br.readLine()) != null) {
					line = Tools.replaceBlank(line);
					Parse.parse(line, indivdualList, familiesList);

				}
				errors_sprint1 = Sprint1_Checkout.check_List(indivdualList,
						familiesList);
				errors_sprint2 = Sprint2_Checkout.check_List(indivdualList,
						familiesList);
				Print.print_arraylist_indivdual((ArrayList<Indivdual>) indivdualList);
				Print.print_arraylist_family((ArrayList<Family>) familiesList);
				// Print.print_Error((ArrayList<String>) errors);
				Print.print_Error((HashMap<String, List<String>>) errors_sprint1);
				Print.print_Error((HashMap<String, List<String>>) errors_sprint2);
				// Close the input stream
				br.close();
				fis.close();
			} else {
				System.out
						.println("Caution: This file does not exist or is not a txt file!!!");
			}
		} catch (FileNotFoundException e) {
			System.out.println("Can NOT read the file!");
			e.printStackTrace();
		}

	}

}
