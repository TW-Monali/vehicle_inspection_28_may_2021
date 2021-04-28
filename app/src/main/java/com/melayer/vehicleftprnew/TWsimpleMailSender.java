package com.melayer.vehicleftprnew;

import android.content.Context;
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

/**
 * Created by twteh on 25/10/17.
 */

public class TWsimpleMailSender extends javax.mail.Authenticator {

    private String unitId1;
    private String password1;
    private String mailhost1;
    private String smtpPort1;
    private Session session;
    Context mContext;
    String Tag="TWsimpleMailSender";

    static {
        Security.addProvider(new JSSEProvider());
    }

    public TWsimpleMailSender(Context context, String unitID, String password, String mailhost, String smtpPort) {
        this.mContext = context;
        this.unitId1 = unitID;
        this.password1 = password;
        this.mailhost1 = mailhost;
        this.smtpPort1 = smtpPort;

        // new MyLogger().storeMassage("TWsimpleMailSender", "going to set Properties");
        // new DatabaseOperations(mContext).storeRegularLog("1", "TWsimpleMailSender method called for going to set Properties");

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.user", unitID);
        props.put("mail.smtp.password", password);
        props.put("mail.store.protocol", "smtp");
        props.put("mail.smtp.host", mailhost1);
        props.put("mail.smtp.port", smtpPort1);

        //===
        props.put("mail.smtp.connectiontimeout", 15 * 1000);
        props.put("mail.smtp.timeout", 15 * 1000);

        session = Session.getInstance(props, this);

        //new MyLogger().storeMassage("TWsimpleMailSender", "Properties set successfully and start Session...");
        //new DatabaseOperations(mContext).storeRegularLog("2", "TWsimpleMailSender method Properties set successfully and start Session");

        Log.v("UnitID", "" + unitID + " _ " + unitId1);
        Log.e("UnitID", "" + unitID + " _ " + unitId1);
        Log.v("Password", "" + password + " _ " + password1);
        Log.d("SmtpHost", "" + mailhost + " _ " + mailhost1);
        Log.d("SmtpPort", "" + smtpPort + " _ " + smtpPort1);
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(unitId1, password1);
    }

    public synchronized boolean sendMail(String subject, String body, String sender, String recipients) throws Exception {

        boolean flag = false;

        Log.e("to Mail ID"," : "+recipients);

     //   new MyLogger().storeMassage(Tag+ ": to mail Id ","HArCoded : "+recipients);
      //  new MyLogger().storeMassage(Tag+ " smtpHost ",": "+mailhost1);

       // new MyLogger().storeMassage(Tag+" : method called", " recipients- " + recipients);
        //new DatabaseOperations(mContext).storeRegularLog("1", "TWsimpleMailSender sendMail method called");
        Transport transport = session.getTransport("smtp");

        try {
            MimeMessage message = new MimeMessage(session);
            DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));
            message.setSender(new InternetAddress(sender));
            message.setSubject(subject);
            message.setDataHandler(handler);

            if (recipients.indexOf(',') > 0)
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
            else
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
            //Log.e("Error",String new InternetAddress(recipients);
          //  new MyLogger().storeMassage(Tag+" : before Transport.send method", "recipients- " + recipients);
            //  new DatabaseOperations(mContext).storeRegularLog("2","TWsimpleMailSender before Transport.send method");

            transport.connect();
            transport.send(message);
            //transport.close();
            flag = true;

          //  new MyLogger().storeMassage(Tag+" : TWsimpleMailSender after Transport.send method", "recipients- " + recipients);
            //new DatabaseOperations(mContext).storeRegularLog("2", "TWsimpleMailSender after Transport.send method");

        } catch (Exception e) {
            new MyLogger().storeMassage(Tag+" : Transport.send", "Exception-" + e.getMessage());
            //new DatabaseOperations(mContext).storeExceptionLog("3", "Exception in TWsimpleMailSender Transport.send in TWsimpleMailSender mailsend method");

           // boolean flag = mTWsimpleMailSender.sendMail(UnitID, returnData, UnitID, ToMailId);



        } finally {
            transport.close();
        }

        return flag;
    }

    public synchronized boolean sendMail(String subject, String body, String sender, String recipients, String attachmentPath) throws Exception {

        boolean flag = false;

       // new MyLogger().storeMassage(Tag+" :  method called for Attachment", "recipients- " + recipients);
        //new DatabaseOperations(mContext).storeRegularLog("1", "TWsimpleMailSender sendMail method called");
        Transport transport = session.getTransport("smtp");

     //  try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(sender));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipients));

            // Set Subject: header field
            message.setSubject(subject);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText(body);

            // Create a multipar message
            Multipart multipart = new MimeMultipart("mixed");

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = attachmentPath;
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            Log.e("filename",filename);
            multipart.addBodyPart(messageBodyPart);
            Log.e("Message Body", String.valueOf(messageBodyPart));

            // Send the complete message parts
            message.setContent(multipart);
            Log.e("multipart", String.valueOf(multipart));

            // Send message
            Transport.send(message);
            flag=true;
            Log.e("transport", String.valueOf(flag));

            Log.e("Sent msg successfully","Success");

       // } catch (MessagingException e) {
           // Log.e("Exception",e.getMessage());
          //  new MyLogger().storeMassage(Tag+" : Transport.send", "Exception-" + e.getMessage());

         //   throw new RuntimeException(e);

       // }
      // finally {
            transport.close();
      //  }

        return flag;
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
