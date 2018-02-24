package com.icarusrises.caseyellowgateway.services.analysis;

import com.icarusrises.caseyellowgateway.domain.analysis.model.GoogleVisionRequest;
import com.icarusrises.caseyellowgateway.domain.analysis.model.ImageClassification;
import com.icarusrises.caseyellowgateway.domain.analysis.model.OcrResponse;
import com.icarusrises.caseyellowgateway.domain.analysis.model.VisionRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import java.util.List;

public interface AnalysisRequests {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("ocr_request")
    Call<OcrResponse> ocrRequest(@Body GoogleVisionRequest googleVisionRequest);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("classify-image")
    Call<List<ImageClassification>> classifyImage(@Body VisionRequest visionRequest);
}
