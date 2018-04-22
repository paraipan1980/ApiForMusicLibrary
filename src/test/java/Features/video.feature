Feature: video

    Scenario: Get Request for the complete list of videos
        Given I access the api at "http://turing.niallbunting.com:3003/api/video"
        And the list is not empty
        When I request the list of videos
        Then I receive the correct json file with the list of videos
        And the GET status code is 200

    Scenario: Get Request for one specific video
        Given I access the api at "http://turing.niallbunting.com:3003/api/video"
        When I request "One" by "Metallica"
        Then I receive the correct json file for "One" by "Metallica"
        And the new GET status code is 200 for "One" by "Metallica"

    Scenario: Post Request for one specific video
        Given I access the api at "http://turing.niallbunting.com:3003/api/video"
        And the video "Fear of the dark" is not in the list already
        When I want to add to the list "Fear of the dark" by "Iron Maiden" published on "1991-09-01"
        Then the video "Fear of the dark" by "Iron Maiden" published on "1991-09-01" is added to the list of videos
        And the POST status code is 201

    Scenario: Patch a specific video
        Given I access the api at "http://turing.niallbunting.com:3003/api/video"
        When I want to update "Innuendo" by "Queen"
        Then the PATCH status code is 501

    Scenario: Delete a specific video
        Given I access the api at "http://turing.niallbunting.com:3003/api/video"
        When I delete "Fear of the dark" by "Iron Maiden"
        Then the video for "Fear of the dark" is deleted
        And the DELETE status code is 204