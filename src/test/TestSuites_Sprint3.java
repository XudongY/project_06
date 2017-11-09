package test;

import com.sun.source.tree.AssertTree;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import sprints.Sprint2_Checkout;
import sprints.Sprint3_Checkout;
import sun.jvm.hotspot.utilities.Assert;

public class TestSuites_Sprint3 extends TestCase {
    public TestSuites_Sprint3() {
        super();
    }

    public TestSuites_Sprint3(String name) {
        super(name);
    }

    public void setUp() throws Exception {

    }
    public void Birth_before_death_of_parents () throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
        Date date1 = simpleDateFormat.parse(new String("24 SEP 1995"));
        Date date2 = simpleDateFormat.parse(new String("22 FEB 1994"));
        String ID = "1111";
        Date date3 = simpleDateFormat.parse(new String("24 SEP 1995"));
        Date date4 = simpleDateFormat.parse(new String("22 FEB 2001"));
        String ID2 = "1111";
        boolean res = Sprint3_Checkout.us09_birth_before_death_of_parents(date1,date2,ID);
        boolean res2 = Sprint3_Checkout.us09_birth_before_death_of_parents(date3,date4,ID2);
        System.out.println(res);
        System.out.println(res2);
        org.junit.Assert.assertTrue("US09 is false", res == true);
        org.junit.Assert.assertFalse("US09 is true", res2 == true);
    }

    public void marriage_after_14 () throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
        Date date1 = simpleDateFormat.parse(new String("24 SEP 1995"));
        Date date2 = simpleDateFormat.parse(new String("22 FEB 1994"));
        String ID = "1111";
        Date date3 = simpleDateFormat.parse(new String("24 SEP 1995"));
        Date date4 = simpleDateFormat.parse(new String("22 FEB 1966"));
        String ID2 = "1111";
        boolean res = Sprint3_Checkout.us10_marriage_after_14(date1,date2,ID);
        boolean res2 = Sprint3_Checkout.us10_marriage_after_14(date3,date4,ID2);
        System.out.println(res);
        System.out.println(res2);
        org.junit.Assert.assertFalse("US10 is false", res == true);
        org.junit.Assert.assertTrue("US10 is true", res2 == true);
    }

    public void isOrphans () throws ParseException {
        int childAge1 = 15;
        int childAge2 = 18;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM yyyy");
        Date fatherDeath1 = null;
        Date fatherDeath2 = simpleDateFormat.parse(new String ("18 02 2011"));
        Date motherDeath1 = null;
        Date motherDeath2 = simpleDateFormat.parse(new String ("18 02 2011"));
        boolean res1 = Sprint3_Checkout.us33_list_orphans(childAge1, fatherDeath1, motherDeath1, "child1", "I01", "F01"); //False
        boolean res2 = Sprint3_Checkout.us33_list_orphans(childAge1, fatherDeath1, motherDeath2, "child1", "I01", "F02"); //False
        boolean res3 = Sprint3_Checkout.us33_list_orphans(childAge1, fatherDeath2, motherDeath1, "child1", "I01", "F03"); //False
        boolean res4 = Sprint3_Checkout.us33_list_orphans(childAge1, fatherDeath2, motherDeath2, "child1", "I01", "F04"); //True
        boolean res5 = Sprint3_Checkout.us33_list_orphans(childAge2, fatherDeath1, motherDeath1, "child2", "I02", "F05"); //False
        boolean res6 = Sprint3_Checkout.us33_list_orphans(childAge2, fatherDeath1, motherDeath2, "child2", "I02", "F06"); //False
        boolean res7 = Sprint3_Checkout.us33_list_orphans(childAge2, fatherDeath2, motherDeath1, "child2", "I02", "F07"); //False
        boolean res8 = Sprint3_Checkout.us33_list_orphans(childAge2, fatherDeath2, motherDeath2, "child2", "I02", "F08"); //False
        org.junit.Assert.assertFalse("Test case 1 is failed.", res1);
        org.junit.Assert.assertFalse("Test case 2 is failed.", res2);
        org.junit.Assert.assertFalse("Test case 3 is failed.", res3);
        org.junit.Assert.assertTrue("Test case 4 is failed.", res4);
        org.junit.Assert.assertFalse("Test case 5 is failed.", res5);
        org.junit.Assert.assertFalse("Test case 6 is failed.", res6);
        org.junit.Assert.assertFalse("Test case 7 is failed.", res7);
        org.junit.Assert.assertFalse("Test case 8 is failed.", res8);
    }

    public void isLargeAgeDifference () throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM yyyy");
        Date merriage = simpleDateFormat.parse(new String ("18 09 2017"));
        Date fatherBirth1 = simpleDateFormat.parse(new String ("18 02 1997"));
        Date fatherBirth2 = simpleDateFormat.parse(new String ("18 02 1977"));
        Date motherBirth1 = simpleDateFormat.parse(new String ("18 02 1997"));
        Date motherBirth2 = simpleDateFormat.parse(new String ("18 02 1977"));
        boolean res1 = Sprint3_Checkout.us34_list_large_age_differences(fatherBirth1, motherBirth1, merriage, "F01"); //False
        boolean res2 = Sprint3_Checkout.us34_list_large_age_differences(fatherBirth1, motherBirth2, merriage, "F01"); //True
        boolean res3 = Sprint3_Checkout.us34_list_large_age_differences(fatherBirth2, motherBirth1, merriage, "F01"); //True
        org.junit.Assert.assertFalse("Test case 1 is failed.", res1);
        org.junit.Assert.assertTrue("Test case 2 is failed.", res2);
        org.junit.Assert.assertTrue("Test case 3 is failed.", res3);
    }

    public static junit.framework.Test suite() {
        TestSuite s = new TestSuite();
        s.addTest(new TestSuites_Sprint3("Birth_before_death_of_parents"));
        s.addTest(new TestSuites_Sprint3("marriage_after_14"));
        s.addTest(new TestSuites_Sprint3("isOrphans"));
        s.addTest(new TestSuites_Sprint3("isLargeAgeDifference"));
        return s;
    }
}
