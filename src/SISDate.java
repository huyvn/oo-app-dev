import java.util.*;
import java.text.*;

/**
 * The SISDate class contains utility methods to facilitate the handling of Date objects
 */
public class SISDate {
    private Date dateTime;

    /**
     * Default constructor for SISDate class
     * SISDate object is set to current date and time
     */
    public SISDate() {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(today);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        dateTime = calendar.getTime();
    }

    /**
     * Specific constructor for SISDate class that
     * creates a SISDate object by converting date represented in String format
     *
     * @param strDateTime date and time representation in "dd/MM/yyyy HH:mm" (24 hr format))
     * @throws ParseException when the format is not valid (valid format : "dd/MM/yyyy HH:mm").
     */
    public SISDate(String strDateTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        dateTime = sdf.parse(strDateTime);
    }

    /**
     * @return Date object representation of SISDate
     */
    private Date getDateTime() {
        return dateTime;
    }

    /**
     * Change SISDate to the specified date/time
     *
     * @param dateTime
     */
    private void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Comparing if this SISDate object is equal to another SISDate object
     *
     * @param anotherSISDate another SISDate object for comparison
     * @return boolean
     */
    public boolean equals(SISDate anotherSISDate) {
        return this.dateTime.equals(anotherSISDate.getDateTime());
    }

    /**
     * Comparing if this SISDate object occurs at a later date/time
     * than another SISDate object
     *
     * @param anotherSISDate another SISDate object for comparison
     * @return boolean
     */
    public boolean after(SISDate anotherSISDate) {
        return this.dateTime.after(anotherSISDate.getDateTime());
    }

    /**
     * Comparing if this SISDate object occurs at an earlier date/time
     * than another SISDate object
     *
     * @param anotherSISDate another SISDate object for comparison
     * @return boolean
     */
    public boolean before(SISDate anotherSISDate) {
        return this.dateTime.before(anotherSISDate.getDateTime());
    }

    /**
     * Computes date and time resulting from the addition of given number of hours to the current time
     *
     * @param hrsToBeAdded number of hours to be added to this current SISDate object
     * @return the end date and time of the booking
     */
    public SISDate addHours(int hrsToBeAdded) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(dateTime);
        try {
            calendar.add(Calendar.HOUR_OF_DAY, hrsToBeAdded);
        } catch (IllegalArgumentException e) {
            // todo
            // log to file the error
           /* AppLogger appLogger = new AppLogger();
            String message = "System error. Details: " + e.toString();
            appLogger.log("ERROR", message);*/
        }
        SISDate resultingDate = new SISDate();
        resultingDate.setDateTime(calendar.getTime());
        return resultingDate;
    }

    /**
     * Returns the textual representation of a SISDate object
     * in "dd/MM/yyyy HH:mm" format
     *
     * @return the textual representation of SISDate object
     */
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        return sdf.format(dateTime);
    }
}
