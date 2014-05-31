package com.github.ompc.nchat.channel;

import static com.github.ompc.nchat.util.NChatStringUtils.getCmdOp;
import static com.github.ompc.nchat.util.NChatStringUtils.isCommand;
import static com.github.ompc.nchat.util.NChatStringUtils.isWords;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ompc.nchat.Broadcaster;
import com.github.ompc.nchat.util.NChatStringUtils;

/**
 * 命令处理器
 * @author vlinux
 *
 */
public class CommandHandler extends ChannelInboundHandlerAdapter {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final Broadcaster broadcaster;
	
	public CommandHandler(Broadcaster broadcaster) {
		this.broadcaster = broadcaster;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		super.channelRead(ctx, msg);
		
		// 不是字符则让开
		if( !isWords(msg) ) {
			return;
		}
		
		// 核对是否命令，不是命令则让开
		final String words = (String)msg;
		if( !isCommand(words) ) {
			return;
		}
		
		final String[] cmds = getCmdOp(words);
		handleCommand(cmds, ctx);
		
	}
	
	/**
	 * 处理命令
	 * @param cmdOps
	 * @param ctx
	 * @throws InterruptedException 
	 */
	private void handleCommand(String[] cmdOps, ChannelHandlerContext ctx) throws InterruptedException {
		
		final Channel channel = ctx.channel();
		
		// quit
		if( NChatStringUtils.equalsIgnoreCaseIn(cmdOps[0], "/q", "/quit") ) {
			channel.writeAndFlush("Goodbye sir.\n").sync();
			ctx.channel().close();
		} 
		
		// who
		else if( NChatStringUtils.equalsIgnoreCaseIn(cmdOps[0], "/w", "/who") ) {
			final String who = NChatStringUtils.getRemoter(ctx);
			channel.writeAndFlush("you are \""+who+"\".\n");
		}
		
		// list
		else if( NChatStringUtils.equalsIgnoreCaseIn(cmdOps[0], "/l", "/list") ) {
			final Collection<Channel> clones = broadcaster.list();
			final StringBuilder sb = new StringBuilder("there is ").append(clones.size()).append(" users online.\n");
			for( Channel c : clones ) {
				final String who = NChatStringUtils.getRemoter(c);
				sb.append(" >>\"").append(who).append("\"\n");
			}
			
			channel.writeAndFlush(sb.toString()+(clones.isEmpty()?"\n":""));
		}
		
		// help
		else if( NChatStringUtils.equalsIgnoreCaseIn(cmdOps[0], "/?", "/h", "/help") ) {
			final StringBuilder sb = new StringBuilder();
			sb.append(" >>/w /who     : show who am I.\n");
			sb.append(" >>/l /list    : list the online user.\n");
			sb.append(" >>/q /quit    : quit from this chat.\n");
			sb.append(" >>/? /h /help : show the useage.\n");
			channel.writeAndFlush("help for useage: \n"+sb.toString());
		}
		
		else {
			channel.writeAndFlush("invalid command: "+cmdOps[0]+".\n");
			logger.info("invalid command: {}",cmdOps[0]);
		}
	}
	
}
