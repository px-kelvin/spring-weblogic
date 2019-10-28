/*package com.inspur.JMS.controller;

import java.io.IOException;

import javax.jms.JMSException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.ws.spi.http.HttpHandler;

import org.apache.http.protocol.HttpContext;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.inspur.JMS.service.QueueMsgReceiver;
import com.inspur.JMS.service.QueueMsgSender;

public class SendJMSMessage1{
	 //JMS消息发送者
	 public static  String sendSQLMessage( ) throws  JSONException, NamingException{
		
		String returnJsonStr = "{\"result\" : \"接受成功并向JMS消息队列插入了一条数据\"}";
		
		
		String sqlJson1 = "{\"UUID\":\"01\",\"SQL\":\"select * from tableName\"}";
		
		QueueMsgSender QMS = new QueueMsgSender();
		
	    try {
	    	InitialContext ctx = QMS.getInitialContext();  
	    	
		    QueueMsgSender sender = new QueueMsgSender(); 
		    
		    sender.init(ctx, QMS.QUEUE_JNDI_NAME);  
		    
			QMS.readAndSendJMSSQL(sender, sqlJson1);
			
			sender.close();  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	
		
		
		return returnJsonStr;
		
		
	}
	
	//JMS消息接受者（监听器）接受向数据库插入是否成功的返回通知
	 public static  String receiveMessage( ) throws  JSONException, NamingException, JMSException{
		    String returnJsonStrCG = "{\"result\" : \"成功向数据库插入一条申报数据\"}";
		    
		    String returnJsonStrSB = "{\"result\" : \"向数据库插入一条申报数据失败\"}";
		 
		    QueueMsgReceiver QMS = new QueueMsgReceiver();
		  
	        InitialContext ctx = QMS.getInitialContext();  
	        QueueMsgReceiver receiver = new QueueMsgReceiver();   
	        receiver.init(ctx, QMS.QUEUE_JNDI_NAME);  
	        
	        synchronized (receiver) {  
	            while (!receiver.quit) {  
	                try {  
	                    receiver.wait();  
	                } catch (InterruptedException e) {   
	                    throw new RuntimeException("error happens", e);  
	                }  
	            }  
	        }  
	        receiver.close();  
	        
	        if(QMS.isSuccess == true){
	        	
	        	return returnJsonStrCG;
	        }else{
	        	return returnJsonStrSB;
	        }
	        
	       
	    
	 }
	 
	 
	public static void main(String[] args) throws Exception {  
		
		String sqlJson = "";
		
		sendSQLMessage();
    }  
}
*/