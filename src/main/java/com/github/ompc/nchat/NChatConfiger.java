package com.github.ompc.nchat;

/**
 * 服务器配置
 * @author vlinux
 *
 */
public class NChatConfiger {

	/*
	 * 服务端口
	 */
	private int port;
	
	/*
	 * 链接超时时间
	 */
	private int connectTimeout;
	
	/*
	 * backlog
	 */
	private int backlog;
	
	/*
	 * 字符编码
	 */
	private String charset;
	
	/*
	 * 超时时间
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
