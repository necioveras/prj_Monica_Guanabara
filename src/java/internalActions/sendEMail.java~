// Internal action code for project prj_AGuanabara

package internalActions;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.StringTerm;
import jason.asSyntax.Term;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class sendEMail extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {    	
    	
    	StringTerm messageTerm = (StringTerm) args[0];
    	String msg = messageTerm.getString();
    	
        String to = "necioveras@gmail.com";

        String from = "necioveras@gmail.com";

        String host = "smtp.gmail.com";
        
        java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.auth", "true");

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties, new MyAuthenticator());

        try{
           // Create a default MimeMessage object.
           MimeMessage message = new MimeMessage(session);

           // Set From: header field of the header.
           message.setFrom(new InternetAddress(from));

           // Set To: header field of the header.
           message.addRecipient(Message.RecipientType.TO,
                                    new InternetAddress(to));

           // Set Subject: header field
           message.setSubject("Monitoramento Guanabara");

           // Send the actual HTML message, as big as you like
           message.setContent("<h3>Resultados do monitoramento de hoje</h3><br/>" +
           		"<br/>" + msg,
                              "text/html" );

           // Send message
           Transport.send(message);
           System.out.println("Email enviado com sucesso....");
           return true;
        }catch (MessagingException mex) {
           mex.printStackTrace();
           return false;
        }
    }
    
    class MyAuthenticator extends Authenticator
    {
         MyAuthenticator()
         {
              super();
         }

         protected PasswordAuthentication getPasswordAuthentication()
         {
              return new PasswordAuthentication("necioveras@gmail.com", "");
         }
    }
       
}
