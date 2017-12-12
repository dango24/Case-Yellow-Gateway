package com.icarusrises.caseyellowgateway.services.infrastrucre;

import com.icarusrises.caseyellowgateway.exceptions.RequestFailureException;
import retrofit2.Call;

import java.io.IOException;
import java.util.Map;

public interface RequestHandler {

    <T extends Object> T execute(Call<T> request) throws RequestFailureException;
    <T extends Object> Map<String, String> getResponseHeaders(Call<T> request) throws RequestFailureException, IOException;
}
