package ru.igrey.dev.service

import org.apache.http.client.methods.HttpGet
import org.apache.http.client.utils.URIBuilder
import org.apache.http.impl.client.HttpClients
import ru.igrey.dev.config.HttpClientConfig
import org.apache.commons.io.FileUtils
import java.io.IOException
import org.apache.http.client.ClientProtocolException
import org.apache.http.HttpResponse
import org.apache.http.client.ResponseHandler
import ru.igrey.dev.domain.PplDocumentType
import java.io.File
import java.net.UnknownHostException

class NoncreditService {

    fun getIEDocuments(casId: String, month: String, type: PplDocumentType): File? {
        return try {
            val httpClient = HttpClients.createDefault()
            httpClient.execute(getRequestIE(casId, month, type), FileDownloadResponseHandler())
        }  catch (e: Exception) {
            throw e
        }
    }

    fun getCompanyDocuments(companyId: String, month: String, type: PplDocumentType): File? {
        return try {
            val httpClient = HttpClients.createDefault()
            httpClient.execute(getRequestCompany(companyId, month, type), FileDownloadResponseHandler())
        } catch (e: Exception) {
            throw e
        }
    }

    private fun getRequestIE(casId: String, month: String, type: PplDocumentType): HttpGet {
        val builder = URIBuilder("${HttpClientConfig.NONCREDIT_URI}/srv/v1/ppl/documents")
        builder.setParameter("casId", casId)
        builder.setParameter("month", month)
        builder.setParameter("type", type.name.toLowerCase())
        val request = HttpGet(builder.build())
        request.setHeader("Content-Type", "application/pdf")
        request.setHeader("x-auth-token", HttpClientConfig.NONCREDIT_TOKEN)
        return request
    }

    private fun getRequestCompany(companyId: String, month: String, type: PplDocumentType): HttpGet {
        val builder = URIBuilder("${HttpClientConfig.NONCREDIT_URI}/srv/v1/ppl/documents/company")
        builder.setParameter("companyId", companyId)
        builder.setParameter("month", month)
        builder.setParameter("type", type.name.toLowerCase())
        val request = HttpGet(builder.build())
        request.setHeader("Content-Type", "application/pdf")
        request.setHeader("x-auth-token", HttpClientConfig.NONCREDIT_TOKEN)
        return request
    }
}

internal class FileDownloadResponseHandler() : ResponseHandler<File> {

    @Throws(ClientProtocolException::class, IOException::class)
    override fun handleResponse(response: HttpResponse): File? {
        if (response.entity.contentLength == 0L) return null
        val source = response.entity.content
        val target = File("target.pdf")
        FileUtils.copyInputStreamToFile(source, target)
        return target
    }
}