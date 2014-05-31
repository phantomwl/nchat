package com.github.ompc.nchat;

import java.util.Date;

/**
 * ������
 * @author vlinux
 *
 */
public class Talker {

	/*
	 * ϵͳ������
	 */
	public static final String SYSTEM = "SYSTEM";
	
	/*
	 * ����ʱ��
	 */
	private Date occectTime;
	
	/*
	 * ��������
	 */
	private String author;
	
	/*
	 * ��������
	 */
	private String words;
	
	public Talker(Date occectTime, String author, String words) {
		this.occectTime = occectTime;
		this.author = author;
		this.words = words;
	}

	public Date getOccectTime() {
		return occectTime;
	}

	public void setOccectTime(Date occectTime) {
		this.occectTime = occectTime;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getWords() {
		return words;
	}

	public void setWords(String words) {
		this.words = words;
	}
	
}
