package com.icarusrises.caseyellowgateway.domain.analysis.model;

import java.util.ArrayList;
import java.util.List;

public class GoogleVisionRequest {

    private List<VisionRequest> requests;

    public GoogleVisionRequest() {
        requests = new ArrayList<>();
    }

    public List<VisionRequest> getRequests() {
        return requests;
    }

    public void setRequests(List<VisionRequest> requests) {
        this.requests = requests;
    }

    public void addRequest(VisionRequest visionRequest) {
        requests.add(visionRequest);
    }

    @Override
    public String toString() {
        return "GoogleVisionRequest{" +
                "requests=" + requests +
                '}';
    }
}


