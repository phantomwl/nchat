package com.github.ompc.nchat;

import io.netty.channel.Channel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ompc.nchat.util.NChatStringUtils;

/**
 * �㲥��
 * @author vlinux
 *
 */
public class Broadcaster {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	/*
	 * ��Ϣ����
	 */
	private final LinkedBlockingQueue<Talker> talkQueue = new LinkedBlockingQueue<Talker>();
	
	/*
	 * ������Ϣͨ��
	 */
	private final Set<Channel> channels = new HashSet<Channel>();
	
	/*
	 * �������̳߳�
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
	 * ������
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
					
					final Iterator<Channel> it = channels.iterator();
					while( it.hasNext() ) {
						final Channel channel = it.next();
						executors.execute(new Runnable(){

							@Override
							public void run() {
								if( StringUtils.isBlank(talker.getWords()) ) {
									return;
								}
								final StringBuilder talkSB = new StringBuilder();
								final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
								talkSB.append(sdf.format(talker.getOccectTime())).append(" ");
								talkSB.append(talker.getAuthor()).append(">>");
								talkSB.append(talker.getWords());
								
								try {
									channel.writeAndFlush(talkSB.toString());
								} catch(Throwable t) {
									logger.warn("write talk to {} failed.", NChatStringUtils.getRemoter(channel), t);
								}
								
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
	 * Ͷ����Ϣ
	 * @param talker
	 */
	public void post(Talker talker) {
		talkQueue.offer(talker);
	}
	
	/**
	 * ע��һ��ͨ��
	 * @param channel
	 */
	public void regChannel(Channel channel) {
		synchronized (channels) {
			channels.add(channel);
		}
	}
	
	/**
	 * ȡ��һ��ͨ��
	 * @param channel
	 */
	public void unRegChannel(Channel channel) {
		synchronized (channels) {
			channels.remove(channel);
		}
	}
	
	/**
	 * �г���ǰ���е��û�
	 * @return
	 */
	public List<Channel> list() {
		final List<Channel> clones = new ArrayList<Channel>();
		final Iterator<Channel> it = channels.iterator();
		while( it.hasNext() ) {
			final Channel channel = it.next();
			clones.add(channel);
		}
		return clones;
	}
	
}
