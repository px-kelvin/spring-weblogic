package com.inspur.service;

public class testSleep {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		System.out.println("���Գ����ӳ�����ִ�У���������������������������������");
		
		Thread.sleep(5000);
		
		GETuuid uu = new GETuuid();	
		
		System.out.println("�����ӳٺ�ʼִ�У�����������������������������������");
		
		String uuid = uu.getId();
		
		System.out.println("�����ӳٺ�ʼִ�У�����������������������������������"+uuid);
	}

}
