package com.icarusrises.caseyellowgateway.domain.analysis.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VisionRequest {

    private Image image;
    private ImageContext imageContext;
    private List<Feature> features;

    public VisionRequest() {
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public ImageContext getImageContext() {
        return imageContext;
    }

    public void setImageContext(ImageContext imageContext) {
        this.imageContext = imageContext;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    @Override
    public String toString() {
        return "VisionRequest{" +
                "image=" + image +
                ", imageContext=" + imageContext +
                ", features=" + features +
                '}';
    }
}
