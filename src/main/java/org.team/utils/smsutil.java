pack  org.team.utils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Component;

@Component
public class SmsUtil {

    public static final String ACCOUNT_SID = "ACf96b2d0806b07514461e77780d9d50d1";
    public static final String AUTH_TOKEN = "a3344e3c6b330ac4ac651caaf2b0e9fe";
    public static final String TRIAL_NUMBER = "+19014457024";

    public static void main(String body, Long to) throws Exception {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        String toNumber = "+14033999999"; //+ to.toString();
        Mess mess = Message.creator(
                new PhoneNumber(toNumber), new PhoneNumber(TRIAL_NUMBER), body).create();

        System.out.println(message.getSid());
    }
}
