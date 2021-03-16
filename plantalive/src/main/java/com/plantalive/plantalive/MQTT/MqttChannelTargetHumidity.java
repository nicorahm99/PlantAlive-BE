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

public class MqttChannelTargetHumidity extends MqttChannel {
    private final String topicName;
    private final MQTTService mqttService;
    private final Logger logger = LoggerFactory.getLogger(MqttChannelTargetHumidity.class);


    public MqttChannelTargetHumidity(String topicName, MQTTService mqttService) {
        this.topicName = topicName + "/targetHumidity";
        this.mqttService = mqttService;
    }

    @Override
    public void handleMessage(String topic, MqttMessage message) {
        logger.info("Received new targetHumidity from" + topic + " : " + message);
        try {
            JSONObject json = new JSONObject(new String(message.getPayload()));
            mqttService.updateTargetHumidityFrom(topic, json.getDouble(TARGET_HUMIDITY));
        } catch (JSONException e) {
            logger.error("JSON from message" + message + " could not be parsed", e);
            e.printStackTrace();
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