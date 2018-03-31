package com.icarusrises.caseyellowgateway.services.analysis;

import com.icarusrises.caseyellowgateway.domain.analysis.model.*;

public interface AnalysisService {
    OcrResponse ocrRequest(GoogleVisionRequest googleVisionRequest);
    ImageClassificationResult classifyImage(String identifier, VisionRequest visionRequest);
    DescriptionMatch isDescriptionExist(String identifier, boolean startTest, GoogleVisionRequest visionRequest);
    HTMLParserResult retrieveResultFromHtml(String identifier, HTMLParserRequest htmlParserRequest);
}
