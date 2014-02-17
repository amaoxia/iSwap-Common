package com.common.utils.queue;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消费Queue中消息的任务基类.
 * 
 * 定义了任务的初始化流程及停止流程.
 * 
 * @see QueueHolder
 * 
 * @author calvin
 */
@SuppressWarnings("unchecked")
public abstract class QueueConsumerTask implements Runnable {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected String queueName;
	protected int shutdownWait = 10000;

	protected boolean persistence = true;
	protected String persistencePath = System.getProperty("java.io.tmpdir") + File.separator + "queue";
	protected Object persistenceLock = new Object();

	protected BlockingQueue queue;
	protected ExecutorService executor;

	/**
	 * 任务所消费的队列名称.
	 */
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	/**
	 * 停止任务时最多等待的时间, 单位为毫秒, 默认为10秒.
	 */
	public void setShutdownWait(int shutdownWait) {
		this.shutdownWait = shutdownWait;
	}

	/**
	 * 在JVM关闭时是否需要持久化未完成的消息到文件.
	 */
	public void setPersistence(boolean persistence) {
		this.persistence = persistence;
	}

	/**
	 * 系统关闭时将队列中未处理的消息持久化到文件的目录,默认为系统临时文件夹下的queue目录.
	 */
	public void setPersistencePath(String persistencePath) {
		this.persistencePath = persistencePath;
	}

	/**
	 * 任务初始化函数.
	 */
	@PostConstruct
	public void start() throws IOException, ClassNotFoundException {
		queue = QueueHolder.getQueue(queueName);

		if (persistence) {
			synchronized (persistenceLock) {
				restore();//将消息恢复到队列中
			}
		}

		executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
			public Thread newThread(Runnable runable) {
				return new Thread(runable, "Queue Consumer-" + queueName);
			}
		});
		executor.execute(this);
	}

	/**
	 * 任务结束函数.
	 */
	@PreDestroy
	public void stop() throws IOException {
		try {
			executor.shutdownNow();
			executor.awaitTermination(shutdownWait, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			logger.debug("awaitTermination被中断", e);
		}

		if (persistence) {
			synchronized (persistenceLock) {
				backup();//消息备份到文件系统中
			}
		}
	}

	/**
	 * 保存队列中的消息到文件.
	 */
	public void backup() throws IOException {
		List list = new ArrayList();
		queue.drainTo(list);//将队列中所有的消息移除放入list

		if (!list.isEmpty()) {
			ObjectOutputStream oos = null;
			try {
				File file = new File(getPersistenceDir(), getPersistenceFileName());
				oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
				for (Object message : list) {
					oos.writeObject(message);
				}
				logger.info("队列{}已持久化{}个消息到{}", new Object[] { queueName, list.size(), file.getAbsolutePath() });
			} finally {
				if (oos != null) {
					oos.close();
				}
			}
		} else {
			logger.debug("队列{}为空,不需要持久化 .", queueName);
		}
	}

	/**
	 * 载入持久化文件中的消息到队列.
	 */
	public void restore() throws ClassNotFoundException, IOException {
		ObjectInputStream ois = null;
		File file = new File(getPersistenceDir(), getPersistenceFileName());

		if (file.exists()) {
			try {
				ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
				int count = 0;
				while (true) {
					try {
						Object message = ois.readObject();
						queue.offer(message);//消息加入队列中
						count++;
					} catch (EOFException e) {
						break;
					}
				}
				logger.info("队列{}已从{}中恢复{}个消息.", new Object[] { queueName, file.getAbsolutePath(), count });
			} finally {
				if (ois != null) {
					ois.close();
				}
			}
			file.delete();//最后记得移除对应的文件
		} else {
			logger.debug("队列{}的持久化文件{}不存在", queueName, file.getAbsolutePath());
		}
	}

	/**
	 * 获取持久化文件路径.
	 * 持久化文件默认路径为java.io.tmpdir/queue/队列名.
	 * 如果java.io.tmpdir/queue/目录不存在,会进行创建.
	 */
	protected File getPersistenceDir() {
		File parentDir = new File(persistencePath + File.separator);
		if (!parentDir.exists()) {
			parentDir.mkdirs();
		}
		return parentDir;
	}

	/**
	 * 获取持久化文件的名称,默认为queueName,可重载.
	 */
	protected String getPersistenceFileName() {
		return queueName;
	}
}
