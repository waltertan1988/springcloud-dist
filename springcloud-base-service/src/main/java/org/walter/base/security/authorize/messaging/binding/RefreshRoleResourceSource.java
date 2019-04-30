package org.walter.base.security.authorize.messaging.binding;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface RefreshRoleResourceSource {
	
	String OUTPUT = "refreshRoleResourceOutout";

    @Output(RefreshRoleResourceSource.OUTPUT)
    MessageChannel refreshRoleResourceOutout();
}
