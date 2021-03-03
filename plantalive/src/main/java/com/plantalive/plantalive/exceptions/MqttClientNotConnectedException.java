package com.plantalive.plantalive.exceptions;

import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttClientNotConnectedException extends MqttException {
    public MqttClientNotConnectedException(int reasonCode) {
        super(reasonCode);
    }

    public MqttClientNotConnectedException(Throwable cause) {
        super(cause);
    }

    public MqttClientNotConnectedException(int reason, Throwable cause) {
        super(reason, cause);
    }

    public MqttClientNotConnectedException() {
        super(0);
    }
}
