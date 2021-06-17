import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Task9 {
    static HashMap<Integer, String> map = new HashMap<>();
    public static class DownloadThread extends Thread{
        List<Integer> urls;
        Writter writter;
        public DownloadThread(List<Integer>urls, Writter writter){
            this.urls = urls;
            this.writter= writter;
        }
        public void run(){
            for (Integer id: urls) {
                String str = map.get(id);
                String lines = "";
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(str).openStream()));
                    String inputLine;
                    while((inputLine = reader.readLine()) != null){
                        lines = lines + inputLine + "\n";
                    }
                    writter.mapUrls.put(id, str);
                    writter.map.put(id, lines);
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    public static class Writter{
        public ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        public ConcurrentHashMap<Integer, String> mapUrls = new ConcurrentHashMap<>();
        String filename;
        Writter(String filename){
            this.filename = filename;
        }
        public void write(){
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
                for (int i = 0; i < 20; i++){
                    writer.write("===== НАЧАЛО САЙТА <"+ mapUrls.get(i)+"> ======");
                    writer.write(map.get(i));
                    writer.write("===== КОНЕЦ САЙТА <"+ mapUrls.get(i)+"> ======");
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void prepareData(){
        map.put(0, "https://ru.wikipedia.org/wiki/%D0%97%D0%B0%D0%B3%D0%BB%D0%B0%D0%B2%D0%BD%D0%B0%D1%8F_%D1%81%D1%82%D1%80%D0%B0%D0%BD%D0%B8%D1%86%D0%B0");
        map.put(1, "https://commons.wikimedia.org/wiki/%D0%97%D0%B0%D0%B3%D0%BB%D0%B0%D0%B2%D0%BD%D0%B0%D1%8F_%D1%81%D1%82%D1%80%D0%B0%D0%BD%D0%B8%D1%86%D0%B0");
        map.put(2, "https://www.mediawiki.org/wiki/MediaWiki/ru");
        map.put(3, "https://ru.wikipedia.org/w/index.php?title=%D0%A1%D0%BB%D1%83%D0%B6%D0%B5%D0%B1%D0%BD%D0%B0%D1%8F:DownloadAsPdf&page=%D0%97%D0%B0%D0%B3%D0%BB%D0%B0%D0%B2%D0%BD%D0%B0%D1%8F_%D1%81%D1%82%D1%80%D0%B0%D0%BD%D0%B8%D1%86%D0%B0&action=show-download-screen");
        map.put(4, "https://ru.wikipedia.org/wiki/%D0%A1%D0%BB%D1%83%D0%B6%D0%B5%D0%B1%D0%BD%D0%B0%D1%8F:%D0%A1%D1%81%D1%8B%D0%BB%D0%BA%D0%B8_%D1%81%D1%8E%D0%B4%D0%B0/%D0%97%D0%B0%D0%B3%D0%BB%D0%B0%D0%B2%D0%BD%D0%B0%D1%8F_%D1%81%D1%82%D1%80%D0%B0%D0%BD%D0%B8%D1%86%D0%B0");
        map.put(5, "https://ru.wikipedia.org/wiki/%D0%A1%D0%BB%D1%83%D0%B6%D0%B5%D0%B1%D0%BD%D0%B0%D1%8F:%D0%A1%D0%B2%D1%8F%D0%B7%D0%B0%D0%BD%D0%BD%D1%8B%D0%B5_%D0%BF%D1%80%D0%B0%D0%B2%D0%BA%D0%B8?hidebots=1&hidecategorization=1&hideWikibase=1&target=%D0%97%D0%B0%D0%B3%D0%BB%D0%B0%D0%B2%D0%BD%D0%B0%D1%8F_%D1%81%D1%82%D1%80%D0%B0%D0%BD%D0%B8%D1%86%D0%B0&limit=50&days=7&urlversion=2");
        map.put(6, "https://ru.wikipedia.org/w/index.php?title=%D0%97%D0%B0%D0%B3%D0%BB%D0%B0%D0%B2%D0%BD%D0%B0%D1%8F_%D1%81%D1%82%D1%80%D0%B0%D0%BD%D0%B8%D1%86%D0%B0&oldid=111552052");
        map.put(7, "https://ru.wikipedia.org/w/index.php?title=%D0%97%D0%B0%D0%B3%D0%BB%D0%B0%D0%B2%D0%BD%D0%B0%D1%8F_%D1%81%D1%82%D1%80%D0%B0%D0%BD%D0%B8%D1%86%D0%B0&action=info");
        map.put(8, "https://ru.wikipedia.org/wiki/%D0%92%D0%B8%D0%BA%D0%B8%D0%BF%D0%B5%D0%B4%D0%B8%D1%8F:%D0%A1%D0%BE%D0%BE%D0%B1%D1%89%D0%B5%D0%BD%D0%B8%D1%8F_%D0%BE%D0%B1_%D0%BE%D1%88%D0%B8%D0%B1%D0%BA%D0%B0%D1%85");
        map.put(9, "https://ru.wikipedia.org/wiki/%D0%92%D0%B8%D0%BA%D0%B8%D0%BF%D0%B5%D0%B4%D0%B8%D1%8F:%D0%A1%D0%BE%D0%BE%D0%B1%D1%89%D0%B5%D1%81%D1%82%D0%B2%D0%BE");
        map.put(10, "https://ru.wikipedia.org/wiki/%D0%92%D0%B8%D0%BA%D0%B8%D0%BF%D0%B5%D0%B4%D0%B8%D1%8F:%D0%A4%D0%BE%D1%80%D1%83%D0%BC");
        map.put(11, "https://ru.wikipedia.org/wiki/%D0%A1%D0%BB%D1%83%D0%B6%D0%B5%D0%B1%D0%BD%D0%B0%D1%8F:%D0%A1%D0%B2%D0%B5%D0%B6%D0%B8%D0%B5_%D0%BF%D1%80%D0%B0%D0%B2%D0%BA%D0%B8?hidebots=1&hidecategorization=1&hideWikibase=1&limit=50&days=7&urlversion=2");
        map.put(12, "https://ru.wikipedia.org/wiki/%D0%A1%D0%BB%D1%83%D0%B6%D0%B5%D0%B1%D0%BD%D0%B0%D1%8F:%D0%9D%D0%BE%D0%B2%D1%8B%D0%B5_%D1%81%D1%82%D1%80%D0%B0%D0%BD%D0%B8%D1%86%D1%8B");
        map.put(13, "https://ru.wikipedia.org/wiki/%D0%92%D0%B8%D0%BA%D0%B8%D0%BF%D0%B5%D0%B4%D0%B8%D1%8F:%D0%A1%D0%BF%D1%80%D0%B0%D0%B2%D0%BA%D0%B0");
        map.put(14, "https://donate.wikimedia.org/w/index.php?title=Special:LandingPage&country=RU&uselang=ru&utm_medium=sidebar&utm_source=donate&utm_campaign=C13_ru.wikipedia.org");
        map.put(15, "https://ru.wikipedia.org/wiki/%D0%9F%D0%BE%D1%80%D1%82%D0%B0%D0%BB:%D0%A2%D0%B5%D0%BA%D1%83%D1%89%D0%B8%D0%B5_%D1%81%D0%BE%D0%B1%D1%8B%D1%82%D0%B8%D1%8F");
        map.put(16, "https://ru.wikipedia.org/wiki/%D0%A1%D0%BE%D1%81%D1%82%D0%BE%D1%8F%D0%BD%D0%B8%D0%B5_%D0%B3%D0%BE%D0%BD%D0%BA%D0%B8");
        map.put(17, "https://pvs-studio.com/ru/blog/terms/0042/");
        map.put(18, "https://github.com/gladorange/orion_java_2021/blob/main/_LECTIONS/9/home-work9.txt");
        map.put(19, "https://ru.wikipedia.org/wiki/%D0%93%D0%B5%D1%80%D1%86%D0%BE%D0%B3,_%D0%98%D1%86%D1%85%D0%B0%D0%BA");
    }
    public static void main(String [] args){
        {
            prepareData();
            Writter writter = new Writter("sequential.txt");
            DownloadThread downloadThread = new DownloadThread(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19), writter);
            long start = System.nanoTime();
            downloadThread.start();
            try {
                downloadThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            writter.write();
            long finish = System.nanoTime();
            System.out.println(finish - start + "nanotime");
        }

        {
            prepareData();
            Writter writter = new Writter("parallel.txt");
            ArrayList<DownloadThread> downloadThreads = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                downloadThreads.add(new DownloadThread(Arrays.asList(i), writter));
            }
            long start = System.nanoTime();
            downloadThreads.forEach(Thread::start);
            try {
                for (DownloadThread downloadThread : downloadThreads) {
                    downloadThread.join();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            writter.write();
            long finish = System.nanoTime();
            System.out.println(finish - start + "nanotime");
        }
    }
}
