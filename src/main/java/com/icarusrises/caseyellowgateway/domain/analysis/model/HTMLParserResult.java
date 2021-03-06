package com.icarusrises.caseyellowgateway.domain.analysis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HTMLParserResult {

    private String result;
    private boolean succeed;
}
