package com.github.ompc.nchat;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

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
		
		final OptionParser parser = new OptionParser();
		parser.accepts("backlog").withRequiredArg();
		parser.accepts("connect-timeout").withRequiredArg();
		parser.accepts("port").withRequiredArg();
		parser.accepts("charset").withRequiredArg();
		
		final OptionSet options = parser.parse(args);
		configer.setBacklog(Integer.valueOf(options.valueOf("backlog").toString()));
		configer.setConnectTimeout(Integer.valueOf(options.valueOf("connect-timeout").toString()));
		configer.setPort(Integer.valueOf(options.valueOf("port").toString()));
		configer.setCharset(options.valueOf("charset").toString());
		
		final NChatServer server = new NChatServer(configer);
		server.startup();
		
	}
	
}
