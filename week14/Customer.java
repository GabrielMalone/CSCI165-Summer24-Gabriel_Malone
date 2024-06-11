// Gabriel Malone / CSCI165 / Week 14 / Summer 2024
import java.util.Scanner;

public class Customer extends Person{

	private Date dateJoined;
	private String custID;
	Scanner getInput = new Scanner(System.in);

	/**
	 * No arguments constructor
	 */
	public Customer() throws IDNotWellFormedException{
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
	public Customer(String FirstName, String LastName, String Phone, Date DOB, Date dateJoined, String custID)throws IDNotWellFormedException{
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
	public Customer (Person person, Date dateJoined, String custID)throws IDNotWellFormedException{
		super(person);
		setDateJoined(dateJoined);
		setID(custID);
	}

	/**
	 * Method to copy a customer's info
	 * @param toCopy
	 */
	public Customer (Customer toCopy)throws IDNotWellFormedException{
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
	public void setID(String custID) throws IDNotWellFormedException{
		if (custID.length() != 4){
			throw new IDNotWellFormedException("\n" + custID + "ID invalid. 1 letter followed by 3 digits required: ");
		}
		if (! Character.isAlphabetic(custID.charAt(0))) {
			throw new IDNotWellFormedException("\n" + custID + " invalid. must begin with a letter followed by 3 digits: ");
		}
		char [] remainingChars = custID.substring(1, 4).toCharArray();
		for (char character : remainingChars){
			if (!Character.isDigit(character)){
				throw new IDNotWellFormedException("\n" + custID + " must contain three digits after the first letter: ");
			}
		}
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
		return
			super.toString() +
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
