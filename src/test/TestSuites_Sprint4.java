package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import important.Indivdual;
import junit.framework.Assert;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import sprints.Sprint3_Checkout;
import sprints.Sprint4_Checkout;

public class TestSuites_Sprint4 extends TestCase{
    public TestSuites_Sprint4() {
        super();
    }

    public TestSuites_Sprint4(String name) {
        super(name);
    }
    public List<Indivdual> indivdualList = new ArrayList<>();
    public List<Indivdual> indivdualList1 = new ArrayList<>();

    protected void setUp() throws Exception {
        Indivdual in1 = new Indivdual();
        Indivdual in2 = new Indivdual();
        Indivdual in3 = new Indivdual();
        Indivdual in4 = new Indivdual();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
        in1.setID("123456789");
        in1.setName("qqq");
        in1.setBirthday(simpleDateFormat.parse(new String("24 SEP 1994")));

        in2.setID("1234567899");
        in2.setName("aaa");
        in2.setBirthday(simpleDateFormat.parse(new String("24 SEP 1995")));

        in3.setID("123456789");
        in3.setName("qqq");
        in3.setBirthday(simpleDateFormat.parse(new String("24 SEP 1995")));

        in4.setID("123456789");
        in4.setName("qqq");
        in4.setBirthday(simpleDateFormat.parse(new String("24 SEP 1995")));

        indivdualList.add(in1);
        indivdualList.add(in2);
        indivdualList1.add(in3);
        indivdualList1.add(in4);
    }

    public void Unique_IDs() throws ParseException {
        boolean res = Sprint4_Checkout.us22_Unique_IDs(indivdualList);
        boolean res1 = Sprint4_Checkout.us22_Unique_IDs(indivdualList1);
        org.junit.Assert.assertTrue("US22 is true", res == true);
        org.junit.Assert.assertFalse("US22 is false", res1 == true);
        //rg.junit.Assert.assertTrue("US09 is false", res == true);
    }

    public void Unique_name_and_birth_date() throws ParseException {
        boolean res = Sprint4_Checkout.us23_Unique_name_and_birth_date(indivdualList);
        boolean res1 = Sprint4_Checkout.us23_Unique_name_and_birth_date(indivdualList1);
        org.junit.Assert.assertTrue("US23 is true", res == true);
        org.junit.Assert.assertFalse("US23 is false", res1 == true);
    }


    public static junit.framework.Test suite() {
        TestSuite s = new TestSuite();
        s.addTest(new TestSuites_Sprint4("Unique_IDs"));
        s.addTest(new TestSuites_Sprint4("Unique_name_and_birth_date"));
        return s;
    }
}
