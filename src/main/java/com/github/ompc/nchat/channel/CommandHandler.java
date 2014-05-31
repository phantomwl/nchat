package com.github.ompc.nchat.channel;

import static com.github.ompc.nchat.util.NChatStringUtils.getCmdOp;
import static com.github.ompc.nchat.util.NChatStringUtils.isCommand;
import static com.github.ompc.nchat.util.NChatStringUtils.isWords;

import java.util.Date;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ompc.nchat.Broadcaster;
import com.github.ompc.nchat.Talker;

/**
 * �������
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
		
		// �����ַ����ÿ�
		if( !isWords(msg) ) {
			return;
		}
		
		// �˶��Ƿ���������������ÿ�
		final String words = (String)msg;
		if( !isCommand(words) ) {
			return;
		}
		
		final String[] cmds = getCmdOp(words);
		handleCommand(cmds, ctx);
		
	}
	
	/**
	 * ��������
	 * @param cmdOps
	 * @param ctx
	 * @throws InterruptedException 
	 */
	private void handleCommand(String[] cmdOps, ChannelHandlerContext ctx) throws InterruptedException {
		
		// �˳�����
		if( StringUtils.equalsIgnoreCase(cmdOps[0], "/q") ) {
			ctx.channel().writeAndFlush("Goodbye sir.\n").sync();
			ctx.channel().close();
		} else {
			broadcaster.post(new Talker(new Date(), Talker.SYSTEM, "invalid command: "+cmdOps[0]+"."));
			logger.info("invalid command: {}",cmdOps[0]);
		}
	}
	
}
