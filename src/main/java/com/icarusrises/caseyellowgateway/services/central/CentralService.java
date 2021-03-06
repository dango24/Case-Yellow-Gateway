package com.icarusrises.caseyellowgateway.services.central;


import com.icarusrises.caseyellowgateway.domain.file.model.FileDownloadProperties;
import com.icarusrises.caseyellowgateway.domain.test.model.*;
import com.icarusrises.caseyellowgateway.domain.users.LogData;
import com.icarusrises.caseyellowgateway.domain.webSite.model.GoogleVisionKey;
import com.icarusrises.caseyellowgateway.domain.webSite.model.SpeedTestMetaData;
import com.icarusrises.caseyellowgateway.exceptions.RequestFailureException;

import java.util.List;
import java.util.Map;

/**
 * Created by dango on 6/3/17.
 */
public interface CentralService {
    String healthCheck();
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
    List<String> getChromeOptionsArguments(String user);
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
    void uploadLogData(String user, LogData logData);
    void usersStatistics();
    void buildAllTests();
    void unAnalyzedTests(int periodInDays, int analyzedStateCode);
    int getTestLifeCycle(String userName);
    void updateTestLifeCycle(String userName);
    boolean runClassicTest(String user);
}
