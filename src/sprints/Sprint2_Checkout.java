package sprints;

import important.Family;
import important.Indivdual;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Sprint2_Checkout {

	public static HashMap<String, List<String>> errors = new HashMap<String, List<String>>();
	public static HashMap<String, Date> recent_births = new HashMap<String, Date>();
	public static HashMap<String, Date> recent_deaths = new HashMap<String, Date>();

	public static HashMap<String, List<String>> check_List(
			List<Indivdual> indivdualList, List<Family> familyList) {
		for (Indivdual in : indivdualList) {
			// geting individual details
			String ID = in.getID();

			Date DeathDay = in.getDeath();
			Date Birthday = in.getBirthday();
			check_for_recent_births(ID, Birthday);

			if (DeathDay != null) {
				check_for_recent_deaths(ID, DeathDay);
			}
			us07_less_than_150_years_old(in.getBirthday(), in.getDeath(),
					in.getID());
		}

		us35_list_recent_births(recent_births);
		us36_list_recent_deaths(recent_deaths);

		for (Family fam : familyList) {
			String husband_id = null;
			String husband_name = null;
			String husband_gender = null;
			Date husband_death = null;
			Date husband_birth = null;

			String wife_id = null;
			String wife_name = null;
			String wife_gender = null;
			Date wife_death = null;
			Date wife_birth = null;

			Date child_birth = null;
			List<String> children = fam.getChildren();

			for (int i = 0; i < indivdualList.size(); ++i) {
				if (fam.getHusbandID().equals(indivdualList.get(i).getID())) {
					husband_id = indivdualList.get(i).getID();
					husband_name = indivdualList.get(i).getName();
					husband_death = indivdualList.get(i).getDeath();
					husband_birth = indivdualList.get(i).getBirthday();
					husband_gender = indivdualList.get(i).getGender();
				}
				if (fam.getWifeID().equals(indivdualList.get(i).getID())) {
					wife_id = indivdualList.get(i).getID();
					wife_name = indivdualList.get(i).getName();
					wife_death = indivdualList.get(i).getDeath();
					wife_birth = indivdualList.get(i).getBirthday();
					wife_gender = indivdualList.get(i).getGender();
				}
				for (String child_id : fam.getChildren()) {
					if (child_id.equals(indivdualList.get(i).getID())) {
						child_birth = indivdualList.get(i).getBirthday();
						// System.out.println("Here!!!");
						us08_birth_before_marriage_of_parents(child_birth,
								fam.getMarried(), fam.getDivorced(), child_id);
					}
				}
			}
			us15_fewer_than_15_siblings(fam.getChildren(), fam.getID());
			us16_male_last_names(husband_name, wife_name, fam.getID());
			for (String child_id : children) {
				us12_parents_not_too_old(fam.getID(), husband_birth,
						wife_birth, child_id, indivdualList);
			}
			us21_correct_gender_for_role(fam.getID(), husband_gender,
					wife_gender);
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
				String error = "ERROR: FAMILY: US16: " + fam_id
						+ ": Does not have the same male last name.";
				addError(errors, "US16", error);
				return false;
			}
		}
		return true;
	}

	// US07 and US08 are done by Xudong Yu

	// US07: Death should be less than 150 years after birth for dead people,
	// and current date should be less than 150 years after birth for all living
	// people
	public static boolean us07_less_than_150_years_old(Date birth, Date death,
			String id) {
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
			Date marriage, Date divorce, String id) {
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

	// US12: Mother should be less than 60 years older than her children and
	// father should be less than 80 years older than his children
	public static boolean us12_parents_not_too_old(String family_id,
			Date husband_birth, Date wife_birth, String child_id,
			List<Indivdual> indivdualList) {
		boolean result = true;
		for (int j = 0; j < indivdualList.size(); j++) {
			if (child_id.equals(indivdualList.get(j).getID())) {
				Date child_birth = indivdualList.get(j).getBirthday();
				boolean resFather = us12_helper_check_age_difference(
						husband_birth, child_birth);
				boolean resMother = us12_helper_check_age_difference(
						wife_birth, child_birth);
				if (resFather && resMother) {
					result = true;
				}
				if (resFather == false) {
					result = false;
					addError(
							errors,
							"US12",
							"ERROR: FAMILY: US12: "
									+ family_id
									+ ": Father is more than 60 years older than child ("
									+ child_id + ").");
				}
				if (resMother == false) {
					result = false;
					addError(
							errors,
							"US12",
							"ERROR: FAMILY: US12: "
									+ family_id
									+ ": Mother is more than 60 years older than child ("
									+ child_id + ").");
				}
				break;
			}
		}
		return result;
	}

	public static boolean us12_helper_check_age_difference(Date parentBirthday,
			Date childBirthday) {
		int parentYear = Integer.parseInt(new SimpleDateFormat("yyyy-MM-dd")
				.format(parentBirthday).substring(0, 4));
		int parentMonth = Integer.parseInt(new SimpleDateFormat("yyyy-MM-dd")
				.format(parentBirthday).substring(5, 7));
		int parentDay = Integer.parseInt(new SimpleDateFormat("yyyy-MM-dd")
				.format(parentBirthday).substring(8, 10));

		int childYear = Integer.parseInt(new SimpleDateFormat("yyyy-MM-dd")
				.format(childBirthday).substring(0, 4));
		int childMonth = Integer.parseInt(new SimpleDateFormat("yyyy-MM-dd")
				.format(childBirthday).substring(5, 7));
		int childDay = Integer.parseInt(new SimpleDateFormat("yyyy-MM-dd")
				.format(childBirthday).substring(8, 10));

		int yearsDiff = childYear - parentYear;
		int monthsDiff = childMonth - parentMonth;
		int daysInDiff = childDay - parentDay;

		boolean result;
		if (yearsDiff < 60)
			result = true;
		else if (yearsDiff == 60) {
			if (monthsDiff < 0) {
				result = true;
			} else if (monthsDiff == 0) {
				if (daysInDiff <= 0) {
					result = true;
				} else
					result = false;
			} else
				result = false;
		} else
			result = false;
		return result;
	}

	// US21: Husband in family should be male and wife in family should be
	// female
	public static boolean us21_correct_gender_for_role(String family_id,
			String husband_gender, String wife_gender) {
		boolean result = true;
		if (!husband_gender.equals("M")) {
			addError(errors, "US21", "ERROR: FAMILY: US21: " + family_id
					+ ": Wrong gender for husband.");
			result = false;
		}
		if (!wife_gender.equals("F")) {
			addError(errors, "US21", "ERROR: FAMILY: US21: " + family_id
					+ ": Wrong gender for wife.");
			result = false;
		}
		return result;
	}

	// US35 and US36 are done by Disha

	// US35: List all people in a GEDCOM file who were born in the last 30 days
	public static void us35_list_recent_births(HashMap map) {
		// System.out
		// .println("US35: PRINT: INDIVIDUAL: US35: List all people who were born in the last 30 days: \n");
		Set mapSet = (Set) map.entrySet();
		Iterator mapIterator = mapSet.iterator();
		while (mapIterator.hasNext()) {
			Map.Entry mapEntry = (Map.Entry) mapIterator.next();
			// getKey Method of HashMap access a key of map
			String keyValue = (String) mapEntry.getKey();
			// getValue method returns corresponding key's value
			Date value = (Date) mapEntry.getValue();
			String d = new SimpleDateFormat("yyyy-MM-dd").format(value);
			/*
			 * String error = "ERROR: FAMILY: US16: " + fam_id +
			 * ": Does not have the same male last name."; addError(errors,
			 * "US16", error);
			 */
			String print = "PRINT: INDIVIDUAL: US35: " + keyValue
					+ ": Birthday " + d + " is in the last 30 days";
			addError(errors, "US35", print);
		}
	}

	public static boolean check_for_recent_births(String ID, Date Birthday) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -30);
		SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");

		// get current date in string
		String old_date_String_format = format.format(cal.getTime());
		// System.out.println("date is : " + old_date_String_format);

		// convert String- date to Date format
		try {
			Date old_date_Date_format = format.parse(old_date_String_format);
			// Date here=format.format(old_date_Date_format);
			// System.out.println("date is  :" + old_date_Date_format);
			if (Birthday.before(old_date_Date_format)) {
				return false;
			} else {
				recent_births.put(ID, Birthday);
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return true;

	}

	// US36: List all people in a GEDCOM file who died in the last 30 days

	public static void us36_list_recent_deaths(HashMap map) {
		// System.out
		// .println("\nUS36: PRINT: INDIVIDUAL: US36: List all people who died in the last 30 days: ");
		Set mapSet = (Set) map.entrySet();
		Iterator mapIterator = mapSet.iterator();
		while (mapIterator.hasNext()) {
			Map.Entry mapEntry = (Map.Entry) mapIterator.next();
			// getKey Method of HashMap access a key of map
			String keyValue = (String) mapEntry.getKey();
			// getValue method returns corresponding key's value
			Date value = (Date) mapEntry.getValue();
			String d = new SimpleDateFormat("yyyy-MM-dd").format(value);
			String print = "PRINT: INDIVIDUAL: US36: " + keyValue
					+ ": Died on " + d + " in the last 30 days";
			addError(errors, "US36", print);
		}
	}

	public static boolean check_for_recent_deaths(String ID, Date DeathDay) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -30);
			SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
			// get current date in string
			String old_date_String_format = format.format(cal.getTime());
			// System.out.println("date is : " + old_date_String_format);
			Date old_date_Date_format = format.parse(old_date_String_format);
			// Date here=format.format(old_date_Date_format);
			// System.out.println("date is  :" + old_date_Date_format);
			if (DeathDay.before(old_date_Date_format)) {
				return false;
			} else {
				recent_deaths.put(ID, DeathDay);
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return true;
	}

}
