package com.icarusrises.caseyellowgateway.services.central;

import com.icarusrises.caseyellowgateway.domain.file.model.FileDownloadProperties;
import com.icarusrises.caseyellowgateway.domain.test.model.*;
import com.icarusrises.caseyellowgateway.domain.users.LogData;
import com.icarusrises.caseyellowgateway.domain.users.UserService;
import com.icarusrises.caseyellowgateway.domain.webSite.model.GoogleVisionKey;
import com.icarusrises.caseyellowgateway.domain.webSite.model.SpeedTestMetaData;
import com.icarusrises.caseyellowgateway.exceptions.RequestFailureException;
import com.icarusrises.caseyellowgateway.persistence.model.UserDAO;
import com.icarusrises.caseyellowgateway.services.analysis.Payload;
import com.icarusrises.caseyellowgateway.services.infrastrucre.RequestHandler;
import com.icarusrises.caseyellowgateway.services.infrastrucre.RetrofitBuilder;
import com.timgroup.statsd.StatsDClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CentralServiceImp implements CentralService {

    @Value("${central_url}")
    private String centralUrl;

    private RequestHandler requestHandler;
    private UserService userService;
    private CentralRequests centralRequests;
    private StatsDClient statsDClient;

    @PostConstruct
    public void init() {
        Retrofit retrofit = RetrofitBuilder.Retrofit(centralUrl)
                                           .build();

        centralRequests = retrofit.create(CentralRequests.class);
    }

    @Autowired
    public CentralServiceImp(RequestHandler requestHandler, StatsDClient statsDClient, UserService userService) {
        this.requestHandler = requestHandler;
        this.statsDClient = statsDClient;
        this.userService = userService;
    }

    @Override
    public String healthCheck() {
        Payload payload = requestHandler.execute(centralRequests.healthCheck());

        return payload.getData();
    }

    @Override
    public void failedTest(FailedTestDetails failedTestDetails, String user) {
        requestHandler.execute(centralRequests.failedTest(user, failedTestDetails));
        statsDClient.increment(String.format("failed-test.%s.%s", failedTestDetails.getUser(), failedTestDetails.getIdentifier()));
    }

    @Override
    public void saveTest(Test test, String user) throws RequestFailureException {
        requestHandler.execute(centralRequests.saveTest(user, test));
    }

    @Override
    public void uploadLogData(String user, LogData logData) {
        requestHandler.execute(centralRequests.uploadLogData(user, logData));
    }

    @Override
    public void saveConnectionDetails(UserDetails userDetails, String user) {
        requestHandler.execute(centralRequests.saveConnectionDetails(user, userDetails));
        statsDClient.increment(String.format("save-connection-details"));
    }

    @Override
    public boolean isUserExist(String userName) {
        return requestHandler.execute(centralRequests.isUserExist(userName, userName));
    }

    @Override
    public int getTestLifeCycle(String userName) {
        return requestHandler.execute(centralRequests.getTestLifeCycle(userName));
    }

    @Override
    public void updateTestLifeCycle(String userName) {
        requestHandler.execute(centralRequests.updateTestLifeCycle(userName));
    }

    @Override
    public boolean runClassicTest(String user) {
        return requestHandler.execute(centralRequests.runClassicTest());
    }

    @Override
    public SpeedTestMetaData getNextSpeedTestWebSite(String user) {
        return requestHandler.execute(centralRequests.getNextSpeedTestWebSite(user));
    }

    @Override
    public List<FileDownloadProperties> getNextUrls(String user) {
        return requestHandler.execute(centralRequests.getNextUrls(user));
    }

    @Override
    public List<String> getChromeOptionsArguments(String user) {
        return requestHandler.execute(centralRequests.getChromeOptionsArguments(user));
    }

    @Override
    public List<Test> getAllTests(String user) {
        return requestHandler.execute(centralRequests.getAllTests(user));
    }

    @Override
    public List<Test> getAllTestsByUser(String user) {
        return requestHandler.execute(centralRequests.getAllTests(user, user));
    }

    @Override
    public UserLastTest userLastTest(String user) {
        return requestHandler.execute(centralRequests.userLastTest(user));
    }

    @Override
    public UserLastTest userLastFailedTest(String user) {
        return requestHandler.execute(centralRequests.userLastFailedTest(user));
    }

    @Override
    public Map<String, Long> countUserTests() {
        return requestHandler.execute(centralRequests.countUserTests());
    }

    @Override
    public void notifyLastTests() {
        List<UserDAO> users =
                userService.getAllUsers()
                           .stream()
                           .filter(UserDAO::isEnabled)
                           .collect(Collectors.toList());

        requestHandler.execute(centralRequests.notifyLastTests(users));
    }

    @Override
    public void investigateSuspiciousTestRatio(String outliarRatio, String hours) {
        requestHandler.execute(centralRequests.investigateSuspiciousTestRatio(outliarRatio, hours));
    }

    @Override
    public void usersStatistics() {
        List<UserDAO> users =
                userService.getAllUsers()
                           .stream()
                           .filter(UserDAO::isEnabled)
                           .collect(Collectors.toList());

        requestHandler.execute(centralRequests.usersStatistics(users));
    }

    @Override
    public void buildAllTests() {
        requestHandler.execute(centralRequests.buildAllTests());
    }

    @Override
    public void unAnalyzedTests(int periodInDays, int analyzedStateCode) {
        requestHandler.execute(centralRequests.unAnalyzedTests(periodInDays, analyzedStateCode));
    }

    @Override
    public UsersLastTest usersLastTest(int lastTimeInHours) {
        return requestHandler.execute(centralRequests.usersLastTest(lastTimeInHours));
    }

    @Override
    public Map<String, String> getUserMeanRate(String user) {
        return requestHandler.execute(centralRequests.getUserMeanRate(user));
    }

    @Override
    public Map<String, String> meanFileDownloadRate() {
        return requestHandler.execute(centralRequests.meanFileDownloadRate());
    }

    @Override
    public PreSignedUrl generatePreSignedUrl(String fileKey, String user) {
        return requestHandler.execute(centralRequests.generatePreSignedUrl(user, fileKey));
    }

    @Override
    public GoogleVisionKey googleVisionKey(String user) {
        return requestHandler.execute(centralRequests.googleVisionKey(user));
    }

    @Override
    public Map<String, Long> countIPs() {
       return requestHandler.execute(centralRequests.countIPs());
    }

    @Override
    public Map<String, List<String>> getConnectionDetails(String user) {
        return requestHandler.execute(centralRequests.getConnectionDetails(user));
    }

    @Override
    public Map<String, IdentifierDetails> createIdentifiersDetails(String filter) {
        return requestHandler.execute(centralRequests.createIdentifiersDetails(filter));
    }

    @Override
    public Map<String, IdentifierDetails> createIdentifiersDetails(String user, String filter) {
        return requestHandler.execute(centralRequests.createIdentifiersDetails(user, filter));
    }

    @Override
    public void startTest(StartTestDetails startTestDetails) {
        requestHandler.execute(centralRequests.startTest(startTestDetails));

        statsDClient.increment(String.format("start-test.%s.%s", startTestDetails.getUser(), startTestDetails.getIdentifier()));
        startTestDetails.getUrls().forEach(url -> statsDClient.increment(String.format("start-sub-test.%s.%s.%s", startTestDetails.getUser(), startTestDetails.getIdentifier(), url)));
    }
}
