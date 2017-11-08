package sprints;

import important.Family;
import important.Indivdual;
import jnr.ffi.annotations.In;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;

public class Sprint3_Checkout {
    public static HashMap<String, Date> recent_births = new HashMap<String, Date>();
    public static HashMap<String, Date> recent_deaths = new HashMap<String, Date>();
    public static HashMap<String, List<String>> errors = new HashMap<String, List<String>>();

    public static HashMap<String, List<String>> check_List(List<Indivdual> indivdualList, List<Family> familyList) {

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

            String childName;
            LocalDate childBirth;
            int childAge;

            for (int i = 0; i < indivdualList.size(); ++i) {
                if (fam.getHusbandID().equals(indivdualList.get(i).getID())) {
                    husband_id = indivdualList.get(i).getID();
                    husband_name = indivdualList.get(i).getName();
                    husband_death = indivdualList.get(i).getDeath();
                    husband_birth = indivdualList.get(i).getBirthday();
                    husband_gender = indivdualList.get(i).getGender();
                    us10_marriage_after_14(fam.getMarried(),husband_birth,husband_id);
                    if(husband_death != null) {
                        for (String child_id : fam.getChildren()) {
                            for (Indivdual in2 : indivdualList) {
                                if (child_id.equals(in2.getID())) {
                                    us09_birth_before_death_of_parents(husband_death, in2.getBirthday(), in2.getID());
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
                    us10_marriage_after_14(fam.getMarried(),wife_birth,wife_id);
                    if(wife_death != null) {
                        for (String child_id : fam.getChildren()) {
                            for (Indivdual in2 : indivdualList) {
                                if (child_id.equals(in2.getID())) {
                                    us09_birth_before_death_of_parents(wife_death, in2.getBirthday(), in2.getID());
                                }
                            }
                        }
                    }
                }

                if (husband_death != null && wife_death != null) {
                    for (String child_id : fam.getChildren()) {
                        if (child_id.equals(indivdualList.get(i).getID())) {
                            childName = indivdualList.get(i).getName();
                            child_birth = indivdualList.get(i).getBirthday();
                            childBirth = child_birth.toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate();
                            childAge = us33_helper_calculateAge(childBirth);
                            us33_list_orphans(childAge, husband_death, wife_death, childName, child_id, fam.getID());
                        }
                    }
                }
            }
            us34_list_large_age_differences(husband_birth, wife_birth, fam.getMarried(), fam.getID());
        }
        return errors;
    }


    public static boolean us10_marriage_after_14(Date marriage, Date birth, String ID) {
        int birth_year = Integer
                .parseInt(new SimpleDateFormat("yyyy-MM-dd").format(birth)
                        .substring(0, 4));
        if (marriage != null) {
            int death_year = Integer.parseInt(new SimpleDateFormat(
                    "yyyy-MM-dd").format(marriage).substring(0, 4));
            if ((death_year - birth_year) <= 14) {
                String error = "ERROR: INDIVIDUAL: US10: " + ID
                        + ": marriage before 14.";
                addError(errors, "US10", error);
                return false;
            }
        }
        return true;
    }

    public static boolean us09_birth_before_death_of_parents(Date parent_death, Date child_birth, String ID) {
        if(child_birth.after(parent_death)) {
            String error = "ERROR: INDIVIDUAL: US09: " + ID
                    + ": Birth after death of parents.";
            addError(errors, "US10", error);
            return false;
        }
        return true;
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
}
