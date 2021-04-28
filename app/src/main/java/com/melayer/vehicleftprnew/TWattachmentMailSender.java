package com.melayer.vehicleftprnew;

/**
 * Created by twtech on 5/7/17.
 */

import android.os.StrictMode;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class TWattachmentMailSender extends javax.mail.Authenticator {
    private String user1;
    private String password1;
    private String mailhost1;
    private String smtpPort1;
    private Session session;

    static {

        Security.addProvider(new JSSEProvider());

    }

    public TWattachmentMailSender(String user, String password, String mailhost, String smtpPort) {
        this.user1 = user;
        this.password1 = password;
        this.mailhost1 = mailhost;
        this.smtpPort1 = smtpPort;
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.user", user);
        props.put("mail.smtp.password", password);
        props.put("mail.store.protocol", "smtp");
        props.put("mail.smtp.host", mailhost1);
        props.put("mail.smtp.port", smtpPort1);
        session = Session.getDefaultInstance(props, this);
        Log.v("User", "" + user + " _ " + user1);
        Log.v("Password", "" + password + " _ " + password1);
        Log.d("SmtpHost", "" + mailhost + " _ " + mailhost1);
        Log.d("SmtpPort", "" + smtpPort + " _ " + smtpPort1);
    }


    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user1, password1);


    }

    public synchronized void sendMail1(String subject, String body, String filename, String sender, String recipients) throws Exception {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            MimeMessage message = new MimeMessage(session);
            DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));
            message.setSender(new InternetAddress(sender));
            message.setSubject(subject);
            message.setDataHandler(handler);


            //set the body part
            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText("" + body);

            //set the attachment
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
            DataSource source = new FileDataSource(filename);
          /*  messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName(filename);
*/
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart1);
//            multipart.addBodyPart(messageBodyPart2);

            message.setContent(multipart);

            if (recipients.indexOf(',') > 0)
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
            else
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
            Transport.send(message);
            Log.v("SendMail report", "SendMail called");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ByteArrayDataSource implements DataSource {
        private byte[] data;
        private String type;

        public ByteArrayDataSource(byte[] data, String type) {
            super();
            this.data = data;
            this.type = type;
        }

        public ByteArrayDataSource(byte[] data) {
            super();
            this.data = data;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContentType() {
            if (type == null)
                return "application/octet-stream";
            else
                return type;
        }

        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(data);
        }

        public String getName() {
            return "ByteArrayDataSource";
        }

        public OutputStream getOutputStream() throws IOException {
            throw new IOException("Not Supported");
        }
    }
}