package test;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import entity.student;

public class test {
	public static void main(String[] args) {  
        ApplicationContext ct = new ClassPathXmlApplicationContext("applicationContext.xml");//读取xml配置文件  
        student stu = (student) ct.getBean("Student"); //得到spring管理的bean  
        System.out.println("name:"+stu.getName());  
    }  
}
