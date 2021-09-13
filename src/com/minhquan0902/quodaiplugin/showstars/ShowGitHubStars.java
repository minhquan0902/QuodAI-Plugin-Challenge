package com.minhquan0902.quodaiplugin.showstars;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.javac.JavacRemoteProto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class ShowGitHubStars extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {

        // Get the username and repo value
       String getUsername = Messages.showInputDialog(anActionEvent.getProject(),"Enter the GitHub username: ", "GitHub Star Search", Messages.getQuestionIcon());
       String getRepoName = Messages.showInputDialog(anActionEvent.getProject(), "Enter the public repo name: ", "GitHub STar Search", Messages.getQuestionIcon());



       HashMap<String, Integer> dataHashMap = new HashMap<String, Integer>();
        ArrayList<String> dataList = new ArrayList<String>();

        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        // Fetch the GitHub API data using java.net.HttpURLConnection
        try{
            // Format URL String
            String URLString = String.format("https://api.github.com/users/%s/repos", getUsername);

            // Define GitHub API URL and open connection
            URL url = new URL(URLString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Request setup
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();

            // Check if connection status is good or bad
            if (status > 299){
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null){
                    responseContent.append(line);
                }

                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null){
                    responseContent.append(line);
                }

                reader.close();
            }

            // Get the HashMap of Data for future data index
            HashMap<String, Integer> data= ParseJSON.putDataToHashMap(responseContent.toString(), dataHashMap);

            // Put Data in to ArrayList of Strings for search queries
            ArrayList<String> dataArray = ParseJSON.putDataToArrayList(responseContent.toString(), dataList);


            // Test Search Function using keyword
            ArrayList<String> searchList = new ArrayList<String>();

            System.out.println("Search Result for 'abc': ");
            System.out.println("\n-----------------------------");
            for (String element: dataArray){
                assert getRepoName != null;
                if (element.contains(getRepoName.toUpperCase())){
                    searchList.add(element);

                }
            }
                // data.get(element)
            if (searchList.isEmpty()){
                Messages.showMessageDialog(anActionEvent.getProject(), "Cannot find the repo you requested :(. Does that repo exist? Or is it a public repo?", "Errors", Messages.getErrorIcon());
            } else {

                for (String element : searchList) {
                    String formatMessage = String.format("Stargazer counts for the requested repo is: %s", data.get(element));
                    Messages.showMessageDialog(anActionEvent.getProject(), formatMessage, "Success! :)", Messages.getInformationIcon());

                }
            }




        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }





}
