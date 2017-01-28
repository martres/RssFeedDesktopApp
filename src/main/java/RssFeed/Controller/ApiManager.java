package RssFeed.Controller;

import RssFeed.Model.Feed;
import RssFeed.Model.Singleton;
import RssFeed.Model.User;
import RssFeed.View.FeedView;
import RssFeed.View.LoginView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.net.ssl.HttpsURLConnection;
import javax.print.DocFlavor;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * Created by martreux on 27/01/2017.
 */
public class ApiManager {

    static String url = "https://gentle-forest-84146.herokuapp.com";

    private static String sendGet(String url, Boolean withToken) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        if (withToken)
            con.addRequestProperty("token" , Singleton.getInstance().getUserConnected().token);

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println(response.toString());
        return response.toString();
    }

    public static void connectUser(LoginView view, String email, String password) throws IOException {
        String urlConnect = url + "/login/mailLogin";
        String response = sendPostRequest(urlConnect, "email=" + email + "&password=" + password, false);
        Gson gson = new Gson();
        User user = gson.fromJson(response , User.class);
        if (Objects.equals(user.status, "login.error")) {
            view.errorConnexion();
        } else {
            Singleton.getInstance().setUserConnected(user);
            view.correctConnexion();
        }
    }

    private static String sendPostRequest(String urlConnect, String urlParameters, Boolean withToken) throws IOException {
        URL obj = new URL(urlConnect);
        System.out.print(urlConnect);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        if (withToken)
            con.addRequestProperty("token" , Singleton.getInstance().getUserConnected().token);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println(response.toString());
        return response.toString();
    }

    private static String sendDeleteRequest(String urlConnect, String urlParameters, Boolean withToken) throws IOException {
        URL obj = new URL(urlConnect);
        System.out.print(urlConnect);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("DELETE");
        if (withToken)
            con.addRequestProperty("token" , Singleton.getInstance().getUserConnected().token);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'DELETE' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println(response.toString());
        return response.toString();
    }


    public static void createAccount(LoginView view, String email, String password) throws IOException {
        String urlConnect = url + "/accountRss/create";
        String response = sendPostRequest(urlConnect, "email=" + email + "&password=" + password, false);
        Gson gson = new Gson();
        User user = gson.fromJson(response , User.class);
        if (Objects.equals(user.status, "compte.exist")) {
            view.errorConnexion();
        } else {
            Singleton.getInstance().setUserConnected(user);
            view.correctConnexion();
        }
    }

    public static void pushFlux(FeedView view, String urlParam) throws IOException {
        String urlConnect =  url + "/feed/addFeeds";
        String response = sendPostRequest(urlConnect, "feedUrl=" + urlParam, true);
        getFlux(view);
    }

    public static void deleteFlux(FeedView view, String idFlux) throws IOException {
        String urlConnect = url + "/feed/" + idFlux;
        String response = sendDeleteRequest(urlConnect, "", true);
        getFlux(view);
    }


    public static void getFlux(FeedView view) throws  IOException {
        String urlConnect = url + "/feed/getFeeds";
        try {
            String response = sendGet(urlConnect, true);
            Gson gson = new Gson();
            JsonObject feedList = gson.fromJson(response, JsonObject.class);
            if (Objects.equals(feedList.get("message").toString(), "\"ko\"")) {
                return;
            }
            System.out.println(feedList.get("message").toString());
            Feed[] feeds = gson.fromJson(feedList.getAsJsonObject("feedList").getAsJsonArray("feedList"), Feed[].class);
            Singleton.getInstance().setFeeds(feeds);
            view.reloadListFlux();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
