package sprints;

import important.Family;
import important.Indivdual;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Sprint2_Checkout {

	public static HashMap<String, List<String>> errors = new HashMap<String, List<String>>();

	public static HashMap<String, List<String>> check_List(
			List<Indivdual> indivdualList, List<Family> familyList) {
		for (Indivdual in : indivdualList) {
			us07_less_than_150_years_old(in.getBirthday(), in.getDeath(),
					in.getID());
		}
		for (Family fam : familyList) {
			String husband_id = null;
			String husband_name = null;
			Date husband_death = null;
			Date husband_birth = null;

			String wife_id = null;
			String wife_name = null;
			Date wife_death = null;
			Date wife_birth = null;

			Date child_birth = null;

			for (int i = 0; i < indivdualList.size(); ++i) {
				if (fam.getHusbandID().equals(indivdualList.get(i).getID())) {
					husband_id = indivdualList.get(i).getID();
					husband_name = indivdualList.get(i).getName();
					husband_death = indivdualList.get(i).getDeath();
					husband_birth = indivdualList.get(i).getBirthday();
				}
				if (fam.getWifeID().equals(indivdualList.get(i).getID())) {
					wife_id = indivdualList.get(i).getID();
					wife_name = indivdualList.get(i).getName();
					wife_death = indivdualList.get(i).getDeath();
					wife_birth = indivdualList.get(i).getBirthday();
				}
				for (String child_id : fam.getChildren()) {
					if (child_id.equals(indivdualList.get(i).getID())) {
						child_birth = indivdualList.get(i).getBirthday();
<<<<<<< HEAD
						// System.out.println("Here!!!");
=======
						System.out.println("Here!!!");
>>>>>>> a5e1d7b343024dab10f5d4456f54c5ecb64360f9
						us08_birth_before_marriage_of_parents(child_birth,
								fam.getMarried(), fam.getDivorced(), child_id);
					}
				}
			}
			us15_fewer_than_15_siblings(fam.getChildren(), fam.getID());
			us16_male_last_names(husband_name, wife_name, fam.getID());

		}
		return errors;
	}

	public static void addError(HashMap<String, List<String>> errors,
								String key, String value) {
		if (errors.containsKey(key)) {
			List<String> res = errors.get(key);
			res.add(value);
			errors.put(key, res);
		} else {
			List<String> res = new ArrayList<String>();
			res.add(value);
			errors.put(key, res);
		}
	}

	// US15 and US16 are done by Yutong Zhao

	// US15: There should be fewer than 15 siblings in a family
	public static boolean us15_fewer_than_15_siblings(List<String> children,
													  String fam_id) {
		if (children != null && children.size() >= 15) {
			String error = "ERROR: FAMILY: US15: " + fam_id
					+ ": Has more than 15 childs.";
			addError(errors, "US15", error);
			return false;
		}
		return true;
	}

	// US16: All male members of a family should have the same last name
	public static boolean us16_male_last_names(String hus_name,
											   String wife_name, String fam_id) {
		if (hus_name != null && wife_name != null) {
			String[] hus = hus_name.split("/");
			String[] wife = wife_name.split("/");
			if (!hus[1].equals(wife[1])) {
				String error = "ERRPR: FAMILY: US16: " + fam_id
						+ ": Does not have the same male last name.";
				addError(errors, "US16", error);
				return false;
			}
		}
		return true;
	}

	// US07 and US08 are done by Xudong

	// US07: Death should be less than 150 years after birth for dead people,
	// and current date should be less than 150 years after birth for all living
	// people
	public static boolean us07_less_than_150_years_old(Date birth, Date death,
<<<<<<< HEAD
			String id) {
=======
													   String id) {
>>>>>>> a5e1d7b343024dab10f5d4456f54c5ecb64360f9
		if (birth != null) {
			int birth_year = Integer
					.parseInt(new SimpleDateFormat("yyyy-MM-dd").format(birth)
							.substring(0, 4));
			if (death != null) {
				int death_year = Integer.parseInt(new SimpleDateFormat(
						"yyyy-MM-dd").format(death).substring(0, 4));
				if ((death_year - birth_year) >= 150) {
					String error = "ERROR: INDIVIDUAL: US07: " + id
							+ ": Age is more than 150.";
					addError(errors, "US07", error);
					return false;
				}
			} else {
				int current_year = Integer.parseInt(new SimpleDateFormat(
						"yyyy-MM-dd").format(new Date()).substring(0, 4));
				if ((current_year - birth_year) >= 150) {
					String error = "ERROR: INDIVIDUAL: US07: " + id
							+ ": Age is more than 150.";
					addError(errors, "US07", error);
					return false;
				}
			}
		}
		return true;
	}

	// US08: Children should be born after marriage of parents (and not more
	// than 9 months after their divorce)
	public static boolean us08_birth_before_marriage_of_parents(Date birth,
<<<<<<< HEAD
			Date marriage, Date divorce, String id) {
=======
																Date marriage, Date divorce, String id) {
>>>>>>> a5e1d7b343024dab10f5d4456f54c5ecb64360f9
		if (birth != null) {
			if (marriage != null && birth.before(marriage)) {
				String child_birth = new SimpleDateFormat("yyyy-MM-dd")
						.format(birth);
				String parents_marriage = new SimpleDateFormat("yyyy-MM-dd")
						.format(marriage);
				String error = "ERROR: INDIVIDUAL: US08: " + id + ": Born on "
						+ child_birth + " before parents marry on "
						+ parents_marriage;
				addError(errors, "US08", error);
				return false;
			}
			if (divorce != null) {
				if (birth.getTime() - divorce.getTime() >= (1000 * 3600 * 24 * 30 * 9)) {
					String child_birth = new SimpleDateFormat("yyyy-MM-dd")
							.format(birth);
					String parents_divorce = new SimpleDateFormat("yyyy-MM-dd")
							.format(divorce);
					String error = "ERROR: INDIVIDUAL: US08: "
							+ id
							+ ": Born on "
							+ child_birth
							+ " after more than 9 months after parents divorced on "
							+ parents_divorce;
					addError(errors, "US08", error);
					return false;
				}
			}
		}
		return true;
	}

	// US12 and US21 are done by Chenglin

	// US35 and US36 are done by Disha

}
