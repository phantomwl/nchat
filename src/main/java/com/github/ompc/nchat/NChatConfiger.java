package com.github.ompc.nchat;

/**
 * ����������
 * @author vlinux
 *
 */
public class NChatConfiger {

	/*
	 * ����˿�
	 */
	private int port;
	
	/*
	 * ���ӳ�ʱʱ��
	 */
	private int connectTimeout;
	
	/*
	 * backlog
	 */
	private int backlog;
	
	/*
	 * �ַ�����
	 */
	private String charset;
	
	/*
	 * ��ʱʱ��
	 */
	private int idle;
	
	public int getIdle() {
		return idle;
	}
	public void setIdle(int idle) {
		this.idle = idle;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getConnectTimeout() {
		return connectTimeout;
	}
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	public int getBacklog() {
		return backlog;
	}
	public void setBacklog(int backlog) {
		this.backlog = backlog;
	}
	
}
