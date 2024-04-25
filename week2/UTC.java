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
		// byte timeZoneAdjust = Byte.parseByte(args[0]);
        
        byte timeZoneAdjust = - 4;
        String AM_PM = "AM";
        
        // convert milliseconds since Epoch to today's time in milliseconds
        
        long curHourInMil  = millis       %  MIL_PER_DAY;
        long curMinInMil   = curHourInMil %  MIL_PER_HOUR;
        long curSecInMil   = curHourInMil %  MIL_PER_MIN; 
        
        // convert today's milliseconds into readable time
        
        int currentHour = (int)curHourInMil / MIL_PER_HOUR;
        int currentMin  = (int)curMinInMil  / MIL_PER_MIN;
        int currentSec  = (int)curSecInMil  / MIL_PER_SEC;
        
        // output for UTC
        
        System.out.printf("GMT Time: %d:%d:%d%n",currentHour,currentMin,currentSec);
        
        // conditionals for if timezone adjusts put you into previous or next day
        
        if (currentHour + timeZoneAdjust > 24)
            currentHour -= 24;
        else if (currentHour + timeZoneAdjust < 0)
            currentHour += 24;
       
        // conditionals for 12 hour clock adjust with AM/PM
        
        if (currentHour + timeZoneAdjust >= 12){
            AM_PM = "PM";
            if (currentHour + timeZoneAdjust > 12){
                currentHour -= 12;
            }  
        }
        else
            AM_PM = "AM";
       
        // current locations time output
        
        System.out.printf("Local Time: %d:%d:%d %S%n", currentHour + timeZoneAdjust, currentMin, currentSec, AM_PM);

	}
}   