package sprints;

import important.Family;
import important.Indivdual;
import important.Family;
import important.Indivdual;
import jnr.ffi.annotations.In;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Sprint3_Checkout {
	public static HashMap<String, Date> recent_births = new HashMap<String, Date>();
	public static HashMap<String, Date> recent_deaths = new HashMap<String, Date>();
	public static HashMap<String, Date> upcoming_birthdays=new HashMap<>();
	public static HashMap<String, Date> upcoming_marriage_anniversaries=new HashMap<>();
	public static HashMap<String, Date> deceased = new HashMap<String, Date>();
	public static HashMap<String, Date> living_married = new HashMap<String, Date>();
	public static HashMap<String, List<String>> errors = new HashMap<String, List<String>>();

	public static HashMap<String, List<String>> check_List(
			List<Indivdual> indivdualList, List<Family> familyList) {
		for (Indivdual in : indivdualList) {
			String id = in.getID();
			Date death_day = in.getDeath();
			Date birthday = in.getBirthday();
			us29_isDeceased(id, death_day);
			us38_check_for_upcoming_birthdays(id,birthday);
		}

		us29_list_deceased(deceased);
		us38_list_upcoming_birthdays(upcoming_birthdays);

		for (Family fam : familyList) {
			String id = fam.getID();
			Date marry_date = fam.getMarried();

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

			String childName;
			LocalDate childBirth;
			int childAge;

			us39_check_for_upcoming_marriage_anniversaries(id,marry_date);

			for (int i = 0; i < indivdualList.size(); ++i) {
				if (fam.getHusbandID().equals(indivdualList.get(i).getID())) {
					husband_id = indivdualList.get(i).getID();
					husband_name = indivdualList.get(i).getName();
					husband_death = indivdualList.get(i).getDeath();
					husband_birth = indivdualList.get(i).getBirthday();
					husband_gender = indivdualList.get(i).getGender();
					us10_marriage_after_14(fam.getMarried(), husband_birth,
							husband_id);
					us30_check_living_married(husband_id, husband_death,
							fam.getMarried());
					if (husband_death != null) {
						for (String child_id : fam.getChildren()) {
							for (Indivdual in2 : indivdualList) {
								if (child_id.equals(in2.getID())) {
									us09_birth_before_death_of_parents(
											husband_death, in2.getBirthday(),
											in2.getID());
								}
							}
						}
					}
				}
				if (fam.getWifeID().equals(indivdualList.get(i).getID())) {
					wife_id = indivdualList.get(i).getID();
					wife_name = indivdualList.get(i).getName();
					wife_death = indivdualList.get(i).getDeath();
					wife_birth = indivdualList.get(i).getBirthday();
					wife_gender = indivdualList.get(i).getGender();
					us10_marriage_after_14(fam.getMarried(), wife_birth,
							wife_id);
					us30_check_living_married(wife_id, wife_death,
							fam.getMarried());
					if (wife_death != null) {
						for (String child_id : fam.getChildren()) {
							for (Indivdual in2 : indivdualList) {
								if (child_id.equals(in2.getID())) {
									us09_birth_before_death_of_parents(
											wife_death, in2.getBirthday(),
											in2.getID());
								}
							}
						}
					}
				}
				for (String child_id : fam.getChildren()) {

				}

			}
		}
		us30_list_living_married(living_married);
		us39_list_for_upcoming_marriage_anniversaries(upcoming_marriage_anniversaries);

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

	// US09 and US10 are written by Xudong Yu
	public static boolean us10_marriage_after_14(Date marriage, Date birth,
			String ID) {
		int birth_year = Integer.parseInt(new SimpleDateFormat("yyyy-MM-dd")
				.format(birth).substring(0, 4));
		if (marriage != null) {
			int death_year = Integer
					.parseInt(new SimpleDateFormat("yyyy-MM-dd").format(
							marriage).substring(0, 4));
			if ((death_year - birth_year) <= 14) {
				String error = "ERROR: INDIVIDUAL: US10: " + ID
						+ ": marriage before 14.";
				addError(errors, "US10", error);
				return false;
			}
		}
		return true;
	}

	public static boolean us09_birth_before_death_of_parents(Date parent_death,
			Date child_birth, String ID) {
		if (child_birth.after(parent_death)) {
			String error = "ERROR: INDIVIDUAL: US09: " + ID
					+ ": Birth after death of parents.";
			addError(errors, "US10", error);
			return false;
		}
		return true;
	}

	// US29 and US30 are written by Yutong Zhao

	// US29: List all deceased individuals in a GEDCOM file
	public static void us29_list_deceased(HashMap map) {
		Set mapSet = (Set) map.entrySet();
		Iterator mapIterator = mapSet.iterator();
		while (mapIterator.hasNext()) {
			Map.Entry mapEntry = (Map.Entry) mapIterator.next();
			// getKey Method of HashMap access a key of map
			String keyValue = (String) mapEntry.getKey();
			String error = "PRINT: INDIVIDUAL: US29: " + keyValue
					+ ": Is deceased.";
			addError(errors, "US29", error);
		}
	}

	public static boolean us29_isDeceased(String id, Date death_day) {
		if (death_day != null) {
			deceased.put(id, death_day);
			return true;
		} else {
			return false;
		}
	}

	// US30: List all living married people in a GEDCOM file
	public static void us30_list_living_married(HashMap map) {
		Set mapSet = (Set) map.entrySet();
		Iterator mapIterator = mapSet.iterator();
		while (mapIterator.hasNext()) {
			Map.Entry mapEntry = (Map.Entry) mapIterator.next();
			// getKey Method of HashMap access a key of map
			String keyValue = (String) mapEntry.getKey();
			Date value = (Date) mapEntry.getValue();
			String date = new SimpleDateFormat("yyyy-MM-dd").format(value);
			String error = "PRINT: INDIVIDUAL: US30: " + keyValue
					+ ": Is alive and married on " + date + ".";
			addError(errors, "US30", error);
		}
	}

	public static boolean us30_check_living_married(String id, Date death_day,
			Date marriage_day) {
		if (death_day == null && marriage_day != null) {
			living_married.put(id, marriage_day);
			return true;
		}
		return false;
	}

	//US33 & US34 by Chenglin Wu
	public static boolean us33_list_orphans(int childAge, Date fatherDeath, Date motherDeath, String childName, String childID, String famID) {
		if (childAge > 0 && childAge < 18 && fatherDeath != null && motherDeath != null) {
			addError(errors, "US33", "ORPHANS: INDIVIDUAL: US33: " + childName + "(" + childID + ")" + " is orphan.");
			return true;
		} else return false;
	}

	public static int us33_helper_calculateAge(LocalDate birthDate) {
		if ((birthDate != null)) {
			return Period.between(birthDate, LocalDate.now()).getYears();
		} else {
			return 0;
		}
	}

	public static boolean us34_list_large_age_differences(Date husband_birth, Date wife_birth, Date merriage, String famID) {
		LocalDate husbandBirth = husband_birth.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
		LocalDate wifeBirth = wife_birth.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
		LocalDate merriageDate = merriage.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
		if (us34_helper_calculateAge(husbandBirth, merriageDate) > us34_helper_calculateAge(wifeBirth, merriageDate)) {
			if (us34_helper_calculateAge(husbandBirth, merriageDate) - us34_helper_calculateAge(wifeBirth, merriageDate)
					>= us34_helper_calculateAge(wifeBirth, merriageDate)) {
				addError(errors, "US34", "Large Age Difference: Family: " + famID + " Husband is more than twice as old as wife.");
				return true;
			} else return false;
		} else if (us34_helper_calculateAge(husbandBirth, merriageDate) < us34_helper_calculateAge(wifeBirth, merriageDate)) {
			if (us34_helper_calculateAge(wifeBirth, merriageDate) - us34_helper_calculateAge(husbandBirth, merriageDate)
					>= us34_helper_calculateAge(husbandBirth, merriageDate)) {
				addError(errors, "US34", "Large Age Difference: Family: " + famID + " Wife is more than twice as old as husband.");
				return true;
			} else return false;
		} else return false;
	}

	public static int us34_helper_calculateAge(LocalDate birthDate, LocalDate merriage) {
		if ((birthDate != null)) {
			return Period.between(birthDate, merriage).getYears();
		} else {
			return 0;
		}
	}
	//us38 and us39 are  done  by Disha Sareen

	// US38:List all living people in a GEDCOM file whose birthdays occur in the next 30 days
	public static boolean us38_check_for_upcoming_birthdays(String id, Date birthday){


		if(birthday!=null) {
			String birth_date = new SimpleDateFormat("yyyy-MM-dd").format(birthday);
			String month = birth_date.substring(5, 7);
			String day = birth_date.substring(8, 10);
			//System.out.println(month + " " + day);
			Date current = new Date();
			String current_year = new SimpleDateFormat("yyyy-MM-dd").format(current).substring(0,4);
			String temp = current_year+"-"+month+"-"+day;

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date to_compare = null;
			try {
				to_compare = df.parse(temp);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			long difference = to_compare.getTime() - current.getTime();
			//System.out.println(temp+" diff: "+difference);
			if(difference <= (Long.parseLong("2592000000")) && difference>=0){
				upcoming_birthdays.put(id,birthday);

				return true;
			}
		}


		return false;
	}


	public static void us38_list_upcoming_birthdays(HashMap map) {
		Set mapSet = (Set) map.entrySet();
		Iterator mapIterator = mapSet.iterator();
		while (mapIterator.hasNext()) {
			Map.Entry mapEntry = (Map.Entry) mapIterator.next();
			// getKey Method of HashMap access a key of map
			String keyValue = (String) mapEntry.getKey();
			// getValue method returns corresponding key's value
			Date value = (Date) mapEntry.getValue();
			String d = new SimpleDateFormat("yyyy-MM-dd").format(value);
			String error = "PRINT: INDIVIDUAL: US38: " + keyValue
					+ ": Birthday " + d + " is upcoming in recent 30 days.";
			addError(errors, "US38", error);
		}
	}


	// US39:List all living couples in a GEDCOM file whose marriage anniversaries occur in the next 30 days
	public static boolean us39_check_for_upcoming_marriage_anniversaries(String id, Date marriage){


		if(marriage!=null) {
			String marry_date = new SimpleDateFormat("yyyy-MM-dd").format(marriage);
			String month = marry_date.substring(5, 7);
			String day = marry_date.substring(8, 10);
			//System.out.println(month + " " + day);
			Date current = new Date();
			String current_year = new SimpleDateFormat("yyyy-MM-dd").format(current).substring(0,4);
			String temp = current_year+"-"+month+"-"+day;

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date to_compare = null;
			try {
				to_compare = df.parse(temp);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			long difference = to_compare.getTime() - current.getTime();
			//System.out.println(temp+" diff: "+difference);
			if(difference <= (Long.parseLong("2592000000")) && difference>=0){
				upcoming_marriage_anniversaries.put(id,marriage);
				return true;
			}
		}


		return false;
	}


	public static void us39_list_for_upcoming_marriage_anniversaries(HashMap map) {
		Set mapSet = (Set) map.entrySet();
		Iterator mapIterator = mapSet.iterator();
		while (mapIterator.hasNext()) {
			Map.Entry mapEntry = (Map.Entry) mapIterator.next();
			// getKey Method of HashMap access a key of map
			String keyValue = (String) mapEntry.getKey();
			// getValue method returns corresponding key's value
			Date value = (Date) mapEntry.getValue();
			String d = new SimpleDateFormat("yyyy-MM-dd").format(value);
			String error = "PRINT: INDIVIDUAL: US39: " + keyValue
					+ ": Marriage anniversary " + d + " is upcoming in recent 30 days.";
			addError(errors, "US39", error);
		}
	}
}
