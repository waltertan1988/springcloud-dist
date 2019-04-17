package com.walter.service.stream;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableBinding({RefreshStaticMemoryDataSink.class})
public class RefreshStaticMemoryDataConsumer {
	
	@StreamListener(RefreshStaticMemoryDataSink.INPUT)
	public void consumerMessage(String msg) {
		log.info("消费消息成功：" + msg);
	}
}
