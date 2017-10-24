package sprints;
import important.Family;
import important.Indivdual;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.text.ParseException;
import java.util.*;

public class Sprint2_Checkout {
	static HashMap<String, Date> recent_births = new HashMap<String, Date>();
    static HashMap<String, Date> recent_deaths = new HashMap<String, Date>();
	public static HashMap<String, List<String>> errors = new HashMap<String, List<String>>();

	public static HashMap<String, List<String>> check_List(
			List<Indivdual> indivdualList, List<Family> familyList) {
		for (Indivdual in : indivdualList) {
			//geting individual details
			String ID = in.getID();
			Date DeathDay=in.getDeath();
			Date Birthday = in.getBirthday();
			check_for_recent_births(ID,Birthday);
			check_for_recent_deaths(ID,Birthday);
			//get 30 days back date

		}
		us35_list_recent_births(recent_births);
        us36_list_recent_deaths(recent_deaths);

			for (Family fam : familyList) {
				String husband_id = null;
				String husband_name = null;
				Date husband_death = null;
				Date husband_birth = null;

				String wife_id = null;
				String wife_name = null;
				Date wife_death = null;
				Date wife_birth = null;

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

	// US35 and US36 are done by Disha
	public static boolean check_for_recent_births(String ID, Date Birthday){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -30);
		SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");

		//get current date in string
		String old_date_String_format = format.format(cal.getTime());
		//convert  String- date to Date format
		try {
			Date old_date_Date_format = format.parse(old_date_String_format);
			//Date here=format.format(old_date_Date_format);
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

	public static boolean check_for_recent_deaths(String ID, Date DeathDay){
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -30);
			SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
			//get current date in string
			String old_date_String_format = format.format(cal.getTime());
			System.out.println("date is : " + old_date_String_format);
			Date old_date_Date_format = format.parse(old_date_String_format);
			//Date here=format.format(old_date_Date_format);
			System.out.println("date is  :" + old_date_Date_format);
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
    public static void us35_list_recent_births(HashMap map)
    {
        System.out.println("The following are recent births: \n");
        Set mapSet=(Set)map.entrySet();
        Iterator mapIterator=mapSet.iterator();
        while (mapIterator.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) mapIterator.next();
            // getKey Method of HashMap access a key of map
            String keyValue = (String) mapEntry.getKey();
            //getValue method returns corresponding key's value
            int value = (int) mapEntry.getValue();
            System.out.println("ID : " + keyValue + " has BIRTHDAY:  " + value);
        }
    }
    public static void us36_list_recent_deaths(HashMap map)
    {
        System.out.println("The following are recent deaths: \n");
        Set mapSet=(Set)map.entrySet();
        Iterator mapIterator=mapSet.iterator();
        while (mapIterator.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) mapIterator.next();
            // getKey Method of HashMap access a key of map
            String keyValue = (String) mapEntry.getKey();
            //getValue method returns corresponding key's value
            int value = (int) mapEntry.getValue();
            System.out.println("ID : " + keyValue + " has DeathDay:  " + value);
        }
    }
	// US07 and US08 are done by Xudong

	// US12 and US21 are done by Chenglin


	}
