//package com.untildawn.controllers.PreGameControllers;
//
//import jakarta.mail.internet.InternetAddress;
//import jakarta.mail.internet.MimeMessage;
//
//import java.net.PasswordAuthentication;
//import java.util.Properties;
//
//import jakarta.mail.*;
//
//public class EmailSender {
//    public static boolean sendEmail(String toEmail, String body) {
//        final String fromEmail = "stardewvalley.team@gmail.com"; // TODO: sender email address
//        final String password = "apriqtbbjwzezjxa"; // TODO: app password
//
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");
//
//        Session session = Session.getInstance(props, new Authenticator() {
//            protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
//                return new jakarta.mail.PasswordAuthentication(fromEmail, password);
//            }
//        });
//
//        try {
//            Message msg = new MimeMessage(session);
//            msg.setFrom(new InternetAddress(fromEmail));
//            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
//            msg.setSubject("App Notification");
//            msg.setText(body);
//            Transport.send(msg);
//            System.out.println("Email sent successfully to " + toEmail);
//            return true;
//        } catch (MessagingException e) {
//            return false;
//        }
//    }
//}
