package com.icarusrises.caseyellowgateway.services.central;

import com.icarusrises.caseyellowgateway.domain.file.model.FileDownloadProperties;
import com.icarusrises.caseyellowgateway.domain.test.model.*;
import com.icarusrises.caseyellowgateway.domain.webSite.model.GoogleVisionKey;
import com.icarusrises.caseyellowgateway.domain.webSite.model.SpeedTestMetaData;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

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
    Call<List<FileDownloadProperties>> getNextUrls();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("central/save-test")
    Call<Void> saveTest(@Body Test test);

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
    @GET("central/all-user-tests")
    Call<List<Test>> getAllTests(@Query("user") String user);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("central/failed-test")
    Call<Void> failedTest(@Body FailedTestDetails failedTestDetails);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("central/pre-signed-url")
    Call<PreSignedUrl> generatePreSignedUrl(@Query("file_key") String fileKey);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("central/google-vision-key")
    Call<GoogleVisionKey> googleVisionKey();

    @Headers({
        "Accept: application/json",
        "Content-Type: application/json"
    })
    @GET("central/is-user-exist")
    Call<Boolean> isUserExist(@Query("user_name")String userName);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("central/connection-details")
    Call<Map<String, List<String>>> getConnectionDetails();


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("central/save-user-details")
    Call<Void> saveConnectionDetails(@Body UserDetails userDetails);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("statistics/count-ips")
    Call<Map<String, Long>> countIPs();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("statistics/identifiers-details")
    Call<Map<String, IdentifierDetails>> createIdentifiersDetails();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("statistics/identifiers-details/{user}")
    Call<Map<String, IdentifierDetails>> createIdentifiersDetails(@Path("user") String user);
}