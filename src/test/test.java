package test;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import entity.student;

public class test {
	public static void main(String[] args) {  
        ApplicationContext ct = new ClassPathXmlApplicationContext("applicationContext.xml");//��ȡxml�����ļ�  
        student stu = (student) ct.getBean("Student"); //�õ�spring�����bean  
        System.out.println("name:"+stu.getName());  
    }  
}
