package com.github.ompc.nchat;

import java.util.Date;

/**
 * 发言者
 * @author vlinux
 *
 */
public class Talker {

	/*
	 * 系统发言人
	 */
	public static final String SYSTEM = "SYSTEM";
	
	/*
	 * 发言时间
	 */
	private Date occectTime;
	
	/*
	 * 发言作者
	 */
	private String author;
	
	/*
	 * 发言内容
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
