import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class HttpClient {
    //Setting timeouts
    private static final RequestConfig REQUEST_CONFIG = RequestConfig.custom()
            .setConnectionRequestTimeout(5000)
            .setConnectTimeout(5000)
            .setSocketTimeout(5000)
            .build();
    //Building Http client
    private static final CloseableHttpClient CLIENT = HttpClientBuilder.create()
            .setDefaultRequestConfig(REQUEST_CONFIG)
            .build();

    private static String entityScanner(HttpEntity entity) throws IOException {
        Scanner scanner = new Scanner(entity.getContent());
        StringBuilder stringBuilder = new StringBuilder();
        while (scanner.hasNext()) {
            stringBuilder.append(scanner.nextLine()).append("\n");
        }
        return stringBuilder.toString();
    }

    private static void setContentTypeJSON(HttpRequestBase requestBase) {
        requestBase.setHeader("Accept", "application/json");
        requestBase.setHeader("Content-type", "application/json");
    }

    //Request entity, put request type to parameter(HttpGet/HttpPost....)
    private static HttpEntity requestEntity(HttpRequestBase requestBase) throws IOException {
        CloseableHttpResponse response = CLIENT.execute(requestBase);
        System.out.println(response.getStatusLine() + " *** Request Type: " + requestBase.getMethod());
        return response.getEntity();
    }

    //Send GET
    public static String getRequest(String URI) throws IOException {
        HttpGet request = new HttpGet(URI);//GET request
        HttpEntity entity = requestEntity(request);
        return EntityUtils.toString(entity);
    }

    //Send GET by user id
    public static String getUserById(String URI, String URN, int id) throws IOException {
        return getRequest(URI
                .concat(URN)
                .concat(Integer
                        .toString(id)));
    }

    //Send GET by user username
    public static String getUserByUserName(String URI, String URN, String userName) throws IOException {
        return getRequest(URI
                .concat(URN)
                .concat("?username=")
                .concat(userName));
    }

    //Send POST
    public static String postRequest(String URI, String jsonPath) throws IOException {
        HttpPost request = new HttpPost(URI);//POST request
        setContentTypeJSON(request);
        StringEntity stringEntity = new StringEntity(Files.readString(Paths.get(jsonPath)));
        request.setEntity(stringEntity);
        HttpEntity httpEntity = requestEntity(request);
        return entityScanner(httpEntity);
    }

    //Send PUT
    public static String putRequest(String URI, String jsonPath) throws IOException {
        HttpPut request = new HttpPut(URI);//PUT request
        StringEntity entity = new StringEntity(Files.readString(Paths.get(jsonPath)));
        setContentTypeJSON(request);
        request.setEntity(entity);
        HttpEntity httpEntity = requestEntity(request);
        request.releaseConnection();
        return entityScanner(httpEntity);
    }

    //Send DELETE
    public static boolean deleteRequest(String URI) throws IOException {
        HttpDelete request = new HttpDelete(URI);//DELETE request
        HttpEntity httpEntity = requestEntity(request);
        return httpEntity.getContent().available() == 2;//2 because "{}" left in stream
    }
}