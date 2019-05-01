package org.walter.base.security.authorize.messaging.binding;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface RefreshRoleProcessor {

	String INPUT = "refreshRoleInput";
	String OUTPUT = "refreshRoleOutput";

    @Input(RefreshRoleProcessor.INPUT)
    SubscribableChannel refreshRoleInput();
    
    @Output(RefreshRoleProcessor.OUTPUT)
    MessageChannel refreshRoleOutput();
}
