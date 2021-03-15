package com.plantalive.plantalive.MQTT;

import com.plantalive.plantalive.persistence.TopicDAO;
import com.plantalive.plantalive.persistence.TopicRepository;
import com.plantalive.plantalive.service.MQTTService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqttChannelNew extends MqttChannel{
    private static final Logger logger = LoggerFactory.getLogger(MqttChannelNew.class);

    private final TopicDAO topic;
    private final MQTTService mqttService;
    private final TopicRepository topicRepository;

    @Autowired
    public MqttChannelNew(MQTTService mqttService, TopicRepository topicRepository) {
        this.mqttService = mqttService;
        this.topicRepository = topicRepository;
        this.topic = new TopicDAO(MqttConstants.TOPIC_NEW);
    }

    @Override
    public void handleMessage(String ignored, MqttMessage message) {
        logger.info("Handle Message {} on Topic New", message);
        String newTopicName = message.toString();
        TopicDAO topic = topicRepository.save(new TopicDAO(newTopicName));
        MqttChannel newChannel = new MqttChannelCustom(topic);
        try {
            logger.info("Trying to subscribe to new Topic: {}", topic.getTopicName());
            mqttService.subscribeTopic(newChannel);
        } catch (MqttException e) {
            //TODO Retry
            logger.error("Topic of Plant with MAC " + newTopicName + "could not be subscribed", e);
        }
    }

    @Override
    public TopicDAO getTopic() {
        return topic;
    }

}
