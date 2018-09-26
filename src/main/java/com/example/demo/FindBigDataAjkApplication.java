package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FindBigDataAjkApplication {

	public static void main(String[] args) {
		SpringApplication.run(FindBigDataAjkApplication.class, args);
	}
	
	
	public static void name() {
		content = content.substring(content.indexOf("<body>")+6,content.lastIndexOf("<br>"));
		System.out.println(content);
		String[] ipv4 = content.split("<br>");
		System.out.println("**********************分割线*******************");
		for (String string : ipv4) {
			System.out.println(string);
		}
	}
}
