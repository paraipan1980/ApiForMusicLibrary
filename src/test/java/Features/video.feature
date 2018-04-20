Feature: video

    Scenario: Get the complete list of videos
        Given I access the api at "http://turing.niallbunting.com:3003/api/video"
        When I request the list of videos
        Then I receive the correct json file with the list of videos
        And the status code is 200

    Scenario: Get one specific video
        Given I access the api at "http://turing.niallbunting.com:3003/api/video"
        When I request one specific video
        Then I receive the correct json file with that specific video
        And the status code is 200 again

    Scenario: Create a video
        Given I access the api at "http://turing.niallbunting.com:3003/api/video"
        When I want to create a video
        Then the video is created
        And the video is added to the complete list of videos

    Scenario: Update a specific video
        GGiven I access the api at "http://turing.niallbunting.com:3003/api/video"
        When I want to update a specific video
        Then the video is not updated
        And the video remains the same in the complete list of videos

    Scenario: Delete a specific video
        Given I access the api at "http://turing.niallbunting.com:3003/api/video"
        When I want to delete a specific video
        Then the video is deleted
        And it is not visible in the complete list of videos