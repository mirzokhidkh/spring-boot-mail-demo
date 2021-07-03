package uz.mk.springbootmaildemo.service;


import freemarker.ext.servlet.FreemarkerServlet;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import uz.mk.springbootmaildemo.payload.ApiResponse;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailService {

    @Autowired
    JavaMailSender sender;

    @Autowired
    Configuration configuration;

    public ApiResponse sentText(String sendToEmail) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setText("Bu xabar Spring Boot Application test qilib jo'natilgan");
            simpleMailMessage.setTo(sendToEmail);
            simpleMailMessage.setSubject("Salom Hayr!!!");
            sender.send(simpleMailMessage);
            return new ApiResponse("OK", true);

        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("Error", false);
        }
    }

    public ApiResponse sendHtml(String email) {
        try {
            Map<String, Object> model = new HashMap<>();
            model.put("email", "mirzohidkhasanov13@gmail.com");
            model.put("fullName", "Xasanov Mirzohid");
            model.put("code", "111111");
            model.put("phoneNumber", "+998999354112");
            MimeMessage mimeMessage = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            Template template = configuration.getTemplate("email-template.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            helper.setTo(email );
            helper.setSubject("Sending Email From Myself");
            helper.setText(html, true);
            sender.send(mimeMessage);

            return new ApiResponse("OK", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ApiResponse("Error", false);
    }

    public ApiResponse sendFile(String email) {

        try {
            MimeMessage mimeMessage = sender.createMimeMessage();
            MimeMessageHelper helper;
            helper= new MimeMessageHelper(mimeMessage,true);
            helper.setTo(email);
            helper.setSubject("Sending Email From Myself");
            helper.setText("Hello. This is a file that is sent by Spring Boot application. ");
            String name="sun.jpeg";
            File file = ResourceUtils.getFile("classpath:static/appFiles/" + name);
            InputStream in = new FileInputStream(file);
            byte[] bdata = FileCopyUtils.copyToByteArray(in);
            ByteArrayDataSource attachment=new ByteArrayDataSource(bdata,"application/octet-stream");
            helper.addAttachment(name,attachment);
            Thread thread = new Thread(){
                public void run(){
                    sender.send(mimeMessage);
                }
            };
            thread.start();
            return new ApiResponse("Sended",true);
        } catch (Exception e) {
            return new ApiResponse("Error",false);

        }
    }
}
