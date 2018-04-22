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
    String videoURL;
    String playlistURL;
    String url;
    GetAPITest getAPITest;
    RestClient restClient;
    Util util;
    CloseableHttpResponse closeableHttpResponse;

    public String setupURL() {
        baseURL = properties.getProperty("baseURL");
        videoURL = properties.getProperty("videoURL");
        url = baseURL+videoURL;
        return url;
    }

    public String setupURLwithID(String id) {
        baseURL = properties.getProperty("baseURL");
        videoURL = properties.getProperty("videoURL");
        url = baseURL+videoURL+"/"+id;
        return url;
    }

    public String setupPlaylistURL() {
        baseURL = properties.getProperty("baseURL");
        playlistURL = properties.getProperty("playlistURL");
        url = baseURL+playlistURL;
        return url;
    }

    public String setupPlaylistURLwithID(String id) {
        baseURL = properties.getProperty("baseURL");
        playlistURL = properties.getProperty("playlistURL");
        url = baseURL+playlistURL+"/"+id;
        return url;
    }


    public String getId(String song, String artist) throws IOException {

        getAPITest = new GetAPITest();
        util = new Util();
        restClient = new RestClient();
        closeableHttpResponse = restClient.get(util.setupURL());
        JSONObject jObject = getAPITest.getResponseJSON(closeableHttpResponse);

        JSONArray jArray = jObject.getJSONArray("items");

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
        System.out.println(getId("xxx", "xxx"));
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

        String s = checkIfSongIsInTheList("One");
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

    public void checkIfListOfPlaylistsIsEmpty() throws IOException {

        getAPITest = new GetAPITest();
        restClient = new RestClient();
        util = new Util();
        closeableHttpResponse = restClient.get(util.setupPlaylistURL());

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
    public void testEmptyListOfPLaylists() throws IOException {
        checkIfListOfPlaylistsIsEmpty();
    }

    public String checkIfPlaylistIsInTheList(String playlist) throws IOException {

        getAPITest = new GetAPITest();
        util = new Util();
        restClient = new RestClient();

        closeableHttpResponse = restClient.get(util.setupPlaylistURL());
        JSONObject jObject = getAPITest.getResponseJSON(closeableHttpResponse);

        //converting the json object to array
        JSONArray jArray = jObject.getJSONArray("items");

        String playlistInArray = null;

        for (int i = 0; i < jArray.length(); i++) {
            JSONObject childJObject = jArray.getJSONObject(i);
            playlistInArray = childJObject.getString("title");

            if (playlistInArray.equals(playlist)) {
                System.out.println("ERROR: The playlist is already in the list. It cannot be added again.");
                break;
            }
        }
        return playlistInArray;
    }

    @Test
    public void testPlaylistInTheList() throws IOException {

        String s = checkIfPlaylistIsInTheList("Classic Rock List");
        System.out.println(s);
    }

    public String getPlaylistId(String title, String desc) throws IOException {

        getAPITest = new GetAPITest();
        util = new Util();
        restClient = new RestClient();
        closeableHttpResponse = restClient.get(util.setupPlaylistURL());
        JSONObject jObject = getAPITest.getResponseJSON(closeableHttpResponse);

        JSONArray jArray = jObject.getJSONArray("items");

        String id = null;
        String t;
        String d;

        for (int i = 0; i < jArray.length(); i++) {
            JSONObject childJObject = jArray.getJSONObject(i);
            t = childJObject.getString("title");
            d = childJObject.getString("desc");

            if (t.equals(title)) {
                if(d.equals(desc)) {
                    {id = childJObject.getString("_id");}
                }
            }
        }
        return id;
    }

    @Test
    public void testID() throws IOException {
        System.out.println(getPlaylistId("Top 200", "Larry's Favourite"));
    }


}
