package com.plantalive.plantalive.service;

import com.plantalive.plantalive.MQTT.MqttChannel;
import javassist.NotFoundException;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

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

    void updateTargetHumidityFrom(String topic, double targetHumidity) throws NotFoundException;

    void updatePlantInfo(String topic, JSONObject plantInfoJson) throws NotFoundException, JSONException;

    boolean isTopicAlreadyKnown(String topicName);

    void publishTargetHumidityOnTopic(String topicName) throws MqttException;
}
