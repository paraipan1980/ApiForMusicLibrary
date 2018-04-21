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

    public String setupURLwithID(String id) {
        baseURL = properties.getProperty("baseURL");
        serviceURL = properties.getProperty("serviceURL");
        url = baseURL+serviceURL+"/"+id;
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

    public JSONObject getResponseJSON() throws IOException {

        restClient = new RestClient();
        closeableHttpResponse = restClient.get(setupURL());

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

   public String getId(String song, String artist) throws IOException {

        String id = null;
       for (int i = 0; i < 100; i++) {

           String a = Util.getValueByJsonPath(getResponseJSON(), "/items[" + i + "]/artist");
           String s = Util.getValueByJsonPath(getResponseJSON(),"/items[" + i + "]/song");
           id = Util.getValueByJsonPath(getResponseJSON(), "/items[" + i + "]/_id");

           if (artist.equals(a) && song.equals(s)) {
               break;
           }
       }
       return(id);
   }

    @Test
    public void test() throws IOException {
        System.out.println(getId("Innuendo","Queen"));
    }

    @Test
    public void testUrl() throws IOException {
        String id = getId("Innuendo", "Queen");
        System.out.print(setupURLwithID(id));
    }
}
