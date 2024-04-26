// Gabriel Malone / CS165 / Summer 2024

// Import the correct Calendar class here
import java.util.Calendar;

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
		
        int timeZoneAdjust = Byte.parseByte(args[0]);
       
        int milAdjust = timeZoneAdjust * MIL_PER_HOUR;

    // convert milliseconds since Epoch to today's time in milliseconds
        
        long curHourInMil  = millis       %  MIL_PER_DAY;
        long curMinInMil   = curHourInMil %  MIL_PER_HOUR;
        long curSecInMil   = curHourInMil %  MIL_PER_MIN; 
        
        // convert today's milliseconds into readable time
        
        int currentHour = (int)curHourInMil / MIL_PER_HOUR;
        int currentMin  = (int)curMinInMil  / MIL_PER_MIN;
        int currentSec  = (int)curSecInMil  / MIL_PER_SEC;

    // redo above for timezone adjusted UTC

        long curHourInMilTimeAdj  = (millis + milAdjust) %  MIL_PER_DAY;
        long curMinInMilTimeAdj  = curHourInMil %  MIL_PER_HOUR;
        long curSecInMilTimeAdj   = curHourInMil %  MIL_PER_MIN; 
        
        // convert timezoned adjusted milliseconds into readable time
        
        int currentHourTimeAdj = (int)curHourInMilTimeAdj / MIL_PER_HOUR;
        int currentMinTimeAdj  = (int)curMinInMilTimeAdj  / MIL_PER_MIN;
        int currentSecTimeAdj  = (int)curSecInMilTimeAdj  / MIL_PER_SEC;

    // output for UTC
        
        System.out.printf("GMT Time:  %02d:%02d:%02d%n",currentHour,currentMin,currentSec);
        
    // conditionals for 12 hour clock adjust with AM/PM
        // initialize AM_PM var
        String AM_PM = "AM";
        if (currentHourTimeAdj >= 12){
            AM_PM = "PM";
            if (currentHourTimeAdj > 12){
                currentHourTimeAdj -= 12;
            }  
        }
        else
            AM_PM = "AM";
       
     // output for current time
        
        System.out.printf("Local Time: %d:%02d:%02d %S%n", currentHourTimeAdj, currentMinTimeAdj, currentSecTimeAdj, AM_PM);

	}
}   