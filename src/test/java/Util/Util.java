package Util;

import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;


public class Util {

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
