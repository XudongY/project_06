package important;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Family {
	private String ID;
	private Date Married;
	private Date Divorced;
	private String HusbandID;
	private String HusbandName;
	private String WifeID;
	private String WifeName;
	private List<String> Children = new ArrayList<String>();

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public Date getMarried() {
		return Married;
	}

	public void setMarried(Date married) {
		Married = married;
	}

	public Date getDivorced() {
		return Divorced;
	}

	public void setDivorced(Date divorced) {
		Divorced = divorced;
	}

	public String getHusbandID() {
		return HusbandID;
	}

	public void setHusbandID(String husbandID) {
		HusbandID = husbandID;
	}

	public String getHusbandName() {
		return HusbandName;
	}

	public void setHusbandName(String husbandName) {
		HusbandName = husbandName;
	}

	public String getWifeID() {
		return WifeID;
	}

	public void setWifeID(String wifeID) {
		WifeID = wifeID;
	}

	public String getWifeName() {
		return WifeName;
	}

	public void setWifeName(String wifeName) {
		WifeName = wifeName;
	}

	public List<String> getChildren() {
		return Children;
	}

	public void setChildren(List<String> children) {
		Children = children;
	}

}