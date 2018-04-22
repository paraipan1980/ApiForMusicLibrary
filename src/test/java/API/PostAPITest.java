package API;

import Base.BasicProperties;
import Data.InputData;
import Data.InputPlaylistData;
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

public class PostAPITest extends BasicProperties {

   RestClient restClient;
   CloseableHttpResponse closeableHttpResponse;
   Util util;

   public CloseableHttpResponse PostVideo(String song, String artist, String publishedDate) throws IOException {

       restClient = new RestClient();
       util = new Util();
       HashMap<String, String> headerMap = new HashMap<String, String>();
       headerMap.put("Content-Type","application/json");

       //using jackson API to convert an Object content to Json
       //jackson Api
       ObjectMapper mapper = new ObjectMapper();
       InputData inputData = new InputData(artist,song,publishedDate);

       //convert object to Json file
       mapper.writeValue(new File("/Users/laurentiuborza/Documents/MyJavaTest/ApiForMusicLibrary/src/test/java/Data/newsong.json"), inputData);

       //convert object to Json in String
       String inputDataToString = mapper.writeValueAsString(inputData);

       closeableHttpResponse = restClient.post(util.setupURL(),inputDataToString,headerMap);

       return closeableHttpResponse;
   }

   @Test
   public void testPostVideo() throws IOException {
       PostVideo("fernando", "abba","1975-01-01" );
   }

    public CloseableHttpResponse PostPlaylist(String desc, String title) throws IOException {

        restClient = new RestClient();
        util = new Util();
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type","application/json");

        //using jackson API to convert an Object content to Json
        //jackson Api
        ObjectMapper mapper = new ObjectMapper();
        InputPlaylistData inputPlaylistData = new InputPlaylistData(desc,title);

        String inputDataToString = mapper.writeValueAsString(inputPlaylistData);

        closeableHttpResponse = restClient.post(util.setupPlaylistURL(),inputDataToString,headerMap);

        return closeableHttpResponse;
    }

    @Test
    public void testPlaylistVideo() throws IOException {
        PostPlaylist("abc", "cfd" );
    }

   public JSONObject postResponseJSON(CloseableHttpResponse closeableHttpResponse) throws IOException {

        //JSON Body
        String responseBody = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");

        if (responseBody.charAt(0) == '[') {
            responseBody = "{ \"items\" :" + responseBody + "}";
        }

        JSONObject responseJSON = new JSONObject(responseBody);

        return responseJSON;
    }

    //test statusCode
    public void postApiStatusCode(CloseableHttpResponse closeableHttpResponse, int statusCode) {

        int statusCode201 = closeableHttpResponse.getStatusLine().getStatusCode();
        System.out.println("Status Code is : " + statusCode201);
        Assert.assertEquals(statusCode, statusCode201);
    }

    public void postApiResponseBody(String song, String artist, String publishedDate, JSONObject responseJSON) {
        String artistResp = Util.getValueByJsonPath(responseJSON,"/artist");
        String songResp = Util.getValueByJsonPath(responseJSON,"/song");
        String publishedDateResp = Util.getValueByJsonPath(responseJSON,"/publishDate");
        String v = Util.getValueByJsonPath(responseJSON,"/__v");

        Assert.assertEquals(artist,artistResp);
        Assert.assertEquals(song,songResp);
        Assert.assertTrue(publishedDateResp.contains(publishedDate));
        Assert.assertEquals(v,"0");
    }

    public void postApiPlaylistResponseBody(String desc, String title, JSONObject responseJSON) {
        String descResp = Util.getValueByJsonPath(responseJSON,"/desc");
        String titleResp = Util.getValueByJsonPath(responseJSON,"/title");
        String v = Util.getValueByJsonPath(responseJSON,"/__v");

        Assert.assertEquals(desc,descResp);
        Assert.assertEquals(title,titleResp);
        Assert.assertEquals(v,"0");
    }
}
