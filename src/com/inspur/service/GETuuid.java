package com.inspur.service;
  
import java.util.UUID;  
  
public class GETuuid {  
    //���ȫ��Ψһ�Ե�id  
        public static String getId(){  
            String id=UUID.randomUUID().toString();//���ɵ�id942cd30b-16c8-449e-8dc5-028f38495bb5�м京�к�ܣ�<span style="color: rgb(75, 75, 75); font-family: Verdana, Arial, Helvetica, sans-serif; line-height: 20.7999992370605px;">�����������ݿ������id�Ǻ�ʵ�õġ�</span>  
            id=id.replace("-", "");//�滻���м���Ǹ�б��  
            return id;  
        }  
}  