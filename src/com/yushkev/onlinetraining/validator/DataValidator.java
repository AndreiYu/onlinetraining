package com.yushkev.onlinetraining.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

import javax.servlet.http.Part;

import com.yushkev.onlinetraining.constant.GeneralConstant;
import com.yushkev.onlinetraining.service.AvatarImgService;

public class DataValidator {

    public static final String EMAIL_REGEX = "[\\w\\.-_]+@\\w+\\.[\\.\\w]+";
    public static final String LOGIN_REGEX = "[_A-Za-z0-9-]{3,20}";
    public static final String PASSWORD_REGEX = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}"; //a digit must occur at least once, a lower case letter must occur at least once, an upper case letter must occur at least once, no whitespace allowed in the entire string, at least 8 to 20 items
    public static final String MONEY_REGEX = "[\\d]{1,8}\\.*[\\d]{1,2}";
    public static final String MARK_REGEX = "[0-10]";
    public static final String NAME_REGEX = "[а-яА-ЯёЁa-zA-Z]{2,40}";
    public static final String AVATAR_IMG_REGEX = ".*\\.(jpg|JPG|jpeg|JPEG|gif|GIF|png|PNG)$"; 
    public static final String COURSE_TITLE_REGEX = "(^[а-яА-ЯёЁa-zA-Z0-9].*){1,250}";
    
    
    public static final String DATE_SIMPLEFORMAT = "dd/MM/yyyy";

    
//    public static final String URL_QUERY_REGEX = "(\\?[a-z+&\\$_.-][a-z0-9;:@&%=+\\/\\$_.-]*)?";
    
    
    public static boolean isValidToRegex(String input, String regex) {
        return (input != null) && input.matches(regex);
    }
    
    public static Optional<GregorianCalendar> checkDate(String data) {
    	GregorianCalendar result = null;
		if (data!= null && !data.isEmpty()) { 
			SimpleDateFormat datePattern = new SimpleDateFormat(DATE_SIMPLEFORMAT); 
			datePattern.setLenient(false);
				try {
					Date dateParsed = datePattern.parse(data);
					result = (GregorianCalendar) Calendar.getInstance();
					result.setTime(dateParsed);
					return Optional.of(result);
					} 
					catch (ParseException ex) {
						result = null;
					}
		}
    return Optional.ofNullable(result);
    }

    public static boolean isImageValid (Part part) {
    	return (isValidToRegex(AvatarImgService.getImageExtention(part), DataValidator.AVATAR_IMG_REGEX) && 
				DataValidator.isImageSizeValid(part));
    }
    
    private static boolean isImageSizeValid(Part part) {
    	return part != null && (part.getSize() > 0 && part.getSize() <= GeneralConstant.AVATAR_IMG_MAXSIZE);
    }
    
    public static boolean isDateAfterPresent(String data) {
    	Optional<GregorianCalendar> date = checkDate(data);
    	return (!date.isPresent() || date.isPresent() && (date.get().getTimeInMillis() >= 
    			Instant.now().getEpochSecond() * 1000));
    }
    
    public static boolean isEndDateAfterStartDate(String startDate, String endDate) {
    	Optional<GregorianCalendar> startD = checkDate(startDate);
    	Optional<GregorianCalendar> endD = checkDate(endDate);
    	return ((!startD.isPresent() && !endD.isPresent())  || //both absent = valid
    			(startD.isPresent() && endD.isPresent() &&  //if both present - check dates, else - false
    			(checkDate(startDate).get().getTimeInMillis() <= checkDate(endDate).get().getTimeInMillis())));
    }
    
}
