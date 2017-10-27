package test;

import important.Family;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.Assert;

import sprints.Sprint1_Checkout;

public class TestSuites_Sprint1 extends TestCase {

	public TestSuites_Sprint1() {
		super();
	}

	public TestSuites_Sprint1(String name) {
		super(name);
	}

	public void setUp() throws Exception {

	}

	// Done by Xudong Yu
	public void Deathafterbirth() throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
		Date date1 = simpleDateFormat.parse(new String("24 SEP 1995"));
		Date date2 = simpleDateFormat.parse(new String("22 FEB 2002"));
		String id = "id";
		boolean res = Sprint1_Checkout
				.us03_birth_before_death(date1, date2, id);
		Assert.assertTrue("US03 is true", res == true);
		return;
	}

	// Done by Xudong Yu
	public void marriage_Before_Death() throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
		Date date1 = simpleDateFormat.parse(new String("24 SEP 2008"));
		Date date2 = simpleDateFormat.parse(new String("22 FEB 2008"));
		Date date3 = simpleDateFormat.parse(new String("17 SEP 2005"));
		String husband_id = "YYY";
		String wife_id = "XXX";
		String fam_id = "NNN";
		boolean res1 = Sprint1_Checkout.us05_marriage_before_death(date1,
				date2, date3, fam_id, husband_id, wife_id);
		Assert.assertTrue("US05 is true", (res1) == true);
	}

	// Done by Chenglin Wu
	public void marrigeBeforeDivorce() throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
		Date marriage = simpleDateFormat.parse(new String("24 SEP 1995"));
		Date divorce = simpleDateFormat.parse(new String("22 FEB 2002"));
		String fam_id = "NNN";
		boolean res = Sprint1_Checkout.us04_marrige_before_divorce(marriage,
				divorce, fam_id);
		Assert.assertTrue("US04 is true", res == true);
		return;
	}

	// Done by Chenglin Wu
	public void divorceBeforeDeath() throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
		Date divorce = simpleDateFormat.parse(new String("24 SEP 1995"));
		Date husband_death = simpleDateFormat.parse(new String("22 FEB 2002"));
		Date wife_death = simpleDateFormat.parse(new String("17 SEP 2005"));
		String husband_id = "YYY";
		String wife_id = "XXX";
		String fam_id = "NNN";
		boolean res1 = Sprint1_Checkout
				.us06_divorce_before_death(husband_death, wife_death, divorce,
						fam_id, husband_id, wife_id);
		Assert.assertTrue("US06 is true", res1 == true);
	}

	// Done by Yutong Zhao
	public void dates_before_current_date() throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
		Date date = simpleDateFormat.parse(new String("24 SEP 1937"));
		String ID = "hfudilabfd";
		boolean res = Sprint1_Checkout.us01_dates_before_current_date(date, ID,
				"INDI");
		Assert.assertTrue(
				"US01: Dates (birth, death, marriage, divorce) should NOT be after the current date!",
				res == true);
	}

	// Done by Yutong Zhao
	public void birth_before_marriage() throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
		Date husband_birth = simpleDateFormat.parse(new String("24 SEP 1973"));
		Date wife_birth = simpleDateFormat.parse(new String("24 SEP 1976"));
		Date marriage = simpleDateFormat.parse(new String("22 FEB 2002"));
		String husband_id = "YYY";
		String wife_id = "XXX";
		String fam_id = "hfudilabfd";
		boolean res = Sprint1_Checkout.us02_birth_before_marriage(
				husband_birth, wife_birth, marriage, fam_id, husband_id,
				wife_id);
		Assert.assertTrue(
				"US02: Birth should occur before marriage of an individual!",
				res == true);
	}

	// Done by Disha Sareen
	public void No_bigamy() throws ParseException {
		Family family1 = new Family();
		Family family2 = new Family();
		family1.setHusbandID("IN01");
		family2.setHusbandID("IN02");
		family1.setWifeID("IN03");
		family2.setWifeID("IN04");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
		Date divorce = simpleDateFormat.parse(new String("24 SEP 1991"));
		Date marriage = simpleDateFormat.parse(new String("22 FEB 1990"));
		family1.setMarried(marriage);
		family1.setDivorced(divorce);
		Date divorce1 = simpleDateFormat.parse(new String("24 SEP 2008"));
		Date marriage1 = simpleDateFormat.parse(new String("22 FEB 1992"));
		family1.setMarried(marriage1);
		family1.setDivorced(divorce1);
		List<Family> familyList = new ArrayList<Family>();
		familyList.add(family1);
		familyList.add(family2);
		boolean res = Sprint1_Checkout.us11_no_bigamy("IN04", familyList);
		Assert.assertTrue(
				"Alert: Birth should occur before marriage of an individual!",
				res == true);
	}

	public void isValid() throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
		Date date = simpleDateFormat.parse(new String("24 SEP 1991"));
		boolean output1 = Sprint1_Checkout.us42_reject_illegitimate_dates(date,
				"XXX", "INDIVIDUAL");
		Assert.assertTrue("US42 is true.", output1 == true);

	}

	public static junit.framework.Test suite() {
		TestSuite s = new TestSuite();
		s.addTest(new TestSuites_Sprint1("Deathafterbirth"));
		s.addTest(new TestSuites_Sprint1("marriage_Before_Death"));
		s.addTest(new TestSuites_Sprint1("marrigeBeforeDivorce"));
		s.addTest(new TestSuites_Sprint1("divorceBeforeDeath"));
		s.addTest(new TestSuites_Sprint1("dates_before_current_date"));
		s.addTest(new TestSuites_Sprint1("birth_before_marriage"));
		// s.addTest(new TestSuites_Sprint1("No_bigamy"));
		s.addTest(new TestSuites_Sprint1("isValid"));
		;
		return s;
	}
}
