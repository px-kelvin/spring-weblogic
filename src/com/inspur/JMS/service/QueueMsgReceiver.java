package com.inspur.JMS.service;

import java.util.Properties;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;



public class QueueMsgReceiver {  
    public final static String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";  
    public final static String PROVIDER_URL = "t3://10.19.22.94:7011";  //10.72.86.108  10.19.22.94:7011
    //public final static String CONNECTION_FACTORY_JNDI_NAME = "weblogic.jms.XAConnectionFactory";  
    public final static String CONNECTION_FACTORY_JNDI_NAME = "gt3.esb.jms.con.ESBConnectionFactory";  
    public final static String QUEUE_JNDI_NAME = "gt3.esb.jms.mdb.BaseQueueAsynMDBean";  
    
    private QueueConnectionFactory qconFactory;  
    private QueueConnection queueConnection;  
    private QueueSession queueSession;  
    private QueueReceiver queueReceiver;  
    private MessageConsumer consumer;
    private Queue queue;  
    public boolean quit = false;  
    
    public boolean isSuccess = false;
      
    public static InitialContext getInitialContext() throws NamingException {  
    	
        Properties properties = new Properties();
        properties.setProperty(Context.PROVIDER_URL, PROVIDER_URL);
        properties.setProperty(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
        
        InitialContext context = new InitialContext(properties);  
        return context;  
    }  
      
    public void init(Context ctx, String queueName,String UUID) throws NamingException, JMSException {  
        qconFactory = (QueueConnectionFactory) ctx.lookup(CONNECTION_FACTORY_JNDI_NAME);  
        queueConnection = qconFactory.createQueueConnection();   
        queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);  
        queue = (Queue) ctx.lookup(queueName);  
       
        //使用组的感念，消息选择器
        consumer = queueSession.createConsumer(queue, "JMSXGroupID='"+UUID+"'");
       
        queueConnection.start();    
    }  
  

    public  void close() throws JMSException {  
       
    	consumer.close();
        queueSession.close();  
        queueConnection.close();  
    }  
  
    public static void main(String[] args) throws Exception {  
       /* InitialContext ctx = getInitialContext();  
        QueueMsgReceiver receiver = new QueueMsgReceiver();   
        receiver.init(ctx, QUEUE_JNDI_NAME);  
  
        readJMSMessage(receiver);
        */
        
    }

	public static String readJMSMessage(QueueMsgReceiver receiver) throws JMSException {
		
		Message message = receiver.consumer.receive();
		
		String msgStr = "";
		 if (message instanceof TextMessage) { 
          	
    		 
             msgStr = ((TextMessage) message).getText();  
         }

       System.out.println("Message Received: " + msgStr );  
       
       receiver.close();
       
       return msgStr;
	}  
}  