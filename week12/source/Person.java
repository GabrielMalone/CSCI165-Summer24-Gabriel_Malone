/*
 * File:	Person.java
 * Author:	Ken Whitener
 * Date:	4/15/2024
 * 
 * Description: This class represents a person with a first name, last name, phone number, and date of birth.
 */

public class Person{

	// instance variables
	private String 	firstName;
	private String 	lastName;
	private String 	phoneNumber;
	private Date	DOB;

	//constructors

	/**
	 * No argument constructor	
	 */
	public Person(){
		getAndSetFirstNameInfo();
		getAndSetLastNameInfo();
		getAndSetPhoneNumber();
		getAndSetDOB();
	}

	/**
	 * 
	 * @param firstName
	 * @param lastName
	 */
	public Person(String firstName, String lastName) {
		this.firstName	= firstName;
		this.lastName	= lastName;
		getAndSetPhoneNumber();
		getAndSetDOB();
	}

	/**
	 * 
	 * @param firstName
	 * @param lastName
	 * @param phoneNumber
	 */
	public Person(String firstName, String lastName, String phoneNumber) {
		this(firstName, lastName);			// call to overloaded constructor
		this.setPhoneNumber(phoneNumber);
		getAndSetDOB();
	}

	/**
	 * 
	 * @param firstName
	 * @param lastName
	 * @param phoneNumber
	 * @param DOB
	 */
	public Person(String firstName, String lastName, String phoneNumber, Date DOB) {
		this(firstName, lastName);
		this.setPhoneNumber(phoneNumber);
		this.setDOB(DOB);
	}

	/**
	 * Copy Constructor
	 * @param toCopy Person object to copy
	 */
	public Person(Person toCopy){
		// uses "this" to call constructor in the same class
		this(toCopy.firstName, toCopy.lastName, toCopy.phoneNumber, toCopy.DOB);
	}

	/**
	 * 
	 * @return Person's first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * 
	 * @return Person's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * 
	 * @return Person's full name
	 */
	public String getName(){
		return this.firstName + " " + this.lastName;
	}

	/**
	 * 
	 * @return Person's formatted phone number
	 */
	public String getPhoneNumber() {
		String area		= phoneNumber.substring(0, 3);
		String prefix	= phoneNumber.substring(3, 6);
		String route	= phoneNumber.substring(6);
		return "(" + area + ")" + prefix + "-" + route;
	}

	/**
	 * 
	 * @param phoneNumber
	 */
	public void setPhoneNumber(String phoneNumber) {
		if(phoneNumber.length() == 10)
			this.phoneNumber = phoneNumber;
	}

	/**
	 * 
	 * @param DOB
	 */
	public void setDOB(Date DOB){
		this.DOB = new Date(DOB);		// privacy protection
	}

	/**
	 * 
	 * @return The Person's Date of Birth
	 */
	public Date getDOB(){
		return new Date(this.DOB);		// privacy protection
	}

	@Override
	public String toString() {
		return  "Name: " 	+ firstName + " " + lastName + "\n" +
				"Phone: " 	+ getPhoneNumber() + "\n" +
				"DOB: " 	+ DOB;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)					return true;	// identity check
		if (obj == null)					return false;	// null check
		if (getClass() != obj.getClass())	return false;	// origin check	(inheritance)

		Person other = (Person) obj;						// down cast

		// check each field, be mindful of null pointers when dealing with objects
		if (firstName == null){							// check first name
			if (other.firstName != null)
				return false;
		}
		else if (!firstName.equals(other.firstName))		// calls String.equals (composition)
			return false;

		if (lastName == null){								// check last name
			if (other.lastName != null)
				return false;
		}
		else if (!lastName.equals(other.lastName))			// calls String.equals (composition)
			return false;

		if (phoneNumber == null){							// check phone number
			if (other.phoneNumber != null)
				return false;
		}
		else if (!phoneNumber.equals(other.phoneNumber))	// calls String.equals (composition)
			return false;

		if (DOB == null){									// check DOB
			if (other.DOB != null)
				return false;
		}
		else if (!DOB.equals(other.DOB))					// calls Date.equals (composition)
			return false;

		return true;
	}

	private void getAndSetFirstNameInfo(){
		if (this.firstName == null){
			this.firstName = "UNKNOWN FIRST";
			/* 
			System.out.println("please provide a frist name for this employee");
			String firstName = Driver.scanner.nextLine();
			this.firstName = firstName;
			*/
		}
	}
	private void getAndSetLastNameInfo(){
		if (this.lastName == null){
			this.lastName = "UNKNOWN LAST";
			/*
			System.out.println("please provide a last name for this employee");
			String lastName = Driver.scanner.nextLine();
			this.lastName = lastName;
			*/
		}
	}
	private void getAndSetPhoneNumber(){
		if (this.phoneNumber == null){
			this.phoneNumber = "0000000000";
			/*
			System.out.println("please provide a phone number for this employee");
			String phoneNumber = Driver.scanner.nextLine();
			*/
			setPhoneNumber(phoneNumber);
		}
	}
	private void getAndSetDOB(){
		if (this.DOB == null){
			this.DOB = new Date(1,1,1111);
			/*
			System.out.println("please provide a birthdate for this employee:");
			String DOBstring = Driver.scanner.nextLine();
			String [] DOBarray = DOBstring.split("/");
			// get the date info from string
			// split into array at  "/"
			// iterate through array and convert to int
			// pass those ints as args for new Date obj.s
			int [] DOBintArray = new int[3];
			int index = 0;
			for (String dateInfo : DOBarray){
				int data = Integer.valueOf(dateInfo);
				DOBintArray[index] = data;
				index ++;
			}
			int month 	= DOBintArray[0];
			int day 	= DOBintArray[1];
			int year 	= DOBintArray[2];
			this.DOB = new Date(month, day, year);
			*/
		}
	}
}