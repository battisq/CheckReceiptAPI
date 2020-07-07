package CheckReceiptAPI;

import CheckReceiptAPI.Resources.Urls;
import CheckReceiptAPI.Results.*;

import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class FNS {

    /**
     * Регистрация нового пользователя. Необходима для получения детальной информации по чекам.
     *
     * @param email
     *        Электронный адрес пользователя
     *
     * @param name
     *        Имя пользователя
     *
     * @param phone
     *        Номер телефона пользователя в формате +79991234567
     *
     * @return
     * @throws IOException
     */
    public static Result registration(String email, String name, String phone) throws IOException {

        if (Urls.isNullOrWhiteSpace(email)) {
            throw new IllegalArgumentException("Недопустимое значение параметра email");
        }

        if (Urls.isNullOrWhiteSpace(name)) {
            throw new IllegalArgumentException("Недопустимое значение параметра name");
        }

        if (Urls.isNullOrWhiteSpace(phone)) {
            throw new IllegalArgumentException("Недопустимое значение параметра phone");
        }

        String json = String.format("{\"email\":\"%s\",\"name\":\"%s\",\"phone\":\"%s\"}",
                email, name, phone);

        StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(Urls.REGISTRATION);
        request.setEntity(entity);

        HttpResponse response = httpClient.execute(request);

        ResultStatusTypes resultStatusTypes = response.getStatusLine().getStatusCode() == 400
            ? ResultStatusTypes.BAD_REQUEST_REGISTRATION
            : ResultStatusTypes.isType(response.getStatusLine().getStatusCode());

        return new Result(response.getStatusLine(), resultStatusTypes);
    }

    /**
     * Аутентификация пользователя. Необходимости в ней нет, но раз ФНС предоставляет, как не воспользоваться.
     *
     * @param phone
     *        Номер телефона пользователя в формате +79991234567
     *
     * @param password
     *        Пароль пользователя, который он получал из СМС при регистрации или восстановлении пароля
     *
     * @return
     * @throws IOException
     */
    public static Result login(String phone, String password) throws IOException {

        if (Urls.isNullOrWhiteSpace(phone)) {
            throw new IllegalArgumentException("Недопустимое значение параметра phone");
        }

        if (Urls.isNullOrWhiteSpace(password)) {
            throw new IllegalArgumentException("Недопустимое значение параметра password");
        }

        byte[] b = String.format("%s:%s", phone, password).getBytes("UTF-8");

        HttpResponse response = Request.Get(Urls.LOGIN)
                .addHeader("Authorization", String.format("Basic %s",Base64.getEncoder().encodeToString(b)))
                .execute()
                .returnResponse();

        return new Result(response.getStatusLine(),
                ResultStatusTypes.isType(response.getStatusLine().getStatusCode()));
    }

    /**
     * Восстановление пароля. Восстановленный пароль придет в СМС.
     *
     * @param phone
     *        Номер телефона в формате +79991234567
     *
     * @return
     * @throws IOException
     */
    public static Result restorePassword(String phone) throws IOException {

        if (Urls.isNullOrWhiteSpace(phone)) {
            throw new IllegalArgumentException("Недопустимое значение параметра phone");
        }

        String json = String.format("{\"phone\":\"%s\"}", phone);

        StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(Urls.RESTORE);
        request.setEntity(entity);

        HttpResponse response = httpClient.execute(request);

        ResultStatusTypes resultStatusTypes = response.getStatusLine().getStatusCode() == 204
                ? ResultStatusTypes.NO_CONTENT_RESTORE_PASSWORD
                : ResultStatusTypes.isType(response.getStatusLine().getStatusCode());

        return new Result(response.getStatusLine(), resultStatusTypes);
    }

    /**
     * Проверить поступил ли чек в ФНС
     *
     * @param fiscalNumber
     *        Фискальный номер, также известный как ФН. Номер состоит из 16 цифр.
     *
     * @param fiscalDocument
     *        Номер фискального документа, также известный как ФД. Состоит максимум из 10 цифр.
     *
     * @param fiscalSign
     *        Фискальный признак документа, также известный как ФП, ФПД. Состоит максимум из 10 цифр.
     *
     * @param date
     *        Дата, указанная в чеке. Секунды не обязательные.
     * @param sum
     *        Сумма, указанная в чеке. Включая копейки.
     *
     * @return
     * @throws IOException
     */
    public static CheckResult checkReceipt(String fiscalNumber, String fiscalDocument,
                                         String fiscalSign, LocalDateTime date,
                                         double sum) throws IOException {

        HttpResponse response = Request.Get(Urls.getCheckUrl(fiscalNumber, fiscalDocument, fiscalSign, date, sum))
                .execute()
                .returnResponse();

        CheckResult res = new CheckResult();

        res.setStatusLine(response.getStatusLine());
        res.setReceiptExists(response.getStatusLine().getStatusCode() == 204);

        ResultStatusTypes resultStatusTypes = response.getStatusLine().getStatusCode() == 204
                ? ResultStatusTypes.NO_CONTENT_CHECKING_EXIST_CHECK
                : response.getStatusLine().getStatusCode() == 400
                    ? ResultStatusTypes.BAD_REQUEST_CHECKING_EXIST_CHECK
                    : ResultStatusTypes.isType(response.getStatusLine().getStatusCode());

        res.setResultStatusTypes(resultStatusTypes);

        return res;
    }

    /**
     * Получить детальную информацию по чеку. Если перед этим не проверялось поступил ли он в ФНС,
     * то при первом обращении данный метод вернет лишь 202 Accepted(Это не правда. Он возвращает ошибку,
     * и чтобы всё хорошо работало мне приходится сначала через телефон активировать) и никакой информации по чеку.
     * При повторном будет вся необходимая информация.
     *
     * @param fiscalNumber
     *        Фискальный номер, также известный как ФН. Номер состоит из 16 цифр.
     *
     * @param fiscalDocument
     *        Номер фискального документа, также известный как ФД. Состоит максимум из 10 цифр.
     *
     * @param fiscalSign
     *        Фискальный признак документа, также известный как ФП, ФПД. Состоит максимум из 10 цифр.
     *
     * @param phone
     *        Номер телефона в формате +79991234567, использованный при регистрации
     *
     * @param password
     *        Пароль пользователя, полученный в СМС
     *
     * @return
     * @throws IOException
     */
    public static ReceiptResult receiveCheck(String fiscalNumber, String fiscalDocument,
                                             String fiscalSign, String phone,
                                             String password) throws IOException {

        if (Urls.isNullOrWhiteSpace(phone)) {
            throw new IllegalArgumentException("Недопустимое значение параметра phone");
        }

        if (Urls.isNullOrWhiteSpace(password)) {
            throw new IllegalArgumentException("Недопустимое значение параметра password");
        }

        byte[] b = String.format("%s:%s", phone, password).getBytes("UTF-8");

        HttpResponse response = Request.Get(Urls.getReceiveUrl(fiscalNumber, fiscalDocument, fiscalSign))
                .addHeader("Authorization", String.format("Basic %s",Base64.getEncoder().encodeToString(b)))
                .addHeader("Device-Id","")
                .addHeader("Device-OS", "")
                .execute()
                .returnResponse();

        ReceiptResult result = new ReceiptResult();
        result.setStatusLine(response.getStatusLine());
        result.setResultStatusTypes(ResultStatusTypes.isType(response.getStatusLine().getStatusCode()));

        if(result.getResultStatusTypes().getCode() != 200)
            throw new IllegalArgumentException(result.getResultStatusTypes().getMessage());

        JSONObject jsonResponse = new JSONObject(IOUtils.toString(response.getEntity().getContent()))
                .getJSONObject("document")
                .getJSONObject("receipt");

        result.setReceipt(new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()
            .fromJson(jsonResponse.toString(), Receipt.class)
        );

        additionalVariables(result);

        return result;
    }

    /**
     *  Добавлены переменные для хранения данных в дробном виде
     */
    private static void additionalVariables(ReceiptResult receiptResult) {

        receiptResult.getReceipt().setSubunitSum(receiptResult.getReceipt().getTotalSum() / 100.00);
        receiptResult.getReceipt().setSubunitSum(receiptResult.getReceipt().getCashTotalSum() / 100.00);
        receiptResult.getReceipt().setECashSubunitSum(receiptResult.getReceipt().getECashTotalSum() / 100.00);
        receiptResult.getReceipt().setSubunitNds10Sum(receiptResult.getReceipt().getTotalNds10Sum() / 100.00);
        receiptResult.getReceipt().setSubunitNds18Sum(receiptResult.getReceipt().getTotalNds18Sum() / 100.00);

        for (int i = 0; i < receiptResult.getReceipt().getItems().size(); i++) {

            receiptResult.getReceipt().getItems().get(i).setSubunitSum(receiptResult.getReceipt().getItems().get(i)
                    .getTotalSum() / 100.00);
            receiptResult.getReceipt().getItems().get(i).setNdsSubunit10Sum(receiptResult.getReceipt().getItems().get(i)
                    .getNdsTotal10Sum() / 100.00);
            receiptResult.getReceipt().getItems().get(i).setNdsSubunit18Sum(receiptResult.getReceipt().getItems().get(i)
                    .getNdsTotal18Sum() / 100.00);
        }
    }
}





