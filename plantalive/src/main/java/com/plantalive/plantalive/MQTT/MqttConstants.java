package com.plantalive.plantalive.MQTT;

public class MqttConstants {
    public static final int QOS_FIRE_AND_FORGET = 0;
    public static final int QOS_AT_LEAST_ONCE = 1;
    public static final int QOS_EXACTLY_ONCE = 2;
    public static final String TOPIC_NEW = "new";
    public static final String TARGET_HUMIDITY = "targetMoisture";
    public static final String CURRENT_HUMIDITY = "moisture";
    public static final String TEMPERATURE = "temperature";
    public static final String WATER_LEVEL = "waterlevel";
}
