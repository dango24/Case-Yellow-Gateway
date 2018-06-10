package com.icarusrises.caseyellowgateway.services.central;

import com.icarusrises.caseyellowgateway.domain.file.model.FileDownloadProperties;
import com.icarusrises.caseyellowgateway.domain.test.model.*;
import com.icarusrises.caseyellowgateway.domain.webSite.model.GoogleVisionKey;
import com.icarusrises.caseyellowgateway.domain.webSite.model.SpeedTestMetaData;
import com.icarusrises.caseyellowgateway.exceptions.RequestFailureException;
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

@Service
public class CentralServiceImp implements CentralService {

    @Value("${central_url}")
    private String centralUrl;

    private RequestHandler requestHandler;
    private CentralRequests centralRequests;
    private StatsDClient statsDClient;

    @PostConstruct
    public void init() {
        Retrofit retrofit = RetrofitBuilder.Retrofit(centralUrl)
                                           .build();

        centralRequests = retrofit.create(CentralRequests.class);
    }

    @Autowired
    public CentralServiceImp(RequestHandler requestHandler, StatsDClient statsDClient) {
        this.requestHandler = requestHandler;
        this.statsDClient = statsDClient;
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
    public void saveConnectionDetails(UserDetails userDetails, String user) {
        requestHandler.execute(centralRequests.saveConnectionDetails(user, userDetails));
        statsDClient.increment(String.format("save-connection-details"));
    }

    @Override
    public boolean isUserExist(String userName) {
        return requestHandler.execute(centralRequests.isUserExist(userName, userName));
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
        requestHandler.execute(centralRequests.notifyLastTests());
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
    public Map<String, IdentifierDetails> createIdentifiersDetails() {
        return requestHandler.execute(centralRequests.createIdentifiersDetails());
    }

    @Override
    public Map<String, IdentifierDetails> createIdentifiersDetails(String user) {
        return requestHandler.execute(centralRequests.createIdentifiersDetails(user));
    }

    @Override
    public void startTest(StartTestDetails startTestDetails) {
        requestHandler.execute(centralRequests.startTest(startTestDetails));

        statsDClient.increment(String.format("start-test.%s.%s", startTestDetails.getUser(), startTestDetails.getIdentifier()));
        startTestDetails.getUrls().forEach(url -> statsDClient.increment(String.format("start-sub-test.%s.%s.%s", startTestDetails.getUser(), startTestDetails.getIdentifier(), url)));
    }
}
