package API;

import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RestClient {

    //GET Method
    public CloseableHttpResponse get(String url) throws IOException{

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpGet);

        return closeableHttpResponse;

    }

    //POST Method
    public CloseableHttpResponse post(String url, String payload, HashMap<String,String> headerMap) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(payload));

        //headers
        for(Map.Entry<String,String> entry: headerMap.entrySet()){
            httpPost.addHeader(entry.getKey(), entry.getValue());
        }

        CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpPost);

        return closeableHttpResponse;
    }

    public CloseableHttpResponse patch(String url) throws IOException{

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPatch httpPatch = new HttpPatch(url);
        CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpPatch);

        return closeableHttpResponse;

    }

    public CloseableHttpResponse delete(String url) throws IOException{

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(url);
        CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpDelete);

        return closeableHttpResponse;

    }


}
