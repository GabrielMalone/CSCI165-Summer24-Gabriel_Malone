/*
	There are four key principles associated with object-oriented programming:
		1) abstraction
		2) encapsulation
		3) inheritance
		4) polymorphism

	This Date class illustrates principles 1 and 2

	1) abstraction: Abstraction in object-oriented programming allows us to focus only on the desired 
		properties and behaviors of the objects and discard what is unimportant or irrelevant. It also 
		allows us the abiity to hide implementation details from agents that are using our classes. We
		will only expose the "interface"
	
	2) encapsulation: The object-oriented principle of encapsulation is the notion that we should hide 
		the contents of a class, except what is absolutely necessary to expose. Hence, we will restrict 
		the access to our class as much as we can, so that a user can change the class properties and 
		behaviors only from methods provided by the class. This also allows us to easily perform
		'domain validation'.

		marking something 'private' hides it (denies access)
		marking something 'public' exposes it (permits access)

	domain: The set of all possible values of a variable.

	Date Domains:
	=============
	1) Days {1 - 31} with some exceptions
	2) Months {1 - 12}
	3) Years {4-digit numbers}
 */

import java.util.HashMap;
import java.util.Map.Entry;

public class Date{

	// ================== //
	// INSTANCE VARIABLES //
	// ================== //
	
	// Class Level Instance Variables (non-static mean "instance ownership")
	// Each instance will get unique copies of these variables
	// These are initialized to zero unless otherwise written
	// could put default values here
	private int month	= 1;
	private int day		= 1;
	private int year	= 1000;
	private DateFormat format;

	// =============== //
	// CLASS VARIABLES //
	// =============== //

	// class variables (static means "class ownership")
	// class features only exist once in memory
	// The values are shared across all instances
	private static final HashMap<Integer, String> months = new HashMap<Integer, String>();

	// fill the map with month number to month string mappings
	// static blocks will run when the class loads
	static{
		months.put(1,"January");
		months.put(2,"February");
		months.put(3,"March");
		months.put(4,"April");
		months.put(5,"May");
		months.put(6,"June");
		months.put(7,"July");
		months.put(8,"August");
		months.put(9,"September");
		months.put(10,"October");
		months.put(11,"November");
		months.put(12,"December");
	};

	private static int[] days = {31,28,31,30,31,30,31,31,30,31,30,31};

	// ================ //
	// PUBLIC CONSTANTS //
	// ================ //

	// enumeration of date formats. 'enums' are constant by default
	public static enum DateFormat{LONG_DATE, SHORT_DATE};

	// =================== //
	// CONSTRUCTOR METHODS //
	// =================== //

	/**
	 * The no argument constructor which uses default values of
	 * 1/1/1000 and LONG_DATE formatting
	 */
	public Date() {
		this.month	= 1;
		this.day	= 1;
		this.year	= 1000;
		this.format = DateFormat.LONG_DATE;
	}

	/**
	 * Overloaded constructor to set all fields.
	 * Arguments will be domain validated
	 * 
	 * @param month
	 * @param day
	 * @param year
	 */ 
	public Date(int month, int day, int year){
		this.setYear(year);
		this.setMonth(month);
		this.setDay(day);
		this.format = DateFormat.LONG_DATE;
	}

	/**
	 * Copy constructor
	 * 
	 * @param d Date object to copy
	 */
	public Date(Date d){
		this.month	= d.month;
		this.day	= d.day;
		this.year	= d.year;
		this.format = d.format;
	}

	// ================ //
	// INSTANCE METHODS //
	// ================ //

	/**
	 * Validates and sets the month. Month will be left alone if invalid
	 * 
	 * @param month The integer month to be set
	 */
	public void setMonth(int month){
		// perform some domain validation
		if(month >= 1 && month <= 12)
			this.month = month;
	}

	/**
	 * Validates and sets the month. Month will be left alone if invalid
	 * 
	 * @param month The String month to be set
	 */
	public void setMonth(String month){
		// is the month in the map?
		// no iteration required for this check
		if(!months.containsValue(month))return;

		// it is in the map, get the key
		// could use a bidirectional map to avoid iteration in value -> key mapping
		for (Entry<Integer, String> month_mapping : months.entrySet())
			if (month_mapping.getValue().equals(month))
				this.month = month_mapping.getKey();
	}

	/**
	 * Returns the current month
	 * 
	 * @return this.month
	 */
	public int getMonth(){
		return month;
	}

	/**
	 * Returns the month as a string
	 * 
	 * @return The month name for "this" month
	 */
	public String getMonthName(){
		return months.get(month);
	}

	/**
	 * Validates and sets the day. Day will be set to 1 if invalid
	 * 
	 * @param day The day to be set
	 */
	public void setDay(int _day){
		// broad validation first
		if(_day < 1 || _day > 31) return;

		// granular validation
		if(_day <= days[ this.month - 1 ])
			this.day = _day;
		else if(this.month == 2 && isLeapYear(this.year) && _day <= 29)
			this.day = _day;
		else this.day = 1;
	}

	/**
	 * Returns the current day
	 * 
	 * @return this.day
	 */
	public int getDay(){
		return day;
	}

	/**
	 * Validates and sets the year. Year will be set to 1000 if invalid (100% arbitrary decision)
	 * 
	 * @param year The year to be set
	 */
	public void setYear(int year){
		// perform domain validation. Lazy 4 digit check
		if(year >= 1000 && year <= 9999)
			this.year = year;
	}

	/**
	 * Returns the current year
	 * 
	 * @return this.year
	 */
	public int getYear(){
		return year;
	}

	/**
	 * Sets the date display format
	 * 
	 * @param format The new format
	 */
	public void setFormat(DateFormat format){
		// enums make domain validation trivial because you can
		// only use one of the provided values
		this.format = format;
	}

	/**
	 * Defines lexical ordering between two dates
	 * 
	 * @param dateArg The Date object to compare against "this" Date
	 * @return integer representing the lexical ordering
	 */
	public int compareTo(Date dateArg){
		if(	this.equals( dateArg ) )// use equals method
			return 0;				// they are equal

		// they aren't equal so figure out which one "comes first"
		if(	this.year  < dateArg.year ) return -1; // "this" has smaller year, comes first
		if( this.year  > dateArg.year )	return  1; // "this" has larger year, comes after
		if( this.month < dateArg.month) return -1; // "this" has smaller month, comes first
		if( this.month > dateArg.month) return  1; // "this" has larger month, comes after
		if( this.day   < dateArg.day ) 	return -1; // "this" has smaller day, comes first
										return  1; // "this" must have a larger day, comes after
	}

	// =============================== //
	// CONVENTIONALLY EXPECTED METHODS //
	// =============================== //

	// these methods are overridden versions of inherited methods (more on this later)
	// so there's no need for javadoc API spec

	public String toString(){
		if(format == DateFormat.SHORT_DATE)
			return month + "/" + day + "/" + year;
		else 
			return getMonthName() + " " + day + ", " + year;
	}

	public boolean equals(Date d){
		if(this == d) return true;			// identity check
		if(d == null) return false;			// null check

		return	this.day	== d.day	&&	// days should be the same
				this.month	== d.month	&&	// month should be the same
				this.year	== d.year;		// year should be the same
	}

	// ====================== //
	// PRIVATE HELPER METHODS //
	// ====================== //

	// no need for javadoc here as private methods generally
	// don't have API Spec associated with them
	// it could be argued that this method be marked public static
	private boolean isLeapYear(int y){
		return (y % 4 == 0 && (y % 100 != 0 || y % 400 == 0));
	}
}
