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

        public void patchApiStatusCode(String url, int statusCode) throws IOException {

            restClient = new RestClient();
            closeableHttpResponse = restClient.patch(url);

            //statusCode
            int statusCode501 = closeableHttpResponse.getStatusLine().getStatusCode();
            System.out.println("Status Code is : " + statusCode501);
            Assert.assertEquals(statusCode, statusCode501);
        }

        @Test
        public void testStatusCode() throws  IOException {
            util = new Util();
            String url = util.setupURLwithID("5ad64ce6f8cca6035fe8f06b");
            patchApiStatusCode(url,500);
        }

        public JSONObject patchResponseJSON(String url) throws IOException {

            restClient = new RestClient();
            util = new Util();
            closeableHttpResponse = restClient.patch(url);

            //JSON Body
            String responseBody = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");

            JSONObject responseJSON = new JSONObject(responseBody);
            return responseJSON;

         }


}

