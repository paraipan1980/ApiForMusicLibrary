package StepDefinitions;

import API.GetAPITest;
import API.PostAPITest;
import API.RestClient;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.io.IOException;

public class MyStepdefs {
    RestClient restClient = new RestClient();
    GetAPITest getAPITest = new GetAPITest();
    PostAPITest postAPITest = new PostAPITest();

    @Given("^I access the api at \"([^\"]*)\"$")
    public void iAccessTheApiAt(String endpoint) {
        //GetAPITest getAPITest = new GetAPITest();
        Assert.assertTrue(getAPITest.setupURL().equals(endpoint));
    }

    @When("^I request the list of videos$")
    public void iRequestTheListOfVideos() throws IOException {
        //RestClient restClient = new RestClient();
        //GetAPITest getAPITest = new GetAPITest();
        restClient.get(getAPITest.setupURL());
    }

    @Then("^I receive the correct json file with the list of videos$")
    public void iReceiveTheCorrectJsonFileWithTheListOfVideos() throws IOException {
        //GetAPITest getAPITest = new GetAPITest();
        String url = getAPITest.setupURL();
        getAPITest.getApiResponseBody(url);
    }

    @And("^the GET status code is (\\d+)$")
    public void theGETStatusCodeIs(int statusCode) throws IOException {
        //GetAPITest getAPITest = new GetAPITest();
        getAPITest.getApiStatusCode(getAPITest.setupURL(),statusCode);
    }

    @When("^I request \"([^\"]*)\" by \"([^\"]*)\"$")
    public void iRequestBy(String song, String artist) throws IOException {
        //GetAPITest getAPITest = new GetAPITest();
        //RestClient restClient = new RestClient();
        String id = getAPITest.getId(song,artist);
        String url = getAPITest.setupURLwithID(id);
        restClient.get(url);
    }

    @Then("^I receive the correct json file for \"([^\"]*)\" by \"([^\"]*)\"$")
    public void iReceiveTheCorrectJsonFileForBy(String song, String artist) throws IOException {
        //GetAPITest getAPITest = new GetAPITest();
        String id = getAPITest.getId(song,artist);
        String url = getAPITest.setupURLwithID(id);
        //getAPITest.getApiResponseBody(url);
    }

    @And("^the new GET status code is (\\d+) for \"([^\"]*)\" by \"([^\"]*)\"$")
    public void theNewGETStatusCodeIsForBy(int statusCode, String song, String artist) throws IOException {
        //GetAPITest getAPITest = new GetAPITest();
        String id = getAPITest.getId(song,artist);
        String url = getAPITest.setupURLwithID(id);
        getAPITest.getApiStatusCode(url,statusCode);
    }

    @When("^I want to add to the list \"([^\"]*)\" by \"([^\"]*)\" published on \"([^\"]*)\"$")
    public void iWantToAddToTheListByPublishedOn(String song, String artist, String publishedDate) throws IOException {
        //PostAPITest postAPITest = new PostAPITest();
        postAPITest.PostVideo(song,artist,publishedDate);
    }

    @Then("^the video \"([^\"]*)\" by \"([^\"]*)\" published on \"([^\"]*)\" is added to the list of videos$")
    public void theVideoByPublishedOnIsAddedToTheListOfVideos(String song, String artist, String publishedDate) throws IOException {
        //PostAPITest postAPITest = new PostAPITest();
        postAPITest.postApiResponseBody(song,artist,publishedDate);
        //check that its in the list of videos
        //test the header
    }

    @And("^the POST status code for \"([^\"]*)\" by \"([^\"]*)\" published on \"([^\"]*)\" is (\\d+)$")
    public void thePOSTStatusCodeForByPublishedOnIs(String song, String artist, String publishedDate, int statusCode) throws IOException{
        //PostAPITest postAPITest = new PostAPITest();
        //GetAPITest getAPITest = new GetAPITest();
        postAPITest.postApiStatusCode(getAPITest.setupURL(),statusCode,song,artist,publishedDate);
    }
}
