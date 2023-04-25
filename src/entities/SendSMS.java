package entities;

import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


public class SendSMS {
    public static final String ACCOUNT_SID = "AC5284c58259a6215762156626499fb03f";
    public static final String AUTH_TOKEN = "cacea84af27b64e954cb9626b4dd0b6a";

    public static void sendSMS(Categorie a) {
        Twilio.init("AC5284c58259a6215762156626499fb03f", "cacea84af27b64e954cb9626b4dd0b6a");

        String messageBody = "Nouvelle categorie ajouté : " + a.getNom_cat()+ ", avec le code  "+ a.getCode_cat();
        Message message = Message.creator(
                new PhoneNumber("+21651899808"),
                new PhoneNumber("+17402174435"),
                messageBody
        ).create();

        System.out.println(message.getSid());
    }
}
