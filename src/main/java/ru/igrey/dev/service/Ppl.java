package ru.igrey.dev.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import static ru.igrey.dev.config.HttpClientConfig.CAS_TOKEN;

/**
 * Created by sanasov on 10.04.2017.
 */
@Slf4j
public class Ppl {

    private static void updateStatus(UpdatePpl ppl) throws IOException {
        HttpPost request = new HttpPost("http://prod.kube.oxy.sber/lego/noncredit-portal/srv/v1/ppl/persons/" + ppl.casId + "?status=" + ppl.status + "&notify=true" + (ppl.comment.equals("") ? "" : "&comment=" + URLEncoder.encode(ppl.comment, "UTF-8")));
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        request.setHeader("x-auth-token", "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjYXNJZCI6MjcyNTY1LCJwZXJzb25JZCI6bnVsbCwicm9sZXMiOlsiUk9MRV9BQlNUUkFDVF9QQVJMSU5FX1VTRVIiLCJST0xFX0FETUlOIiwiUk9MRV9QQVJUTkVSSFVCX1VTRVIiLCJST0xFX1BPTF9QUklWQVRFX0FHRU5UIiwiUk9MRV9QT1JUQUxfVVNFUiJdLCJhY3RpdmUiOnRydWUsImlhdCI6MTU3NDE3NDcyM30.L0w10f1vOV8zLSk0XZtOY2RAp370j9yYU7xtm14xGhgsFRkY20ZL6Gh2VlrqN73cVpnBiSZT4IuIzbbBTBfsrvU9MLfGfUaDuz993Bjv1JB4lE3JuHeZu0FWMs5ViF83cmKSbyUv3ocOMSGopR_Z908xK3_VGZCoq9zktgmpn4Prva3FnxqmfkFuIQBHP2H7KuxyHustJNXIVmNXUwWC43rhtZLq1TMrdmEQUwqumx1FCe1GMHItonBHpXoSj9RCsOCEPvuLyLAnWASklPlZ0601Q76fV6okikFCJeC_PIPu3R3zaDuPBWqE3_vRW50uSLmWYN_lpS8ctAXNf-5Png");

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {
            System.out.println(ppl.casId + " " + response.getStatusLine().getStatusCode());
        }
    }

    public static void getDocuments(Long dealId) throws IOException {
        HttpGet request = new HttpGet("http://localhost:8080/api/v1/deals/" + dealId + "/documents");
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        request.setHeader("x-auth-token", "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjYXNJZCI6NjM0NTk1LCJwZXJzb25JZCI6bnVsbCwicm9sZXMiOlsiUk9MRV9QT1JUQUxfVVNFUiJdLCJhY3RpdmUiOnRydWUsImlhdCI6MTU3NDE3NDcyM30.cgi14Otk9uWxzvC1mEbfujk6F9087hPL8jU9a06RkK7W0-0iX6SW1ezRs5Kk1SZvCTN1sOpxhDdsYp80FHWktpXqahCqj8bkfwfjKWNO5N2gS_VL4p55g5v37Mp2Vkmmn63F0YPIF0Q0HW8UUIM6Un5_j6Vg01c686JmAYgIRLofvHo0Q95NJDLkJ8k393VArNq7q0TDswQLzoyJ25WCSfSTS2kYXNHpuB8rxCirFX94u3kCE544Vys1J1gfciuf4N36KdsSzYOJxAw9_PeKfCEGNZOQ_5qHInbsW7xFT7jQRz1FUvgwpF94IB5xaRl5Hz1pWFUU1tiJ1ECVeocKcQ");

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {
            if (response.getStatusLine().getStatusCode() != 200)
                System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    public static void sendSms(String phone) throws IOException {
        HttpPost request = new HttpPost("http://prod.kube.oxy.sber/javaplat-ns/notification/api/v1/sms");
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        request.setHeader("Authorization", "Basic YXBpOnBhc3M=");

        StringEntity entity = new StringEntity(new Gson().toJson(new SmsRequest(phone)), "UTF-8");

        request.setEntity(entity);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {
            System.out.println(phone + " " + response.getStatusLine().getStatusCode());
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        for (UpdatePpl updatePpl : UpdatePpl.updatePpls)
            updateStatus(updatePpl);
    }

    private void initHeader(HttpGet request) {
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        request.setHeader("Authorization", CAS_TOKEN);
    }
}

class UpdatePpl {
    Long casId;
    String status;
    String comment;

    private UpdatePpl(Long casId, String status, String comment) {
        this.status = status;
        this.casId = casId;
        byte[] bytes = StringUtils.getBytesUtf8(comment);
        String utf8EncodedString = StringUtils.newStringUtf8(bytes);
        this.comment = utf8EncodedString;
    }

    static List<UpdatePpl> updatePpls =
            Arrays.asList(
                    new UpdatePpl(14796263L, "DECLINED", "В отношении проверяемого лица открыты исполнительные производства на сумму превышающую 100 тыс. рублей."),
                    new UpdatePpl(3636591L, "DECLINED", "По указанному ИНН ИП не зарегистрировано"),
                    new UpdatePpl(5405190L, "DECLINED", "Имеется информация о приостановлении операций по банковским счетам."),
                    new UpdatePpl(6658L, "DECLINED", "ИНН принадлежит другому лицу. По данному ИНН имеется информация о приостановлении операций по банковским счетам.")
            );
}

class SmsRequest {
    private String phone;
    private String message;
    private String notificationType = "NOTIFICATION";
    private String sourceServer = "DEFAULT";
    private Integer provider = 9000;

    SmsRequest(String phone) {
        this.phone = phone;
        byte[] bytes = StringUtils.getBytesUtf8("Конец месяца - время получать выплаты за сделки без ипотеки! Остался один шаг. Проверьте свою почту участника, чтобы сверить расчеты");
        String utf8EncodedString = StringUtils.newStringUtf8(bytes);
        this.message = utf8EncodedString;
    }
}
