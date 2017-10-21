package sprints;

import important.Family;
import important.Indivdual;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.*;

public class Sprint2_Checkout {

	public static HashMap<String, List<String>> errors = new HashMap<String, List<String>>();

	public static HashMap<String, List<String>> check_List(
			List<Indivdual> indivdualList, List<Family> familyList) {
		for (Indivdual in : indivdualList) {

		}
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

			List<String> children = fam.getChildren();

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
			}
			us15_fewer_than_15_siblings(fam.getChildren(), fam.getID());
			us16_male_last_names(husband_name, wife_name, fam.getID());
			us12_parents_not_too_old(husband_birth, wife_birth, children, indivdualList);
			us21_correct_gender_for_role(husband_gender, wife_gender);
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

	// US12 and US21 are done by Chenglin
	public static boolean us12_parents_not_too_old(Date husband_birth, Date wife_birth, List<String> children, List<Indivdual> indivdualList) {
		boolean result = true;
		for (String child : children) {
			for (int j = 0; j < indivdualList.size(); j++) {
				if (child.equals(indivdualList.get(j).getID())) {
					Date child_birth = indivdualList.get(j).getBirthday();
					Calendar child_cal = DateToCalendar(child_birth);
					Calendar husband_cal = DateToCalendar(husband_birth);
					Calendar wife_cal = DateToCalendar(wife_birth);
					int yearsInBetween_father = husband_cal.get(Calendar.YEAR) - child_cal.get(Calendar.YEAR);
					int monthsInBetween_father = husband_cal.get(Calendar.MONTH) - child_cal.get(Calendar.MONTH);
					int daysInBetween_father = husband_cal.get(Calendar.DATE) - child_cal.get(Calendar.DATE);
					int yearsInBetween_mother = wife_cal.get(Calendar.YEAR) - child_cal.get(Calendar.YEAR);
					int monthsInBetween_mother = wife_cal.get(Calendar.MONTH) - child_cal.get(Calendar.MONTH);
					int daysInBetween_mother = wife_cal.get(Calendar.DATE) - child_cal.get(Calendar.DATE);
					if (yearsInBetween_father < 60) {
						continue;
					} else if (yearsInBetween_father == 60) {
						if (monthsInBetween_father > 0) {
							continue;
						} else if (monthsInBetween_father == 0) {
							if (daysInBetween_father >= 0) {
								continue;
							} else {
								result = false;
								addError(errors, "US12", "Father error");
							}
						} else {
							result = false;
							addError(errors, "US12", "Father error");
						}
					} else {
						result = false;
						addError(errors, "US12", "Father error");
					}

					if (yearsInBetween_mother < 60) {
					} else if (yearsInBetween_mother == 60) {
						if (monthsInBetween_mother > 0) {
						} else if (monthsInBetween_mother == 0) {
							if (daysInBetween_mother >= 0) {
							} else {
								result = false;
								addError(errors, "US12", "Mother error");
							}
						} else {
							result = false;
							addError(errors, "US12", "Mother error");
						}
					} else {
						result = false;
						addError(errors, "US12", "Mother error");
					}
				}
			}
		}
		return result;
	}

	public static Calendar DateToCalendar(Date date ) {
		Calendar cal = null;
		try {
			DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			date = formatter.parse(date.toString());
			cal=Calendar.getInstance();
			cal.setTime(date);
		}
		catch (ParseException e)
		{
			System.out.println("Exception :"+e);
		}
		return cal;
	}

	public static boolean us21_correct_gender_for_role (String husband_gender, String wife_gender) {
		boolean result = true;
		if (!husband_gender.equals("M")) addError(errors,"US21", "Wrong gender for husband");
		else result = false;
		if (!wife_gender.equals("F")) addError(errors,"US21", "Wrong gender for wife");
		else result = false;
		return result;
	}
	// US35 and US36 are done by Disha

}
