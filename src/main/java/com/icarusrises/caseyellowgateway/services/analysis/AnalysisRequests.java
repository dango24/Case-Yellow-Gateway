package com.icarusrises.caseyellowgateway.services.analysis;

import com.icarusrises.caseyellowgateway.domain.analysis.model.*;
import retrofit2.Call;
import retrofit2.http.*;

public interface AnalysisRequests {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("health")
    Call<Payload> healthCheck();

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
    Call<DescriptionMatch> isDescriptionExist(@Query("user")String user,
                                              @Query("identifier")String identifier,
                                              @Query("startTest")boolean startTest,
                                              @Body GoogleVisionRequest visionRequest);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("parse-html")
    Call<HTMLParserResult> retrieveResultFromHtml(@Query("identifier")String identifier, @Body HTMLParserRequest htmlParserRequest);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("start-button-successfully-found")
    Call<Void> startButtonSuccessfullyFound(@Query("user") String user,
                                            @Query("identifier")String identifier,
                                            @Query("x")int x,
                                            @Query("y")int y,
                                            @Body VisionRequest visionRequest);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("start-button-failed")
    Call<Void> startButtonFailed(@Query("user") String user,
                                 @Query("identifier")String identifier,
                                 @Query("x")int x,
                                 @Query("y")int y,
                                 @Body VisionRequest visionRequest);
}
