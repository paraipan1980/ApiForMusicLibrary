package API;

import Base.BasicProperties;
import Util.Util;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.junit.Assert;

import java.io.IOException;

public class DeleteAPITest extends BasicProperties {

    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;
    Util util;

    public void deleteApiStatusCode(String url, int statusCode) throws IOException {

        restClient = new RestClient();
        closeableHttpResponse = restClient.delete(url);

        //statusCode
        int statusCode204 = closeableHttpResponse.getStatusLine().getStatusCode();
        System.out.println("Status Code is : " + statusCode204);
        Assert.assertEquals(statusCode, statusCode204);
    }

}
