package Lab8;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CrawlerTask implements Runnable{
    URLPool upool;
    public static final String URL_PREFIX = "<a href=\"http";


    @Override
    public void run() {
        do {
            try {
                URLDepthPair pair = upool.getPair();
                int currDepth = pair.getDepth();
                try {
                    Socket s = new Socket(pair.getHost(), 80);
                    s.setSoTimeout(1000);
                    PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                    BufferedReader in =  new BufferedReader(new InputStreamReader(s.getInputStream()));
                    request(out,pair);
                    String line;
                    while ((line = in.readLine()) != null) {
                        if (line.contains(URL_PREFIX)) buildNewUrl(line, currDepth, upool);
                    }
                    s.close();
                }
                catch (IOException e){ }
            }
            catch (NullPointerException e){ }

        }
        while (true);
    }


    public CrawlerTask(URLPool pool) {
        this.upool = pool;
    }


    public static void request(PrintWriter out, URLDepthPair pair) {
        out.println("GET " + pair.getPath() + " HTTP/1.1");
        out.println("Host: " + pair.getHost());
        out.println("Connection: close");
        out.println();
        out.flush();
    }


    public static void buildNewUrl(String str, int depth,URLPool pool) {
        try {
            String currentLink = str.substring(str.indexOf(URL_PREFIX) +
                    URL_PREFIX.indexOf("\"") + 1, str.indexOf("\"", str.indexOf(URL_PREFIX) +
                    URL_PREFIX.indexOf("\"") + 2));
            pool.addPair(new URLDepthPair(currentLink, depth + 1));
        }
        catch (StringIndexOutOfBoundsException e){ }
    }

}
