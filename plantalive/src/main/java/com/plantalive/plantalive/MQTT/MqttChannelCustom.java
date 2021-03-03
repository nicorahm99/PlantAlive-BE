package com.plantalive.plantalive.MQTT;

import com.plantalive.plantalive.persistence.TopicDAO;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttChannelCustom extends MqttChannel {
    private final TopicDAO topic;

    public MqttChannelCustom(TopicDAO topic) {
        this.topic = topic;
    }

    @Override
    public void handleMessage(String topic, MqttMessage message) {
        //TODO LOGIC
        System.out.println("Received following message from topic " + topic + " : " + message);
    }

    @Override
    public TopicDAO getTopic() {
        return topic;
    }
}
