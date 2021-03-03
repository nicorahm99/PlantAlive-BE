package com.plantalive.plantalive.service;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

@Service
public interface MQTTService {
    void publishMqttMessage(String message, String topic) throws MqttException;

    default MqttMessage buildMqttMessage(String message) {
        byte[] payload = message.getBytes();
        return new MqttMessage(payload);
    }
}
