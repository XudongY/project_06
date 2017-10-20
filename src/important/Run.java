package important;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class Run {
	static PrintStream oldPrintStream;
	static FileOutputStream bos;
	static MultiOutputSteam multi;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		show_on_console_and_outputfile();
		Scanner sc = new Scanner(System.in);
		System.out
				.println("If you want to choose a file, please press 1; if you want to test in console, please press 2.");
		String choice = sc.nextLine();
		if (choice.equals("1")) {
			try {
				Read.openFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (choice.equals("2")) {
			System.out
					.println("Please enter quit when you complete inputing your lines:");
			Console.readLine();
		}
	}

	private static void show_on_console_and_outputfile() throws IOException {
		oldPrintStream = System.out;
		bos = new FileOutputStream("output.txt");
		multi = new MultiOutputSteam(new PrintStream(bos), oldPrintStream);
		System.setOut(new PrintStream(multi));
	}
}
