package com.icarusrises.caseyellowgateway.domain.analysis.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Image {

    private String content; // Image In Base64;
    private String md5;

    @Override
    public String toString() {
        return "image hash code: " + content.hashCode();
    }
}
