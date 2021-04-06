package com.plantalive.plantalive.MQTT;

import com.plantalive.plantalive.service.MQTTService;
import javassist.NotFoundException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;

import static com.plantalive.plantalive.MQTT.MqttConstants.TARGET_HUMIDITY;
import static com.plantalive.plantalive.MQTT.MqttConstants.TOPIC_TARGET_HUMIDITY;

public class MqttChannelTargetHumidity extends MqttChannel {
    private final String topicName;
    private final MQTTService mqttService;
    private final Logger logger = LoggerFactory.getLogger(MqttChannelTargetHumidity.class);


    public MqttChannelTargetHumidity(String topicName, MQTTService mqttService) {
        this.topicName = topicName + "/" + TOPIC_TARGET_HUMIDITY;
        this.mqttService = mqttService;
    }

    @Override
    public void handleMessage(String topic, MqttMessage message) {
        logger.info("Received new targetHumidity from" + topic + " : " + message);
        try {
            JSONObject json = new JSONObject(new String(message.getPayload()));
            mqttService.updateTargetHumidityFrom(topic, json.getDouble(TARGET_HUMIDITY));
        } catch (JSONException ignored) {
            // Reflective message form update humidity. Is ignored because it is meant to be used by the Pot and not the Server
        } catch (NoSuchElementException e){
            logger.error("Topic or plant not found: " + message, e);
        } catch (NotFoundException e){
            logger.warn("Topic " + topic + "has no plant connected", e);
        }
    }

    @Override
    public String getTopicName() {
        return topicName;
    }
}