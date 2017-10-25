package important;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {

	// Remove the blank on head and tail of a line
	// Also, merge multiple blanks to one blank
	public static String replaceBlank(String str) {
		Pattern pt = Pattern.compile("^\\s+|\\s+$");
		Matcher mt = pt.matcher(str);
		str = mt.replaceAll("");

		// Merge multiple blanks
		Pattern p = Pattern.compile("\\s+");
		Matcher m = p.matcher(str);
		str = m.replaceAll(" ");
		return str;
	}

}
