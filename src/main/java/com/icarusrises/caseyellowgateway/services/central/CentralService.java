package com.icarusrises.caseyellowgateway.services.central;


import com.icarusrises.caseyellowgateway.domain.file.model.FileDownloadProperties;
import com.icarusrises.caseyellowgateway.domain.test.model.*;
import com.icarusrises.caseyellowgateway.domain.webSite.model.GoogleVisionKey;
import com.icarusrises.caseyellowgateway.domain.webSite.model.SpeedTestMetaData;
import com.icarusrises.caseyellowgateway.exceptions.RequestFailureException;

import java.util.List;
import java.util.Map;

/**
 * Created by dango on 6/3/17.
 */
public interface CentralService {
    void failedTest(FailedTestDetails failedTestDetails, String user);
    void saveTest(Test test, String user) throws RequestFailureException;
    void saveConnectionDetails(UserDetails userDetails, String user);
    void startTest(StartTestDetails startTestDetails);
    boolean isUserExist(String userName);
    GoogleVisionKey googleVisionKey(String user);
    SpeedTestMetaData getNextSpeedTestWebSite(String user);
    PreSignedUrl generatePreSignedUrl(String fileKey, String user);
    List<Test> getAllTests(String user);
    List<FileDownloadProperties> getNextUrls(String user);
    Map<String, Long> countIPs();
    Map<String, List<String>> getConnectionDetails(String user);
    Map<String, IdentifierDetails> createIdentifiersDetails(String filter);
    Map<String, IdentifierDetails> createIdentifiersDetails(String user, String filter);
    List<Test> getAllTestsByUser(String user);
    UserLastTest userLastTest(String user);
    UserLastTest userLastFailedTest(String user);
    Map<String,Long> countUserTests();
    void notifyLastTests();
    UsersLastTest usersLastTest(int lastTimeInHours);
    Map<String, String> getUserMeanRate(String user);
    Map<String,String> meanFileDownloadRate();
    void investigateSuspiciousTestRatio(String outliarRatio, String hours);
    void usersStatistics();
    void buildAllTests();
}
