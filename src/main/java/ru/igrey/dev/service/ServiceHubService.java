package ru.igrey.dev.service;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import ru.igrey.dev.domain.ServiceDeal;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ru.igrey.dev.config.HttpClientConfig.SERVICE_HUB_TOKEN;

/**
 * Created by sanasov on 10.04.2017.
 */
@RequiredArgsConstructor
public class ServiceHubService {

    public List<ServiceDeal> getServices(Long dealId) {
        HttpGet request = new HttpGet("https://sberbank-partner.ru/service_hub/api/v1/services/deals/" + dealId);
        initHeader(request);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String json = EntityUtils.toString(entity);
                return Arrays.asList(new Gson().fromJson(json, ServiceDeal[].class));
            }
        } catch (Exception e) {
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }


    private void initHeader(HttpGet request) {
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        request.setHeader("auth-token", SERVICE_HUB_TOKEN);

    }
}
