
package com.geotag.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendWelcomeEmail(String email, String name) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("Welcome to GeoTag App!");

        message.setText(
            "Hello " + name + ",\n\n" +
            "Welcome to GeoTag App!\n" +
            "Your registration was successful.\n\n" +
            "Thank you for joining us.\n\n" +
            "Regards,\nGeoTag Team"
        );

        mailSender.send(message);
    }

    public void sendLoginNotification(String email, String name) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("GeoTag Login Notification");

        message.setText(
            "Hello " + name + ",\n\n" +
            "You have successfully logged in to your GeoTag account.\n\n" +
            "Regards,\nGeoTag Team"
        );

        mailSender.send(message);
    }
}
