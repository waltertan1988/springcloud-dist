package com.walter.service.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface RefreshStaticMemoryDataSource {
	
	String OUTPUT = "refreshStaticMemoryDataOutput";

    @Output(RefreshStaticMemoryDataSource.OUTPUT)
    MessageChannel refreshStaticMemoryDataOutput();
}
