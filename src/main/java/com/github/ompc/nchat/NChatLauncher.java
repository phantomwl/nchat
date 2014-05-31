package com.github.ompc.nchat;

/**
 * Æô¶¯Æ÷
 * @author vlinux
 *
 */
public class NChatLauncher {

	public static void main(String... args) throws InterruptedException {
		
		final NChatConfiger configer = new NChatConfiger();
		configer.setBacklog(128);
		configer.setConnectTimeout(1000*3);
		configer.setPort(7878);
		configer.setCharset("UTF-8");
		
		final NChatServer server = new NChatServer(configer);
		server.startup();
		
	}
	
}
