package important;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Indivdual {
	private String ID;
	private String Name;
	private String Gender;
	private Date Birthday;
	private int Age;
	private boolean Alive;
	private Date Death;
	private List<String> Child = new ArrayList<String>();
	private List<String> Spouse = new ArrayList<String>();

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public Date getBirthday() {
		return Birthday;
	}

	public void setBirthday(Date birthday) {
		Birthday = birthday;
	}

	public int getAge() {
		return Age;
	}

	public void setAge(int age) {
		Age = age;
	}

	public boolean isAlive() {
		return Alive;
	}

	public void setAlive(boolean alive) {
		Alive = alive;
	}

	public Date getDeath() {
		return Death;
	}

	public void setDeath(Date death) {
		Death = death;
	}

	public List<String> getChild() {
		return Child;
	}

	public void setChild(List<String> child) {
		Child = child;
	}

	public List<String> getSpouse() {
		return Spouse;
	}

	public void setSpouse(List<String> spouse) {
		Spouse = spouse;
	}

}
