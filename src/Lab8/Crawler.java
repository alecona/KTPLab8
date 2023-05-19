package Lab8;

import java.util.LinkedList;


/** Класс, который перемещается по веб-страницам и ищет URL-адреса **/
public class Crawler {

    public static void main(String[] args){
        //String[] args = new String[]{"http://mtuci.ru/", "1", "3"};
        if (args.length == 3 && checkNum(args[1]) && checkNum(args[2]))
        {
            String surl = args[0];
            int treadNum = Integer.parseInt(args[2]);

            URLPool pool = new URLPool(Integer.parseInt(args[1]));
            pool.addPair(new URLDepthPair(surl, 0));

            for (int i = 0; i < treadNum; i++) {
                CrawlerTask c = new CrawlerTask(pool);
                Thread t = new Thread(c);
                t.start();
            }

            while (pool.getWait() != treadNum) {
                try {
                    Thread.sleep(500);
                }

                catch (InterruptedException e) {
                    System.out.println("Ignoring Interrupted Exception");
                }
            }
            try {
                showResult(pool.getResult());
            }

            catch (NullPointerException e) {
                System.out.println("Not Link");
            }
            System.exit(0);


        }
        else System.out.println("Error");
    }


    public static void showResult(LinkedList<URLDepthPair> resultLink) {
        for (URLDepthPair c : resultLink)
            System.out.println("Depth: " + c.getDepth() + "\tLink: " + c.toString());
        System.out.println("\nFound links: " + resultLink.size());
    }

    /** Вспомогательная функция для проверки, является ли строка числом **/
    public static boolean checkNum(String num) {
        boolean ok = true;
        for (int i = 0; i < num.length() && ok; i++) ok = Character.isDigit(num.charAt(i));
        return ok;
    }

/**
    public static final String URL_PREFIX = "<a href=\"http";
    static LinkedList<URLDepthPair> findLink = new LinkedList<>();
    static LinkedList<URLDepthPair> resultLink = new LinkedList<>();





    public static boolean check(LinkedList<URLDepthPair> resultLink, URLDepthPair pair) {
        boolean isAlready = true;
        for (URLDepthPair c : resultLink)
            if (c.toString().equals(pair.toString()))
                isAlready = false;
        return isAlready;
    }


    public static void request(PrintWriter out, URLDepthPair pair) {
        out.println("GET " + pair.getPath() + " HTTP/1.1");
        out.println("Host: " + pair.getHost());
        out.println("Connection: close");
        out.println();
        out.flush();
    }


    public static void searchURLs(String urlString, int maxDepth) {
        URLDepthPair urlPair = new URLDepthPair(urlString, 0);
        try {
            findLink.add(urlPair);
            while (!findLink.isEmpty()) {
                URLDepthPair currentPair = findLink.removeFirst();
                int depth = currentPair.getDepth();
                try {
                    Socket s = new Socket(currentPair.getHost(), 80);
                    s.setSoTimeout(1000);
                    PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    request(out, currentPair);
                    String line;
                    while ((line = in.readLine()) != null) {
                        if (line.indexOf(URL_PREFIX) > 0 && depth < maxDepth) {
                            boolean isLinkFound = false;
                            StringBuilder currentLink = new StringBuilder();
                            char c = line.charAt(line.indexOf(URL_PREFIX) + 9);
                            currentLink.append(c);
                            for (int i = line.indexOf(URL_PREFIX) + 10; c != '"' && i < line.length() - 1; i++) {
                                c = line.charAt(i);
                                if (c == '"') isLinkFound = true;
                                else currentLink.append(c);
                            }
                            if (isLinkFound) {
                                URLDepthPair newPair = new URLDepthPair(currentLink.toString(), depth + 1);
                                if (check(findLink, newPair)) findLink.add(newPair);
                            }
                        }
                    }
                    s.close();
                    if (check(resultLink, currentPair)) resultLink.add(currentPair);
                }
                catch (IOException e) { }
            }
            showResult(resultLink);
        }
        catch (NullPointerException e) {
            System.out.println("Not Link");
        }
    }
**/
}
