package com.icarusrises.caseyellowgateway.services.infrastrucre;

import com.icarusrises.caseyellowgateway.exceptions.RequestFailureException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Slf4j
@Component
public class RequestHandlerImpl implements RequestHandler {

    @Override
    public <T extends Object> T execute(Call<T> request) throws RequestFailureException {
        try {
            Response<T> response = request.execute();

            if (response.isSuccessful()) {
                return response.body();

            } else {
                throw new RequestFailureException(response.errorBody().string(), response.code());
            }

        } catch (IOException e) {
            throw new RequestFailureException(e.getMessage(), e);
        }
    }

    @Override
    public <T> Map<String, String> getResponseHeaders(Call<T> request) throws RequestFailureException, IOException {
        Response<T> response = request.execute();

        if (response.isSuccessful()) {
            return createHeadersMap(response.headers());

        } else {
            throw new RequestFailureException(response.errorBody().string(), response.code());
        }
    }

    private Map<String, String> createHeadersMap(Headers headers) {

        return headers.names()
                      .stream()
                      .collect(toMap(Function.identity(), name -> headers.values(name).get(0)));
    }

}
