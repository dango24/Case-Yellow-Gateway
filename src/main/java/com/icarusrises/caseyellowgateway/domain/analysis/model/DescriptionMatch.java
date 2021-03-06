package com.icarusrises.caseyellowgateway.domain.analysis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DescriptionMatch {

    private boolean isMatchedDescription;
    private DescriptionLocation descriptionLocation;
}
