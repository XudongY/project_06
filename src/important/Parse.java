package important;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Parse {
	private static String pre = "";

	public static void parse(String line, List<Indivdual> indivduals,
			List<Family> families) {
		String level = "";
		String tag = "";
		String valid = " ";
		String arguments = "";
		int level_indice = 0;
		int tag_indice = 0;

		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == ' ') {
				level_indice = i;
				break;
			}
		}
		for (int i = level_indice + 1; i < line.length(); i++) {

			if (i == line.length() - 1) {
				tag_indice = line.length() - 1;
				break;
			}

			if (line.charAt(i) == ' ') {
				tag_indice = i;
				break;
			} else {
				tag_indice = line.length() - 1;
			}

		}
		level = line.substring(0, level_indice);

		tag = line.substring(level_indice + 1, tag_indice + 1);

		if (tag.charAt(tag.length() - 1) == ' ') {
			tag = line.substring(level_indice + 1, tag_indice);
		}
		arguments = line.substring(tag_indice + 1, line.length());

		// Done by xudong YU
		if (arguments.contains("INDI") && LegalTags.checkTags(arguments)) {
			Indivdual indivdual = new Indivdual();
			indivdual.setID(tag);
			indivduals.add(indivdual);
		} else if (tag.contains("NAME") && LegalTags.checkTags(tag)) {
			indivduals.get(indivduals.size() - 1).setName(arguments);
		} else if (tag.contains("SEX") && LegalTags.checkTags(tag)) {
			indivduals.get(indivduals.size() - 1).setGender(arguments);
		} else if (tag.contains("DATE") && LegalTags.checkTags(tag)) {
			String[] age = arguments.split(" ");
			int age1 = Integer.parseInt(age[age.length - 1]);
			indivduals.get(indivduals.size() - 1).setAge(2017 - age1);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"dd MMM yyyy");
			try {
				Date date = simpleDateFormat.parse(arguments);
				if (pre.equals("BIRT")) {
					indivduals.get(indivduals.size() - 1).setBirthday(date);
				} else if (pre.equals("Death")) {
					indivduals.get(indivduals.size() - 1).setDeath(date);
				}
			} catch (ParseException ex) {
				System.out.println("Exception " + ex);
			}
		} else if (tag.contains("BIRT") && LegalTags.checkTags(tag)) {
			pre = "BIRT";
		} else if (tag.contains("DEAT") && LegalTags.checkTags(tag)) {
			pre = "Death";
			indivduals.get(indivduals.size() - 1).setAlive(false);
		} else if (tag.contains("FAMC") && LegalTags.checkTags(tag)) {
			// indivduals.get(indivduals.size() - 1).Child.add(arguments);
			List<String> childs = indivduals.get(indivduals.size() - 1)
					.getChild();
			childs.add(arguments);
			indivduals.get(indivduals.size() - 1).setChild(childs);
		} else if (tag.contains("FAMS") && LegalTags.checkTags(tag)) {
			List<String> spouses = indivduals.get(indivduals.size() - 1)
					.getSpouse();
			spouses.add(arguments);
			// indivduals.get(indivduals.size() - 1).Spouse.add(arguments);
			indivduals.get(indivduals.size() - 1).setSpouse(spouses);
		}

		if (arguments.contains("FAM") && LegalTags.checkTags(arguments)) {
			Family family = new Family();
			family.setID(tag);
			families.add(family);
		} else if (tag.contains("MARR") && LegalTags.checkTags(tag)) {
			pre = "MARR";
		}

		else if (tag.contains("DATE") && LegalTags.checkTags(tag)) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"dd MMM yyyy");
			try {
				Date date = simpleDateFormat.parse(arguments);
				if (pre.equals("MARR")) {
					families.get(families.size() - 1).setMarried(date);
				}
				if (pre.equals("DIV")) {
					families.get(families.size() - 1).setDivorced(date);
				}
			} catch (ParseException ex) {
				System.out.println("Exception " + ex);
			}
		} else if (tag.contains("HUSB") && LegalTags.checkTags(tag)) {
			families.get(families.size() - 1).setHusbandID(arguments);
			for (Indivdual p : indivduals) {
				if (p.getID().equals(arguments)) {
					families.get(families.size() - 1).setHusbandName(
							p.getName());
				}
			}
		} else if (tag.contains("WIFE") && LegalTags.checkTags(tag)) {
			families.get(families.size() - 1).setWifeID(arguments);
			for (Indivdual p : indivduals) {
				if (p.getID().equals(arguments)) {
					families.get(families.size() - 1).setWifeName(p.getName());
				}
			}
		}

		else if (tag.contains("DIV") && LegalTags.checkTags(tag)) {
			pre = "DIV";
		} else if (tag.contains("CHIL") && LegalTags.checkTags(tag)) {

			List<String> children = families.get(families.size() - 1)
					.getChildren();
			children.add(arguments);
			families.get(families.size() - 1).setChildren(children);

		}
	}
}
