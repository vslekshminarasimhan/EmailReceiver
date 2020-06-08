package com.tfg.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.mail.ImapIdleChannelAdapter;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import org.springframework.integration.mail.ImapMailReceiver;
import org.springframework.integration.mail.dsl.Mail;
import org.springframework.integration.mail.transformer.MailToStringTransformer;
/**
 *
 * Spring Boot application starter class
 */
@SpringBootApplication
public class Application {
	private static Log logger = LogFactory.getLog(Application.class);
	
    public static void main(String[] args) throws Exception {
 
    	@SuppressWarnings("resource")
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext(
				"/META-INF/spring/integration/gmail-imap-idle-config.xml");
		DirectChannel inputChannel = ac.getBean("receiveChannel", DirectChannel.class);

		
			inputChannel.subscribe(new MessageHandler() {
			@Override
			public void handleMessage(Message<?> message) throws MessagingException {
				logger.info("Message: " + message);
		
			try {
				//recupero il mime message e propago l'evento
				MimeMessage mimeMessage = (MimeMessage) message.getPayload();
			
				String msgId = mimeMessage.getMessageID();
				System.out.println("The value of msgId"+msgId);
			
			} catch (Exception e) {
			//	logger.error("Errore durante la pubblicazione dell'evento di ricezione mail {}", e.getMessage(), e);
				throw new RuntimeException(e);
			}
			 
	        }
	    });
	
    }

       // SpringApplication.run(Application.class, args);
 
}
