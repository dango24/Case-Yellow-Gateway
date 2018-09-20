package com.icarusrises.caseyellowgateway.services.analysis;

import com.icarusrises.caseyellowgateway.domain.analysis.model.*;
import com.icarusrises.caseyellowgateway.services.infrastrucre.RequestHandler;
import com.icarusrises.caseyellowgateway.services.infrastrucre.RetrofitBuilder;
import com.timgroup.statsd.StatsDClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;

import javax.annotation.PostConstruct;

@Service
public class AnalysisServiceImpl implements AnalysisService {

    @Value("${analysis_url}")
    private String analysisUrl;

    private RequestHandler requestHandler;
    private AnalysisRequests analysisRequests;
    private StatsDClient statsDClient;

    @PostConstruct
    public void init() {
        Retrofit retrofit = RetrofitBuilder.Retrofit(analysisUrl)
                                           .build();

        analysisRequests = retrofit.create(AnalysisRequests.class);
    }

    @Autowired
    public AnalysisServiceImpl(RequestHandler requestHandler, StatsDClient statsDClient) {
        this.requestHandler = requestHandler;
        this.statsDClient = statsDClient;
    }

    @Override
    public String healthCheck() {
        Payload payload = requestHandler.execute(analysisRequests.healthCheck());
        return payload.getData();
    }

    @Override
    public OcrResponse ocrRequest(GoogleVisionRequest googleVisionRequest) {
        return requestHandler.execute(analysisRequests.ocrRequest(googleVisionRequest));
    }

    @Override
    public ImageClassificationResult classifyImage(String user, String identifier, VisionRequest visionRequest) {
        ImageClassificationResult imageClassificationResult = requestHandler.execute(analysisRequests.classifyImage(identifier, visionRequest.getImage().getMd5(), visionRequest));
        statsDClient.increment(String.format("classify-image.%s.%s.%s", user, identifier, imageClassificationResult.getStatus()));

        return imageClassificationResult;
    }

    @Override
    public DescriptionMatch isDescriptionExist(String user, String identifier, boolean startTest, GoogleVisionRequest visionRequest) {
        DescriptionMatch descriptionMatch = requestHandler.execute(analysisRequests.isDescriptionExist(user, identifier, startTest, visionRequest));
        String testState = startTest ? "start.test" : "end.test";
        statsDClient.increment(String.format("is-description-exist.%s.%s.%s", user, identifier, testState));

        return descriptionMatch;
    }

    @Override
    public HTMLParserResult retrieveResultFromHtml(String user, String identifier, HTMLParserRequest htmlParserRequest) {
        HTMLParserResult htmlParserResult = requestHandler.execute(analysisRequests.retrieveResultFromHtml(identifier, htmlParserRequest));
        statsDClient.increment(String.format("parse-html.%s.%s", user, identifier));

        return htmlParserResult;
    }

    @Override
    public void startButtonSuccessfullyFound(String user, String identifier, int x, int y, VisionRequest visionRequest) {
        statsDClient.increment(String.format("start-button-successfully-found.%s.%s", user, identifier));
        requestHandler.execute(analysisRequests.startButtonSuccessfullyFound(user, identifier, x, y, visionRequest));
    }

    @Override
    public void startButtonFailed(String user, String identifier, int x, int y, VisionRequest visionRequest) {
        statsDClient.increment(String.format("start-button-failed.%s.%s", user, identifier));
        requestHandler.execute(analysisRequests.startButtonFailed(user, identifier, x, y, visionRequest));
    }
}
