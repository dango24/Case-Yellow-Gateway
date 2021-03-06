package com.icarusrises.caseyellowgateway.domain.test.model;


import com.icarusrises.caseyellowgateway.domain.file.model.FileDownloadInfo;
import com.icarusrises.caseyellowgateway.domain.webSite.model.SpeedTestWebSite;

/**
 * Created by Dan on 12/10/2016.
 */
public class ComparisonInfo {

    private SpeedTestWebSite speedTestWebSite;
    private FileDownloadInfo fileDownloadInfo;

    public ComparisonInfo() {
    }

    public ComparisonInfo(SpeedTestWebSite speedTestWebSite, FileDownloadInfo fileDownloadInfo) {
        this.speedTestWebSite = speedTestWebSite;
        this.fileDownloadInfo = fileDownloadInfo;
    }

    public SpeedTestWebSite getSpeedTestWebSite() {
        return speedTestWebSite;
    }

    public FileDownloadInfo getFileDownloadInfo() {
        return fileDownloadInfo;
    }

    public void setSpeedTestWebSite(SpeedTestWebSite speedTestWebSite) {
        this.speedTestWebSite = speedTestWebSite;
    }

    public void setFileDownloadInfo(FileDownloadInfo fileDownloadInfo) {
        this.fileDownloadInfo = fileDownloadInfo;
    }

    @Override
    public String toString() {
        return "ComparisonInfo{" +
                "speedTestWebSite=" + speedTestWebSite +
                ", fileDownloadInfo=" + fileDownloadInfo +
                '}';
    }
}
