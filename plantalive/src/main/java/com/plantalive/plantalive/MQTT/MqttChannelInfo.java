package com.plantalive.plantalive.MQTT;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttChannelInfo extends MqttChannel {
    private final String topicName;

    public MqttChannelInfo(String topicName) {
        this.topicName = topicName + "/info";
    }

    @Override
    public void handleMessage(String topic, MqttMessage message) {
        //TODO LOGIC
        System.out.println("Received following message from topic " + topic + " : " + message);
    }

    @Override
    public String getTopicName() {
        return topicName;
    }
}
