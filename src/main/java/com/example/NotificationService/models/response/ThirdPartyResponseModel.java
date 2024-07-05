package com.example.NotificationService.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThirdPartyResponseModel {

    @JsonProperty("response")
    private List<ResponseList> response;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseList {

        private String code;
        private String transid;
        private String description;
        private String correlationid;

    }

}
