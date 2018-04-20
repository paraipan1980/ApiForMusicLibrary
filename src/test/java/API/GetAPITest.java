package API;

import Base.BasicProperties;
import Util.Util;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.junit.Assert;

import java.io.IOException;

public class GetAPITest extends BasicProperties {

    //Define Global Variables
    String baseURL;
    String serviceURL;
    String url;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;

    public String setupURL() {
        baseURL = properties.getProperty("baseURL");
        serviceURL = properties.getProperty("serviceURL");
        url = baseURL+serviceURL;
        return url;
    }

    public void getApiStatusCode(String url, int statusCode) throws IOException {

        restClient = new RestClient();
        closeableHttpResponse = restClient.get(url);

        //statusCode
        int statusCode200 = closeableHttpResponse.getStatusLine().getStatusCode();
        System.out.println("Status Code is : " + statusCode200);

        Assert.assertEquals(statusCode, statusCode200);

    }

    public void getApiResponseBody(String url) throws IOException {

        restClient = new RestClient();
        closeableHttpResponse = restClient.get(url);

        //JSON Body
        String responseBody = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");

        if (responseBody.charAt(0) == '[') {
            responseBody = "{ \"items\" :" + responseBody + "}";
        }

        JSONObject responseJSON = new JSONObject(responseBody);

        String artist = Util.getValueByJsonPath(responseJSON,"/items[0]/artist");
        String song = Util.getValueByJsonPath(responseJSON,"/items[0]/song");
        String publishDate = Util.getValueByJsonPath(responseJSON,"/items[0]/publishDate");
        String v = Util.getValueByJsonPath(responseJSON,"/items[0]/__v");
        String date_created = Util.getValueByJsonPath(responseJSON,"/items[0]/date_created");

        Assert.assertEquals(artist,"Lady Gaga");
        Assert.assertEquals(song,"Poker face");
        Assert.assertEquals(publishDate,"2017-09-01T00:00:00.000Z");
        Assert.assertEquals(v,"0");
        Assert.assertEquals(date_created,"2018-04-17T19:37:10.700Z");

        artist = Util.getValueByJsonPath(responseJSON,"/items[6]/artist");
        song = Util.getValueByJsonPath(responseJSON,"/items[6]/song");
        publishDate = Util.getValueByJsonPath(responseJSON,"/items[6]/publishDate");
        v = Util.getValueByJsonPath(responseJSON,"/items[6]/__v");
        date_created = Util.getValueByJsonPath(responseJSON,"/items[6]/date_created");

        Assert.assertEquals(artist,"Queen");
        Assert.assertEquals(song,"Bohemian Rhapsody");
        Assert.assertEquals(publishDate,"1975-12-25T00:00:00.000Z");
        Assert.assertEquals(v,"0");
        Assert.assertEquals(date_created,"2018-04-18T20:44:00.247Z");

    }
}
