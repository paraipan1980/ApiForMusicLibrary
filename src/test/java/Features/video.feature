Feature: video

    Scenario: Get Request for the complete list of videos
        Given I access the api at "http://turing.niallbunting.com:3003/api/video"
        When I request the list of videos
        Then I receive the correct json file with the list of videos
        And the GET status code is 200

    Scenario: Get Request for one specific video
        Given I access the api at "http://turing.niallbunting.com:3003/api/video"
        When I request "Innuendo" by "Queen"
        Then I receive the correct json file for "Innuendo" by "Queen"
        And the new GET status code is 200 for "Innuendo" by "Queen"

    Scenario: Post Request for one specific video
        Given I access the api at "http://turing.niallbunting.com:3003/api/video"
        When I want to add to the list "Fear of the dark" by "Iron Maiden" published on "1991-09-01"
        Then the video "Fear of the dark" by "Iron Maiden" published on "1991-09-01" is added to the list of videos
        And the POST status code for "Fear of the dark" by "Iron Maiden" published on "1991-09-01" is 201

    Scenario: Patch a specific video
        Given I access the api at "http://turing.niallbunting.com:3003/api/video"
        When I want to update "Innuendo" by "Queen"
        Then the PATCH status code is 501 for "Innuendo" by "Queen"

    Scenario: Delete a specific video
        Given I access the api at "http://turing.niallbunting.com:3003/api/video"
        When I want to delete "Poker face" by "Lady Gaga"
        Then the video is deleted
        And the DELETE status code is 204 for deleting "Poker face" by "Lady Gaga"