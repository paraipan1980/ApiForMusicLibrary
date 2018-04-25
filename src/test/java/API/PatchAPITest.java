package API;

import Base.BasicProperties;
import Data.InputData;
import Data.InputPatchData;
import Util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

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
            patchApiStatusCode(closeableHttpResponse,500);
        }

    @Test
    public void testPatchAdd(int statusCode) throws IOException {
        String title = "Jazz List";
        Util util = new Util();
        String id = util.getPlaylistId(title);
        RestClient restClient = new RestClient();
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type","application/json");
        String payload = "{ \"videos\": [ {\"5adca7cba71f98039b8979b9\": \"add\"}, {\"5adca7cba71f98039b8979ba\": \"add\"} ] }";
        CloseableHttpResponse closeableHttpResponse = restClient.patchPlaylist(util.setupPlaylistURLwithID(id), payload, headerMap);
        this.patchApiStatusCode(closeableHttpResponse, statusCode);
    }

    @Test
    public void testPatchRemove(int statusCode) throws IOException {
        String title = "Jazz List";
        Util util = new Util();
        String id = util.getPlaylistId(title);
        RestClient restClient = new RestClient();
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type","application/json");
        String payload = "{ \"videos\": [ {\"596cabbe9f0525001db52242\": \"remove\"} ] }";
        CloseableHttpResponse closeableHttpResponse = restClient.patchPlaylist(util.setupPlaylistURLwithID(id), payload, headerMap);
        this.patchApiStatusCode(closeableHttpResponse, statusCode);
    }

}

