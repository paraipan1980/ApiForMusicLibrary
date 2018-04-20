package API;

import Base.BasicProperties;
import Data.InputData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class PostAPITest extends BasicProperties {

   RestClient restClient;

   @Test
   public void PostAPITest() throws IOException {

       restClient = new RestClient();
       HashMap<String, String> headerMap = new HashMap<String, String>();
       headerMap.put("Content-Type","application/json");

       //using jackson API to convert an Object content to Json
       //jackson Api
       ObjectMapper mapper = new ObjectMapper();
       InputData inputData = new InputData("Iron Maiden", "Fear of the dark", "1991-09-01");

       //convert object to Json file
       mapper.writeValue(new File("/Users/laurentiuborza/Documents/MyJavaTest/ApiForMusicLibrary/src/test/java/Data/post.json"), inputData);
       }
}
