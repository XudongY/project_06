package important;

import java.util.HashMap;

public class LegalTags {

	static HashMap<String, Integer> tags = new HashMap<String, Integer>();

	public static HashMap createTags() {
		tags.put("INDI", 0);
		tags.put("NAME", 1);
		tags.put("SEX", 1);
		tags.put("BIRT", 1);
		tags.put("DEAT", 1);
		tags.put("FAMC", 1);
		tags.put("FAMS", 1);
		tags.put("FAM", 0);
		tags.put("MARR", 1);
		tags.put("HUSB", 1);
		tags.put("WIFE", 1);
		tags.put("CHIL", 1);
		tags.put("DIV", 1);
		tags.put("DATE", 2);
		tags.put("HEAD", 0);
		tags.put("TRLR", 0);
		tags.put("NOTE", 0);

		return tags;
	}

	public static boolean checkTags(String tag) {
		HashMap<String, Integer> tags = createTags();
		if (tags.containsKey(tag)) {
			return true;
		}

		return false;
	}
}
