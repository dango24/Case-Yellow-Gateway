package com.icarusrises.caseyellowgateway.services.central;

import com.icarusrises.caseyellowgateway.domain.file.model.FileDownloadMetaData;
import com.icarusrises.caseyellowgateway.domain.test.model.PreSignedUrl;
import com.icarusrises.caseyellowgateway.domain.test.model.Test;
import com.icarusrises.caseyellowgateway.domain.webSite.model.GoogleVisionKey;
import com.icarusrises.caseyellowgateway.domain.webSite.model.SpeedTestMetaData;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface CentralRequests {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("central/additional-time")
    Call<Integer> additionalTimeForWebTestToFinishInSec();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("central/next-web-site")
    Call<SpeedTestMetaData> getNextSpeedTestWebSite();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("central/next-urls")
    Call<List<FileDownloadMetaData>> getNextUrls(@Query("num_of_comparison_per_test") int numOfComparisonPerTest);

    @Multipart
    @POST("central/save-test")
    Call<Void> upload(
            @Part("payload") RequestBody message,
            @Part List<MultipartBody.Part> files
    );


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("central/all-tests")
    Call<List<Test>> getAllTests();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("central/pre-signed-url")
    Call<PreSignedUrl> generatePreSignedUrl(@Query("user_ip") String userIP, @Query("file_name") String fileName);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("central/google-vision-key")
    Call<GoogleVisionKey> googleVisionKey();
}
