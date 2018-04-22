package StepDefinitions;

import API.*;
import Util.Util;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.conn.HttpHostConnectException;
import org.json.JSONObject;
import org.junit.Assert;

import java.io.IOException;

public class MyStepdefs {
    RestClient restClient;
    GetAPITest getAPITest;
    PostAPITest postAPITest;
    PatchAPITest patchAPITest;
    DeleteAPITest deleteAPItest;
    Util util;
    public CloseableHttpResponse closeableHttpResponse;
    public JSONObject responseJSON = new JSONObject();

    //Get Request for the complete list of videos************************************

    @Given("^I access the api at \"([^\"]*)\"$")
    public void iAccessTheApiAt(String endpoint) {
        util = new Util();
        Assert.assertTrue(util.setupURL().equals(endpoint));
    }

    @And("^the list is not empty$")
    public void theListIsNotEmpty() throws IOException {
        util = new Util();
        util.checkIfSongListIsEmpty();
    }

    @When("^I request the list of videos$")
    public void iRequestTheListOfVideos() throws IOException {
        restClient = new RestClient();
        closeableHttpResponse = restClient.get(util.setupURL());
    }

    @Then("^I receive the correct json file with the list of videos$")
    public void iReceiveTheCorrectJsonFileWithTheListOfVideos() throws IOException {
        getAPITest = new GetAPITest();
        responseJSON = getAPITest.getResponseJSON(closeableHttpResponse);
        getAPITest.getApiResponseBody(responseJSON);
    }

    @And("^the GET status code is (\\d+)$")
    public void theGETStatusCodeIs(int statusCode) throws IOException {
        getAPITest = new GetAPITest();
        getAPITest.getApiStatusCode(closeableHttpResponse,statusCode);
    }

    //Get Request for one specific video************************************

    @When("^I request \"([^\"]*)\" by \"([^\"]*)\"$")
    public void iRequestBy(String song, String artist) throws IOException {
        util = new Util();
        restClient = new RestClient();
        getAPITest = new GetAPITest();
        closeableHttpResponse = restClient.get(util.setupURL());
        responseJSON = getAPITest.getResponseJSON(closeableHttpResponse);
        String id = util.getId(song,artist);
        String url = util.setupURLwithID(id);
        restClient.get(url);
    }

    @Then("^I receive the correct json file for \"([^\"]*)\" by \"([^\"]*)\"$")
    public void iReceiveTheCorrectJsonFileForBy(String song, String artist) throws IOException {
        util = new Util();
        String id = util.getId(song,artist);
        String url = util.setupURLwithID(id);
        getAPITest.getApiResponseBody(responseJSON);
    }

    @And("^the new GET status code is (\\d+) for \"([^\"]*)\" by \"([^\"]*)\"$")
    public void theNewGETStatusCodeIsForBy(int statusCode, String song, String artist) throws IOException {
        util = new Util();
        getAPITest = new GetAPITest();
        String id = util.getId(song,artist);
        getAPITest.getApiStatusCode(closeableHttpResponse,statusCode);
    }

   // Post Request for one specific video *****************************

    @And("^the video \"([^\"]*)\" is not in the list already$")
    public void theVideoIsNotInTheListAlready(String song) throws Throwable {
        util = new Util();
        String songInArray = util.checkIfSongIsInTheList(song);
        Assert.assertFalse(song.equals(songInArray));
    }

    @When("^I want to add to the list \"([^\"]*)\" by \"([^\"]*)\" published on \"([^\"]*)\"$")
    public void iWantToAddToTheListByPublishedOn(String song, String artist, String publishedDate) throws IOException {
        postAPITest = new PostAPITest();
        closeableHttpResponse = postAPITest.PostVideo(song,artist,publishedDate);
    }

    @Then("^the video \"([^\"]*)\" by \"([^\"]*)\" published on \"([^\"]*)\" is added to the list of videos$")
    public void theVideoByPublishedOnIsAddedToTheListOfVideos(String song, String artist, String publishedDate) throws IOException {
        postAPITest = new PostAPITest();
        responseJSON = postAPITest.postResponseJSON(closeableHttpResponse);
        postAPITest.postApiResponseBody(song,artist,publishedDate, responseJSON);
        //check that its in the list of videos
        //test the header
    }

    @And("^the POST status code is (\\d+)$")
    public void thePOSTStatusCodeIs(int statusCode) throws IOException {
        postAPITest = new PostAPITest();
        postAPITest.postApiStatusCode(closeableHttpResponse,statusCode);
    }

    // Patch a specific video *********************************************

    @When("^I want to update \"([^\"]*)\" by \"([^\"]*)\"$")
    public void iWantToUpdateBy(String song, String artist) throws IOException {
        util = new Util();
        restClient = new RestClient();
        getAPITest = new GetAPITest();
        closeableHttpResponse = restClient.get(util.setupURL());
        responseJSON = getAPITest.getResponseJSON(closeableHttpResponse);
        String id = util.getId(song,artist);
        String url = util.setupURLwithID(id);
        closeableHttpResponse = restClient.patch(url);
    }

    @Then("^the PATCH status code is (\\d+)$")
    public void thePATCHStatusCodeIs(int statusCode) throws IOException {
        patchAPITest = new PatchAPITest();
        patchAPITest.patchApiStatusCode(closeableHttpResponse,statusCode);
    }

    //Delete a specific video***********************************************

    @When("^I delete \"([^\"]*)\" by \"([^\"]*)\"$")
    public void iDeleteBy(String song, String artist) throws IOException {

        try{
            util = new Util();
            restClient = new RestClient();
            String id = util.getId(song,artist);
            String url = util.setupURLwithID(id);
            closeableHttpResponse = restClient.delete(url);
        }
        catch (HttpHostConnectException e)
        {
            System.out.println("ERROR: The song you are trying to delete is not in the list");
        }
    }

    @Then("^the video is deleted$")
    public void theVideoIsDeleted()  {
        ///check the video is not in the list
    }


    @And("^the DELETE status code is (\\d+)$")
    public void theDELETEStatusCodeIs(int statusCode) {
        try {
            util = new Util();
            deleteAPItest = new DeleteAPITest();
            deleteAPItest.deleteApiStatusCode(closeableHttpResponse, statusCode);
        }
        catch (NullPointerException npe)
        {
            System.out.println("\n");
            System.out.println("ERROR: The request does not work because there the ID in /api/video/(id) does not exist");
        }
    }

}
