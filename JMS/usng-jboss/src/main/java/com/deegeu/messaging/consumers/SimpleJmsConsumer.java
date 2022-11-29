package com.deegeu.messaging.consumers;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.Destination;
import jakarta.jms.JMSConsumer;
import jakarta.jms.JMSContext;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

/**
 * <p>This is a very simple example of a JMS comsumer.  This is a simplified version of the quickstarts
 * provided by JBoss.</p>
 *
 */
public class SimpleJmsConsumer {
	private static final String CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
    private static final String QUEUE_DESTINATION = "java:/jms/queue/testQueue";
    private static final String INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
    private static final String PROVIDER_URL = "http-remoting://127.0.0.1:8080";
    
    public static void main( String[] args ) throws NamingException {

    	Context namingContext = null;
        JMSContext context = null;
        
        // Set up the namingContext for the JNDI lookup
        // Make sure you create an application user in Wildfly that matches the 
        // username and password below.  Usually a bad practice to have passwords
        // in code, but this is just a simple example.
        final Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
        env.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL, PROVIDER_URL));
        env.put(Context.SECURITY_PRINCIPAL, "guest");     // username
        env.put(Context.SECURITY_CREDENTIALS, "guest");   // password
        
        try {
	        namingContext = new InitialContext(env);
	        
	        // Use JNDI to look up the connection factory and queue
	        ConnectionFactory connectionFactory = (ConnectionFactory) namingContext.lookup(CONNECTION_FACTORY);
	        Destination destination = (Destination) namingContext.lookup(QUEUE_DESTINATION);
	        
	        // Create a JMS context to use to create consumers
	        context = connectionFactory.createContext("guest", "guest"); // again, don't do this in production
	        
	        // Read a message.  If nothing is there, this will return null
	        JMSConsumer consumer = context.createConsumer(destination);
			for (int i =0; i<3;i++){
				String text = consumer.receiveBodyNoWait(String.class);
				System.out.println("Received message: " + text );
			}
        } finally {
        	if (namingContext != null) {
        		namingContext.close();
        	}
        	if (context != null) {
        		context.close();
        	}
        }
    }
}
