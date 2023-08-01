import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherApp {
    private static final String API_KEY = "b6907d289e10d714a6e88b30761fae22";
    private static final String BASE_URL = "https://samples.openweathermap.org/data/2.5/forecast/hourly";

    public static void main(String[] args) throws IOException {
        displayMenu();
    }

    private static void displayMenu() throws IOException {
        boolean exit = false;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (!exit) {
            System.out.println("\nPlease select an option:");
            System.out.println("1. Get weather");
            System.out.println("2. Get Wind Speed");
            System.out.println("3. Get Pressure");
            System.out.println("0. Exit");

            int option = Integer.parseInt(br.readLine());

            switch (option) {
                case 1:
                    getWeatherData(br);
                    break;
                case 2:
                    getWindSpeedData(br);
                    break;
                case 3:
                    getPressureData(br);
                    break;
                case 0:
                    exit = true;
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }

        br.close();
    }

    private static void getWeatherData(BufferedReader br) throws IOException {
        System.out.print("Enter the date and time (yyyy-MM-dd HH:mm:ss): ");
        String dateTime = br.readLine();
        String apiUrl = BASE_URL + "?q=London,us&appid=" + API_KEY;

        try {
            String response = sendHttpRequest(apiUrl);
            JSONObject jsonObject = new JSONObject(response);
            JSONArray list = jsonObject.getJSONArray("list");

            for (int i = 0; i < list.length(); i++) {
                JSONObject item = list.getJSONObject(i);
                String itemDate = item.getString("dt_txt");

                if (itemDate.equals(dateTime)) {
                    JSONObject main = item.getJSONObject("main");
                    double temp = main.getDouble("temp");
                    System.out.println("Temperature on " + dateTime + ": " + temp + " °C");
                    return;
                }
            }

            System.out.println("No weather data available for the entered date.");
        } catch (Exception e) {
            System.out.println("Error occurred while fetching weather data.");
            e.printStackTrace();
        }
    }

    private static void getWindSpeedData(BufferedReader br) throws IOException {
         System.out.print("Enter the date and time (yyyy-MM-dd HH:mm:ss): ");
        String dateTime = br.readLine();
        String apiUrl = BASE_URL + "?q=London,us&appid=" + API_KEY;

        try {
            String response = sendHttpRequest(apiUrl);
            JSONObject jsonObject = new JSONObject(response);
            JSONArray list = jsonObject.getJSONArray("list");

            for (int i = 0; i < list.length(); i++) {
                JSONObject item = list.getJSONObject(i);
                String itemDate = item.getString("dt_txt");

                if (itemDate.equals(dateTime)) {
                    JSONObject wind = item.getJSONObject("wind");
                    double windSpeed = wind.getDouble("speed");
                    System.out.println("Wind Speed on " + dateTime + ": " + windSpeed + " m/s");
                    return;
                }
            }

            System.out.println("No wind speed data available for the entered date.");
        } catch (Exception e) {
            System.out.println("Error occurred while fetching wind speed data.");
            e.printStackTrace();
        }
    }

    private static void getPressureData(BufferedReader br) throws IOException {
         System.out.print("Enter the date and time (yyyy-MM-dd HH:mm:ss): ");
        String dateTime = br.readLine();
        String apiUrl = BASE_URL + "?q=London,us&appid=" + API_KEY;

        try {
            String response = sendHttpRequest(apiUrl);
            JSONObject jsonObject = new JSONObject(response);
            JSONArray list = jsonObject.getJSONArray("list");

            for (int i = 0; i < list.length(); i++) {
                JSONObject item = list.getJSONObject(i);
                String itemDate = item.getString("dt_txt");

                if (itemDate.equals(dateTime)) {
                    JSONObject main = item.getJSONObject("main");
                    double pressure = main.getDouble("pressure");
                    System.out.println("Pressure on " + dateTime + ": " + pressure + " hPa");
                    return;
                }
            }

            System.out.println("No pressure data available for the entered date.");
        } catch (Exception e) {
            System.out.println("Error occurred while fetching pressure data.");
            e.printStackTrace();
        }
    }

    private static String sendHttpRequest(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        return response.toString();   
    }
}
