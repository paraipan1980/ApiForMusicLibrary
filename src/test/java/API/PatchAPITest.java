package API;

import Base.BasicProperties;
import Util.Util;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class PatchAPITest extends BasicProperties {

        RestClient restClient;
        CloseableHttpResponse closeableHttpResponse;
        Util util;

        public void patchApiStatusCode(CloseableHttpResponse closeableHttpResponse, int statusCode) throws IOException {

            //statusCode
            int statusCode501 = closeableHttpResponse.getStatusLine().getStatusCode();
            System.out.println("Status Code is : " + statusCode501);
            Assert.assertEquals(statusCode, statusCode501);
        }

        @Test
        public void testStatusCode() throws  IOException {

            util = new Util();
            String url = util.setupURLwithID("5adc8ecb1bdd190389781a0c");
            patchApiStatusCode(closeableHttpResponse,500);
        }
}

