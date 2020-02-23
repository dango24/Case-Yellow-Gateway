package com.icarusrises.caseyellowgateway.domain.users;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSignInDetails {

    private String userName;
    private String rawPassword;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String spotMasterReferral;
}
