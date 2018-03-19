package com.icarusrises.caseyellowgateway.services.analysis;

import com.icarusrises.caseyellowgateway.domain.analysis.model.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

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
    Call<ImageClassificationResult> classifyImage(@Query("identifier")String identifier, @Query("md5")String md5, @Body VisionRequest visionRequest);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("is-description-exist")
    Call<DescriptionMatch> isDescriptionExist(@Query("identifier")String identifier,
                                              @Query("startTest")boolean startTest,
                                              @Body GoogleVisionRequest visionRequest);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("parse-html")
    Call<String> retrieveResultFromHtml(@Query("identifier")String identifier, @Query("htmlPayload")String htmlPayload);
}
