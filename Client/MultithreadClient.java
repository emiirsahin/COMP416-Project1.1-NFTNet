import java.io.IOException;
import java.util.Scanner;
import org.json.JSONObject;

public class MultithreadClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ConnectionToServer connectionToServer = new ConnectionToServer(ConnectionToServer.DEFAULT_SERVER_ADDRESS, ConnectionToServer.DEFAULT_SERVER_PORT);
        connectionToServer.Connect();
        Scanner scanner = new Scanner(System.in);
        System.out.println("The requests you can make and their respective potential parameters are as follows:\n\n 1-list\n\t*order\n\t*asset platform id\n\t*per page\n\t*pages\n2-get\n\t*nft id\n\nyou must supply an nft id as a parameter for the get request.\ntype either 1 or 2 to declare your request, type 'exit' to disconnect.");
        String message = scanner.nextLine();
        while (!message.equals("exit"))
        {
            JSONObject request = new JSONObject();
            switch(Integer.parseInt(message)) {
                case 1:
                    System.out.println("To set an 'order' parameter value, type the value, if not leave empty.");
                    String order, assetPlatformId, perPageString, pageString;

                    order = scanner.nextLine();

                    System.out.println("To set an 'asset platform id' parameter value, type the value, if not leave empty.");

                    assetPlatformId = scanner.nextLine();

                    System.out.println("To set a 'per page' parameter value, type the value, if not leave empty.");

                    perPageString = scanner.nextLine();
                    if(perPageString.isBlank()) {
                        request.put("per page", 0);
                    } else {
                        request.put("per page", Integer.parseInt(perPageString));
                    }

                    System.out.println("To set a 'page' parameter value, type the value, if not leave empty.");

                    pageString = scanner.nextLine();
                    if(pageString.isBlank()) {
                        request.put("page", 0);
                    } else {
                        request.put("page", Integer.parseInt(pageString));
                    }

                    request.put("request type", 1);
                    request.put("order", order);
                    request.put("asset platform id", assetPlatformId);

                    break;
                case 2:
                    String id;

                    System.out.println("Please enter the 'nft id' value.");

                    id = scanner.nextLine();
                    while(id.isBlank()) {
                        System.out.println("You must enter an nft id.");
                        id = scanner.nextLine();
                    }
                    
                    request.put("nft id", id);
                    request.put("request type", 2);

                    break;
                default:
                    System.out.println("Invalid request type");
            }

            System.out.println("Response from server: " + connectionToServer.SendForAnswer(request));
            message = scanner.nextLine();
        }
        connectionToServer.Disconnect();
    }
    
}
