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
        us24_unique_family_by_spouses(familyList);
        us27_include_individual_ages(indivdualList);

        for (Indivdual indi : indivdualList) {

        }

        for (Family fam : familyList) {
             String husband=fam.getHusbandID();
            String wife=fam.getWifeID();
            us18_married_siblings(husband, wife,familyList);
            
            if (fam.getChildren() != null && fam.getChildren().size() > 1) {
                List<Indivdual> children = new ArrayList<>();
                for (String child_id : fam.getChildren()) {
                    for (Indivdual indi : indivdualList) {
                        if (child_id.equals(indi.getID())) {
                            children.add(indi);
                        }
                    }
                }
                us13_siblings_spacing(children);
            }
            us25_Unique_first_names_in_families(fam.getChildren(), indivdualList, fam.getID());
        }

        return errors;
    }
    // US 18 and 19 are doen by Disha
    public static boolean us18_married_siblings(String husband_ID, String wife_ID,List<Family> fam) {
        boolean husbandInThisFamily=false;
        boolean wifeInThisFamily=false;
        String husband_dad_ID="0";
        String husband_mom_ID="0";
        String wife_dad_ID="0";
        String wife_mom_ID="0";
        for(Family f: fam){
            for(String child:f.getChildren()){
                if(husband_ID.equals(child)){
                   husbandInThisFamily=true;
                   husband_dad_ID=f.getHusbandID();
                   husband_mom_ID=f.getWifeID();
                }
                if(wife_ID.equals(child)){
                    wifeInThisFamily=true;
                    wife_dad_ID=f.getHusbandID();
                    wife_mom_ID=f.getWifeID();
                }
            }

        }
        if((husband_dad_ID.equals(wife_dad_ID))||(husband_mom_ID.equals(wife_mom_ID))){

            String error = "ERROR: FAMILY  : US18: "+ husband_ID+" and "+wife_ID+" are married siblings.";
            addError(errors, "US18", error);
            return false;
        }
        return true;
    }
    public static boolean us19_married_cousins(String husband_ID, String wife_ID,List<Family> fam) {
        boolean husbandInThisFamily=false;
        boolean wifeInThisFamily=false;
        boolean Husband_dada_InThisFamily=false;
        boolean Husband_dadi_InThisFamily=false;
        boolean Husband_nana_InThisFamily=false;
        boolean Husband_nani_InThisFamily=false;
        boolean wife_dada_InThisFamily=false;
        boolean wife_dadi_InThisFamily=false;
        boolean wife_nana_InThisFamily=false;
        boolean wife_nani_InThisFamily=false;
        String husband_dad_ID="0";
        String husband_mom_ID="0";
        String wife_dad_ID="0";
        String wife_mom_ID="0";
        String husband_dada="0";
        String husband_dadi="0";
        String husband_nana="0";
        String husband_nani="0";
        String wife_dada="0";
        String wife_dadi="0";
        String wife_nana="0";
        String wife_nani="0";


        for(Family f: fam){
            for(String child:f.getChildren()){
                if(husband_ID.equals(child)){
                    husbandInThisFamily=true;
                    husband_dad_ID=f.getHusbandID();
                    husband_mom_ID=f.getWifeID();
                }
                if(wife_ID.equals(child)){
                    wifeInThisFamily=true;
                    wife_dad_ID=f.getHusbandID();
                    wife_mom_ID=f.getWifeID();
                }
            }

        }

        //for grandparents
        for(Family f: fam){
            for(String child:f.getChildren()){
                if(husband_dad_ID.equals(child)){
                    Husband_dada_InThisFamily=true;
                   husband_dada=f.getHusbandID();
                    husband_dadi=f.getWifeID();
                }
                if(husband_mom_ID.equals(child)){
                    Husband_nani_InThisFamily=true;
                    husband_nana=f.getHusbandID();
                    husband_nani=f.getWifeID();
                }
                if(wife_dad_ID.equals(child)){
                   wife_dada_InThisFamily=true;
                    wife_dada=f.getHusbandID();
                    wife_dadi=f.getWifeID();
                }
                if(wife_mom_ID.equals(child)){
                    Husband_nani_InThisFamily=true;
                    wife_nana=f.getHusbandID();
                    wife_nani=f.getWifeID();
                }

            }

        }
        if(( husband_dada.equals(wife_dada))||(husband_dada.equals(wife_nana))|| (husband_nana.equals(wife_dada))||(husband_nana.equals(wife_nana))||
                (husband_dadi.equals(wife_dadi))||(husband_dadi.equals(wife_nani))||(husband_nani.equals(wife_dadi))||(husband_nani.equals(wife_nani))){

            String error = "ERROR: FAMILY  : US19: "+ husband_ID+" and "+wife_ID+" are married cousins.";
            addError(errors, "US19", error);
            return false;
        }
        return true;
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
    public static void us13_siblings_spacing(List<Indivdual> children) {
        for (int i = 0; i < children.size() - 1; i++) {
            System.out.println(children.get(i).getID() + "\t");
            for (int j = 0; j < i; j++) {
                if (!us13_compare_birthday(children.get(i).getBirthday(), children.get(j).getBirthday())) {
                    addError(errors, "US13", "ERROR: FAMILY: US13: " + children.get(i).getID() +
                            ": has spacing conflicts with " + children.get(j).getID());
                }
            }
            for (int j = i + 1; j < children.size(); j++) {
                if (!us13_compare_birthday(children.get(i).getBirthday(), children.get(j).getBirthday())) {
                    addError(errors, "US13", "ERROR: FAMILY: US13: " + children.get(i).getID() +
                            ": has spacing conflicts with " + children.get(j).getID());
                }
            }
        }
    }

    public static boolean us13_compare_birthday(Date b1, Date b2) {
        long birth1 = b1.getTime();
        long birth2 = b2.getTime();
        long day = Long.parseLong("86400000");
        if (Math.abs(birth1 - birth2) > 2 * day && Math.abs(birth1 - birth2) < 8 * 30 * day) {
            return false;
        }
        return true;
    }

    // US27:Include person's current age when listing individuals
    public static void us27_include_individual_ages(List<Indivdual> indivdualList) {
        for (Indivdual indi : indivdualList) {
            if (indi.getDeath() == null) {
                String age = age_calculator(indi.getBirthday(), new Date());
                addError(errors, "US27", "PRINT: INDIVIDUAL: " + indi.getID() + ": " + indi.getName() + " is " + age + " years old now.");
            } else {
                String age = age_calculator(indi.getBirthday(), indi.getDeath());
                addError(errors, "US27", "PRINT: INDIVIDUAL: " + indi.getID() + ": " + indi.getName() + " paased at the age of " + age + ".");
            }
        }
    }

    public static String age_calculator(Date birth, Date current) {
        long diff = current.getTime() - birth.getTime();
        long days = 24 * 60 * 60 * 1000;
        int age = (int) Math.floor(diff / (days * 365));

        return String.valueOf(age);
    }

    //US24 & US25
    //US24: No more than one family with the same spouses by name and the same marriage date should appear in a GEDCOM file
    public static boolean us24_unique_family_by_spouses(List<Family> families) {
        HashMap<String, Date> husbandHash = new HashMap<>();
        HashMap<String, Date> wifeHash = new HashMap<>();
        boolean result = true;
        for (Family f : families) {
            if (husbandHash.containsKey(f.getHusbandName()) && husbandHash.get(f.getHusbandName()).equals(f.getMarried())) {
                addError(errors, "US24", "ERROR: FAMILY: US24: " + f.getID() + ": " + f.getHusbandName() + " has more than one marriage on the same day");
                result = false;
            } else husbandHash.put(f.getHusbandName(), f.getMarried());
            if (wifeHash.containsKey(f.getWifeName()) && wifeHash.get(f.getWifeName()).equals(f.getMarried())) {
                addError(errors, "US24", "ERROR: FAMILY: US24: " + f.getID() + ": " + f.getWifeName() + " has more than one marriage on the same day");
                result = false;
            } else wifeHash.put(f.getWifeName(), f.getMarried());
        }
        return result;
    }

    //US25: No more than one child with the same name and birth date should appear in a family
    public static boolean us25_Unique_first_names_in_families(List<String> children, List<Indivdual> indivduals, String famID) {
        HashMap<String, Date> hashMap = new HashMap<>();
        boolean result = true;
        for (String child : children) {
            for (Indivdual i : indivduals) {
                if (child.equals(i.getID())) {
                    String firstName = child.split("/")[0];
                    if (hashMap.containsKey(firstName) && hashMap.get(firstName).equals(i.getBirthday())) {
                        addError(errors, "US25", "ERROR: FAMILY: US25: " + famID + ": " + i.getName() + " has same first name and Birthday with another child in the family");
                        result = false;
                    } else hashMap.put(firstName, i.getBirthday());
                }
            }
        }
        return result;
    }
}
