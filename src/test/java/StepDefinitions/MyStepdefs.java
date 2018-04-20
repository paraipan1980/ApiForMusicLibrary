package StepDefinitions;

import API.GetAPITest;
import API.RestClient;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.io.IOException;

public class MyStepdefs {

    @Given("^I access the api at \"([^\"]*)\"$")
    public void iAccessTheApiAt(String endpoint) {
        GetAPITest getAPITest = new GetAPITest();
        String url = getAPITest.setupURL();
        Assert.assertTrue(url.equals(endpoint));
    }

    @When("^I request the list of videos$")
    public void iRequestTheListOfVideos() throws IOException {
        RestClient restClient = new RestClient();
        GetAPITest getAPITest = new GetAPITest();
        restClient.get(getAPITest.setupURL());
    }

    @Then("^I receive the correct json file with the list of videos$")
    public void iReceiveTheCorrectJsonFileWithTheListOfVideos() throws IOException {
        GetAPITest getAPITest = new GetAPITest();
        String url = "http://turing.niallbunting.com:3003/api/video";
        getAPITest.getApiResponseBody(url);
    }

    @And("^the status code is (\\d+)$")
    public void theStatusCodeIs(int statusCode) throws IOException {
        String url = "http://turing.niallbunting.com:3003/api/video";
        GetAPITest getAPITest = new GetAPITest();
        getAPITest.getApiStatusCode(url,statusCode);
    }



}
