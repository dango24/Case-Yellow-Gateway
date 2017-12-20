package com.icarusrises.caseyellowgateway.services.central;


import com.icarusrises.caseyellowgateway.domain.file.model.FileDownloadMetaData;
import com.icarusrises.caseyellowgateway.domain.test.model.PreSignedUrl;
import com.icarusrises.caseyellowgateway.domain.test.model.Test;
import com.icarusrises.caseyellowgateway.domain.webSite.model.GoogleVisionKey;
import com.icarusrises.caseyellowgateway.domain.webSite.model.SpeedTestMetaData;
import com.icarusrises.caseyellowgateway.exceptions.RequestFailureException;

import java.util.List;

/**
 * Created by dango on 6/3/17.
 */
public interface CentralService {
    void sendErrorMessage(String errorMessage);
    void saveTest(Test test) throws RequestFailureException;
    SpeedTestMetaData getNextSpeedTestWebSite();
    List<FileDownloadMetaData> getNextUrls(int numOfComparisonPerTest);
    List<Test> getAllTests();
    PreSignedUrl generatePreSignedUrl(String userIP, String fileName);
    GoogleVisionKey googleVisionKey();
}
