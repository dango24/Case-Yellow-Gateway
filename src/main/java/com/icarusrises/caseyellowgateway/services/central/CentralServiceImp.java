package com.icarusrises.caseyellowgateway.services.central;

import com.icarusrises.caseyellowgateway.domain.file.model.FileDownloadMetaData;
import com.icarusrises.caseyellowgateway.domain.test.model.PreSignedUrl;
import com.icarusrises.caseyellowgateway.domain.test.model.Test;
import com.icarusrises.caseyellowgateway.domain.webSite.model.GoogleVisionKey;
import com.icarusrises.caseyellowgateway.domain.webSite.model.SpeedTestMetaData;
import com.icarusrises.caseyellowgateway.exceptions.RequestFailureException;
import com.icarusrises.caseyellowgateway.services.infrastrucre.RequestHandler;
import com.icarusrises.caseyellowgateway.services.infrastrucre.RetrofitBuilder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.List;

import static java.util.stream.Collectors.toMap;

@Service
@Profile("prod")
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
/*

    private UploadTest createUploadTest(Test test) {

        Map<Integer, String> snapshotMap =
            test.getComparisonInfoTests()
                .stream()
                .map(ComparisonInfo::getSpeedTestWebSite)
                .collect(toMap(SpeedTestWebSite::getKey, SpeedTestWebSite::getWebSiteDownloadInfoSnapshot));

        List<MultipartBody.Part> parts =
            snapshotMap.entrySet()
                       .stream()
                       .map(snapshot -> createRequestBodyPart(snapshot.getKey(), snapshot.getValue()))
                       .collect(Collectors.toList());

        RequestBody payload = RequestBody.create(MultipartBody.FORM, new Gson().toJson(test));

        return new UploadTest(payload, parts);
    }
*/

    private MultipartBody.Part createRequestBodyPart(int key, String filePath) {
        File imgFile = new File(filePath);
        RequestBody imgRequestBody = RequestBody.create(MediaType.parse(".png"), imgFile);
        MultipartBody.Part imgPart = MultipartBody.Part.createFormData(String.valueOf(key), imgFile.getName(), imgRequestBody);

        return imgPart;
    }

    private static class UploadTest {

        private RequestBody payload;
        private List<MultipartBody.Part> parts;

        public UploadTest() {
        }

        public UploadTest(RequestBody payload, List<MultipartBody.Part> parts) {
            this.payload = payload;
            this.parts = parts;
        }

        public RequestBody getPayload() {
            return payload;
        }

        public void setPayload(RequestBody payload) {
            this.payload = payload;
        }

        public List<MultipartBody.Part> getParts() {
            return parts;
        }

        public void setParts(List<MultipartBody.Part> parts) {
            this.parts = parts;
        }
    }
}
