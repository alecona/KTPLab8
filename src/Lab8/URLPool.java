package Lab8;

import java.util.LinkedList;

public class URLPool {

    LinkedList<URLDepthPair> urlFind = new LinkedList();
    LinkedList<URLDepthPair> urlRes = new LinkedList();
    int maxDepth;
    int waitN;

    public URLPool(int maxDepth) {
        this.maxDepth = maxDepth;
        waitN = 0;
    }

    /** Метод для использования wait(), чтобы потоки сканера могли ожидать появления новых URL-адресов. **/
    public synchronized URLDepthPair getPair() {
        while (urlFind.size() == 0) {
            waitN++;
            try {
                wait();
            }
            catch (InterruptedException e) {
                System.out.println("Ignoring Interrupted Exception");
            }
            waitN--;
        }
        URLDepthPair nextPair = urlFind.getFirst();
        urlFind.removeFirst();
        return nextPair;
    }

    /** Метод для использования notify(), чтобы потоки сканера могли ожидать появления новых URL-адресов. **/
    public synchronized void addPair(URLDepthPair pair) {
        if(check(urlRes,pair)){
            urlRes.add(pair);
            if (pair.getDepth() < maxDepth){
                urlFind.add(pair);
                notify();
            }
        }
    }


    public synchronized int getWait() {
        return waitN;
    }

    /** Метод для проверки на совпадение пар **/
    private static boolean check(LinkedList<URLDepthPair> resultLink, URLDepthPair pair) {
        boolean chekin = true;
        for (URLDepthPair c : resultLink) if (c.toString().equals(pair.toString())) chekin = false;
        return chekin;
    }


    public LinkedList<URLDepthPair> getResult() {
        return urlRes;
    }


}
