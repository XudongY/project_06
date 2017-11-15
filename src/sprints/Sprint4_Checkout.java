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

    public static HashMap<String, List<String>> check_List(List<Indivdual> indivdualList, List<Family> familyList) {
        us22_Unique_IDs(indivdualList);
        us23_Unique_name_and_birth_date(indivdualList);

        for(Indivdual indi: indivdualList){

        }

        for(Family fam: familyList){
            if(fam.getChildren()!=null && fam.getChildren().size()>1){
                List<Indivdual> children = new ArrayList<>();
                for(String child_id: fam.getChildren()){
                    for(Indivdual indi: indivdualList){
                        if(child_id.equals(indi.getID())){
                            children.add(indi);
                        }
                    }
                }
                us13_siblings_spacing(children);
            }
        }

        return errors;
    }

    // US22 and US23 are done by Xudong
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

    // US13 and US27 are done by Yutong Zhao
    // US13: Birth dates of siblings should be more than 8 months apart
    // or less than 2 days apart (twins may be born one day apart, e.g. 11:59 PM and 12:02 AM the following calendar day)
    public static void us13_siblings_spacing(List<Indivdual> children){
        for(int i=0; i<children.size(); i++){
            System.out.println(children.get(i).getID()+"\t");
            for(int j=0; j<i; j++){
                if(!us13_compare_birthday(children.get(i).getBirthday(), children.get(j).getBirthday())){
                    addError(errors,"US13","ERROR: FAMILY: US13: "+children.get(i).getID()+
                            ": has spacing conflicts with "+children.get(j).getID());
                }
            }
            for(int j=i+1; j<children.size(); j++){
                if(!us13_compare_birthday(children.get(i).getBirthday(), children.get(j).getBirthday())){
                    addError(errors,"US13","ERROR: FAMILY: US13: "+children.get(i).getID()+
                            ": has spacing conflicts with "+children.get(j).getID());
                }
            }
        }
    }

    public static boolean us13_compare_birthday(Date b1, Date b2){
        long birth1 = b1.getTime();
        long birth2 = b2.getTime();
        long day = Long.parseLong("86400000");
        if(Math.abs(birth1-birth2)>2*day && Math.abs(birth1-birth2)<8*30*day){
            return false;
        }
        return true;
    }

    // US27:Include person's current age when listing individuals

}
