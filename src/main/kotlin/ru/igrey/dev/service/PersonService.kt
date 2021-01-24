package ru.igrey.dev.service

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import lombok.extern.slf4j.Slf4j
import mu.KLogging
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import ru.igrey.dev.config.HttpClientConfig.CAS_TOKEN
import ru.igrey.dev.config.HttpClientConfig.PERSON_TOKEN
import ru.igrey.dev.domain.Cas
import ru.igrey.dev.domain.CasUsers
import ru.igrey.dev.domain.Person
import org.apache.http.client.utils.URIBuilder
import ru.igrey.dev.config.HttpClientConfig.PERSON_URI

class PersonService(
    private val casService: CasService
) {

    fun getPersonInfo(fio: String) =
        CasUsers(
            searchByFio(fio)
                .mapNotNull { it.casId }
                .mapNotNull { casService.casUser(it) }
        )

    fun searchByFio(fio: String): List<PersonDto> {
        val builder = URIBuilder("$PERSON_URI/api/v3/persons/search/")
        builder.setParameter("full_name", fio)
        val request = HttpGet(builder.build())
        initHeader(request)
        try {
            HttpClients.createDefault().use { httpClient ->
                httpClient.execute(request).use { response ->
                    val entity = response.entity
                    if (entity != null) {
                        val json = EntityUtils.toString(entity)
                        return Gson().fromJson(json, PersonsResponse::class.java)?.result ?: emptyList()
                    }
                }
            }
        } catch (e: Exception) {
            logger.error("Error search person " + fio + "\n" + e.message)
            return emptyList()
        }

        return emptyList()
    }

    private fun initHeader(request: HttpGet) {
        request.setHeader("Content-Type", "application/json")
        request.setHeader("Accept", "application/json")
        request.setHeader("X-Service-Token", PERSON_TOKEN)
    }

    companion object : KLogging()
}

data class PersonsResponse(
    val result: List<PersonDto>?
)

data class PersonDto(
    @SerializedName(value = "cas_id")
    val casId: Long?
)