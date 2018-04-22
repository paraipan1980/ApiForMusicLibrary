package StepDefinitions;

import API.*;
import Util.Util;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.io.IOException;

public class MyStepdefs {
    RestClient restClient;
    GetAPITest getAPITest;
    PostAPITest postAPITest;
    PatchAPITest patchAPITest;
    DeleteAPITest deleteAPItest;
    Util util;

    @Given("^I access the api at \"([^\"]*)\"$")
    public void iAccessTheApiAt(String endpoint) {
        util = new Util();
        Assert.assertTrue(util.setupURL().equals(endpoint));
    }

    @When("^I request the list of videos$")
    public void iRequestTheListOfVideos() throws IOException {
        restClient = new RestClient();
        restClient.get(util.setupURL());
    }

    @Then("^I receive the correct json file with the list of videos$")
    public void iReceiveTheCorrectJsonFileWithTheListOfVideos() throws IOException {
        getAPITest = new GetAPITest();
        util = new Util();
        String url = util.setupURL();
        getAPITest.getApiResponseBody(url);
    }

    @And("^the GET status code is (\\d+)$")
    public void theGETStatusCodeIs(int statusCode) throws IOException {
        getAPITest = new GetAPITest();
        util = new Util();
        getAPITest.getApiStatusCode(util.setupURL(),statusCode);
    }

    @When("^I request \"([^\"]*)\" by \"([^\"]*)\"$")
    public void iRequestBy(String song, String artist) throws IOException {
        util = new Util();
        restClient = new RestClient();
        String id = util.getId(song,artist);
        String url = util.setupURLwithID(id);
        restClient.get(url);
    }

    @Then("^I receive the correct json file for \"([^\"]*)\" by \"([^\"]*)\"$")
    public void iReceiveTheCorrectJsonFileForBy(String song, String artist) throws IOException {
        util = new Util();
        String id = util.getId(song,artist);
        String url = util.setupURLwithID(id);
        //getAPITest.getApiResponseBody(url);
    }

    @And("^the new GET status code is (\\d+) for \"([^\"]*)\" by \"([^\"]*)\"$")
    public void theNewGETStatusCodeIsForBy(int statusCode, String song, String artist) throws IOException {
        util = new Util();
        getAPITest = new GetAPITest();
        String id = util.getId(song,artist);
        String url = util.setupURLwithID(id);
        getAPITest.getApiStatusCode(url,statusCode);
    }

    @When("^I want to add to the list \"([^\"]*)\" by \"([^\"]*)\" published on \"([^\"]*)\"$")
    public void iWantToAddToTheListByPublishedOn(String song, String artist, String publishedDate) throws IOException {
        postAPITest = new PostAPITest();
        postAPITest.PostVideo(song,artist,publishedDate);
    }

    @And("^the video \"([^\"]*)\" is not in the list already$")
    public void theVideoIsNotInTheListAlready(String song) throws Throwable {
        util = new Util();
        util.checkIfSongIsInTheList(song);
        Assert.assertFalse(song.equals(util.checkIfSongIsInTheList(song)));
    }

    @Then("^the video \"([^\"]*)\" by \"([^\"]*)\" published on \"([^\"]*)\" is added to the list of videos$")
    public void theVideoByPublishedOnIsAddedToTheListOfVideos(String song, String artist, String publishedDate) throws IOException {
        postAPITest = new PostAPITest();
        postAPITest.postApiResponseBody(song,artist,publishedDate);
        //check that its in the list of videos
        //test the header
    }

    @And("^the POST status code for \"([^\"]*)\" by \"([^\"]*)\" published on \"([^\"]*)\" is (\\d+)$")
    public void thePOSTStatusCodeForByPublishedOnIs(String song, String artist, String publishedDate, int statusCode) throws IOException{
        postAPITest = new PostAPITest();
        postAPITest.postApiStatusCode(util.setupURL(),statusCode,song,artist,publishedDate);
    }

    @When("^I want to update \"([^\"]*)\" by \"([^\"]*)\"$")
    public void iWantToUpdateBy(String song, String artist) throws IOException {
        util = new Util();
        restClient = new RestClient();
        String id = util.getId(song,artist);
        String url = util.setupURLwithID(id);
        restClient.patch(url);
    }

    @Then("^the PATCH status code is (\\d+) for \"([^\"]*)\" by \"([^\"]*)\"$")
    public void thePATCHStatusCodeIsForBy(int statusCode, String song, String artist) throws IOException {
        util = new Util();
        patchAPITest = new PatchAPITest();
        String id = util.getId(song,artist);
        String url = util.setupURLwithID(id);
        patchAPITest.patchApiStatusCode(url,statusCode);
    }

    @When("^I want to delete \"([^\"]*)\" by \"([^\"]*)\"$")
    public void iWantToDeleteBy(String song, String artist) throws IOException {
        util = new Util();
        restClient = new RestClient();
        String id = util.getId(song,artist);
        String url = util.setupURLwithID(id);
        restClient.delete(url);
    }

    @Then("^the video is deleted$")
    public void theVideoIsDeleted()  {
        ///check the video is not in the list
    }

    @And("^the DELETE status code is (\\d+) for deleting \"([^\"]*)\" by \"([^\"]*)\"$")
    public void theDELETEStatusCodeIsForDeletingBy(int statusCode, String song, String artist) throws IOException {
        util = new Util();
        deleteAPItest = new DeleteAPITest();
        String id = util.getId(song,artist);
        String url = util.setupURLwithID(id);
        deleteAPItest.deleteApiStatusCode(url,statusCode);
    }

}
