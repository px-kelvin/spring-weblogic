package com.inspur.JMS.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class QueueMsgSender {  
	  
    // Defines the JNDI context factory.  
    public final static String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";  
  
    // Defines the JNDI provider url.  
    public final static String PROVIDER_URL = "t3://10.19.22.94:7011/";  
  
    // Defines the JMS connection factory for the queue.  
    public final static String CONNECTION_FACTORY_JNDI_NAME = "gt3.esb.jms.con.ESBConnectionFactory";  
  
    // Defines the queue, use the queue JNDI name   
    public final static String QUEUE_JNDI_NAME = "gt3.esb.jms.mdb.BaseQueueAsynMDBean";  
  
    private QueueConnectionFactory qconFactory;  
    private QueueConnection queueConnection;  
    private QueueSession queueSession;  
    private QueueSender queueSender;  
    private Queue queue;  
    private TextMessage textMessage;  
    private StreamMessage streamMessage;  
    private BytesMessage bytesMessage;  
    private MapMessage mapMessage;  
    private ObjectMessage objectMessage;  
      
    /** 
     * get the context object. 
     *  
     * @return context object 
     * @throws NamingException if operation cannot be performed 
     */  
    public static InitialContext getInitialContext() throws NamingException {  
        Hashtable<String,String> table = new Hashtable<String,String>();  
        table.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);   
        table.put(Context.PROVIDER_URL, PROVIDER_URL);  
        InitialContext context = new InitialContext(table);  
        return context;  
    }  
  
    /** 
     * Creates all the necessary objects for sending messages to a JMS queue. 
     *  
     * @param ctx JNDI initial context 
     * @param queueName name of queue 
     * @exception NamingException if operation cannot be performed 
     * @exception JMSException if JMS fails to initialize due to internal error 
     */  
    public void init(Context ctx, String queueName) throws NamingException, JMSException {  
        qconFactory = (QueueConnectionFactory) ctx.lookup(CONNECTION_FACTORY_JNDI_NAME);  
        queueConnection = qconFactory.createQueueConnection();  
        queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);  
        queue = (Queue) ctx.lookup(queueName);  
        queueSender = queueSession.createSender(queue);  
       
        textMessage = queueSession.createTextMessage();  
        streamMessage = queueSession.createStreamMessage();  
        bytesMessage = queueSession.createBytesMessage();  
        mapMessage = queueSession.createMapMessage();  
        objectMessage = queueSession.createObjectMessage();  
  
        queueConnection.start();  
    }  
  
    /** 
     * Sends a message to a JMS queue. 
     *  
     * @param message message to be sent 
     * @exception JMSException if JMS fails to send message due to internal error 
     */  
    public void send(String message,String UUID) throws JMSException {  
        // type1: set TextMessage  
        textMessage.setText(message);  
          
        textMessage.setStringProperty("JMSXGroupID","A");//消息选择器对消息进行分组  
          
        queueSender.send(textMessage);  
    }  
  
    /** 
     * read the msg from the console, then send it. 
     *  
     * @param msgSender 
     * @throws IOException if IO fails to send message due to internal error 
     * @throws JMSException if JMS fails to send message due to internal error 
     */  
   /* private static void readAndSend(QueueMsgSender msgSender) throws IOException, JMSException {  
        BufferedReader msgStream = new BufferedReader(new InputStreamReader(System.in));  
        System.out.println("Enter message(input quit to quit):");    
        String line = null;  
        boolean quit = false;   
        do {  
            line = msgStream.readLine();  
            if (line != null && line.trim().length() != 0) {   
                msgSender.send(line);  
                System.out.println("JMS Message Sent: " + line + "\n");  
                quit = line.equalsIgnoreCase("quit");  
            }  
        } while (!quit);  
  
    }  */
    public static void readAndSendJMSSQL(QueueMsgSender msgSender,String SqlJson,String UUID) throws IOException, JMSException {  
       
        String line = null;  
        boolean quit = false;   
        do {  
            line = SqlJson;  
            if (line != null && line.trim().length() != 0) {
            	
                msgSender.send(line,UUID);  
                System.out.println("JMS Message Sent: " + line + "\n");  
                quit = true;  
            }  
        } while (!quit);  
  
    }   
    /** 
     * release resources. 
     *  
     * @exception JMSException if JMS fails to close objects due to internal error 
     */  
    public void close() throws JMSException {  
        queueSender.close();  
        queueSession.close();  
        queueConnection.close();  
    }  
      
    /** 
     * test client. 
     *  
     * @param args 
     * @throws Exception  
     */  
    public static void main(String[] args) throws Exception {  
        /*InitialContext ctx = getInitialContext();   
        QueueMsgSender sender = new QueueMsgSender();    
        sender.init(ctx, QUEUE_JNDI_NAME);  
        readAndSend(sender);  
        readAndSendJMSSQL(sender, null);
        sender.close();  */
    }  
}  