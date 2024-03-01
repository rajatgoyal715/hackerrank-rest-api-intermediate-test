import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NumberOfDrawnMatches {

    /*
     * Complete the 'getNumDraws' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts INTEGER year as parameter.
     */
    public static int getNumDraws(int year) throws IOException {
        String BASE_URL = "https://jsonmock.hackerrank.com/api/football_matches";
        int MAX_GOALS = 10;

        int draws = 0;
        String response;
        for (int goal = 0; goal <= MAX_GOALS; goal++) {
            String matchesUrl = String.format("%s?year=%d&team1goals=%d&team2goals=%d", BASE_URL, year, goal, goal);

            URL url = new URL(matchesUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while ((response = br.readLine()) != null) {
                JsonObject jsonResponse = new Gson().fromJson(response, JsonObject.class);
                int total = jsonResponse.get("total").getAsInt();
                draws += total;
            }
        }
        return draws;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int year = Integer.parseInt(bufferedReader.readLine().trim());

        int result = getNumDraws(year);
        System.out.println(result);

        bufferedReader.close();
    }

}
