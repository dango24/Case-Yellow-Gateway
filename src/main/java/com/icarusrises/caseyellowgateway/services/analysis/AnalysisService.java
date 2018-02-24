package com.icarusrises.caseyellowgateway.services.analysis;

import com.icarusrises.caseyellowgateway.domain.analysis.model.GoogleVisionRequest;
import com.icarusrises.caseyellowgateway.domain.analysis.model.ImageClassification;
import com.icarusrises.caseyellowgateway.domain.analysis.model.OcrResponse;
import com.icarusrises.caseyellowgateway.domain.analysis.model.VisionRequest;

import java.util.List;

public interface AnalysisService {
    OcrResponse ocrRequest(GoogleVisionRequest googleVisionRequest);
    List<ImageClassification> classifyImage(VisionRequest visionRequest);
}
