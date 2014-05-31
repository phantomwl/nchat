package com.github.ompc.nchat;

import io.netty.channel.Channel;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 广播者
 * @author vlinux
 *
 */
public class Broadcaster {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	/*
	 * 消息队列
	 */
	private final LinkedBlockingQueue<Talker> talkQueue = new LinkedBlockingQueue<Talker>();
	
	/*
	 * 所有消息通道
	 */
	private final Set<Channel> channels = new HashSet<Channel>();
	
	/*
	 * 消费者线程池
	 */
	private final ExecutorService executors = Executors.newFixedThreadPool(24, new ThreadFactory() {
		
		@Override
		public Thread newThread(Runnable r) {
			final Thread t = new Thread(r);
			t.setName("nchat-server-talker");
			return t;
		}
	});
	
	/*
	 * 消费者
	 */
	private final Runnable consumer = new Runnable() {

		@Override
		public void run() {
			logger.info("nchat-broadcaster was startup.");
			while(true) {
				try {
					final Talker talker = talkQueue.take();
					if( null == talker ) {
						continue;
					}
					for(final Channel channel : channels) {
						executors.execute(new Runnable(){

							@Override
							public void run() {
								if( StringUtils.isBlank(talker.getWords()) ) {
									return;
								}
								final StringBuilder talkSB = new StringBuilder();
								final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
								talkSB.append(sdf.format(talker.getOccectTime())).append(" ");
								talkSB.append(talker.getAuthor()).append(">>");
								talkSB.append(talker.getWords());
								channel.writeAndFlush(talkSB.toString());
							}
							
						});
					}
				} catch (InterruptedException e) {
					logger.warn("Broadcaster take talker from queue failed.", e);
				}
			}
		}
		
	};
	
	
	public Broadcaster() {
		new Thread(consumer,"nchat-broadcaster").start();
	}
	
	/**
	 * 投递消息
	 * @param talker
	 */
	public void post(Talker talker) {
		talkQueue.offer(talker);
	}
	
	/**
	 * 注册一个通道
	 * @param channel
	 */
	public void regChannel(Channel channel) {
		synchronized (channels) {
			channels.add(channel);
		}
	}
	
	/**
	 * 取消一个通道
	 * @param channel
	 */
	public void unRegChannel(Channel channel) {
		synchronized (channels) {
			channels.remove(channel);
		}
	}
	
}
