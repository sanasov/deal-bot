package ru.igrey.dev.service

import com.google.gson.Gson
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import ru.igrey.dev.config.HttpClientConfig.SERVICE_HUB_TOKEN
import ru.igrey.dev.config.HttpClientConfig.SERVICE_HUB_URI
import ru.igrey.dev.domain.ServiceDeal

class ServiceHubService {

    fun getServices(dealId: Long): List<ServiceDeal> {
        val request = HttpGet("$SERVICE_HUB_URI/v1/services/deals/$dealId")
        initHeader(request)
        try {
            HttpClients.createDefault().use { httpClient ->
                httpClient.execute(request).use { response ->
                    val entity = response.entity
                    if (entity != null) {
                        val json = EntityUtils.toString(entity)
                        return listOf(*Gson().fromJson(json, Array<ServiceDeal>::class.java))
                    }
                }
            }
        } catch (e: Exception) {
            return emptyList()
        }

        return emptyList()
    }

    private fun initHeader(request: HttpGet) {
        request.setHeader("Content-Type", "application/json")
        request.setHeader("Accept", "application/json")
        request.setHeader("auth-token", SERVICE_HUB_TOKEN)
    }
}