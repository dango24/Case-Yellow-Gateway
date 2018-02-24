package com.icarusrises.caseyellowgateway.services.analysis;

import com.icarusrises.caseyellowgateway.domain.analysis.model.GoogleVisionRequest;
import com.icarusrises.caseyellowgateway.domain.analysis.model.ImageClassification;
import com.icarusrises.caseyellowgateway.domain.analysis.model.OcrResponse;
import com.icarusrises.caseyellowgateway.domain.analysis.model.VisionRequest;
import com.icarusrises.caseyellowgateway.services.infrastrucre.RequestHandler;
import com.icarusrises.caseyellowgateway.services.infrastrucre.RetrofitBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class AnalysisServiceImpl implements AnalysisService {

    @Value("${analysis_url}")
    private String analysisUrl;

    private RequestHandler requestHandler;
    private AnalysisRequests analysisRequests;

    @PostConstruct
    public void init() {
        Retrofit retrofit = RetrofitBuilder.Retrofit(analysisUrl)
                                           .build();

        analysisRequests = retrofit.create(AnalysisRequests.class);
    }

    @Autowired
    public void setRequestHandler(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @Override
    public OcrResponse ocrRequest(GoogleVisionRequest googleVisionRequest) {
        return requestHandler.execute(analysisRequests.ocrRequest(googleVisionRequest));
    }

    @Override
    public List<ImageClassification> classifyImage(VisionRequest visionRequest) {
        return requestHandler.execute(analysisRequests.classifyImage(visionRequest));
    }
}
