package com.walter.service.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface RefreshStaticMemoryDataSink {

	String INPUT = "refreshStaticMemoryDataInput";

    @Input(RefreshStaticMemoryDataSink.INPUT)
    SubscribableChannel refreshStaticMemoryDataInput();
}
