package com.plantalive.plantalive.service;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
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

    void subscribeTopic(String topicToBeSubscribed) throws MqttException;

    default IMqttClient connectClient(IMqttClient client) throws MqttException {
        client.connect(getMqttConnectOptions());
        return client;
    }

    default MqttConnectOptions getMqttConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        return options;
    }
}
