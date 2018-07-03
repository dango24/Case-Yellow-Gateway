package com.icarusrises.caseyellowgateway.domain.test.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LastUserTest {

    public static SimpleDateFormat DATE_FORMAT;
    public static SimpleDateFormat DAY_FORMAT;

    static {
        DATE_FORMAT = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        DAY_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("Asia/Jerusalem"));
        DAY_FORMAT.setTimeZone(TimeZone.getTimeZone("Asia/Jerusalem"));
    }

    private String user;
    private long timestamp;
    private String phone;

    public LastUserTest(String user, long timestamp) {
        this.user = user;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        String phoneNumber = null;

        if (StringUtils.isNotEmpty(phone)) {
            phoneNumber = String.format("%s-%s", phone.substring(0, 3), phone.substring(3));
        }

        return String.format("%s last test: %s, phone: %s", user, DATE_FORMAT.format(timestamp), phoneNumber);
    }
}
