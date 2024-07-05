package com.example.NotificationService.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;

@Data
@Builder
@AllArgsConstructor
public class ThirdPartyRequestModel {

    private final  String deliverychannel = "sms";
    private Map<String, Channel> channels;
    private List<Destination> destination;

    public ThirdPartyRequestModel() {
        this.channels = new HashMap<>();
        this.destination = new ArrayList<>();
    }

    public void addSms(String text) {
        if(channels == null) {
            channels = new HashMap<>();
        }
        Channel smsChannel = new Channel();
        smsChannel.setText(text);
        channels.put("sms", smsChannel);
    }

    public void addDestination(String msisdn) {
        if(destination == null) {
            destination = new ArrayList<>();
        }
        Destination dest = new Destination();
        dest.getMsisdn().add(msisdn);
        dest.generateCorrelationId();
        destination.add(dest);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Channel {

        private String text;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Destination {

        private List<String> msisdn = new ArrayList<>();
        private String correlationId;

        public void generateCorrelationId() {
            this.correlationId = UUID.randomUUID().toString();
        }

    }

}
