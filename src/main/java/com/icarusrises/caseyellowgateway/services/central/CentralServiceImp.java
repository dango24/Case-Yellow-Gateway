package com.icarusrises.caseyellowgateway.services.central;

import com.icarusrises.caseyellowgateway.domain.file.model.FileDownloadMetaData;
import com.icarusrises.caseyellowgateway.domain.test.model.PreSignedUrl;
import com.icarusrises.caseyellowgateway.domain.test.model.Test;
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
    public void sendErrorMessage(String errorMessage) {
//        requestHandler.execute(centralRequests.sendMessage(errorMessage));
    }

    @Override
    public void saveTest(Test test) throws RequestFailureException {
        requestHandler.execute(centralRequests.saveTest(test));
    }

    @Override
    public SpeedTestMetaData getNextSpeedTestWebSite() {
        return requestHandler.execute(centralRequests.getNextSpeedTestWebSite());
    }

    @Override
    public List<FileDownloadMetaData> getNextUrls(int numOfComparisonPerTest) {
        return requestHandler.execute(centralRequests.getNextUrls(numOfComparisonPerTest));
    }

    @Override
    public List<Test> getAllTests() {
        return requestHandler.execute(centralRequests.getAllTests());
    }

    @Override
    public PreSignedUrl generatePreSignedUrl(String userIP, String fileName) {
        return requestHandler.execute(centralRequests.generatePreSignedUrl(userIP, fileName));
    }

    @Override
    public GoogleVisionKey googleVisionKey() {
        return requestHandler.execute(centralRequests.googleVisionKey());
    }
}
