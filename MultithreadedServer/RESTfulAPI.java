import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Abdulrezzak Zekiye
 */
 
public class RESTfulAPI {

    public JSONArray api(JSONObject request) throws MalformedURLException, IOException {
        // Uncomment the following if you get an exception related to SSL certificate
		/*
		TrustManager[] trustAllCerts = new TrustManager[]{
        new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(
                java.security.cert.X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(
                java.security.cert.X509Certificate[] certs, String authType) {
            }
        }};
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            
        }
		*/
        String urlString = "https://api.coingecko.com/api/v3/nfts/";
        if(request.getInt("request type") == 1) {
            urlString = urlString + "list?";
            boolean wasBefore = false;
            if(!request.getString("order").isBlank()) {
                urlString = urlString + "order=" + request.getString("order");
                wasBefore = true;
            }
            if(wasBefore) {
                urlString = urlString + "&";
                wasBefore = false;
            }
            if(!request.getString("asset platform id").isBlank()) {
                urlString = urlString + "asset_platform_id=" + request.getString("asset platform id");
                wasBefore = true;
            }
            if(wasBefore) {
                urlString = urlString + "&";
                wasBefore = false;
            }
            if(!(request.getInt("per page") == 0)) {
                urlString = urlString + "per_page=" + request.getInt("per page");
                wasBefore = true;
            }
            if(wasBefore) {
                urlString = urlString + "&";
                wasBefore = false;
            }
            if(!(request.getInt("page") == 0)) {
                urlString = urlString + "page=" + request.getInt("page");
            }
        } else {
            urlString = urlString + request.get("nft id");
        }
        URL url = new URL(urlString);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        String inline = "";
        Scanner scanner = new Scanner(url.openStream());
        while (scanner.hasNext()) {
            inline += scanner.nextLine();
        }
        //Close the scanner
        scanner.close();  
        
        JSONArray jsonObject;
        if(request.getInt("request type") == 1) {
            jsonObject = new JSONArray(inline);
        } else {
            jsonObject = new JSONArray();
            jsonObject.put(inline);
        }
        
        return jsonObject;
    }
    
}
