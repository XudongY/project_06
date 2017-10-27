package test;

import com.sun.source.tree.AssertTree;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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



    public static junit.framework.Test suite() {
        TestSuite s = new TestSuite();
        s.addTest(new TestSuites_Sprint3("Birth_before_death_of_parents"));
        s.addTest(new TestSuites_Sprint3("marriage_after_14"));
        return s;
    }
}
