package com.example.NotificationService.adapters;

import com.example.NotificationService.entity.sql.SmsSql;
import com.example.NotificationService.models.requests.ThirdPartyRequestModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ThirdPartyRequestBodyAdapter {

    public static ThirdPartyRequestModel convertSqlToRequestBody(SmsSql smsSql) {

        ThirdPartyRequestModel requestModel = ThirdPartyRequestModel.builder().build();
        requestModel.addSms(smsSql.getMessage());
        requestModel.addDestination(smsSql.getPhoneNumber());
        log.info(requestModel);
        return requestModel;
    }

}
