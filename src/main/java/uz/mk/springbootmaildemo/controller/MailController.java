package uz.mk.springbootmaildemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.mk.springbootmaildemo.payload.ApiResponse;
import uz.mk.springbootmaildemo.service.MailService;

@RestController
@RequestMapping("/api/mail")
public class MailController {
    @Autowired
    MailService mailService;

    @GetMapping("/sendText")
    public HttpEntity<?> sendText(@RequestParam String email) {
        ApiResponse apiResponse = mailService.sentText(email);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @GetMapping("/sendHtml")
    public HttpEntity<?> sendHtml(@RequestParam String email) {
        ApiResponse apiResponse = mailService.sendHtml(email);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @GetMapping("/activate")
    public HttpEntity<?> activateFromMail(@RequestParam String code) {
        return ResponseEntity.ok(code);
    }


    @GetMapping("/sendFile")
    public HttpEntity<?> sendFile(@RequestParam String email) {
        ApiResponse apiResponse = mailService.sendFile(email);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }
}
