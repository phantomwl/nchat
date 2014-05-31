package com.github.ompc.nchat.channel;

import static com.github.ompc.nchat.util.NChatStringUtils.isCommand;
import static com.github.ompc.nchat.util.NChatStringUtils.isWords;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

import com.github.ompc.nchat.Broadcaster;
import com.github.ompc.nchat.Talker;
import com.github.ompc.nchat.util.NChatStringUtils;

/**
 * �������
 * @author vlinux
 *
 */
public class TalkerHandler extends ChannelInboundHandlerAdapter {

	private final Broadcaster broadcaster;
	
	public TalkerHandler(Broadcaster broadcaster) {
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
		
		// �˶��Ƿ�������������ÿ�
		final String words = (String)msg;
		if( isCommand(words) ) {
			return;
		}
		
		broadcaster.post(new Talker(new Date(), NChatStringUtils.getRemoter(ctx), words));
		
	}
	
}
