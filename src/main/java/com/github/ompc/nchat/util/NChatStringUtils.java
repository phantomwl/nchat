package com.github.ompc.nchat.util;

import java.net.InetSocketAddress;

import org.apache.commons.lang.StringUtils;

import io.netty.channel.ChannelHandlerContext;

/**
 * �ַ�������������
 * @author vlinux
 *
 */
public class NChatStringUtils {

	/**
	 * ��ȡ��Զ�̷�����
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
	 * �ж��Ƿ�������<br/>
	 * ������/��ͷ
	 * @param words
	 * @return
	 */
	public static boolean isCommand(final String words) {
		return StringUtils.startsWith(words, "/");
	}
	
	/**
	 * �ж��Ƿ����ַ�����
	 * @param msg
	 * @return
	 */
	public static boolean isWords(Object msg) {
		return null != msg
				&& msg instanceof String;
	}
	
	/**
	 * ��ȡ���������������������ʽ����
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
