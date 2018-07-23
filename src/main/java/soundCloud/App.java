package soundCloud;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class App {
    final static Logger logger = Logger.getLogger(App.class);

    public static void main(String[] args) {

        List<SoundCloudData>  songList = new CopyOnWriteArrayList<>();


       ExecutorService exeutor;
       exeutor = Executors.newFixedThreadPool(3);

        exeutor.execute(new SoundCloudService(songList,"pixes", 10));
        exeutor.execute(new SoundCloudService(songList,"led zepelin", 10));
        exeutor.execute(new SoundCloudService(songList,"jazz", 10));
        exeutor.execute(new SoundCloudService(songList,"metalica", 10));
        exeutor.execute(new SoundCloudService(songList,"iron maiden", 10));
        exeutor.execute(new SoundCloudService(songList,"guns n roses", 10));
        try {
            exeutor.awaitTermination(5000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
        Thread thread1 = new Thread(new SoundCloudService(songList,"pixes", 10));
        Thread thread2 = new Thread(new SoundCloudService(songList,"led zepelin", 10));
        Thread thread3 = new Thread(new SoundCloudService(songList,"jazz", 10));

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
/**/
        List<SoundCloudData> temp = songList.stream().sorted((o1, o2) -> o1.title.compareTo(o2.title)).collect(Collectors.toList());
        logger.error("song list sorted by title: " + temp.toString());


        SoundCloudService soundCloudService = new SoundCloudService(temp);
        soundCloudService.removeEntryByID(temp.get(2).getId());
        soundCloudService.removeEntryByPermalink_url(temp.get(5).getPermalink_url());

        logger.error("song list sorted by title: " + temp.toString());


    }
}

