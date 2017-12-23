package com.icarusrises.caseyellowgateway.services.analysis;

import com.icarusrises.caseyellowgateway.domain.analysis.googlevision.model.GoogleVisionRequest;
import com.icarusrises.caseyellowgateway.domain.analysis.googlevision.model.OcrResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AnalysisRequests {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("ocr_request")
    Call<OcrResponse> ocrRequest(@Body GoogleVisionRequest googleVisionRequest);
}
