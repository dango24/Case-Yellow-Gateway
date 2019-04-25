package com.icarusrises.caseyellowgateway.domain.file.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Created by Dan on 04/10/2016.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileDownloadInfo {

    private String fileName;
    private String fileURL;
    private long   fileSizeInBytes;
    private double fileDownloadRateKBPerSec;
    private long   fileDownloadedDurationTimeInMs;
    private long   startDownloadingTimestamp;
    private String traceRouteOutputPreviousDownloadFile;
    private String traceRouteOutputAfterDownloadFile;
    private Map<String, List<String>> headers;
}
