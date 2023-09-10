package academy.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class User {
    private String name;
    private String password;
    private boolean online;

    public User(String login, String password, boolean online) {
        this.name = login;
        this.password = password;
        this.online = online;
    }

    public static User fromJSON(String s) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(s, User.class);
    }

    public String toJSON() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    public int login(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            String json = toJSON();
            os.write(json.getBytes(StandardCharsets.UTF_8));
            return conn.getResponseCode(); // 200?
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
