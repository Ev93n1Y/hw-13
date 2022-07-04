import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
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
    static RequestConfig requestConfig = RequestConfig.custom()
            .setConnectionRequestTimeout(5000)
            .setConnectTimeout(5000)
            .setSocketTimeout(5000)
            .build();

    public static String getRequest(String URI) throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .build();
        HttpGet request = new HttpGet(URI);//GET request
        CloseableHttpResponse response = client.execute(request);
        System.out.println(response.getStatusLine() + " *** Request Type: " + request.getMethod());
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity);
    }

    public static String getUserById(String URI, String URN, int id) throws IOException {
        return getRequest(URI
                .concat(URN)
                .concat(Integer
                        .toString(id)));
    }

    public static String getUserByUserName(String URI, String URN, String userName) throws IOException {
        return getRequest(URI
                .concat(URN)
                .concat("?username=")
                .concat(userName));
    }

    public static String postRequest(String URI, String jsonPath) throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .build();
        HttpPost request = new HttpPost(URI);//POST request
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        //String json = ;
        StringEntity entity = new StringEntity(Files.readString(Paths.get(jsonPath)));
        request.setEntity(entity);
        CloseableHttpResponse response = client.execute(request);
        System.out.println(response.getStatusLine() + " *** Request Type: " + request.getMethod());
        Scanner scanner = new Scanner(response.getEntity().getContent());
        StringBuilder stringBuilder = new StringBuilder();
        while (scanner.hasNext()) {
            stringBuilder.append(scanner.nextLine()).append("\n");
        }
        return stringBuilder.toString();
    }

    public static String putRequest(String URI, String jsonPath) throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .build();
        HttpPut request = new HttpPut(URI);//PUT request
        StringEntity entity = new StringEntity(Files.readString(Paths.get(jsonPath)));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setEntity(entity);
        CloseableHttpResponse response = client.execute(request);
        System.out.println(response.getStatusLine() + " *** Request Type: " + request.getMethod());
        Scanner scanner = new Scanner(response.getEntity().getContent());
        StringBuilder stringBuilder = new StringBuilder();
        while (scanner.hasNext()) {
            stringBuilder.append(scanner.nextLine()).append("\n");
        }
        return stringBuilder.toString();
    }

    public static boolean deleteRequest(String URI) throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .build();
        HttpDelete request = new HttpDelete(URI);
        CloseableHttpResponse response = client.execute(request);
        int status = response.getStatusLine().getStatusCode();
        System.out.println(response.getStatusLine() + " *** Request Type: " + request.getMethod());
        return status >= 200 && status < 300;
    }
}
