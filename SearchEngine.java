import java.io.IOException;
import java.net.URI;
import java.util.*;

class SearchHandler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> result = new ArrayList<String>();

    public String handleRequest(URI url) {
        StringBuilder build = new StringBuilder("");
        if (url.getPath().equals("/")) {
            build.append("Jeffrey's strings are: \n");
            for (int i = 0; i < result.size(); i++)
            {
                build.append(" " + result.get(i));
            }
            return build.toString();
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    build.append(parameters[1]);
                    result.add(parameters[1]);
                    return build.toString();
                }
            }
            else if (url.getPath().contains("/search"))
            {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    for (int i = 0; i < result.size(); i++)
                    {
                        if (result.get(i).contains(parameters[1]))
                        {
                            build.append(result.get(i) + " ");
                        }
                    }
                    return build.toString();
                }
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new SearchHandler());
    }
}
