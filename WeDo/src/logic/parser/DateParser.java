/**
 * 
 */
package logic.parser;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import logic.utility.AbstractTask;
import logic.utility.StringHandler;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * @author Kuan Tien Long This class help filter our the date for natty parser.
 */
public class DateParser {

    private String wordUsed;
    private String wordRemaining;
    private List<Date> dateList;
    private boolean isTimeSet;

    /**
     * <p> The source will be parsed to see if it contains date.
     * @param source the String to be parsed
     * @return if source contains valid date 
     */
    public boolean tryParse(String source) {

        if (source == null) {
            return false;
        }

        source = source.trim();

        if (source.isEmpty()) {
            return false;
        }

        source = DateStringMassager.massageData(source);

        Parser nattyParser = new Parser();
        List<DateGroup> dateGroups = nattyParser.parse(source);

        if (dateAvailable(dateGroups)) {
            
            
            String dateWordUsed = DateStringMassager.removeWordDelimiter(getDateWordUsed(source, dateGroups));
            
            source = DateStringMassager.removeDigitDelimiters(source);
            source = DateStringMassager.removeWordDelimiter(source);
            
            String dateConnector = DateStringMassager.getDateConnector(source, dateWordUsed);
            wordUsed =  dateConnector + dateWordUsed;
            
            System.out.println("Total wordUsed = " + wordUsed);


            wordRemaining = StringHandler.removeFirstMatched(source, wordUsed);
            
            dateList = getDateList(dateGroups);
            
            try {
                dateList = parseDateBeforeEpochYear(dateWordUsed, dateList);
            } catch (ParseException e) {
                return false;
                }
         
            
            isTimeSet = isTimeInferred(dateGroups);
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Handles parsing of year before epoch start year (1970), year is parsed by utilizing LocalDateTime parser
     * @param source the String which consist of the date to parse
     * @throws ParseException if source consist of invalid input
     * @return List<Date> which contains all the date information
     */
    private List<Date> parseDateBeforeEpochYear(String source, List<Date> dateList) throws ParseException
    {
        final int INITIAL_INDEX = 0;
        final int EPOCH_START_YEAR = 1970;
        final int yearGroup = 1;
        final int monthGroup = 2;
        final int dayGroup = 3;
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("u/M/d"); 
        String yyyymmddRegex = "(\\d\\d\\d\\d)[/-](0?[1-9]|1[012])[/-](3[01]|[012]?[0-9])";
        
         
        Pattern pattern = Pattern.compile(yyyymmddRegex);
        Matcher matcher = pattern.matcher(source);
        int dateIndex = INITIAL_INDEX;
        
        
        while (matcher.find()) 
        {
                int year = Integer.parseInt(matcher.group(1));
                if(year >= EPOCH_START_YEAR)
                {
                    continue;
                }
                
                Calendar calendar = convertDateToCalendar(dateList.get(dateIndex));
            
                LocalDate localDate = LocalDate.parse(matcher.group(yearGroup) + "/" + matcher.group(monthGroup) + "/" + matcher.group(dayGroup), formatter);
                LocalTime localTime = getLocalTime(calendar);
                LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
                
                Date parseResult = convertLocalDateToDate(localDateTime);
                dateList.remove(dateIndex);
                dateList.add(dateIndex, parseResult);
                dateIndex++;
 
        }
        
        sortDateList(dateList);  
        
        return dateList;
    }

    /**
     * Convert Calendar timing to LocalTime
     * @param calendar the calendar which stores the time 
     * @return LocalTime which consist of HH:MM:SS
     */
    private LocalTime getLocalTime(Calendar calendar) {
        return LocalTime.of(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND), calendar.get(Calendar.MILLISECOND));
    }

    /**
     * Convert java.util.date to calendar
     * @param date the date to be converted to calendar
     * @return calendar which consist of the date converted
     */
    private Calendar convertDateToCalendar(Date date)
    {
        Calendar calendar = GregorianCalendar.getInstance(); 
        calendar.setTime(date);   
        return calendar;
    }
    

    
    private Date convertLocalDateToDate(LocalDateTime localDate)
    {
        return Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant());
          // return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    
    public int getNumberOfDates() {
        return dateList.size();
    }

    public boolean isTimeSet() {
        return isTimeSet;
    }

    public LocalDate getStartDate() {
        final int START_DATE_INDEX = 0;
        return dateToLocalDate(dateList.get(START_DATE_INDEX));
    }
    
    public Date getMydate() 
    {
        return dateList.get(0);
    }

    public LocalDate getEndDate() {
        final int END_DATE_INDEX = dateList.size() - 1;
        final int START_DATE_INDEX = 0;

        if (END_DATE_INDEX == START_DATE_INDEX) {
            return getStartDate();

        } else {
            return dateToLocalDate(dateList.get(END_DATE_INDEX));
        }
    }

    public LocalTime getStartTime() {
        final int START_TIME_INDEX = 0;

        if (isTimeSet) {
            return dateToLocalTime(dateList.get(START_TIME_INDEX));
        } else {
            return AbstractTask.TIME_NOT_SET;
        }

    }

    public LocalTime getEndTime() {
        final int END_TIME_INDEX = dateList.size() - 1;
        final int START_TIME_INDEX = 0;

        
        if (END_TIME_INDEX == START_TIME_INDEX) {
            return getStartTime();
        }

        if (isTimeSet) {
            return dateToLocalTime(dateList.get(END_TIME_INDEX));
        } else {
            return AbstractTask.TIME_NOT_SET;
        }
    }

    private List<Date> getDateList(List<DateGroup> dateGroup) {

        final int START_INDEX = 0;
        List<Date> dateList = dateGroup.get(START_INDEX).getDates();
        sortDateList(dateList);
        return dateList;
    }
    
    private void sortDateList(List<Date> dateList)
    {
        Collections.sort(dateList);
    }

    private boolean dateAvailable(List<DateGroup> dateGroups) {
        return dateGroups.size() > 0;
    }

    private boolean isTimeInferred(List<DateGroup> dateGroups) {
        return !dateGroups.get(0).isTimeInferred();
    }

    private String getDateWordUsed(String source, List<DateGroup> dateGroups) {
        int startPosition = source.length();
        int endPosition = 0;
        for (DateGroup dateGroup : dateGroups) {
            int position = dateGroup.getPosition();
            int length = dateGroup.getText().length();
            startPosition = Math.min(startPosition, position);
            endPosition = Math.max(position + length, endPosition);
        }

        String dateText = source.substring(startPosition, endPosition);

        System.out.println("wordUsed for date = " + dateText);
        return dateText;
    }

    public LocalDate dateToLocalDate(Date date) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
                .toLocalDate();
    }

    public LocalTime dateToLocalTime(Date time) {
        Instant instant = Instant.ofEpochMilli(time.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
                .toLocalTime();
    }

    /**
     * @return the wordUsed
     */
    public String getWordUsed() {
        return wordUsed;
    }

    /**
     * @param wordUsed
     *            the wordUsed to set
     */
    public void setWordUsed(String wordUsed) {
        this.wordUsed = wordUsed;
    }

    /**
     * @return the wordRemaining
     */
    public String getWordRemaining() {
        return wordRemaining;
    }

}
