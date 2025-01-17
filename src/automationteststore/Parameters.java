package automationteststore;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
	
public class Parameters {
	public static WebDriver driver = new ChromeDriver();
	
	public static String loginName;
	public static String pass;
	
	public JsonObject getRandomUser() throws IOException {
        URL url = new URL("https://randomuser.me/api/");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        JsonElement root = JsonParser.parseReader(new InputStreamReader(conn.getInputStream()));
        JsonObject json = root.getAsJsonObject();

        return json.get("results").getAsJsonArray().get(0).getAsJsonObject();
    }
	public String generateRandomString(int length) {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
		}
		return sb.toString();
	}
}