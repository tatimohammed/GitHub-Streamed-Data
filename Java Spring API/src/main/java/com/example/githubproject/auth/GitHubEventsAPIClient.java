package com.example.githubproject.auth;

import com.example.githubproject.database.GithubData;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GitHubEventsAPIClient {
    private ObjectMapper objectMapper = new ObjectMapper();

    public List<GithubData> repos(int streams) throws InterruptedException {
        String start_date = "2023-01-01";
        String end_date = "2023-06-01";

        String accessToken = "ACCESS_Token";


        try {
            List<GithubData> repos = new ArrayList<>();
            for(int c = 0; c < streams; c++) {
                URL url = new URL("https://api.github.com/search/repositories?q=created:" + start_date + ".." + end_date);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Authorization", "Bearer " + accessToken);
                connection.setRequestProperty("Accept", "application/vnd.github.v3+json");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    String json = response.toString();
                    // Find the starting and ending indices of the desired text
                    int startIndex = json.indexOf("\"items\":") + "\"items\":".length();
                    int endIndex = json.indexOf("}]") + "}]".length();

                    // Extract the desired text
                    String extractedText = json.substring(startIndex, endIndex);

                    // Convert JSON string to JSON object

                    Object jsonobject = objectMapper.readValue(extractedText, Object.class);

                    GithubData git = new GithubData();
                    // Generate a random UUID
                    UUID randomUUID = UUID.randomUUID();

                    // Convert UUID to string
                    String randomId = randomUUID.toString();
                    git.setStreamId(randomId);
                    git.setJsondata(jsonobject);
                    repos.add(git);
                    Thread.sleep(500);

                    // Process the JSON response and extract the repository information
                } else {
                    System.out.println("Error: "+ c + " " + responseCode + " - " + connection.getResponseMessage());
                    return null;
                }
            }

            return repos;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }




    }

}
