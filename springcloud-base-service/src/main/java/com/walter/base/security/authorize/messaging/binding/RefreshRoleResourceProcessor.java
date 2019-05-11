package com.walter.base.security.authorize.messaging.binding;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface RefreshRoleResourceProcessor {

	String INPUT = "refreshRoleResourceInput";
	String OUTPUT = "refreshRoleResourceOutput";

    @Input(RefreshRoleResourceProcessor.INPUT)
    SubscribableChannel refreshRoleResourceInput();
    
    @Output(RefreshRoleResourceProcessor.OUTPUT)
    MessageChannel refreshRoleResourceOutput();
}
