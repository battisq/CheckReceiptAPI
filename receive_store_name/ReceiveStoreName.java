package com.our_company.comon.libs.receive_store_name;

import okhttp3.*;

import java.io.IOException;

public class ReceiveStoreName {

    public static String getStoreName(String retailInn) {

        String codeSite = null;
        try {
            codeSite = sendPost(retailInn);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        String startSearchTag = "Краткое название";
        String startTag;
        String endTag;

        int startSearchIndex = codeSite.indexOf(startSearchTag);

        boolean isProprietary = startSearchIndex == -1;

        if (isProprietary) {
            startSearchIndex = codeSite.indexOf("<td style=\"text-transform: uppercase;\">");
            startTag = "<strong>";
            endTag = "</strong>";
        } else {
            startTag = "<td colspan=\"3\">";
            endTag = "</td>";
        }

        int startIndex = codeSite.indexOf(startTag, startSearchIndex) + startTag.length();
        int endIndex = codeSite.indexOf(endTag, startSearchIndex);

        String storeName = codeSite.substring(startIndex, endIndex)
                .replaceAll("\\r\\n|\\r|\\n|\\t", "")
                .trim();

        if (isProprietary) {

            String[] fullName = storeName.substring(storeName.indexOf(','),
                    storeName.length() - 1).split(" ");

            storeName = String.format("ИП %s %c.%c",
                    fullName[1],
                    fullName[2].charAt(0),
                    fullName[3].charAt(0));
        }


        return storeName;
    }

    private static final OkHttpClient httpClient = new OkHttpClient();

    private static String sendPost(String retailInn) throws Exception {

        // form parameters
        RequestBody formBody = new FormBody.Builder()
                .add("name", "")
                .add("ogrn", "")
                .add("inn", retailInn)
                .build();

        Request request = new Request.Builder()
                .url("http://online.igk-group.ru/ru/home")
                .addHeader("User-Agent", "OkHttp Bot")
                .post(formBody)
                .build();

        String codeSite = null;

        Response response = httpClient.newCall(request).execute();

        if (!response.isSuccessful())
            throw new IOException("Unexpected code " + response);

        // Get response body
        codeSite = response.body().string();

        return codeSite;
    }
}
