package com.icarusrises.caseyellowgateway.services.analysis;

import com.icarusrises.caseyellowgateway.domain.analysis.googlevision.model.GoogleVisionRequest;
import com.icarusrises.caseyellowgateway.domain.analysis.googlevision.model.OcrResponse;

public interface AnalysisService {
    OcrResponse ocrRequest(GoogleVisionRequest googleVisionRequest);
}
