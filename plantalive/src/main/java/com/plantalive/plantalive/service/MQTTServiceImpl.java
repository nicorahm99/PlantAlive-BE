package com.plantalive.plantalive.service;

import com.plantalive.plantalive.MQTT.MqttChannel;
import com.plantalive.plantalive.MQTT.MqttConstants;
import com.plantalive.plantalive.MQTT.MqttProperties;
import com.plantalive.plantalive.exceptions.MqttClientNotConnectedException;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
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
        mqttMessage.setQos(MqttConstants.QOS_EXACTLY_ONCE);
        mqttMessage.setRetained(true);
        mqttClient.publish(topic,mqttMessage);
    }

    @Override
    public void subscribeTopic(MqttChannel channel) throws MqttException {
        mqttClient.subscribe(channel.getTopic().getTopicName(), channel::handleMessage);
    }

}
