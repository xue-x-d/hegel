package com.shomop.http.factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import javax.management.monitor.Monitor;

/**
 * 后台监控线程池
 * @see Monitor#DaemonThreadFactory
 * @author spencer.xue
 * @date 2014-5-5
 */
public class DaemonThreadFactory implements ThreadFactory {

	private static final AtomicInteger poolNumber = new AtomicInteger(1);
	private final ThreadGroup group;
	private final String namePrefix;
	private static final String nameSuffix = "]";

	public DaemonThreadFactory() {
		this("daemon-monitor-pool");
	}

	public DaemonThreadFactory(String poolName) {
		SecurityManager s = System.getSecurityManager();
		this.group = ((s != null) ? s.getThreadGroup() : Thread.currentThread()
				.getThreadGroup());
		this.namePrefix = "Monitor " + poolName + " Pool [Thread-"
				+ poolNumber.getAndIncrement();
	}

	public Thread newThread(Runnable r) {
		Thread t = new Thread(group, r, namePrefix
				+ poolNumber.getAndIncrement() + nameSuffix, 0);
		// This method must be invoked before the thread is started.
		t.setDaemon(true);
		// normal priority
		if (t.getPriority() != Thread.NORM_PRIORITY)
			t.setPriority(Thread.NORM_PRIORITY);
		return t;
	}
}