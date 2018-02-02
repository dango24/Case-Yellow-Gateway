package com.icarusrises.caseyellowgateway.services.central;


import com.icarusrises.caseyellowgateway.domain.file.model.FileDownloadProperties;
import com.icarusrises.caseyellowgateway.domain.test.model.FailedTestDetails;
import com.icarusrises.caseyellowgateway.domain.test.model.IdentifierDetails;
import com.icarusrises.caseyellowgateway.domain.test.model.PreSignedUrl;
import com.icarusrises.caseyellowgateway.domain.test.model.Test;
import com.icarusrises.caseyellowgateway.domain.webSite.model.GoogleVisionKey;
import com.icarusrises.caseyellowgateway.domain.webSite.model.SpeedTestMetaData;
import com.icarusrises.caseyellowgateway.exceptions.RequestFailureException;

import java.util.List;
import java.util.Map;

/**
 * Created by dango on 6/3/17.
 */
public interface CentralService {
    void failedTest(FailedTestDetails failedTestDetails);
    void saveTest(Test test) throws RequestFailureException;
    SpeedTestMetaData getNextSpeedTestWebSite();
    List<Test> getAllTests();
    List<FileDownloadProperties> getNextUrls(int numOfComparisonPerTest);
    GoogleVisionKey googleVisionKey();
    PreSignedUrl generatePreSignedUrl(String userIP, String fileName);
    Map<String, Long> countIPs();
    Map<String, IdentifierDetails> createIdentifiersDetails();
}
