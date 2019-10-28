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
	 //JMS��Ϣ������
	 @RequestMapping("first") 
	 public static  String sendSQLMessage( ) throws   NamingException, JMSException, InterruptedException{
		
		String returnJsonStr = "{\"result\" : \"���ܳɹ�����JMS��Ϣ���в�����һ������\"}";
		
		
		String sqlJson1 = "select t.chname,t.iconurl from ydpt.menu_table t  where t.id =1";//ͨ�ŵ��ýӿڴ��ݹ�����SQL����
		
		
		GETuuid uu = new GETuuid();
		
		String UUID = uu.getId();//{"UUID":"01","SQL":"select * from tableName"}
		
		String sendMessage = "{\"UUID\":\""+UUID+"\",\"SQL\":\""+sqlJson1+"\"}";
		
		System.out.println("��weblogic JMS��Ϣ���д��ݵ���Ϣ��"+ sendMessage);
		
		
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
	   
	    //��JMS��Ϣ���н��ܷ���ֵ
	    Thread.sleep(5000);//����������ѯ���ݿ�����ʱ��
	    
	    String FHstr = SendJMSMessage.receiveMessage(UUID);
	    
	    JSONObject json = JSONObject.fromObject(FHstr);
	    
		return FHstr;
		
		
	}
	
	//JMS��Ϣ�����ߣ������������������ݿ�����Ƿ�ɹ��ķ���֪ͨ
	 public  static String receiveMessage(String UUID) throws NamingException, JMSException{

	     QueueMsgReceiver receiver = new QueueMsgReceiver();   
	     
	     InitialContext ctx = receiver.getInitialContext();  
	     
	     receiver.init(ctx, receiver.QUEUE_JNDI_NAME,UUID);//UUIDΪ��Ϣѡ������ʶ  
	  
	     String FHstr  =  receiver.readJMSMessage(receiver);
	     
	     return FHstr;
	 }
	/*//��APP�ṩ�ķ�������
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
