package com.github.ompc.nchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ompc.nchat.channel.CommandHandler;
import com.github.ompc.nchat.channel.GatewayHandler;
import com.github.ompc.nchat.channel.TalkerHandler;

/**
 * 网络聊天服务器
 * @author vlinux
 *
 */
public class NChatServer {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final NChatConfiger configer;
	private final Broadcaster broadcaster;
	

	/**
	 * 构造函数
	 * @param configer 服务器配置
	 */
	public NChatServer(NChatConfiger configer) {
		this.configer = configer;
		this.broadcaster = new Broadcaster();
	}
	
	/**
	 * 服务器启动
	 * @throws InterruptedException 
	 */
	public void startup() throws InterruptedException {
		final EventLoopGroup bossGroup = new NioEventLoopGroup();
	    final EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			logger.info("nchat server is going to startup...");
			final ServerBootstrap boot = new ServerBootstrap();
			boot
				.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() { // (4)
	                @Override
	                public void initChannel(SocketChannel ch) throws Exception {
	                	ch.pipeline().addLast(new IdleStateHandler(configer.getIdle(),configer.getIdle(),configer.getIdle()));
	                	ch.pipeline().addLast(new StringDecoder(Charset.forName(configer.getCharset())));
	                    ch.pipeline().addLast(new GatewayHandler(broadcaster));
	                    ch.pipeline().addLast(new LineBasedFrameDecoder(2048));
	                    ch.pipeline().addLast(new CommandHandler(broadcaster));
	                    ch.pipeline().addLast(new TalkerHandler(broadcaster));
						ch.pipeline().addLast(new StringEncoder(Charset.forName(configer.getCharset())));
	                }
	            })
	            .option(ChannelOption.SO_BACKLOG, configer.getBacklog())
	            .option(ChannelOption.SO_REUSEADDR, true)
	            .childOption(ChannelOption.SO_KEEPALIVE, true);
			
			// 服务器绑定端口
			final ChannelFuture bootFuture = boot.bind(configer.getPort()).sync();
			logger.info("nchat server was startup finished.");
			
			bootFuture.channel().closeFuture().sync();
		} finally {
			logger.info("nchat server is going to shutdown...");
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
			logger.info("nchat server was shutdown finished.");
		}
		
		
		
	}
	
}
