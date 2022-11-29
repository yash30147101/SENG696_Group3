pack  org.team.utils;

import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

@Component
public class CommonUtils {

    public static String replaceText(String text, Map<String, String> textToAndWithReplace) throws NullPointerException {
        text = (text != null ? text : "");
        Iterator<String> itr = textToAndWithReplace.keySet().iterator();
        while (itr.hasNext()) {
            String regex = itr.next();
            if ((textToAndWithReplace.get(regex) != null) && (textToAndWithReplace.get(regex).contains("$"))) {
                String changeValue = textToAndWithReplace.get(regex);
                regex = regex.replaceAll("\\\\", "");
                text = text.replace(regex, changeValue);
            } else {
                text = text.replaceAll(regex, textToAndWithReplace.get(regex));
            }
        }
        return text;
    }


    public static Date addHoursToJavaUtilDate(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }
}
