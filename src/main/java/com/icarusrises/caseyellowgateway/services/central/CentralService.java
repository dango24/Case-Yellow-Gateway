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
    boolean isUserExist(String userName);
    GoogleVisionKey googleVisionKey();
    SpeedTestMetaData getNextSpeedTestWebSite();
    PreSignedUrl generatePreSignedUrl(String userIP, String fileName);
    List<Test> getAllTests();
    List<FileDownloadProperties> getNextUrls(int numOfComparisonPerTest);
    Map<String, Long> countIPs();
    Map<String, List<String>> getConnectionDetails();
    Map<String, IdentifierDetails> createIdentifiersDetails();
}
