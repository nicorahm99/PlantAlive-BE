package com.plantalive.plantalive.MQTT;

import com.plantalive.plantalive.service.MQTTService;
import javassist.NotFoundException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;

import static com.plantalive.plantalive.MQTT.MqttConstants.TOPIC_INFO;

public class MqttChannelInfo extends MqttChannel {
    private final String topicName;
    private final Logger logger = LoggerFactory.getLogger(MqttChannelInfo.class);

    private final MQTTService mqttService;

    public MqttChannelInfo(String topicName, MQTTService mqttService) {
        this.topicName = topicName + "/" + TOPIC_INFO;
        this.mqttService = mqttService;
    }

    @Override
    public void handleMessage(String topic, MqttMessage message) {
        logger.info("Received new info from " + topic + " : " + message);
        try {
            JSONObject plantInfoJson = new JSONObject(new String(message.getPayload()));
            mqttService.updatePlantInfo(topic, plantInfoJson);
            mqttService.publishTargetHumidityOnTopic(topic);
        } catch (JSONException e) {
            logger.error("JSON from message" + message + " could not be parsed", e);
            e.printStackTrace();
        } catch (NoSuchElementException e){
            logger.error("Topic or plant not found: " + message, e);
        } catch (NotFoundException e){
            logger.warn("Topic " + topic + "has no plant connected", e);
        } catch (Exception e){
            logger.error("Unknown error: ", e);
        }
    }

    @Override
    public String getTopicName() {
        return topicName;
    }
}
