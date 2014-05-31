package com.github.ompc.nchat.channel;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ompc.nchat.Broadcaster;
import com.github.ompc.nchat.Talker;
import com.github.ompc.nchat.util.NChatStringUtils;

/**
 * ���ش�����<br/>
 * �����û��������ʺ�
 * @author vlinux
 *
 */
public class GatewayHandler extends ChannelInboundHandlerAdapter {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final Broadcaster broadcaster;
	
	public GatewayHandler(Broadcaster broadcaster) {
		this.broadcaster = broadcaster;
	}
	
	/**
	 * ���Ӵ�������ζ��һ���û��Ľ���
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		if( ctx.channel().remoteAddress() instanceof InetSocketAddress ) {
			logger.info("remote={} was coming...", NChatStringUtils.getRemoter(ctx));
		}
		broadcaster.regChannel(ctx.channel());
		showLogo(ctx);
	}
	
	/**
	 * ���LOGO
	 * @param ctx
	 * @throws IOException 
	 */
	private void showLogo(ChannelHandlerContext ctx) throws IOException {
		final String logo = IOUtils.toString(getClass().getResourceAsStream("/nchat-logo.txt"));
		ctx.channel().writeAndFlush(logo);
		broadcaster.post(new Talker(new Date(), Talker.SYSTEM, "Welcome sir.\n"));
	}
	
	/**
	 * ���ӹرգ���ζ��һ���û����뿪
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		if( ctx.channel().remoteAddress() instanceof InetSocketAddress ) {
			logger.info("remote={} was leaving...", NChatStringUtils.getRemoter(ctx));
		}
		broadcaster.unRegChannel(ctx.channel());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		super.exceptionCaught(ctx, cause);
		if( ctx.channel().remoteAddress() instanceof InetSocketAddress ) {
			logger.warn("remote={} was caught an exception, nchat server will close this channel.", new Object[]{NChatStringUtils.getRemoter(ctx)}, cause);
		}
	}
	
}
