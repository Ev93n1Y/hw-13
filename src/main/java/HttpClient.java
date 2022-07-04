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
    private  static final CloseableHttpClient client = HttpClientBuilder.create()
            .setDefaultRequestConfig(REQUEST_CONFIG)
            .build();

    //Send GET
    public static String getRequest(String URI) throws IOException {
        HttpGet request = new HttpGet(URI);//GET request
        CloseableHttpResponse response = client.execute(request);
        System.out.println(response.getStatusLine() + " *** Request Type: " + request.getMethod());
        HttpEntity entity = response.getEntity();
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
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
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

    //Send PUT
    public static String putRequest(String URI, String jsonPath) throws IOException {
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

    //Send DELETE
    public static boolean deleteRequest(String URI) throws IOException {
        HttpDelete request = new HttpDelete(URI);//DELETE request
        CloseableHttpResponse response = client.execute(request);
        int status = response.getStatusLine().getStatusCode();
        System.out.println(response.getStatusLine() + " *** Request Type: " + request.getMethod());
        return status >= 200 && status < 300;
    }

    //Get user last comment
    public static String getUserLastComment(int id){
        //Дополните программу методом, который будет выводить все комментарии к последнему посту определенного пользователя и записывать их в файл.
        //https://jsonplaceholder.typicode.com/users/1/posts Последним будем считать пост с наибольшим id.
        //https://jsonplaceholder.typicode.com/posts/10/comments
        //Файл должен называться "user-X-post-Y-comments.json", где Х - номер пользователя, Y - номер поста.
        return "String";
    }

    //Get user open task(to do list)
    public static String getUserOpenTodos(){
        //Дополните программу методом, который будет выводить все открытые задачи для пользователя Х.
        //https://jsonplaceholder.typicode.com/users/1/todos.
        //Открытыми считаются все задачи, у которых completed = false.*/
        return "String";
    }
}