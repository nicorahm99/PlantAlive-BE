package com.plantalive.plantalive.MQTT;

import com.plantalive.plantalive.persistence.TopicDAO;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public abstract class MqttChannel {
    TopicDAO topic;
    abstract public void handleMessage(String topic, MqttMessage message);
    abstract public TopicDAO getTopic();
}
