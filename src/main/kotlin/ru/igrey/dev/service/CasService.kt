package ru.igrey.dev.service

import com.google.gson.Gson
import lombok.extern.slf4j.Slf4j
import mu.KLogging
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import ru.igrey.dev.config.HttpClientConfig.CAS_TOKEN
import ru.igrey.dev.config.HttpClientConfig.CAS_URI
import ru.igrey.dev.domain.Cas
import ru.igrey.dev.domain.CasUsers

@Slf4j
class CasService {
    fun casUserInfo(phoneOrCasId: String): Cas? {
        var phone = phoneOrCasId
        if (phoneOrCasId.length.toLong() == 11L) {
            phone = phoneOrCasId.substring(1)
        }
        return if (phoneOrCasId.length < 10L) casUser(phoneOrCasId.toLong())
        else casUserByPhone(phone)
    }

    fun casUsers(casIds: Set<Long>) = CasUsers(casIds.mapNotNull { casUser(it) })

    fun casUser(casId: Long?): Cas? {
        val request = HttpGet("$CAS_URI/v1/srv/users/" + casId!!)
        initHeader(request)
        try {
            HttpClients.createDefault().use { httpClient ->
                httpClient.execute(request).use { response ->

                    val entity = response.entity
                    if (entity != null) {
                        val json = EntityUtils.toString(entity)
                        return Gson().fromJson(json, Cas::class.java)
                    }
                }
            }
        } catch (e: Exception) {
            logger.error("Error casUser " + casId + "\n" + e.message)
            return null
        }

        return null
    }

    private fun casUserByPhone(phone: String): Cas? {
        val request = HttpGet("$CAS_URI/v1/srv/user?login=$phone")
        initHeader(request)
        try {
            HttpClients.createDefault().use { httpClient ->
                httpClient.execute(request).use { response ->

                    val entity = response.entity
                    if (entity != null) {
                        var json = EntityUtils.toString(entity)
                        json = Gson().toJson(Gson().fromJson(json, Map::class.java)["data"])
                        return Gson().fromJson(json, Cas::class.java)
                    }
                }
            }
        } catch (e: Exception) {
            logger.error("Error casUserByPhone " + phone + "\n" + e.message)
            return null
        }

        return null
    }

    private fun initHeader(request: HttpGet) {
        request.setHeader("Content-Type", "application/json")
        request.setHeader("Accept", "application/json")
        request.setHeader("Authorization", CAS_TOKEN)
    }

    companion object : KLogging()
}