package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import important.Family;
import important.Indivdual;
import jnr.ffi.annotations.In;
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

    public void siblings_spacing() throws ParseException{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");

        Date d1 = simpleDateFormat.parse(new String("24 SEP 1995"));
        Date d2 = simpleDateFormat.parse(new String("24 SEP 2016"));
        boolean res1 = Sprint4_Checkout.us13_compare_birthday(d1, d2);
        Assert.assertTrue("US13 is true.", res1);

        Date d3 = simpleDateFormat.parse(new String("30 SEP 2016"));
        Date d4 = simpleDateFormat.parse(new String("24 SEP 2016"));
        boolean res2 = Sprint4_Checkout.us13_compare_birthday(d3, d4);
        Assert.assertFalse("US13 is false", res2);

        Date d5 = simpleDateFormat.parse(new String("25 SEP 2016"));
        Date d6 = simpleDateFormat.parse(new String("24 SEP 2016"));
        boolean res3 = Sprint4_Checkout.us13_compare_birthday(d5, d6);
        Assert.assertTrue("US13 is false", res3);
    }

    public void test_age_calculator() throws ParseException{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");

        Date d1 = simpleDateFormat.parse(new String("24 SEP 1995"));
        Date d2 = simpleDateFormat.parse(new String("24 SEP 2016"));
        String res1 = Sprint4_Checkout.age_calculator(d1, d2);
        Assert.assertTrue("US13 is true.", res1.equals("21"));

        Date d3 = simpleDateFormat.parse(new String("30 SEP 1916"));
        Date d4 = simpleDateFormat.parse(new String("24 SEP 2016"));
        String res2 = Sprint4_Checkout.age_calculator(d3, d4);
        Assert.assertFalse("US13 is false", res2.equals("90"));

    }

    public void unique_family_by_spouse() throws ParseException {
        List<Family> families12 = new ArrayList<>();
        List<Family> families13 = new ArrayList<>();
        List<Family> families14 = new ArrayList<>();
        List<Family> families56 = new ArrayList<>();
        List<Family> families57 = new ArrayList<>();


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");

        Family f1 = new Family();
        Family f2 = new Family();
        Family f3 = new Family();
        Family f4 = new Family();
        Family f5 = new Family();
        Family f6 = new Family();
        Family f7 = new Family();

        //Always true when compare with each other
        families12.add(f1);
        families12.add(f2);
        families13.add(f1);
        families13.add(f3);
        families14.add(f1);
        families14.add(f4);
        //Always false when compare with each other
        families56.add(f5);
        families56.add(f6);
        families57.add(f5);
        families57.add(f7);



        f1.setHusbandName("Jack");
        f1.setWifeName("Merry");
        f1.setMarried(simpleDateFormat.parse("11 JAN 1997"));
        f2.setHusbandName("Neo");
        f2.setWifeName("Flora");
        f2.setMarried(simpleDateFormat.parse("11 JAN 1997"));
        f3.setHusbandName("Jack");
        f3.setWifeName("Flora");
        f3.setMarried(simpleDateFormat.parse("11 JAN 1996"));
        f4.setHusbandName("Neo");
        f4.setWifeName("Merry");
        f4.setMarried(simpleDateFormat.parse("11 JAN 1996"));
        f5.setHusbandName("Jack");
        f5.setWifeName("Flora");
        f5.setMarried(simpleDateFormat.parse("11 JAN 1997"));
        f6.setHusbandName("Jack");
        f6.setWifeName("Merry");
        f6.setMarried(simpleDateFormat.parse("11 JAN 1997"));
        f7.setHusbandName("Neo");
        f7.setWifeName("Flora");
        f7.setMarried(simpleDateFormat.parse("11 JAN 1997"));


        boolean res12 = Sprint4_Checkout.us24_unique_family_by_spouses(families12);
        Assert.assertTrue("families12 fail the test", res12);
        boolean res13 = Sprint4_Checkout.us24_unique_family_by_spouses(families13);
        Assert.assertTrue("families13 fail the test", res13);
        boolean res14 = Sprint4_Checkout.us24_unique_family_by_spouses(families14);
        Assert.assertTrue("families14 fail the test", res14);
        boolean res56 = Sprint4_Checkout.us24_unique_family_by_spouses(families56);
        Assert.assertFalse("families56 fail the test", res56);
        boolean res57 = Sprint4_Checkout.us24_unique_family_by_spouses(families57);
        Assert.assertFalse("families57 fail the test", res57);
    }

    public void unique_first_name_in_family() throws ParseException {
        List<Indivdual> indivduals = new ArrayList<>();
        List<String> c12= new ArrayList<>();
        List<String> c13= new ArrayList<>();
        List<String> c24= new ArrayList<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
        Indivdual c1 = new Indivdual();
        Indivdual c2 = new Indivdual();
        Indivdual c3 = new Indivdual();
        Indivdual c4 = new Indivdual();

        c1.setID("01");
        c1.setName("Nick Zhang");
        c1.setBirthday(simpleDateFormat.parse("09 Jan 1993"));
        c2.setID("02");
        c2.setName("Nick Zhang");
        c2.setBirthday(simpleDateFormat.parse("09 Jan 1993"));
        c3.setID("03");
        c3.setName("Mike Zhang");
        c3.setBirthday(simpleDateFormat.parse("09 Jan 1993"));
        c4.setID("04");
        c4.setName("Mike Zhang");
        c4.setBirthday(simpleDateFormat.parse("09 Jan 1992"));

        c12.add(c1.getID());
        c12.add(c2.getID());
        c13.add(c1.getID());
        c13.add(c3.getID());
        c24.add(c2.getID());
        c24.add(c3.getID());

        indivduals.add(c1);
        indivduals.add(c2);
        indivduals.add(c3);

        boolean res12 = Sprint4_Checkout.us25_Unique_first_names_in_families(c12, indivduals, "fam_ID");
        Assert.assertFalse("c12 fail the test", res12);
        boolean res13 = Sprint4_Checkout.us25_Unique_first_names_in_families(c13, indivduals, "fam_ID");
        Assert.assertTrue("c13 fail the test", res13);
        boolean res24 = Sprint4_Checkout.us25_Unique_first_names_in_families(c24, indivduals, "fam_ID");
        Assert.assertTrue("c24 fail the test", res24);
    }
    public static junit.framework.Test suite() {
        TestSuite s = new TestSuite();
        s.addTest(new TestSuites_Sprint4("Unique_IDs"));
        s.addTest(new TestSuites_Sprint4("Unique_name_and_birth_date"));
        s.addTest(new TestSuites_Sprint4("siblings_spacing"));
        s.addTest(new TestSuites_Sprint4("test_age_calculator"));
        s.addTest(new TestSuites_Sprint4("unique_family_by_spouse"));
        return s;
    }
}
