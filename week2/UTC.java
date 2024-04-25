// Gabriel Malone / CS165 / Summer 2024

// Import the correct Calendar class here
import java.util.Calendar;
import java.lang.Math;

public class UTC{

	// constants for raw time conversion
	// these must be static because they will be called from a "static context" => main
	
    static final int MILLISECONDS	= 1;
	static final int MIL_PER_SEC	= MILLISECONDS * 1000;
	static final int MIL_PER_MIN	= MIL_PER_SEC  * 60;
	static final int MIL_PER_HOUR	= MIL_PER_MIN  * 60;
	static final int MIL_PER_DAY	= MIL_PER_HOUR * 24;

	public static void main(String[] args) {
		
		// write Calendar code here. Research API for details
		
        long millis = System.currentTimeMillis();
		System.out.println("miliseconds since Epoch: " +  millis);
		Calendar rightNow = Calendar.getInstance();
        System.out.println("Java Calendar API Time: " + rightNow.get(Calendar.HOUR_OF_DAY) + ":" + rightNow.get(Calendar.MINUTE) + ":" + rightNow.get(Calendar.SECOND));

        // get the command line argument, convert to an integer
		
        int timeZoneAdjust = Integer.parseInt(args[0]);
        
        // Unix Epoch  // perform the math
            
        // days since the epoch
        double daysSinceEpoch  = (double)millis  / MIL_PER_DAY ;
            
            // the decimal value will be how far the current day has progressed (between 0 and 1)
            // convert the double to a string to get the decimal value alone 
            String daysSinceString   = daysSinceEpoch+"";
            // get the decimal value and assign it to a variable // convert the decimal string back to a double
            int    decimalSpotDays   = daysSinceString.indexOf(".");
            String currentDay        = daysSinceString.substring(decimalSpotDays, daysSinceString.length());
            double currentDayVal     = Double.parseDouble(currentDay);
            // convert the decimal value to current number of hours in today's day from the decimal value in the days calculated above
            double currentHours      = currentDayVal * 24;
            
            // get the decimal value from hours ^ and assign it to a variable 
            // this will give the minutes in the current hour
            // convert the decimal string back to a double
            
            String currentDayHoursString = currentHours+"";
            int    decimalSpotHours      = currentDayHoursString.indexOf(".");
            String currentDayMinutes     = currentDayHoursString.substring(decimalSpotHours, currentDayHoursString.length());
            double currentDayMinutesVal  = Double.parseDouble(currentDayMinutes);
            double currentMinutes        = currentDayMinutesVal * 60;

            // get the decimal value from minutes ^ and assign it to a variable 
            // this will give the seconds in the current minute
            // convert the decimal string back to a double
            
            String currentMinutesString  = currentMinutes+"";
            int    decimalSpotMinutes    = currentMinutesString.indexOf(".");
            String currentDaySeconds     = currentMinutesString.substring(decimalSpotMinutes, currentMinutesString.length());
            double currentDaySecondsVal  = Double.parseDouble(currentDaySeconds);
            double currentSeconds        = currentDaySecondsVal * 60;

        // output 
        System.out.printf("Current GMT Time: %.0f:%.0f:%.0f%n", Math.floor(currentHours), Math.floor(currentMinutes), Math.floor(currentSeconds));
        System.out.printf("Current Local Time: %.0f:%.0f:%.0f%n", Math.floor(currentHours + timeZoneAdjust), Math.floor(currentMinutes), Math.floor(currentSeconds));
	}
}   