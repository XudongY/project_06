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

public class Sprint4_Checkout {
    public static HashMap<String, List<String>> errors = new HashMap<String, List<String>>();
    public static HashSet<String> uniqueID = new HashSet<>();
    public static HashSet<String> uniqueNameAndBirth = new HashSet<>();

    public static HashMap<String, List<String>> check_List(
            List<Indivdual> indivdualList, List<Family> familyList) {
        us22_Unique_IDs(indivdualList);
        us23_Unique_name_and_birth_date(indivdualList);
        return errors;
    }

    public static boolean us22_Unique_IDs(List<Indivdual> indivdualList) {
        for (Indivdual in : indivdualList) {
            if (uniqueID.contains(in.getID())) {
                String error = "ERROR: INDIVIDUAL: US22: " + in.getID()
                        + ": the same IDs as individual before";
                addError(errors, "US22", error);
                return false;
            }
            uniqueID.add(in.getID());
        }
        return true;
    }


    public static boolean us23_Unique_name_and_birth_date(List<Indivdual> indivdualList) {
        for (Indivdual in : indivdualList) {
            String whole = in.getName() + in.getBirthday().toString();
            if (uniqueNameAndBirth.contains(whole)) {
                String error = "ERROR: INDIVIDUAL: US23: " + in.getID()
                        + ": the same name and birthday as individual before";
                addError(errors, "US23", error);
                return false;
            }
            uniqueNameAndBirth.add(whole);
        }
        return true;
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
