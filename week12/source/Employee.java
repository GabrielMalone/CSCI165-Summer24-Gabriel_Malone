/*
 * File:	Employee.java
 * Author:	Ken Whitener
 * Date:	4/15/2024
 * Modified by Gabriel Malone
 * 
 * Description: This class represents an employee with a hire date, id number, and department.
 * 				Employee is a subclass of Person.
 */


public class Employee extends Person {

	private Date	hireDate;
	private int		id;
	private String	department;

	// no argument
	public Employee() {
		// no defaults were previously set 
		// with the no arguments constructor
		// write methods to set defaults
		super();
		getAndSetHireDateInfo();
		getAndSetIDInfo();
		getAndSetDepartmentInfo();
	}

	// overloaded constructor
	/**
	 * 
	 * @param p
	 * @param hired
	 * @param id
	 * @param dept
	 */
	public Employee(Person p, Date hired, int id, String dept) {
		super(p);			// call to super class copy constructor
		setHireDate(hired);	// sets will protect privacy
		setId(id);
		setDepartment(dept);
	}

	// overloaded constructor
	/**
	 * 
	 * @param first
	 * @param last
	 */
	public Employee(String first, String last){
		super(first, last);
		getAndSetHireDateInfo();
		getAndSetIDInfo();
		getAndSetDepartmentInfo();
	}

	// copy constructor
	/**
	 * 
	 * @param toCopy
	 */
	public Employee(Employee toCopy){
		this(toCopy, toCopy.hireDate, toCopy.id, toCopy.department);
	}

	/**
	 * 
	 * @return the date the Employee was hired
	 */
	public Date getHireDate() {
		return new Date(hireDate);			// privacy protection
	}

	/**
	 * 
	 * @param hireDate
	 */
	public void setHireDate(Date hireDate) {
		this.hireDate = new Date(hireDate); // privacy protection
	}

	/**
	 * 
	 * @return The Employee's ID number
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return The Employee's Department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * 
	 * @param department
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	@Override
	public String toString() {
		String space = " ";
		return super.toString() + "\n" + space.repeat(21) + "Hired: " + hireDate + "\n" + space.repeat(21) + "ID: " + id + "\n"+ space.repeat(21) + "Dept: " + department;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)					return true;	// idenitity check
		if (obj == null)					return false;	// null check
		if (getClass() != obj.getClass())	return false;	// origin check 
		if (!super.equals(obj))				return false;	// super class check (inheritance)

		Employee other = (Employee) obj;					// downcast

		if (hireDate == null){								// date hired
			if (other.hireDate != null)		return false;
		}
		else if (!hireDate.equals(other.hireDate))			// calls Date.equals (composition)
			return false;

		if (id != other.id)					return false;	// id (primitive)

		return true;	// everything is equal
	}

	/**
	 * Method to set hire date for employees when hire date not yet provided
	 */
	private void getAndSetHireDateInfo(){
		if (this.hireDate == null){
			this.hireDate = new Date(1,2,1111);
			/*
			System.out.println("please provide a hire date for this employee:");
			String hiredate = Driver.scanner.nextLine();
			String [] hiredateArray = hiredate.split("/");
			// get the date info from string
			// split into array at  "/"
			// iterate through array and convert to int
			// pass those ints as args for new Date obj.s
			int [] hiredateIntArray = new int[3];
			int index = 0;
			for (String dateInfo : hiredateArray){
				int data = Integer.valueOf(dateInfo);
				hiredateIntArray[index] = data;
				index ++;
			}
			int month 	= hiredateIntArray[0];
			int day 	= hiredateIntArray[1];
			int year 	= hiredateIntArray[2];
			this.hireDate = new Date(month, day, year);
			*/
			
		}
	}
	/**
	 * Method to set an ID for an employee when ID not yet provided
	 */
	private void getAndSetIDInfo(){
		if (this.id == 0){
			this.id = 0000;
			/*
			System.out.println("please provide an ID for this employee");
			String ID = Driver.scanner.nextLine();
			int intID = Integer.valueOf(ID);
			this.id = intID;
			*/
		}
	}

	/**
	 * Method to set department info for an employee when not yet provided
	 */
	private void getAndSetDepartmentInfo(){
		if (this.department == null){
			this.department = "UNKNOWN DEPARTMENT";
			/*
			System.out.println("please provide a department for this employee");
			String department = Driver.scanner.nextLine();
			this.department = department;
			*/
		}
	}
}
