// Gabriel Malone / CSCI65 / Week 12 / Summer 2024

public class Customer extends Person{

	private Date dateJoined;
	private String custID;

	/**
	 * No arguments constructor
	 */
	public Customer(){
		super();
		// defaults
		setDateJoined(new Date());
		setID(custID);
	}
	

	/**
	 * Overloaded constructor
	 * @param FirstName
	 * @param LastName
	 * @param Phone
	 * @param DOB
	 * @param dateJoined
	 * @param custID
	 */
	public Customer(String FirstName, String LastName, String Phone, Date DOB, Date dateJoined, String custID){
		super(FirstName, LastName, Phone, DOB);
		setDateJoined(dateJoined);
		setID(custID);
	}

	/**
	 * Overloaded constructor
	 * @param person
	 * @param dateJoined
	 * @param custID
	 */
	public Customer (Person person, Date dateJoined, String custID){
		super(person);
		setDateJoined(dateJoined);
		setID(custID);
	}

	/**
	 * Method to copy a customer's info
	 * @param toCopy
	 */
	public Customer (Customer toCopy){
		this.setFirstName(toCopy.getFirstName());
		this.setLastName(toCopy.getLastName());
		this.setPhoneNumber(rawPhoneNumber(toCopy.getPhoneNumber()));
		this.setDOB(toCopy.getDOB());
		this.setID(toCopy.getID());
		this.setDateJoined(toCopy.getDateJoined());
	}

	/**
	 * Method to get this customer's joined Date variable states
	 * @return
	 */
	public Date getDateJoined(){
		return this.dateJoined;
	}

	/**
	 * Method to set date joined with Date object's variable states
	 * 
	 * @param dateJoined
	 */
	public void setDateJoined(Date dateJoined){
		this.dateJoined = new Date(dateJoined);
	}

	/**
	 * Method to set date joined with int vars
	 * @param month
	 * @param day
	 * @param year
	 */
	public void setDateJoined(int month, int day, int year){
		this.dateJoined = new Date (month, day, year);
	}

	/**
	 * Method to return the ID of this Customer object
	 * @return
	 */
	public String getID(){
		return this.custID;
	}
	
	/**
	 * Method to set the Customer ID for this object
	 * @param custID
	 */
	public void setID(String custID){
		if (custID == null)
			this.custID = "UNKNOWN";
		else
			this.custID = custID;
	}

	public String rawPhoneNumber(String formattedPhoneNumber){
		String numbersonly = "";
		char[] phoneArray = formattedPhoneNumber.toCharArray();
		for (char character : phoneArray){
			if (Character.isDigit(character)){
				numbersonly += character;
			}
		}
		return numbersonly;
	}

	@Override
	public String toString() {
		String space = " ";
		return super.toString() +
		 	"\n" + space.repeat(21) + Colors.ANSI_CYAN + "Joined: " + Colors.ANSI_RESET + dateJoined +
			"\n" + space.repeat(21) + Colors.ANSI_CYAN + "ID: " + Colors.ANSI_RESET + custID + "\n";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)					return true;	// idenitity check
		if (obj == null)					return false;	// null check
		if (getClass() != obj.getClass())	return false;	// origin check 
		if (!super.equals(obj))				return false;	// super class check (inheritance)

		Customer other = (Customer) obj;					// downcast

		if (dateJoined == null){							// date hired
			if (other.dateJoined != null)		return false;
		}
		else if (!dateJoined.equals(other.dateJoined))		// calls Date.equals (composition)
			return false;

		if (!custID.equals(other.custID))					return false;	// String

		return true;	// everything is equal
	}

}
