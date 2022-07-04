import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * This example demonstrates the use of {@link HttpPut} request method.
 * @author Ramesh Fadatare
 */

public class PUTRequestExample {

    public static void main(String[] args) throws IOException {
        putUser();
    }

    public static void putUser() throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPut httpPut = new HttpPut("https://jsonplaceholder.typicode.com/users");
            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Content-type", "application/json");
            String json = """
                    {\r
                      "id":"0",\r
                      "name": "Ram",\r
                      "username": "Jadhav",\r
                      "email": "ramesh1234@gmail.com",\r
                      "address": "2018-09",\r
                      "street": "Ramesh",\r
                      "suite": "2018",\r
                      "city": "Ramesh"\r
                    }""";
            StringEntity stringEntity = new StringEntity(json);
            httpPut.setEntity(stringEntity);

            System.out.println("Executing request " + httpPut.getRequestLine());

            // Create a custom response handler
            ResponseHandler <String> responseHandler = response -> {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                System.out.println(response.getStatusLine() + " *** Request Type: " + httpPut.getMethod());
                return entity != null ? EntityUtils.toString(entity) : null;

            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
            };

            String responseBody = httpclient.execute(httpPut, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
        }
    }
}