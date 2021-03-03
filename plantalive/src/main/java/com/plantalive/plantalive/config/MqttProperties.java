package com.plantalive.plantalive.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "mqtt")
public class MqttProperties {

    /**
     * Url with protocol and port to connect to MQTT broker
     */
    private String brokerUrl;

    /**
     * Publisher Id of this backend
     */
    private String publisherId;
}
