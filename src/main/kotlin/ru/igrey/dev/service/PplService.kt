package ru.igrey.dev.service

import com.google.gson.Gson
import lombok.extern.slf4j.Slf4j
import org.apache.commons.codec.binary.StringUtils
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClients
import ru.igrey.dev.config.HttpClientConfig.CAS_TOKEN
import ru.igrey.dev.config.HttpClientConfig.NONCREDIT_TOKEN
import ru.igrey.dev.config.HttpClientConfig.NONCREDIT_URI
import ru.igrey.dev.config.HttpClientConfig.NOTIFICATION_URI
import java.io.IOException
import java.net.URLEncoder
import java.util.Arrays

/**
 * Created by sanasov on 10.04.2017.
 */
@Slf4j
class PplService {

    private fun initHeader(request: HttpGet) {
        request.setHeader("Content-Type", "application/json")
        request.setHeader("Accept", "application/json")
        request.setHeader("Authorization", CAS_TOKEN)
    }

    companion object {

        @Throws(IOException::class)
        private fun updateStatus(ppl: UpdatePpl) {
            val request = HttpPost(
                NONCREDIT_URI + "/srv/v1/ppl/persons/${ppl.casId}?status=${ppl.status}&notify=true" + if (ppl.comment == "") "" else "&comment=" + URLEncoder.encode(
                    ppl.comment,
                    "UTF-8"
                )
            )
            request.setHeader("Content-Type", "application/json")
            request.setHeader("Accept", "application/json")
            request.setHeader("x-auth-token", NONCREDIT_TOKEN)

            HttpClients.createDefault().use { httpClient ->
                httpClient.execute(request)
                    .use { response -> println(ppl.casId.toString() + " " + response.statusLine.statusCode) }
            }
        }

        @Throws(IOException::class)
        fun getDocuments(dealId: Long?) {
            val request = HttpGet("http://localhost:8080/api/v1/deals/$dealId/documents")
            request.setHeader("Content-Type", "application/json")
            request.setHeader("Accept", "application/json")
            request.setHeader(
                "x-auth-token",
                "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjYXNJZCI6NjM0NTk1LCJwZXJzb25JZCI6bnVsbCwicm9sZXMiOlsiUk9MRV9QT1JUQUxfVVNFUiJdLCJhY3RpdmUiOnRydWUsImlhdCI6MTU3NDE3NDcyM30.cgi14Otk9uWxzvC1mEbfujk6F9087hPL8jU9a06RkK7W0-0iX6SW1ezRs5Kk1SZvCTN1sOpxhDdsYp80FHWktpXqahCqj8bkfwfjKWNO5N2gS_VL4p55g5v37Mp2Vkmmn63F0YPIF0Q0HW8UUIM6Un5_j6Vg01c686JmAYgIRLofvHo0Q95NJDLkJ8k393VArNq7q0TDswQLzoyJ25WCSfSTS2kYXNHpuB8rxCirFX94u3kCE544Vys1J1gfciuf4N36KdsSzYOJxAw9_PeKfCEGNZOQ_5qHInbsW7xFT7jQRz1FUvgwpF94IB5xaRl5Hz1pWFUU1tiJ1ECVeocKcQ"
            )

            HttpClients.createDefault().use { httpClient ->
                httpClient.execute(request).use { response ->
                    if (response.statusLine.statusCode != 200)
                        println(response.statusLine.statusCode)
                }
            }
        }

        @Throws(IOException::class)
        fun sendSms(phone: String) {
            val request = HttpPost("$NOTIFICATION_URI/v1/sms")
            request.setHeader("Content-Type", "application/json")
            request.setHeader("Accept", "application/json")
            request.setHeader("Authorization", "Basic YXBpOnBhc3M=")

            val entity = StringEntity(Gson().toJson(SmsRequest(phone)), "UTF-8")

            request.entity = entity
            HttpClients.createDefault().use { httpClient ->
                httpClient.execute(request).use { response -> println(phone + " " + response.statusLine.statusCode) }
            }
        }

        @Throws(IOException::class, InterruptedException::class)
        @JvmStatic
        fun main(args: Array<String>) {
            for (updatePpl in UpdatePpl.updatePpls)
                updateStatus(updatePpl)
        }
    }
}

internal class UpdatePpl private constructor(var casId: Long?, var status: String, comment: String) {
    var comment: String

    init {
        val bytes = StringUtils.getBytesUtf8(comment)
        val utf8EncodedString = StringUtils.newStringUtf8(bytes)
        this.comment = utf8EncodedString
    }

    companion object {

        var updatePpls = Arrays.asList<UpdatePpl>()
    }
}

internal class SmsRequest(private val phone: String) {
    private val message: String
    private val notificationType = "NOTIFICATION"
    private val sourceServer = "DEFAULT"
    private val provider = 9000

    init {
        val bytes =
            StringUtils.getBytesUtf8("Конец месяца - время получать выплаты за сделки без ипотеки! Остался один шаг. Проверьте свою почту участника, чтобы сверить расчеты")
        val utf8EncodedString = StringUtils.newStringUtf8(bytes)
        this.message = utf8EncodedString
    }
}
