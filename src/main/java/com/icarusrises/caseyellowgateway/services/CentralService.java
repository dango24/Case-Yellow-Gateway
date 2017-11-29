package com.icarusrises.caseyellowgateway.services;

import com.icarusrises.caseyellowgateway.domain.file.model.FileDownloadMetaData;
import com.icarusrises.caseyellowgateway.domain.test.model.Test;
import com.icarusrises.caseyellowgateway.domain.webSite.model.SpeedTestMetaData;
import org.springframework.web.multipart.MultipartRequest;

import java.util.List;

public interface CentralService {
    SpeedTestMetaData getNextSpeedTestWebSite();
    List<Test> getAllTests();
    List<FileDownloadMetaData> getFileDownloadMetaData(int numOfComparisonPerTest);
    void saveTest(String payload, MultipartRequest request);
}
