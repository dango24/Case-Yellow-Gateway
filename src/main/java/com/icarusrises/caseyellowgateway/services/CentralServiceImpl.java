package com.icarusrises.caseyellowgateway.services;

import com.icarusrises.caseyellowgateway.domain.file.model.FileDownloadMetaData;
import com.icarusrises.caseyellowgateway.domain.test.model.Test;
import com.icarusrises.caseyellowgateway.domain.webSite.model.SpeedTestMetaData;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartRequest;

import java.util.List;

@Service
public class CentralServiceImpl implements CentralService {

    @Override
    public SpeedTestMetaData getNextSpeedTestWebSite() {
        return null;
    }

    @Override
    public List<Test> getAllTests() {
        return null;
    }

    @Override
    public List<FileDownloadMetaData> getFileDownloadMetaData(int numOfComparisonPerTest) {
        return null;
    }

    @Override
    public void saveTest(String payload, MultipartRequest request) {

    }
}
