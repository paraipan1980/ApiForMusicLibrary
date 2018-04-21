package API;

import Base.BasicProperties;
import Util.Util;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class GetAPITest extends BasicProperties {

    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;
    Util util;

    public void getApiStatusCode(String url, int statusCode) throws IOException {

        restClient = new RestClient();
        closeableHttpResponse = restClient.get(url);

        //statusCode
        int statusCode200 = closeableHttpResponse.getStatusLine().getStatusCode();
        System.out.println("Status Code is : " + statusCode200);

        Assert.assertEquals(statusCode, statusCode200);

    }

    public JSONObject getResponseJSON() throws IOException {

        restClient = new RestClient();
        util = new Util();
        closeableHttpResponse = restClient.get(util.setupURL());

        //JSON Body
        String responseBody = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");

        if (responseBody.charAt(0) == '[') {
            responseBody = "{ \"items\" :" + responseBody + "}";
        }

        JSONObject responseJSON = new JSONObject(responseBody);
        return responseJSON;
    }

    public void getApiResponseBody(String url) throws IOException {

        String artist = Util.getValueByJsonPath(getResponseJSON(),"/items[0]/artist");
        String song = Util.getValueByJsonPath(getResponseJSON(),"/items[0]/song");
        String publishDate = Util.getValueByJsonPath(getResponseJSON(),"/items[0]/publishDate");
        String v = Util.getValueByJsonPath(getResponseJSON(),"/items[0]/__v");
        String date_created = Util.getValueByJsonPath(getResponseJSON(),"/items[0]/date_created");

        Assert.assertTrue(artist!=null);
        Assert.assertTrue(song!=null);
        Assert.assertTrue(publishDate!=null);
        Assert.assertTrue(v!=null);
        Assert.assertTrue(date_created!=null);
    }

}
