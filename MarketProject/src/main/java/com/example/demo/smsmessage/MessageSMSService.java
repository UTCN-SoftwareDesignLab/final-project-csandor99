package com.example.demo.smsmessage;

import org.springframework.stereotype.Component;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import static com.example.demo.Constants.*;

@Component
public class MessageSMSService {
    public void send(MessageSMS sms) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(new PhoneNumber(sms.getReceiver()), new PhoneNumber(FROM_NUMBER), sms.getMessage()).create();
    }
}
