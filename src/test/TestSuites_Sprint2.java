package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import important.Indivdual;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.Assert;

import sprints.Sprint2_Checkout;

public class TestSuites_Sprint2 extends TestCase {
	public TestSuites_Sprint2() {
		super();
	}

	public TestSuites_Sprint2(String name) {
		super(name);
	}

	public void setUp() throws Exception {

	}

	// Done by Yutong
	public void fewerThan15Siblings() throws ParseException {
		List<String> children = new ArrayList<String>();
		List<String> children1 = new ArrayList<String>();
		int count = 1;
		String fam_id = "NNN";
		for (int i = 0; i < 13; i++) {
			count++;
			children.add(count + "k");
		}
		boolean res = Sprint2_Checkout.us15_fewer_than_15_siblings(children, fam_id);
		Assert.assertTrue("US15 is true", res);
		count = 0;
		for (int i = 0; i < 17; i++) {
			count++;
			children1.add(count + "k");
		}
		boolean res1 = Sprint2_Checkout.us15_fewer_than_15_siblings(children1,
				fam_id);
		Assert.assertFalse("US07 is false!", res1);
		Assert.assertTrue("US15 is true", res == true);
	}

	public void maleLastNames() throws ParseException {
		String hus_name = "Yutong /Zhao/";
		String wife_name = "Xintong /Zhao/";
		String fam_id = "NNN";
		boolean res = Sprint2_Checkout.us16_male_last_names(hus_name, wife_name, fam_id);
		Assert.assertTrue("US16 is true", res);
		String hus_name1 = "Yutong /K/";
		String wife_name1 = "Xintong /T/";
		boolean res1 = Sprint2_Checkout.us16_male_last_names(hus_name1,
				wife_name1, fam_id);
		Assert.assertFalse("US16 is false", res1);
	}

