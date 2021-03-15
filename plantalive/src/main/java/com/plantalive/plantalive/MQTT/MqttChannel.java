package com.plantalive.plantalive.MQTT;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public abstract class MqttChannel {
    abstract public void handleMessage(String topic, MqttMessage message);
    abstract public String getTopicName();
}
