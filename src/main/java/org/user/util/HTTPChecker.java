package org.user.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.SocketTimeoutException;

public class HTTPChecker {

    public static int getStatusCode(String targetUrl) {
        HttpURLConnection con = null;
        try {
            // Abrindo conexão
            URL url = new URL(targetUrl);
            // Servidor deve apenas retornas cabeçalhos, sem body
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("HEAD");
            // Timeout de conexão
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            // Respost/Status
            int code = con.getResponseCode();
            return code;
        } catch (SocketTimeoutException e) {
            return -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -2;
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }
}
