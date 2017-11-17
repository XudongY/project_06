package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Assert;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import sprints.Sprint3_Checkout;

public class TestSuites_Sprint3 extends TestCase {
	public TestSuites_Sprint3() {
		super();
	}

	public TestSuites_Sprint3(String name) {
		super(name);
	}

	public void setUp() throws Exception {

	}

	public void Birth_before_death_of_parents() throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
		Date date1 = simpleDateFormat.parse(new String("24 SEP 1995"));
		Date date2 = simpleDateFormat.parse(new String("22 FEB 1994"));
		String ID = "1111";
		Date date3 = simpleDateFormat.parse(new String("24 SEP 1995"));
		Date date4 = simpleDateFormat.parse(new String("22 FEB 2001"));
		String ID2 = "1111";
		boolean res = Sprint3_Checkout.us09_birth_before_death_of_parents(
				date1, date2, ID);
		boolean res2 = Sprint3_Checkout.us09_birth_before_death_of_parents(
				date3, date4, ID2);
		System.out.println(res);
		System.out.println(res2);
		org.junit.Assert.assertTrue("US09 is false", res == true);
		org.junit.Assert.assertFalse("US09 is true", res2 == true);
	}

	public void marriage_after_14() throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
		Date date1 = simpleDateFormat.parse(new String("24 SEP 1995"));
		Date date2 = simpleDateFormat.parse(new String("22 FEB 1994"));
		String ID = "1111";
		Date date3 = simpleDateFormat.parse(new String("24 SEP 1995"));
		Date date4 = simpleDateFormat.parse(new String("22 FEB 1966"));
		String ID2 = "1111";
		boolean res = Sprint3_Checkout.us10_marriage_after_14(date1, date2, ID);
		boolean res2 = Sprint3_Checkout.us10_marriage_after_14(date3, date4,
				ID2);
		System.out.println(res);
		System.out.println(res2);
		org.junit.Assert.assertFalse("US10 is false", res == true);
		org.junit.Assert.assertTrue("US10 is true", res2 == true);
	}

	public void check_deceased() throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
		Date date1 = simpleDateFormat.parse(new String("24 SEP 1995"));
		String id1 = "XXX";
		String id2 = "YYY";
		boolean res1 = Sprint3_Checkout.us29_isDeceased(id1, date1);
		boolean res2 = Sprint3_Checkout.us29_isDeceased(id2, null);
		Assert.assertTrue("US29 is true", res1);
		Assert.assertFalse("US29 is false", res2);
	}

	public void check_living_married() throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
		String id1 = "XXX";
		Date death_date1 = simpleDateFormat.parse(new String("24 SEP 1995"));
		Date marry_date1 = simpleDateFormat.parse(new String("24 SEP 2016"));
		boolean res1 = Sprint3_Checkout.us30_check_living_married(id1,
				death_date1, marry_date1);
		Assert.assertFalse("US30 is false.", res1);

		String id2 = "YYY";
		Date death_date2 = null;
		Date marry_date2 = simpleDateFormat.parse(new String("24 SEP 2016"));
		boolean res2 = Sprint3_Checkout.us30_check_living_married(id2,
				death_date2, marry_date2);
		Assert.assertTrue("US30 is true.", res2);

		String id3 = "ZZZ";
		Date death_date3 = null;
		Date marry_date3 = null;
		boolean res3 = Sprint3_Checkout.us30_check_living_married(id3,
				death_date3, marry_date3);
		Assert.assertFalse("US30 is false.", res3);

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
	public void upcoming_birthdays() {

		String dateInString_1 = "10 JAN 2016";
		String dateInString_2 = "10 NOV 2017";
		SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
		try {
			Date date_Date_format_1 = format.parse(dateInString_1);
			Date date_Date_format_2 = format.parse(dateInString_2);
			boolean res1 = Sprint3_Checkout.us38_check_for_upcoming_birthdays(
					"I@134253@", date_Date_format_1);
			Assert.assertTrue("US38 is false", res1 == false);
			boolean res3 = Sprint3_Checkout.us38_check_for_upcoming_birthdays(
					"I@1333353@", date_Date_format_2);
			Assert.assertTrue("US38 is true", res3 == true);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	public void upcoming_marriage_anniversaries(){
		String dateInString_1 = "10 JAN 2016";
		String dateInString_2 = "10 NOV 2017";
		SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
		try {
			Date date_Date_format_1 = format.parse(dateInString_1);
			Date date_Date_format_2 = format.parse(dateInString_2);
			boolean res1 = Sprint3_Checkout.us39_check_for_upcoming_marriage_anniversaries(
					"I@134253@", date_Date_format_1);
			Assert.assertTrue("US39 is false", res1 == false);
			boolean res3 = Sprint3_Checkout.us39_check_for_upcoming_marriage_anniversaries(
					"I@1333353@", date_Date_format_2);
			Assert.assertTrue("US39 is true", res3 == true);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static junit.framework.Test suite() {
		TestSuite s = new TestSuite();
		s.addTest(new TestSuites_Sprint3("Birth_before_death_of_parents"));
		s.addTest(new TestSuites_Sprint3("marriage_after_14"));
		s.addTest(new TestSuites_Sprint3("check_deceased"));
		s.addTest(new TestSuites_Sprint3("check_living_married"));
		s.addTest(new TestSuites_Sprint3("isOrphans"));
		s.addTest(new TestSuites_Sprint3("isLargeAgeDifference"));
		s.addTest(new TestSuites_Sprint3("upcoming_birthdays"));
		s.addTest(new TestSuites_Sprint3("upcoming_marriage_anniversaries"));
		return s;
	}
}
