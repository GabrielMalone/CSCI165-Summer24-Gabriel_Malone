import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DateTest {

	@Test
	public void testDateCompareTo(){
		Date before = new Date(1, 1, 2024);
		Date after	= new Date(2, 1, 2024);

		// before is first. smaller month, same day, same year
		int order = before.compareTo(after);    // before is "this"
		assertTrue(order < 0);                  // should be negative
		order = after.compareTo(before);        // after is "this"
		assertTrue(order > 0);                  // should be positive

		// confirm equals behavior
		assertFalse(after.equals(before));		// should be false
		assertFalse(before.equals(after));		// should be false

		// they are equal
		before.setMonth(after.getMonth());		// make the month the same
		order = before.compareTo(after);		// before is "this" 
		assertTrue(order == 0);					// should be 0
		order = after.compareTo(before);		// after is "this"
		assertTrue(order == 0);					// should be 0

		// confirm equals behavior
		assertTrue(after.equals(before));		// equals should return true
		assertTrue(before.equals(after));		// should be true

		// same month, same year, after has larger day
		before.setMonth( after.getMonth() );	// make months the same
		after.setDay( before.getDay() + 1 );	// make after have larger day
		order = before.compareTo(after);		// before is "this"
		assertTrue(order < 0);					// should be negative
		order = after.compareTo(before);		// after is "this"
		assertTrue(order > 0);					// should be positive

		// confirm equals behavior
		assertFalse(after.equals(before));		// should be false
		assertFalse(before.equals(after));		// should be false

		// same month, same day, after has larger year
		before.setMonth( after.getMonth() );	// make months the same
		after.setDay( before.getDay() );		// make days the same
		after.setYear( before.getYear() +1);	// make after have a larger year
		order = before.compareTo(after);		// before is "this"
		assertTrue(order < 0);					// should be negative
		order = after.compareTo(before);		// after is "this"
		assertTrue(order > 0);					// should be positive

		// confirm equals behavior
		assertFalse(after.equals(before));		// should be false
		assertFalse(before.equals(after));		// should be false
	}
}
