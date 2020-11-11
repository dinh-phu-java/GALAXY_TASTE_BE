//package com.galaxytaste.controller;
//
//import com.galaxytaste.service.EmailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class EmailTest {
//    @Autowired
//    private EmailService emailService;
//
//    @GetMapping("/send-mail")
//    public ResponseEntity<String> sendingEmail(){
//        this.emailService.sendSimpleMessage("ndp17hp@gmail.com", "test email subject","test email content");
//        return new ResponseEntity<>("Test Thanh cong", HttpStatus.OK);
//    }
//}