	// Done by Xudong
	public void lt150() throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
		Date date1 = simpleDateFormat.parse(new String("24 SEP 1995"));
		Date date2 = simpleDateFormat.parse(new String("22 FEB 2002"));
		Date date3 = simpleDateFormat.parse(new String("24 SEP 1800"));
		Date date4 = simpleDateFormat.parse(new String("22 FEB 2002"));
		String id = "XXX";
		boolean res = Sprint2_Checkout.us07_less_than_150_years_old(date1,
				date2, id);
		boolean res1 = Sprint2_Checkout.us07_less_than_150_years_old(date3,
				date4, id);
		Assert.assertTrue("US07 is true", res == true);
		Assert.assertFalse("US07 is false", res1 == true);
	}

	public void birthBeforeParentsMarriage() throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
		Date birth = simpleDateFormat.parse(new String("24 SEP 1995"));
		Date marriage = simpleDateFormat.parse(new String("22 FEB 1993"));
		Date divorce = simpleDateFormat.parse(new String("22 FEB 1996"));
		Date birth1 = simpleDateFormat.parse(new String("24 SEP 1990"));
		Date marriage1 = simpleDateFormat.parse(new String("22 FEB 1993"));
		Date divorce1 = simpleDateFormat.parse(new String("22 FEB 1996"));
		String id = "KKK";
		boolean res = Sprint2_Checkout.us08_birth_before_marriage_of_parents(
				birth, marriage, divorce, id);
		boolean res1 = Sprint2_Checkout.us08_birth_before_marriage_of_parents(
				birth1, marriage1, divorce1, id);
		//System.out.println(res);
		Assert.assertTrue("US08 is true", res == true);
		//System.out.println(res1);
		Assert.assertFalse("US08 is false", res1 == true);
	}

	//Chenglin US12 & US21
	public void parentsNotTooOld() throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String family_id = "fam_id";
		Date husband_birth = simpleDateFormat.parse("1940-08-26");
		Date wife_birth = simpleDateFormat.parse("1940-08-24");
		List<String> children_id = new ArrayList<>();
		List<Indivdual> childrenList =  new ArrayList<>();

		children_id.add("01");
		children_id.add("02");
		children_id.add("03");
		children_id.add("04");
		children_id.add("05");

		Indivdual child01 = new Indivdual();
		Indivdual child02 = new Indivdual();
		Indivdual child03 = new Indivdual();
		Indivdual child04 = new Indivdual();
		Indivdual child05 = new Indivdual();

		child01.setID("01");
		child02.setID("02");
		child03.setID("03");
		child04.setID("04");
		child05.setID("05");

		child01.setBirthday(simpleDateFormat.parse(new String ("2000-07-25")));
		child02.setBirthday(simpleDateFormat.parse(new String ("2000-08-23")));
		child03.setBirthday(simpleDateFormat.parse(new String ("2000-08-25")));
		child04.setBirthday(simpleDateFormat.parse(new String ("2000-08-27")));
		child05.setBirthday(simpleDateFormat.parse(new String ("2000-09-23")));

		childrenList.add(child01);
		childrenList.add(child02);
		childrenList.add(child03);
		childrenList.add(child04);
		childrenList.add(child05);
		for (String child_id : children_id) {
			boolean res = Sprint2_Checkout.us12_parents_not_too_old(family_id, husband_birth, wife_birth, child_id, childrenList);
			if (child_id.equals("01") || child_id.equals("02")) {
				Assert.assertTrue("US12 is true for child: " + child_id, res == true);
			} else Assert.assertTrue("US12 is false for child: " + child_id, res == false);
		}
	}

	public void correctGenderForRole() throws ParseException {
		String family_id = "fam_id";
		String husband_gender1 = "M";
		String wife_gender1 = "F";
		String husband_gender2 = "F";
		String wife_gender2 = "F";
		String husband_gender3 = "M";
		String wife_gender3 = "M";
		String husband_gender4 = "F";
		String wife_gender4 = "M";

		boolean res1 = Sprint2_Checkout.us21_correct_gender_for_role(family_id, husband_gender1, wife_gender1);
		Assert.assertTrue("US21 is true", res1 == true);
		boolean res2 = Sprint2_Checkout.us21_correct_gender_for_role(family_id, husband_gender2, wife_gender2);
		Assert.assertTrue("US21 is false", res2 == false);
		boolean res3 = Sprint2_Checkout.us21_correct_gender_for_role(family_id, husband_gender3, wife_gender3);
		Assert.assertTrue("US21 is false", res3 == false);
		boolean res4 = Sprint2_Checkout.us21_correct_gender_for_role(family_id, husband_gender4, wife_gender4);
		Assert.assertTrue("US21 is false", res4 == false);
	}
	public void recent_births() {

		String dateInString_1 = "10-Jan-2016";
		String dateInString_2 = "10-Oct-2017";
		SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
		try{ Date date_Date_format_1 = format.parse(dateInString_1);
			Date date_Date_format_2 = format.parse(dateInString_2);
			boolean res1 = Sprint2_Checkout.check_for_recent_births("I@134253@",date_Date_format_1);
			Assert.assertTrue("US35 is false", res1 == false);
			boolean res3 = Sprint2_Checkout.check_for_recent_births("I@1333353@",date_Date_format_2);
			Assert.assertTrue("US35 is true", res1 == true);}catch (ParseException e) {
			e.printStackTrace();
		}

}
	public void recent_deaths() {

		String dateInString_1 = "10-Jan-2016";
		String dateInString_2 = "10-Oct-2017";
		SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
		try{ Date date_Date_format_1 = format.parse(dateInString_1);
			Date date_Date_format_2 = format.parse(dateInString_2);
			boolean res1 = Sprint2_Checkout.check_for_recent_births("I@134253@",date_Date_format_1);
			Assert.assertTrue("US36 is false", res1 == false);
			boolean res3 = Sprint2_Checkout.check_for_recent_births("I@1333353@",date_Date_format_2);
			Assert.assertTrue("US36 is true", res1 == true);}catch (ParseException e) {
			e.printStackTrace();
		}

	}

	public static junit.framework.Test suite() {
		TestSuite s = new TestSuite();
		s.addTest(new TestSuites_Sprint2("fewerThan15Siblings"));
		s.addTest(new TestSuites_Sprint2("maleLastNames"));
		s.addTest(new TestSuites_Sprint2("lt150"));
		s.addTest(new TestSuites_Sprint2("birthBeforeParentsMarriage"));
		s.addTest(new TestSuites_Sprint2("parentsNotTooOld"));
		s.addTest(new TestSuites_Sprint2("correctGenderForRole"));
		s.addTest(new TestSuites_Sprint2("recent_births"));
		s.addTest(new TestSuites_Sprint2("recent_deaths"));
		return s;
	}
}
