package com.inspur.JMS.controller;

import java.io.IOException;

import javax.jms.JMSException;
import javax.naming.InitialContext;
import javax.naming.NamingException;


import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.json.JSONObject;

import com.inspur.JMS.service.QueueMsgReceiver;
import com.inspur.JMS.service.QueueMsgSender;
import com.inspur.service.GETuuid;

public class SendJMSMessage{
	 //JMS消息发送者
	 @RequestMapping("first") 
	 public static  String sendSQLMessage( ) throws   NamingException, JMSException, InterruptedException{
		
		String returnJsonStr = "{\"result\" : \"接受成功并向JMS消息队列插入了一条数据\"}";
		
		
		String sqlJson1 = "select t.chname,t.iconurl from ydpt.menu_table t  where t.id =1";//通信调用接口传递过来的SQL报文
		
		
		GETuuid uu = new GETuuid();
		
		String UUID = uu.getId();//{"UUID":"01","SQL":"select * from tableName"}
		
		String sendMessage = "{\"UUID\":\""+UUID+"\",\"SQL\":\""+sqlJson1+"\"}";
		
		System.out.println("向weblogic JMS消息队列传递的消息："+ sendMessage);
		
		
		QueueMsgSender QMS = new QueueMsgSender();
		
	    try {
	    	InitialContext ctx = QMS.getInitialContext();  
	    	
		    QueueMsgSender sender = new QueueMsgSender(); 
		    
		    sender.init(ctx, QMS.QUEUE_JNDI_NAME);  
		    
			QMS.readAndSendJMSSQL(sender, sendMessage,UUID);
			
			sender.close();  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	    //从JMS消息队列接受返回值
	    Thread.sleep(5000);//给内网区查询数据库留出时间
	    
	    String FHstr = SendJMSMessage.receiveMessage(UUID);
	    
	    JSONObject json = JSONObject.fromObject(FHstr);
	    
		return FHstr;
		
		
	}
	
	//JMS消息接受者（监听器）接受向数据库插入是否成功的返回通知
	 public  static String receiveMessage(String UUID) throws NamingException, JMSException{

	     QueueMsgReceiver receiver = new QueueMsgReceiver();   
	     
	     InitialContext ctx = receiver.getInitialContext();  
	     
	     receiver.init(ctx, receiver.QUEUE_JNDI_NAME,UUID);//UUID为消息选择器标识  
	  
	     String FHstr  =  receiver.readJMSMessage(receiver);
	     
	     return FHstr;
	 }
	/*//对APP提供的方法名称
	public static  String SBJson() throws JSONException, NamingException, JMSException{
		
		sendSQLMessage();
		
		String re = receiveMessage();
		
		return re;
	}*/
	 
	 
	public static void main(String[] args) throws Exception {  
		
		//String sqlJson = "";
		
		
		String re =  sendSQLMessage();
		/*String re = SBJson();*/
		
    }  
}
