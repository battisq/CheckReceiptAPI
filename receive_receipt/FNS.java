package com.our_company.comon.libs.receive_receipt;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.our_company.comon.libs.qr_parser.DataQRReceipt;
import com.our_company.comon.libs.receive_store_name.ReceiveStoreName;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FNS {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd.MM.yyyy'+'HH':'mm");

    /**
     * Получить детальную информацию по чеку.
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
     */
    public static ReceiptResult receiveReceipt(String fiscalNumber, String fiscalDocument, String fiscalSign,
                                               Date date, double sum) throws Exception {

        String resultStr = sendPostForReceipt(fiscalNumber, fiscalDocument, fiscalSign, date, sum);
                //new String(post(toFnsParams(fiscalNumber, fiscalDocument, fiscalSign, date, sum)), "UTF-8");

        JSONObject jsonFullResponse = new JSONObject(resultStr);

        ReceiptResult result = new ReceiptResult();

        result.resultCode = jsonFullResponse.getInt("code");
        result.message = result.resultCode == 1 ? "OK" : jsonFullResponse.getString("data");
        result.isSuccess = result.resultCode == 1;

        if (result.resultCode == 1) {
            JSONObject jsonDataReceiptObject = jsonFullResponse.getJSONObject("data").getJSONObject("json");

            result.receipt = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create()
                    .fromJson(jsonDataReceiptObject.toString(), Receipt.class);

            if (result.receipt.getStoreName() == null || result.receipt.getStoreName().compareTo("") == 0)
                result.receipt.setStoreName(ReceiveStoreName.getStoreName(result.receipt.getRetailInn()));

        } else
            result.receipt = null;

        return result;
    }

    public static ReceiptResult receiptResult(DataQRReceipt dataQRReceipt) throws Exception {
        if (dataQRReceipt == null)
            throw new IllegalArgumentException("Данные по чеку не должны быть null");

        return receiveReceipt(dataQRReceipt.getFiscalNumber(),
                dataQRReceipt.getFiscalDocument(),
                dataQRReceipt.getFiscalSign(),
                dataQRReceipt.getDate(),
                dataQRReceipt.getSum());
    }

    public static String toFnsParams(String fiscalNumber, String fiscalDocument, String fiscalSign,
                                     Date date, double sum) {
        return String.format(Locale.ENGLISH, "fn=%s&fd=%s&fp=%s&n=1&s=%.2f&t=%s&qr=0",
                fiscalNumber, fiscalDocument, fiscalSign, sum, FORMATTER.format(date));
    }

    private static String toFnsParams(DataQRReceipt dataQRReceipt) {
        return toFnsParams(dataQRReceipt.getFiscalNumber(),
                dataQRReceipt.getFiscalDocument(),
                dataQRReceipt.getFiscalSign(),
                dataQRReceipt.getDate(),
                dataQRReceipt.getSum());
    }

    private static byte[] post(String params) {
        String myURL = "https://proverkacheka.com/check/get";
        byte[] data = null;
        InputStream is = null;

        try {
            URL url = new URL(myURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setRequestProperty("Content-Length", "" + params.getBytes().length);
            OutputStream os = null;

            os = conn.getOutputStream();
            data = params.getBytes(StandardCharsets.UTF_8);
            os.write(data);
            data = null;

            conn.connect();
            int responseCode = conn.getResponseCode();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            is = conn.getInputStream();

            byte[] buffer = new byte[8192]; // Такого вот размера буфер

            // Далее, например, вот так читаем ответ
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            data = baos.toByteArray();
        } catch (Exception ex) {
            Log.i("ex", ex.getMessage());
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (Exception ignored) {
            }
        }
        return data;
    }

    private static final OkHttpClient httpClient = new OkHttpClient();

    private static String sendPostForReceipt(String fiscalNumber, String fiscalDocument, String fiscalSign,
                                             Date date, double sum) throws Exception {

        // form parameters
        RequestBody formBody = new FormBody.Builder()
                .add("fn", fiscalNumber)
                .add("fd", fiscalDocument)
                .add("fp", fiscalSign)
                .add("n", "1")
                .add("s", String.format(Locale.ENGLISH, "%.2f", sum))
                .add("t", FORMATTER.format(date))
                .add("qr", "0")
                .build();

        Request request = new Request.Builder()
                .url("https://proverkacheka.com/check/get")
                .addHeader("User-Agent", "OkHttp Bot")
                .post(formBody)
                .build();

        Response response = null;

        try {
            response = httpClient.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
        }
        catch (Exception ex) {

        }

        return Objects.requireNonNull(response != null ? response.body() : null).string();
    }
}
