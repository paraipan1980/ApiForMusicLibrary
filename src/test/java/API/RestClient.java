package API;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class RestClient {

    //GET Method
    public CloseableHttpResponse get(String url) throws IOException{

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpGet);

        return closeableHttpResponse;

    }

    //POST Method
    public CloseableHttpResponse post(String url, String payload) throws UnsupportedEncodingException, IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(payload));
        CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpPost);

        return closeableHttpResponse;
    }


}
