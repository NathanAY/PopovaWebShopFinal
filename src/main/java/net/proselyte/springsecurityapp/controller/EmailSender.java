package net.proselyte.springsecurityapp.controller;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.util.Properties;

/**
 * Created by minsk on 7/11/2017.
 */
public class EmailSender {


    public void sendEmail(String toEmailAddress){
        try {

            String host = "smtp.yandex.ru";
            String from = "test41245@yandex.ru";
            String pass = "test412451";
            Properties props = System.getProperties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.user", from);
            props.put("mail.smtp.password", pass);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.debug", "true");

            Session session = Session.getInstance(props, new GMailAuthenticator(from, pass));
            MimeMessage message = new MimeMessage(session);
            Address fromAddress = new InternetAddress(from);
            Address toAddress = new InternetAddress(toEmailAddress);

            message.setFrom(fromAddress);
            message.setRecipient(Message.RecipientType.TO, toAddress);

            String key = getKey();
            message.setSubject("Testing JavaMail");
            message.setText("Verify email address. Ket: " + key);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            message.saveChanges();
            Transport.send(message);
            transport.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public String getKey(){
        String key = "";

        SecureRandom secureRandom = new SecureRandom();
        int low = 10000;
        int high = 1000000;
        int result = secureRandom.nextInt(high-low) + low;

        key += result;

        return key;
    }


    class GMailAuthenticator extends Authenticator {

        String user;
        String pw;

        public GMailAuthenticator(String username, String password) {
            super();
            this.user = username;
            this.pw = password;
        }

        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(user, pw);
        }
    }
}