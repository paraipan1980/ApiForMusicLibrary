package API;

import Base.BasicProperties;
import Data.InputData;
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

   public CloseableHttpResponse PostVideo(String song, String artist, String publishedDate) throws IOException {

       restClient = new RestClient();
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
       //System.out.println(inputDataToString);

       GetAPITest getAPITest = new GetAPITest();
       closeableHttpResponse = restClient.post(getAPITest.setupURL(),inputDataToString,headerMap);

       return closeableHttpResponse;
   }

   @Test
   public void testPostVideo() throws IOException {
       PostVideo("fernando", "abba","1975-01-01" );
   }

    public JSONObject postResponseJSON(String song,String artist,String publishedDate) throws IOException {

        restClient = new RestClient();
        GetAPITest getAPITest = new GetAPITest();
        closeableHttpResponse = PostVideo(song,artist,publishedDate);

        //JSON Body
        String responseBody = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");

        if (responseBody.charAt(0) == '[') {
            responseBody = "{ \"items\" :" + responseBody + "}";
        }

        JSONObject responseJSON = new JSONObject(responseBody);

        return responseJSON;
    }

    public void postApiStatusCode(String url, int statusCode, String song, String artist, String publishedDate) throws IOException {
        //statusCode
        int statusCode201 = PostVideo(song,artist,publishedDate).getStatusLine().getStatusCode();
        System.out.println("Status Code is : " + statusCode201);
        Assert.assertEquals(statusCode, statusCode201);
    }

    public void postApiResponseBody(String song, String artist, String publishedDate) throws IOException {

        String artistResp = Util.getValueByJsonPath(postResponseJSON(song,artist,publishedDate),"/artist");
        String songResp = Util.getValueByJsonPath(postResponseJSON(song,artist,publishedDate),"/song");
        String publishedDateResp = Util.getValueByJsonPath(postResponseJSON(song,artist,publishedDate),"/publishDate");
        String v = Util.getValueByJsonPath(postResponseJSON(song,artist,publishedDate),"/__v");
        String date_created = Util.getValueByJsonPath(postResponseJSON(song,artist,publishedDate),"/date_created");

        Assert.assertEquals(artist,artistResp);
        Assert.assertEquals(song,songResp);
        Assert.assertTrue(publishedDateResp.contains(publishedDate));
        Assert.assertEquals(v,"0");
        //Assert.assertEquals(date_created,"2018-04-17T19:37:10.700Z");

    }
}
