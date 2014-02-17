package com.common.utils.email;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.mail.Email;

import com.common.utils.queue.BlockingConsumerTask;


/** 
 * 将Queue中的email进行发送的消费者任务.
 * 
 * 即时阻塞的读取Queue中的事件,达到缓存上限后使用Jdbc批量写入模式.
 * 如需换为定时读取模式,继承于PeriodConsumerTask稍加改造即可.
 * 
 * @see BlockingConsumerTask
 * 
 * @author tanhc
 */
public class EmailConsumerTask extends BlockingConsumerTask {

	protected int batchSize = 10;
	protected List<Email> emailBuffer = new ArrayList<Email>();

	/**
	 * 退出清理函数,完成buffer中未完成的消息.
	 */
	@Override
	protected void clean() {
		if (!emailBuffer.isEmpty()) {
			updateBatch();
		}
		logger.debug("cleaned task {}", this);
	}

	/**
	 * 消息处理函数,将消息放入buffer,当buffer达到batchSize时执行批量更新函数.
	 */
	@Override
	protected void processMessage(Object message) {
		Email mail = (Email) message;
		emailBuffer.add(mail);
		logger.debug("get mail, {}", "主题：" + mail.getSubject() + " From: " + mail.getFromAddress());

		//已到达BufferSize则执行批量插入操作
		if (emailBuffer.size() >= batchSize) {
			updateBatch();
		}
	}

	/**
	 * 将Buffer中的事件列表批量插入数据库.
	 */
	public void updateBatch() {
		try {
			for (Email email : emailBuffer) {
				email.send();
			}
			//清除emailBuffer
			emailBuffer.clear();
		} catch (Exception e) {
			logger.error("批量提交任务时发生错误.", e);
		}
	}

	/**
	 * 批量读取事件数量, 默认为10.
	 */
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

}
