package com.walter.service.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableBinding({RefreshStaticMemoryDataSource.class})
public class RefreshStaticMemoryDataProducer {
	@Autowired
    @Output(RefreshStaticMemoryDataSource.OUTPUT)
    private MessageChannel channel;
	
	public void sendMessage(String msg) {
        channel.send(MessageBuilder.withPayload(msg).build());
        log.info("消息发送成功：" + msg);
    }
}
