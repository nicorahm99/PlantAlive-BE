package com.plantalive.plantalive.service;

import com.plantalive.plantalive.config.MqttProperties;
import com.plantalive.plantalive.config.MqttQOS;
import com.plantalive.plantalive.exceptions.MqttClientNotConnectedException;
import org.eclipse.paho.client.mqttv3.*;

public class MQTTServiceImpl implements MQTTService {
    private final IMqttClient mqttClient;

    public MQTTServiceImpl(MqttProperties mqttProperties) throws MqttException {
        this.mqttClient = new MqttClient(mqttProperties.getBrokerUrl(),mqttProperties.getPublisherId());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        this.mqttClient.connect(options);
    }

    @Override
    public void publishMqttMessage(String message, String topic) throws MqttException {
        if (!mqttClient.isConnected()) throw new MqttClientNotConnectedException();
        MqttMessage mqttMessage = buildMqttMessage(message);
        mqttMessage.setQos(MqttQOS.EXACTLY_ONCE);
        mqttMessage.setRetained(true);
        mqttClient.publish(topic,mqttMessage);
    }

}
