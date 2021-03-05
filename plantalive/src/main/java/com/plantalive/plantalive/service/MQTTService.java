package com.plantalive.plantalive.service;

import com.plantalive.plantalive.MQTT.MqttChannel;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface MQTTService {
    void publishMqttMessage(String message, String topic) throws MqttException;

    default MqttMessage buildMqttMessage(String message) {
        byte[] payload = message.getBytes();
        return new MqttMessage(payload);
    }

    void subscribeTopic(MqttChannel channel) throws MqttException;

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
