package com.icarusrises.caseyellowgateway.services.central;

import com.icarusrises.caseyellowgateway.domain.file.model.FileDownloadProperties;
import com.icarusrises.caseyellowgateway.domain.test.model.*;
import com.icarusrises.caseyellowgateway.domain.webSite.model.GoogleVisionKey;
import com.icarusrises.caseyellowgateway.domain.webSite.model.SpeedTestMetaData;
import com.icarusrises.caseyellowgateway.persistence.model.UserDAO;
import com.icarusrises.caseyellowgateway.services.analysis.Payload;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

import static com.icarusrises.caseyellowgateway.controllers.CentralController.USER_HEADER;

public interface CentralRequests {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("central/health")
    Call<Payload> healthCheck();

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
    Call<SpeedTestMetaData> getNextSpeedTestWebSite(@Header(USER_HEADER) String userHeader);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("central/next-urls")
    Call<List<FileDownloadProperties>> getNextUrls(@Header(USER_HEADER) String userHeader);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("central/save-test")
    Call<Void> saveTest(@Header(USER_HEADER) String userHeader, @Body Test test);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("central/investigate-test-ratio")
    Call<Void> investigateSuspiciousTestRatio(@Query("outliar_ratio") String outliarRatio, @Query("hours")String hours);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("central/all-tests")
    Call<List<Test>> getAllTests(@Header(USER_HEADER) String userHeader);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("central/all-user-tests")
    Call<List<Test>> getAllTests(@Header(USER_HEADER) String userHeader, @Query("user") String user);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("central/failed-test")
    Call<Void> failedTest(@Header(USER_HEADER) String userHeader, @Body FailedTestDetails failedTestDetails);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("central/pre-signed-url")
    Call<PreSignedUrl> generatePreSignedUrl(@Header(USER_HEADER) String userHeader, @Query("file_key") String fileKey);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("central/google-vision-key")
    Call<GoogleVisionKey> googleVisionKey(@Header(USER_HEADER) String userHeader);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("central/is-user-exist")
    Call<Boolean> isUserExist(@Header(USER_HEADER) String userHeader, @Query("user_name")String userName);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("central/chrome-options-arguments")
    Call<List<String>> getChromeOptionsArguments(@Query("user_name") String userHeader);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("central/test-life-cycle")
    Call<Integer> getTestLifeCycle(@Query("user_name") String userHeader);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("central/update-test-life-cycle")
    Call<Void> updateTestLifeCycle(@Query("user_name") String userHeader);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("central/connection-details")
    Call<Map<String, List<String>>> getConnectionDetails(@Header(USER_HEADER) String userHeader);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("central/save-user-details")
    Call<Void> saveConnectionDetails(@Header(USER_HEADER) String userHeader, @Body UserDetails userDetails);

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
    Call<Map<String, IdentifierDetails>> createIdentifiersDetails(@Query("filter") String filter);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("statistics/identifiers-details/{user}")
    Call<Map<String, IdentifierDetails>> createIdentifiersDetails(@Path("user") String user, @Query("filter") String filter);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("statistics/user-last-test")
    Call<UserLastTest> userLastTest(@Query("user") String user);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("statistics/user-last-failed-test")
    Call<UserLastTest> userLastFailedTest(@Query("user") String user);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("statistics/count-user-tests")
    Call<Map<String, Long>> countUserTests();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("statistics/notify-last-tests")
    Call<Void> notifyLastTests(@Body List<UserDAO> users);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("statistics/start-test")
    Call<Void> startTest(@Body StartTestDetails startTestDetails);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("statistics/users-last-test")
    Call<UsersLastTest> usersLastTest(@Query("period") int lastTimeInHours, @Body List<UserDAO> users);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("statistics/user-mean-rate")
    Call<Map<String, String>> getUserMeanRate(@Query("user") String user);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("statistics/file-download-rate-mean")
    Call<Map<String, String>> meanFileDownloadRate();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("statistics/users-statistics")
    Call<Void> usersStatistics(@Body List<UserDAO> users);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("statistics/build-all-tests")
    Call<Void> buildAllTests();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("central/unanalyzed-tests")
    Call<Void> unAnalyzedTests();
}