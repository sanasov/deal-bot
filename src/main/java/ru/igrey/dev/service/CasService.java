package ru.igrey.dev.service;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import ru.igrey.dev.domain.Cas;
import ru.igrey.dev.domain.CasUsers;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by sanasov on 10.04.2017.
 */
public class CasService {

    public CasUsers casUsers(Set<Long> casIds) {
        return new CasUsers(casIds.stream()
                .map(this::casUser)
                .collect(Collectors.toList())
        );
    }

    public Cas casUser(Long casId) {
        HttpGet request = new HttpGet("https://domclick.ru/cas/rest/v1/srv/users/" + casId);
        initHeader(request);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String json = EntityUtils.toString(entity);
                return new Gson().fromJson(json, Cas.class);
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not get Cas by casId: " + casId);
        }
        return null;
    }

    private void initHeader(HttpGet request) {
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
    }
}
