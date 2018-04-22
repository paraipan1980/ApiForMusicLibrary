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

public class MyPlaylistStepdefs {

    RestClient restClient;
    GetAPITest getAPITest;
    PostAPITest postAPITest;
    PatchAPITest patchAPITest;
    DeleteAPITest deleteAPItest;
    Util util;
    public CloseableHttpResponse closeableHttpResponse;
    public JSONObject responseJSON = new JSONObject();

    //Get Request for the complete list of playlists************************************

    @Given("^I access the api for playlists at \"([^\"]*)\"$")
    public void iAccessTheApiForPlaylistsAt(String endpoint) {
        util = new Util();
        Assert.assertTrue(util.setupPlaylistURL().equals(endpoint));
    }


    @And("^the list of playlists is not empty$")
    public void theListOfPlaylistsIsNotEmpty() throws IOException {
        util = new Util();
        util.checkIfListOfPlaylistsIsEmpty();
    }

    @When("^I request the list of playlists$")
    public void iRequestTheListOfPlaylists() throws IOException {
        restClient = new RestClient();
        closeableHttpResponse = restClient.get(util.setupPlaylistURL());
    }


    @Then("^I receive the correct json file with the list of playlists$")
    public void iReceiveTheCorrectJsonFileWithTheListOfPlaylists() throws IOException {
        getAPITest = new GetAPITest();
        responseJSON = getAPITest.getResponseJSON(closeableHttpResponse);
        getAPITest.getApiPlaylistResponseBody(responseJSON);
    }

    @And("^the GET status code for the list of playlists is (\\d+)$")
    public void theGETStatusCodeForTheListOfPlaylistsIs(int statusCode) throws IOException {
        getAPITest = new GetAPITest();
        getAPITest.getApiStatusCode(closeableHttpResponse,statusCode);
    }

    //Get Request for one specific playlist************************************


    //Post Request for one specific playlist***********************************

    @And("^the playlist \"([^\"]*)\" is not in the list of playlists already$")
    public void thePlaylistIsNotInTheListOfPlaylistsAlready(String title) throws IOException {
        util = new Util();
        String playlistInArray = util.checkIfPlaylistIsInTheList(title);
        Assert.assertFalse(title.equals(playlistInArray));
    }

    @When("^I want to add to the list of playlists \"([^\"]*)\" by \"([^\"]*)\"$")
    public void iWantToAddToTheListOfPlaylistsBy(String title, String desc) throws IOException {
        postAPITest = new PostAPITest();
        closeableHttpResponse = postAPITest.PostPlaylist(title,desc);
    }

    @Then("^the playlist \"([^\"]*)\" by \"([^\"]*)\" is added to the list$")
    public void thePlaylistByIsAddedToTheList(String title, String desc) throws IOException {
        postAPITest = new PostAPITest();
        util = new Util();
        responseJSON = postAPITest.postResponseJSON(closeableHttpResponse);
        postAPITest.postApiPlaylistResponseBody(title,desc, responseJSON);
    }

    @And("^the POST status code for the list of playlists is (\\d+)$")
    public void thePOSTStatusCodeForTheListOfPlaylistsIs(int statusCode) {
        postAPITest = new PostAPITest();
        postAPITest.postApiStatusCode(closeableHttpResponse,statusCode);
    }

    //Delete a specific playlist***********************************************

    @When("^I delete the playlist \"([^\"]*)\" by \"([^\"]*)\"$")
    public void iDeleteThePlaylistBy(String title, String desc) throws IOException {
        try{
            util = new Util();
            restClient = new RestClient();
            String id = util.getPlaylistId(title,desc);
            String url = util.setupPlaylistURLwithID(id);
            closeableHttpResponse = restClient.delete(url);
        }
        catch (HttpHostConnectException e)
        {
            System.out.println("ERROR: The playlist you are trying to delete is not in the list");
        }
    }


    @Then("^the playlist for \"([^\"]*)\" is deleted$")
    public void thePlaylistForIsDeleted(String title) throws IOException {
        util = new Util();
        String titleInArray = util.checkIfPlaylistIsInTheList(title);
        Assert.assertFalse(title.equals(titleInArray));
    }



    @And("^the DELETE status code for this is (\\d+)$")
    public void theDELETEStatusCodeForThisIs(int statusCode) {
        try {
            util = new Util();
            deleteAPItest = new DeleteAPITest();
            deleteAPItest.deleteApiStatusCode(closeableHttpResponse, statusCode);
        }
        catch (NullPointerException npe)
        {
            System.out.println("\n");
            System.out.println("ERROR: The request does not work because there the ID in /api/playlist/(id) does not exist");
        }
    }
}

