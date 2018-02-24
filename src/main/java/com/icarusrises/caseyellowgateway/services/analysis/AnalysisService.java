package com.icarusrises.caseyellowgateway.services.analysis;

import com.icarusrises.caseyellowgateway.domain.analysis.model.*;

public interface AnalysisService {
    OcrResponse ocrRequest(GoogleVisionRequest googleVisionRequest);
    ImageClassificationStatus classifyImage(VisionRequest visionRequest);
}
