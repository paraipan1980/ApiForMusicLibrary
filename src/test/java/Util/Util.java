package Util;

import API.GetAPITest;
import API.RestClient;
import Base.BasicProperties;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;


public class Util extends BasicProperties{

    //Define Global Variables
    String baseURL;
    String serviceURL;
    String url;
    GetAPITest getAPITest;
    RestClient restClient;
    Util util;
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

    public String getId(String song, String artist) throws IOException {

        getAPITest = new GetAPITest();
        util = new Util();
        restClient = new RestClient();
        closeableHttpResponse = restClient.get(util.setupURL());
        JSONObject jObject = getAPITest.getResponseJSON(closeableHttpResponse);

        JSONArray jArray = jObject.getJSONArray("items");
        boolean found = false;

        String id = null;
        String s;
        String a;

        for (int i = 0; i < jArray.length(); i++) {
            JSONObject childJObject = jArray.getJSONObject(i);
            s = childJObject.getString("song");
            a = childJObject.getString("artist");

            if (s.equals(song)) {
                if(a.equals(artist)) {
                {id = childJObject.getString("_id");}
            }
            }
        }
        return id;
    }

    @Test
    public void test() throws IOException {
        System.out.println(getId("One", "Metallica"));
    }

    @Test
    public void testUrl() throws IOException {
        //JSONObject responseJSON = getAPITest.getResponseJSON(closeableHttpResponse);
        String id = getId("Innuendo", "Queen");
        System.out.print(setupURLwithID(id));
    }

    public String checkIfSongIsInTheList(String song) throws IOException {

        getAPITest = new GetAPITest();
        util = new Util();
        restClient = new RestClient();

        closeableHttpResponse = restClient.get(util.setupURL());
        JSONObject jObject = getAPITest.getResponseJSON(closeableHttpResponse);

        //converting the json object to array
        JSONArray jArray = jObject.getJSONArray("items");

        String songInArray = null;

        for (int i = 0; i < jArray.length(); i++) {
            JSONObject childJObject = jArray.getJSONObject(i);
            songInArray = childJObject.getString("song");

            if (songInArray.equals(song)) {
                System.out.println("ERROR: The song is already in the list. It cannot be added again.");
                break;
            }
        }
        return songInArray;
    }

    @Test
    public void testSongInTheList() throws IOException {

        String s = checkIfSongIsInTheList("xxx");
        System.out.println(s);
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

    public void checkIfSongListIsEmpty() throws IOException {

        getAPITest = new GetAPITest();
        restClient = new RestClient();
        util = new Util();
        closeableHttpResponse = restClient.get(util.setupURL());

        JSONObject jObject = getAPITest.getResponseJSON(closeableHttpResponse);

        //converting the json object to array
        JSONArray jArray = jObject.getJSONArray("items");

        if (jArray.length() == 0) {
        System.out.println("ERROR: The list is empty");
        }
        else {
            System.out.println("The list is populated");
        }
    }

    @Test
    public void testEmptyList() throws IOException {
        checkIfSongListIsEmpty();
    }


}
