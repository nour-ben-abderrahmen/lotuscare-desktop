package Services;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
public class TwilioService {
    private static final String ACCOUNT_SID = "AC0f26a87bc35e437038bda8d0bc069986";
    private static final String AUTH_TOKEN = "dd6db9ec606a1d1191de649f33b9028a";
    private static final String FROM_NUMBER = "+15746525693";

    public static void sendSms(String toNumber, String messageText) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                        new PhoneNumber(toNumber),
                        new PhoneNumber(FROM_NUMBER),
                        messageText)
                .create();

        System.out.println("Message SID: " + message.getSid());
    }
}
