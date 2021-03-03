package com.plantalive.plantalive.service;

import com.plantalive.plantalive.config.MqttProperties;
import com.plantalive.plantalive.config.MqttQOS;
import com.plantalive.plantalive.exceptions.MqttClientNotConnectedException;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Service;

@Service
public class MQTTServiceImpl implements MQTTService {
    private final IMqttClient mqttClient;

    public MQTTServiceImpl(MqttProperties mqttProperties) throws MqttException {
        IMqttClient clientToBeConnected = new MqttClient(mqttProperties.getBrokerUrl(),mqttProperties.getPublisherId());
        this.mqttClient = connectClient(clientToBeConnected);
    }

    @Override
    public void publishMqttMessage(String message, String topic) throws MqttException {
        if (!mqttClient.isConnected()) throw new MqttClientNotConnectedException();
        MqttMessage mqttMessage = buildMqttMessage(message);
        mqttMessage.setQos(MqttQOS.EXACTLY_ONCE);
        mqttMessage.setRetained(true);
        mqttClient.publish(topic,mqttMessage);
    }

    @Override
    public void subscribeTopic(String topicToBeSubscribed) throws MqttException {
        mqttClient.subscribe(topicToBeSubscribed, (topic, msg) -> {
            System.out.println("#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#");
            System.out.println("#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#");
            System.out.println("#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#");
            System.out.print("Received Message: ");
            System.out.println(msg + " from Topic " + topic);
            System.out.println("#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#");
            System.out.println("#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#");
            System.out.println("#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#=#");
        });
    }

}
