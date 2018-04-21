package Util;

import API.GetAPITest;
import API.RestClient;
import Base.BasicProperties;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;


public class Util extends BasicProperties{

    //Define Global Variables
    String baseURL;
    String serviceURL;
    String url;
    GetAPITest getAPITest;

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

    public String getId(String song, String artist) throws IOException {

        getAPITest = new GetAPITest();
        String id = null;
        for (int i = 0; i < 100; i++) {

            String a = Util.getValueByJsonPath(getAPITest.getResponseJSON(), "/items[" + i + "]/artist");
            String s = Util.getValueByJsonPath(getAPITest.getResponseJSON(),"/items[" + i + "]/song");
            id = Util.getValueByJsonPath(getAPITest.getResponseJSON(), "/items[" + i + "]/_id");

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

    public static String getValueByJsonPath(JSONObject responsejson, String JSONpath){
        Object obj = responsejson;

        for(String s : JSONpath.split("/"))
            if(!s.isEmpty())
                if(!(s.contains("[") || s.contains("]")))
                    obj = ((JSONObject) obj).get(s);
                else if(s.contains("[") || s.contains("]"))
                    obj = ((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0])).get(Integer.parseInt(s.split("\\[")[1].replace("]", "")));
        return obj.toString();
    }



}
