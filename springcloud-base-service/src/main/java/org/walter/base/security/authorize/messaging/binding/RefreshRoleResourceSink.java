package org.walter.base.security.authorize.messaging.binding;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface RefreshRoleResourceSink {

	String INPUT = "refreshRoleResourceInput";

    @Input(RefreshRoleResourceSink.INPUT)
    SubscribableChannel refreshRoleResourceInput();
}
