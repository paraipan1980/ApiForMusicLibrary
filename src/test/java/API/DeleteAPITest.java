package API;

import Base.BasicProperties;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.junit.Assert;

public class DeleteAPITest extends BasicProperties {

    public void deleteApiStatusCode(CloseableHttpResponse closeableHttpResponse, int statusCode) {

        //statusCode
        int statusCode204 = closeableHttpResponse.getStatusLine().getStatusCode();
        System.out.println("Status Code is : " + statusCode204);
        Assert.assertEquals(statusCode, statusCode204);
    }

}
