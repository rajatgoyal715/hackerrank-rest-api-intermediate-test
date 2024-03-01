import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TotalGoals {
    public static int getHomeGoals(String team, int year) throws IOException {
        String BASE_URL = "https://jsonmock.hackerrank.com/api/football_matches";

        int count = 0;
        int page = 1;
        int totalPages = 1;
        String response;

        while (page <= totalPages) {
            String team1Url = String.format("%s?year=%d&team1=%s&page=%d", BASE_URL, year, URLEncoder.encode(team, StandardCharsets.UTF_8), page);

            URL url = new URL(team1Url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while ((response = br.readLine()) != null) {
                JsonObject jsonResponse = new Gson().fromJson(response, JsonObject.class);
                totalPages = jsonResponse.get("total_pages").getAsInt();

                JsonArray data = jsonResponse.getAsJsonArray("data");
                for (JsonElement e : data) {
                    int team1goals = e.getAsJsonObject().get("team1goals").getAsInt();
                    count += team1goals;
                }
            }
            page++;
        }

        return count;
    }

    public static int getAwayGoals(String team, int year) throws IOException {
        String BASE_URL = "https://jsonmock.hackerrank.com/api/football_matches";

        int count = 0;
        int page = 1;
        int totalPages = 1;
        String response;

        while (page <= totalPages) {
            String team2Url = String.format("%s?year=%d&team2=%s&page=%d", BASE_URL, year, URLEncoder.encode(team, StandardCharsets.UTF_8), page);

            URL url = new URL(team2Url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while ((response = br.readLine()) != null) {
                JsonObject jsonResponse = new Gson().fromJson(response, JsonObject.class);
                totalPages = jsonResponse.get("total_pages").getAsInt();

                JsonArray data = jsonResponse.getAsJsonArray("data");
                for (JsonElement e : data) {
                    int team2goals = e.getAsJsonObject().get("team2goals").getAsInt();
                    count += team2goals;
                }
            }
            page++;
        }

        return count;
    }

    /*
     * Complete the 'getTotalGoals' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. STRING team
     *  2. INTEGER year
     */
    public static int getTotalGoals(String team, int year) throws IOException {
        int homeGoals = getHomeGoals(team, year);
        int awayGoals = getAwayGoals(team, year);
        return homeGoals + awayGoals;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String team = bufferedReader.readLine();

        int year = Integer.parseInt(bufferedReader.readLine().trim());

        int result = getTotalGoals(team, year);
        System.out.println(result);

        bufferedReader.close();
    }
}
