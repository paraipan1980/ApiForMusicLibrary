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

    public void getApiStatusCode(CloseableHttpResponse closeableHttpResponse, int statusCode) throws IOException {

        //statusCode
        int statusCode200 = closeableHttpResponse.getStatusLine().getStatusCode();
        System.out.println("\n");
        System.out.println("Status Code is : " + statusCode200);

        Assert.assertEquals(statusCode, statusCode200);

    }

    public JSONObject getResponseJSON(CloseableHttpResponse closeableHttpResponse) throws IOException {

        //JSON Body
        String responseBody = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");

        if (responseBody.charAt(0) == '[') {
            responseBody = "{ \"items\" :" + responseBody + "}";
        }

        JSONObject responseJSON = new JSONObject(responseBody);
        return responseJSON;
    }

    public void getApiResponseBody(JSONObject responseJSON) throws IOException {

        String artist = Util.getValueByJsonPath(responseJSON,"/items[0]/artist");
        String song = Util.getValueByJsonPath(responseJSON,"/items[0]/song");
        String publishDate = Util.getValueByJsonPath(responseJSON,"/items[0]/publishDate");
        String v = Util.getValueByJsonPath(responseJSON,"/items[0]/__v");
        String date_created = Util.getValueByJsonPath(responseJSON,"/items[0]/date_created");

        Assert.assertTrue(artist!=null);
        Assert.assertTrue(song!=null);
        Assert.assertTrue(publishDate!=null);
        Assert.assertTrue(v!=null);
        Assert.assertTrue(date_created!=null);
    }

    public void getApiResponseBodyForOneVideo(String song, String artist,JSONObject responseJSON) {

        String artistArray = Util.getValueByJsonPath(responseJSON,"/items[0]/artist");
        String songArray = Util.getValueByJsonPath(responseJSON,"/items[0]/song");
        String publishDate = Util.getValueByJsonPath(responseJSON,"/items[0]/publishDate");
        String v = Util.getValueByJsonPath(responseJSON,"/items[0]/__v");
        String date_created = Util.getValueByJsonPath(responseJSON,"/items[0]/date_created");

        Assert.assertTrue(artistArray.equals(artist));
        Assert.assertTrue(songArray.equals(song));
        Assert.assertTrue(publishDate!=null);
        Assert.assertTrue(v!=null);
        Assert.assertTrue(date_created!=null);
    }

    @Test
    public void testResp() throws IOException {
        String song = "One";
        String artist = "Metallica";
        Util util = new Util();
        RestClient restClient = new RestClient();
        GetAPITest getAPITest = new GetAPITest();
        JSONObject responseJSON = new JSONObject();
        CloseableHttpResponse closeableHttpResponse = restClient.get(util.setupURL());
        responseJSON = getAPITest.getResponseJSON(closeableHttpResponse);
        String id = util.getId(song,artist);
        getApiResponseBodyForOneVideo(song,artist,responseJSON);
    }



    public void getApiPlaylistResponseBody(JSONObject responseJSON) throws IOException {

        String desc = Util.getValueByJsonPath(responseJSON,"/items[0]/desc");
        String title = Util.getValueByJsonPath(responseJSON,"/items[0]/title");
        String v = Util.getValueByJsonPath(responseJSON,"/items[0]/__v");
        String date_created = Util.getValueByJsonPath(responseJSON,"/items[0]/date_created");

        Assert.assertTrue(desc!=null);
        Assert.assertTrue(title!=null);
        Assert.assertTrue(v!=null);
        Assert.assertTrue(date_created!=null);
    }

}
