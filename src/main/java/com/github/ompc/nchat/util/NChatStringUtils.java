package com.github.ompc.nchat.util;

import java.net.InetSocketAddress;

import org.apache.commons.lang.StringUtils;

import io.netty.channel.ChannelHandlerContext;

/**
 * 字符串操作工具类
 * @author vlinux
 *
 */
public class NChatStringUtils {

	/**
	 * 提取出远程访问者
	 * @param ctx
	 * @return
	 */
	public static String getRemoter(ChannelHandlerContext ctx) {
		final InetSocketAddress remote = (InetSocketAddress) ctx.channel().remoteAddress();
		final StringBuilder sb = new StringBuilder();
		sb.append(remote.getHostName()).append(":").append(remote.getPort());
		return sb.toString();
	}
	
	/**
	 * 判断是否是命令<br/>
	 * 命令以/开头
	 * @param words
	 * @return
	 */
	public static boolean isCommand(final String words) {
		return StringUtils.startsWith(words, "/");
	}
	
	/**
	 * 判断是否是字符操作
	 * @param msg
	 * @return
	 */
	public static boolean isWords(Object msg) {
		return null != msg
				&& msg instanceof String;
	}
	
	/**
	 * 提取命令及其参数，并以数组的形式返回
	 * @param words
	 * @return
	 */
	public static String[] getCmdOp(String words) {
		if( StringUtils.isBlank(words) ) {
			return null;
		}
		return StringUtils.split(words);
	}
	
}
