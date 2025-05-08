package org.libraryapp.util.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSender {
    private final JavaMailSender mailSender;

    public void   sendEmail (String setTo, String subject, String text){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(setTo);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

}
