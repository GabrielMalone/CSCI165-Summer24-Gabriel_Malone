// Gabriel Malone / CSCI65 / Week 10 / Summer 2024

public class Customer {

	// ================================
	// || Private instance variables ||
	// ================================
	private String name	 = "unknown customer";
	private String email = "unknown customer";
	private String phone = "unknown customer";
	

	// ==================
	// || Constructors ||
	// ==================


	/**
	 * No argument constructor lets TerminalDisplay take in the information
	 */
	public Customer(){
	
		String name = TerminalDisplay.nameRequest();
		TerminalDisplay.clearSequence();
		String email = TerminalDisplay.emailRequest();
		TerminalDisplay.clearSequence();
		String phone = TerminalDisplay.phoneRequest();
		TerminalDisplay.clearSequence();
		this.setName(name);
		this.setEmail(email);
		this.setPhone(phone);	

	}
	
	/**
	 * Overloaded constructor
	 * 
	 * @param name	The customer's name
	 * @param email	The customer's email
	 * @param phone	The customer's phone number
	 */
	public Customer(String name, String email, String phone){
		// call the set methods because that is where the domain
		// validation will be. No need to define it twice
		this.setName(name);
		this.setEmail(email);
		this.setPhone(phone);
	}


	// =============================
	// || "GETTERS" and "SETTERS" ||
	// =============================

	/**
	 * Returns the name field
	 * @return name (String)
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the customer's name
	 * @param name The Customer's name
	 */
	public void setName(String name) {
		char [] charArray = name.toCharArray();
		if(name == null || name == "")   
			name = this.name;
		// if no last name included
		else if (! name.contains(" ") && charArray.length != 1) 
			this.name = name.toUpperCase() + " DOE";
		// if name too short
		else if (charArray.length == 1) 
			name = this.name; 
		else this.name = name;
	}

	/**
	 * 
	 * @return email (String) The Customer's email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Sets and validates the Customer's email address
	 * @param email (String) valid email address
	 */
	public void setEmail(String email) {
		// This logic does not validate every single requirement
		// for a real email address. Just the ones listed in the lab
		// Validates explicitly without any fancy, magical, mysterious
		// Regular Expressions <= check it out, I know some of you have
		// It's also fun and good practice to write explicit logic . . .
		// especially while learning :)
		
		if(email == null || email == "") email = this.email; 
		int NOT_FOUND		= -1;	// reader friendly code symbols
		int PREFIX_LENGTH	= 64;

		int at_location = email.indexOf("@");	// find the "@"
		if(at_location == NOT_FOUND)	return;	// no '@' in email
		if(at_location > PREFIX_LENGTH)	return;	// prefix too long

		String prefix = email.substring(0, at_location); // deal with the prefix
		// FIXED to remove @ in the domain
		String domain = email.substring(at_location + 1, email.length());

		if(domain.contains("@")) return;		// @ in domain name
		
		// dot restrictions. Can't be first or last character in prefix
		if('.' == prefix.charAt(0) || '.' == prefix.charAt(prefix.length() - 1))
			return;

		// are there any consecutive dots in the prefix?
		for(int index = 0; index < at_location - 1; ++index)
			if(prefix.charAt(index) == '.' && prefix.charAt(index + 1) == '.')
				return;

		// validate the prefix characters
		String emailCharacterSet = this.emailCharacterSet();
		for(int index = 0; index < prefix.length(); ++ index)
			// is there are prefix character NOT IN the character set?
			// FIXED to change conditional to a negative
			if(! emailCharacterSet.contains(prefix.charAt(index) + ""))
				return;

		this.email = email; // PHEW, safe at last (I hope) Someone else has to test this
	}

	/**
	 * 
	 * @return phone (String) The Customer's phone
	 */
	public String getPhone() {
		return this.phone;
	}

	/**
	 * Sets and validates the Customer's phone number
	 * @param phone (String) 10 digit string
	 */
	public void setPhone(String phone) {
		// fail first, fail fast
		if(phone == null || phone == "") phone = this.phone;
		if(phone.length() != 10)return;

		// is each character a digit?
		// could also try/catch a parse to Long. Why not int?
		for(int index = 0; index < phone.length(); ++index)
			if(!Character.isDigit(phone.charAt(index)))
				return;

		this.phone = phone; // no other option but it being valid
	}
	
	// ======================
	// || Expected Methods ||
	// ======================

	/**
	 * Returns a strong of Customer's name, email, and phone number
	 */
	public String toString(){
		return  this.name	+ "\n" +
				this.email	+ "\n" +
				this.phone;
	}

	/**
	 * Method to compare one customer to another to see if they are the same
	 * 
	 * @param other
	 * @return (boolean) true or false
	 */
	public boolean equals(Customer other) {
		if (this == other)return true;		// identity check
		if (other == null)return false;		// argument is null, can't be equal

		// Collection of fail first, fail fast conditions
		if (email == null)					// email, with null checks
			if (other.email != null)
				return false;
		else if (!email.equals(other.email))// call String class equals
			return false;

		if (name == null)					// name, with null checks
			if (other.name != null)
				return false;
		else if (!name.equals(other.name))	// call String class equals
			return false;

		if (phone == null)					// phone, with null checks
			if (other.phone != null)
				return false;
		else if (!phone.equals(other.phone))
			return false;

		return true;						// all Fail conditions are false. Guaranteed equality
	}

	/**
	 * Method to order customer names alphabetically
	 * 
	 * @param other
	 * @return (int) 1 for greater than, 0 for equal, -1 for less than
	 */
	public int compareTo(Customer other){
		
		// convert names to all uppercase for comparisons
		String otherName = other.name.toUpperCase();
		String thisName  = this.name.toUpperCase();
		// create character arrays
		char[] otherNameArray = otherName.toCharArray();
		char[] thisNameArray  = thisName.toCharArray();
		// find shortest name for indexing range purposes
		int shortestName = 0;
		if (thisName.length() <= otherName.length()) shortestName = thisName.length();
		if (thisName.length() >= otherName.length()) shortestName = otherName.length();
		// iterate through characters in each name and compare
		int index = 0;
		while (index < shortestName){
			 if 		(otherNameArray [index] <  thisNameArray [index]) return   1;
			 else if 	(otherNameArray [index] >  thisNameArray [index]) return  -1;
			 else if 	(otherNameArray [index] == thisNameArray [index]) index += 1;
			}
		// if all letters same and names the same length
		// or if otherName shorter but has same substring
		// or otherName longer and has same substring
		if 		(otherName.length() == thisName.length()) return  0;
		else if (otherName.length() <  thisName.length()) return  1;
		else 											  return -1;
		}

	// ======================
	// || Private Helpers  ||
	// ======================
	private String emailCharacterSet(){
		StringBuilder sb = new StringBuilder();
		String special_characters = "#!%$'&+*-/=?^_`.{|}~";

		// build alphabet
		// FIXED - needed lowercase alphabet
		// FIXED < changed to <= to include final character
		for(int ascii = (int)'a'; ascii <= (int)'z'; ++ascii)
			sb.append((char)ascii);
		for(int ascii = (int)'A'; ascii <= (int)'Z'; ++ascii)
			sb.append((char)ascii);

		// add special characters 
		sb.append(special_characters);

		return sb.toString();
	}
}
