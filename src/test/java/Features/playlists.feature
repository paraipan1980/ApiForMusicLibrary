Feature: playlist

    Scenario: Get Request for the complete list of playlist
        Given I access the api for playlists at "http://turing.niallbunting.com:3003/api/playlist"
        And the list of playlists is not empty
        When I request the list of playlists
        Then I receive the correct json file with the list of playlists
        And the GET status code for the list of playlists is 200

    Scenario: Get Request for one specific playlist
        Given I access the api for playlists at "http://turing.niallbunting.com:3003/api/playlist"
        When I request "Chill List" list
        Then I receive the correct json file for "Chill List"
        And the new GET status code is 200 for "Chill List"

    Scenario: Post Request for one specific video
        Given I access the api for playlists at "http://turing.niallbunting.com:3003/api/playlist"
        And the playlist "Top 200" is not in the list of playlists already
        When I want to add to the list of playlists "Top 200" by "Larry's Favourite"
        Then the playlist "Top 200" by "Larry's Favourite" is added to the list
        And the POST status code for the list of playlists is 201

    Scenario: Patch a specific video
        Given I access the api for playlists at "http://turing.niallbunting.com:3003/api/playlist"
        When I want to add a video to a list
        Then the PATCH status code for this request is 204

    Scenario: Remove a specific video
        Given I access the api for playlists at "http://turing.niallbunting.com:3003/api/playlist"
        When I want to remove a video from a list
        Then the PATCH status code for this is 501

    Scenario: Delete a specific video
        Given I access the api for playlists at "http://turing.niallbunting.com:3003/api/playlist"
        When I delete the playlist "Top 200" by "Larry's Favourite"
        Then the playlist for "Top 200" is deleted
        And the DELETE status code for this is 204