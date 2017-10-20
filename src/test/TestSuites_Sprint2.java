package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
		int count = 1;
		String fam_id = "NNN";
		for (int i = 0; i < 13; i++) {
			children.add(count + "k");
		}
		boolean res = Sprint2_Checkout.us15_fewer_than_15_siblings(children,
				fam_id);
		Assert.assertTrue("US15 is true", res == true);
	}

	public void maleLastNames() throws ParseException {
		String hus_name = "Yutong /Zhao/";
		String wife_name = "Xintong /Zhao/";
		String fam_id = "NNN";
		boolean res = Sprint2_Checkout.us16_male_last_names(hus_name,
				wife_name, fam_id);
		Assert.assertTrue("US16 is true", res == true);
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

	public static junit.framework.Test suite() {
		TestSuite s = new TestSuite();
		s.addTest(new TestSuites_Sprint2("fewerThan15Siblings"));
		s.addTest(new TestSuites_Sprint2("maleLastNames"));
		s.addTest(new TestSuites_Sprint2("lt150"));
		s.addTest(new TestSuites_Sprint2("birthBeforeParentsMarriage"));
		return s;
	}
}
