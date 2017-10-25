package sprints;

import important.Family;
import important.Indivdual;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Sprint1_Checkout {

	public static HashMap<String, List<String>> errors = new HashMap<String, List<String>>();

	public static HashMap<String, List<String>> check_List(
			List<Indivdual> indivdualList, List<Family> familyList) {
		for (Indivdual in : indivdualList) {
			us03_birth_before_death(in.getBirthday(), in.getDeath(),
					in.getName());
			us01_dates_before_current_date(in.getBirthday(), in.getID(),
					"INDIVIDUAL");
			us01_dates_before_current_date(in.getDeath(), in.getID(),
					"INDIVIDUAL");
			us42_reject_illegitimate_dates(in.getBirthday(), in.getID(),
					"INDIVIDUAL");
			us42_reject_illegitimate_dates(in.getDeath(), in.getID(),
					"INDIVIDUAL");
			// us11_no_bigamy(in.getID(), familyList);
		}
		for (Family fam : familyList) {
			String husband_id = null;
			Date husband_death = null;
			Date husband_birth = null;

			String wife_id = null;
			Date wife_death = null;
			Date wife_birth = null;

			for (int i = 0; i < indivdualList.size(); ++i) {
				if (fam.getHusbandID().equals(indivdualList.get(i).getID())) {
					husband_id = indivdualList.get(i).getID();
					husband_death = indivdualList.get(i).getDeath();
					husband_birth = indivdualList.get(i).getBirthday();
				}
				if (fam.getWifeID().equals(indivdualList.get(i).getID())) {
					wife_id = indivdualList.get(i).getID();
					wife_death = indivdualList.get(i).getDeath();
					wife_birth = indivdualList.get(i).getBirthday();
				}
			}
			us04_marrige_before_divorce(fam.getMarried(), fam.getDivorced(),
					fam.getID());
			us05_marriage_before_death(husband_death, wife_death,
					fam.getMarried(), fam.getID(), husband_id, wife_id);
			us06_divorce_before_death(husband_death, wife_death,
					fam.getDivorced(), fam.getID(), husband_id, wife_id);
			us01_dates_before_current_date(fam.getMarried(), fam.getID(),
					"FAMILY");
			us01_dates_before_current_date(fam.getDivorced(), fam.getID(),
					"FAMILY");
			us42_reject_illegitimate_dates(fam.getMarried(), fam.getID(),
					"FAMILY");
			us42_reject_illegitimate_dates(fam.getDivorced(), fam.getID(),
					"FAMILY");
			us02_birth_before_marriage(husband_birth, wife_birth,
					fam.getMarried(), fam.getID(), husband_id, wife_id);
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

	// US03 and US05 are done by Xudong Yu

	// US03: Birth should occur before death of an individual
	public static boolean us03_birth_before_death(Date birth, Date death,
			String id) {
		if (birth != null && death != null) {
			if (birth.after(death)) {
				String death_date = new SimpleDateFormat("yyyy-MM-dd")
						.format(death);
				String birth_date = new SimpleDateFormat("yyyy-MM-dd")
						.format(birth);
				String error = "ERROR: INDIVIDUAL: US03: " + id + ": Died "
						+ death_date + " before born " + birth_date;
				addError(errors, "US03", error);
				return false;
			}
			return true;
		}
		return false;
	}

	// US05: Marriage should occur before death of both spouses
	public static boolean us05_marriage_before_death(Date husband_death,
			Date wife_death, Date marriage, String fam_id, String husband_id,
			String wife_id) {
		boolean res_husband = true;
		boolean res_wife = true;
		if (marriage != null) {

			if (husband_death != null && husband_death.before(marriage)) {
				String marriage_date = new SimpleDateFormat("yyyy-MM-dd")
						.format(marriage);
				String death_date = new SimpleDateFormat("yyyy-MM-dd")
						.format(husband_death);
				String error = "ERROR: FAMILY: US05: " + fam_id + ": Married "
						+ marriage_date + " after husband's (" + husband_id
						+ ") death on " + death_date;
				addError(errors, "US05", error);
				res_husband = false;
			}
			if (wife_death != null && wife_death.before(marriage)) {
				String marriage_date = new SimpleDateFormat("yyyy-MM-dd")
						.format(marriage);
				String death_date = new SimpleDateFormat("yyyy-MM-dd")
						.format(wife_death);
				String error = "ERROR: FAMILY: US05: " + fam_id + ": Married "
						+ marriage_date + " after husband's (" + wife_id
						+ ") death on " + death_date;
				addError(errors, "US05", error);
				res_wife = false;
			}
		}
		if (res_husband == false || res_wife == false) {
			return false;
		} else {
			return true;
		}
	}

	// US04 and US06 are done by Chenglin Wu

	// US04: Marriage should occur before divorce of spouses, and divorce can
	// only occur after marriage
	public static boolean us04_marrige_before_divorce(Date marriage,
			Date divorce, String fam_id) {
		if (marriage != null && divorce != null) {
			if (marriage.after(divorce)) {
				String marriage_date = new SimpleDateFormat("yyyy-MM-dd")
						.format(marriage);
				String divorce_date = new SimpleDateFormat("yyyy-MM-dd")
						.format(divorce);
				String error = "ERROR: FAMILY: US04: " + fam_id + ": Divorced "
						+ divorce_date + " before marriage on " + marriage_date;
				addError(errors, "US04", error);
				return false;
			}
		}
		return true;
	}

	// US06: Divorce can only occur before death of both spouses
	public static boolean us06_divorce_before_death(Date husband_death,
			Date wife_death, Date divorce, String fam_id, String husband_id,
			String wife_id) {

		boolean res_husband = true;
		boolean res_wife = true;
		if (divorce != null) {

			if (husband_death != null && husband_death.before(divorce)) {
				String divorce_date = new SimpleDateFormat("yyyy-MM-dd")
						.format(divorce);
				String death_date = new SimpleDateFormat("yyyy-MM-dd")
						.format(husband_death);
				String error = "ERROR: INDIVIDUAL: US06: " + fam_id
						+ ": Divorced " + divorce_date + " after husband's ("
						+ husband_id + ") death on " + death_date;
				addError(errors, "US06", error);
				res_husband = false;
			}
			if (wife_death != null && wife_death.before(divorce)) {
				String divorce_date = new SimpleDateFormat("yyyy-MM-dd")
						.format(divorce);
				String death_date = new SimpleDateFormat("yyyy-MM-dd")
						.format(wife_death);
				String error = "ERROR: FAMILY: US06: " + fam_id + ": Divorced "
						+ divorce_date + " after husband's (" + wife_id
						+ ") death on " + death_date;
				addError(errors, "US06", error);
				res_wife = false;
			}
		}
		if (res_husband == false || res_wife == false) {
			return false;
		} else {
			return true;
		}
	}

	// done by Yutong Zhao
	// US01: Dates (birth, marriage, divorce, death)should not be after the
	// current date
	public static boolean us01_dates_before_current_date(Date date, String id,
			String message) {
		Date current = new Date();
		if (date != null && date.after(current)) {
			String sdf = new SimpleDateFormat("yyyy-MM-dd").format(date);
			String error = "ERROR: " + message + ": US01: " + id + ": Date "
					+ sdf + " occurs in the future.";
			addError(errors, "US01", error);
			return false;
		}
		return true;
	}

	// US02: Birth should occur before marriage of an individual
	public static boolean us02_birth_before_marriage(Date husband_birth,
			Date wife_birth, Date marriage, String fam_id, String husband_id,
			String wife_id) {
		boolean res_husband = true;
		boolean res_wife = true;
		if (marriage != null) {
			if (husband_birth != null && husband_birth.after(marriage)) {
				String marriage_date = new SimpleDateFormat("yyyy-MM-dd")
						.format(marriage);
				String birth_date = new SimpleDateFormat("yyyy-MM-dd")
						.format(husband_birth);
				String error = "ERROR: FAMILY: US02: " + fam_id + ": Married "
						+ marriage_date + " before husband's (" + husband_id
						+ ") birth on " + birth_date;
				addError(errors, "US02", error);
				res_husband = false;
			}
			if (wife_birth != null && wife_birth.after(marriage)) {
				String marriage_date = new SimpleDateFormat("yyyy-MM-dd")
						.format(marriage);
				String birth_date = new SimpleDateFormat("yyyy-MM-dd")
						.format(wife_birth);
				String error = "ERROR: FAMILY: US02: " + fam_id + ": Married "
						+ marriage_date + " before wife's (" + wife_id
						+ ") birth on " + birth_date;
				addError(errors, "US02", error);
				res_wife = false;
			}
		}
		if (res_husband == false || res_wife == false) {
			return false;
		} else {
			return true;
		}
	}

	// US11 and US42 are done by Disha Sareen

	// US11: Marriage should not occur during marriage to another spouse
	public static boolean us11_no_bigamy(String ID, List<Family> familyList1) {
		// int count = 0;
		// Date marrige1 = null;
		// Date marrige2 = null;
		// Date divorce1 = null;
		// Date divorce2 = null;
		//
		// for (int i = 0; i < familyList1.size(); ++i) {
		// if (ID.equals(familyList1.get(i).getWifeID())
		// || ID.equals(familyList1.get(i).getHusbandID())) {
		// marrige1 = familyList1.get(i).getMarried();
		// divorce1 = familyList1.get(i).getDivorced();
		// count++;
		// }
		// if (ID.equals(familyList1.get(i).getWifeID())
		// || ID.equals(familyList1.get(i).getHusbandID())) {
		// if (count == 1) {
		// marrige2 = familyList1.get(i).getMarried();
		// divorce2 = familyList1.get(i).getDivorced();
		// break;
		// }
		// }
		// }
		// if (count == 2) {
		// error.add("ERROR: INDIVIDUAL: US11: " + ID + " is bigamy");
		// return false;
		// }
		return true;
	}

	// US42: All dates should be legitimate dates for the months specified
	// (e.g., 2/30/2015 is not legitimate)
	public static boolean us42_reject_illegitimate_dates(Date date, String id,
			String message) {
		if (date != null) {
			String sdf = new SimpleDateFormat("dd-MM-yyyy").format(date);
			try {

				DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				df.setLenient(false);
				df.parse(sdf);
				return true;
			} catch (ParseException e) {
				String error = "ERROR: " + message + ": US42: " + id
						+ ": Date " + sdf + " is illegitimate.";
				addError(errors, "US42", error);
				return false;
			}
		}
		return true;
	}
}
