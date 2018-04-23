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

    public CloseableHttpResponse PatchPlaylist(String action, String id) throws IOException {

        restClient = new RestClient();
        util = new Util();
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type","application/json");

        //using jackson API to convert an Object content to Json
        //jackson Api
        ObjectMapper mapper = new ObjectMapper();
        InputPatchData inputPatchData = new InputPatchData(action);

        //convert object to Json in String
        String inputDataToString = mapper.writeValueAsString(inputPatchData);

        closeableHttpResponse = restClient.post(util.setupPlaylistURLwithID(id),inputDataToString,headerMap);

        return closeableHttpResponse;
    }

    @Test
    public void testPatchPlaylist() throws IOException {
        PatchPlaylist("add", "5adcc560bb859c03d1f918a1");
    }

}

