package com.icarusrises.caseyellowgateway.services.central;

import com.icarusrises.caseyellowgateway.domain.file.model.FileDownloadProperties;
import com.icarusrises.caseyellowgateway.domain.test.model.*;
import com.icarusrises.caseyellowgateway.domain.webSite.model.GoogleVisionKey;
import com.icarusrises.caseyellowgateway.domain.webSite.model.SpeedTestMetaData;
import com.icarusrises.caseyellowgateway.exceptions.RequestFailureException;
import com.icarusrises.caseyellowgateway.services.infrastrucre.RequestHandler;
import com.icarusrises.caseyellowgateway.services.infrastrucre.RetrofitBuilder;
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

    @PostConstruct
    public void init() {
        Retrofit retrofit = RetrofitBuilder.Retrofit(centralUrl)
                                           .build();

        centralRequests = retrofit.create(CentralRequests.class);
    }

    @Autowired
    public void setRequestHandler(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @Override
    public void failedTest(FailedTestDetails failedTestDetails) {
        requestHandler.execute(centralRequests.failedTest(failedTestDetails));
    }

    @Override
    public void saveTest(Test test) throws RequestFailureException {
        requestHandler.execute(centralRequests.saveTest(test));
    }

    @Override
    public void saveConnectionDetails(UserDetails userDetails) {
        requestHandler.execute(centralRequests.saveConnectionDetails(userDetails));
    }

    @Override
    public boolean isUserExist(String userName) {
        return requestHandler.execute(centralRequests.isUserExist(userName));
    }

    @Override
    public SpeedTestMetaData getNextSpeedTestWebSite() {
        return requestHandler.execute(centralRequests.getNextSpeedTestWebSite());
    }

    @Override
    public List<FileDownloadProperties> getNextUrls() {
        return requestHandler.execute(centralRequests.getNextUrls());
    }

    @Override
    public List<Test> getAllTests() {
        return requestHandler.execute(centralRequests.getAllTests());
    }

    @Override
    public List<Test> getAllTestsByUser(String user) {
        return requestHandler.execute(centralRequests.getAllTests(user));
    }

    @Override
    public String userLastTest(String user) {
        return requestHandler.execute(centralRequests.userLastTest(user));
    }

    @Override
    public String userLastFailedTest(String user) {
        return requestHandler.execute(centralRequests.userLastFailedTest(user));
    }

    @Override
    public PreSignedUrl generatePreSignedUrl(String fileKey) {
        return requestHandler.execute(centralRequests.generatePreSignedUrl(fileKey));
    }

    @Override
    public GoogleVisionKey googleVisionKey() {
        return requestHandler.execute(centralRequests.googleVisionKey());
    }

    @Override
    public Map<String, Long> countIPs() {
       return requestHandler.execute(centralRequests.countIPs());
    }

    @Override
    public Map<String, List<String>> getConnectionDetails() {
        return requestHandler.execute(centralRequests.getConnectionDetails());
    }

    @Override
    public Map<String, IdentifierDetails> createIdentifiersDetails() {
        return requestHandler.execute(centralRequests.createIdentifiersDetails());
    }

    @Override
    public Map<String, IdentifierDetails> createIdentifiersDetails(String user) {
        return requestHandler.execute(centralRequests.createIdentifiersDetails(user));
    }
}
