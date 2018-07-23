package soundCloud;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SoundCloudService implements Runnable{
    final static Logger logger = Logger.getLogger(SoundCloudService.class);

    List<SoundCloudData> songList;
    String artist;
    int limit;

    public SoundCloudService(List<SoundCloudData> songList, String artist, int limit) {
        this.songList = songList;
        this.artist = artist;
        this.limit = limit;
    }

    public SoundCloudService(List<SoundCloudData> songList) {
        this.songList = songList;
    }

    @Override
    public void run() {
        getRecords();
    }


    public void getRecords(){
        String url = "http://api.soundcloud.com/tracks/?q=" + artist + "&client_id=pCNN85KHlpoe5K6ZlysWZBEgLJRcftOd";

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.error("getting data for: " + artist);
        List<SoundCloudData> localSongList = new CopyOnWriteArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity responseEntity;
        responseEntity = restTemplate.getForEntity(url, String.class);

        String s = (String) responseEntity.getBody();
        try {
            localSongList = objectMapper.readValue(s, new TypeReference<List<SoundCloudData>>(){});
            logger.error("query for: " + artist + ", got " + localSongList.size() + " results");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(limit <= 0 || localSongList.size() < limit){
            songList.addAll(localSongList);
        }
        else{
            songList.addAll(localSongList.subList(0,limit));
        }

        System.err.println("finished " + Thread.currentThread().getId());
    }

    public void removeEntryByID(int id){
        songList.removeIf(soundCloudData -> soundCloudData.getId() == id);
    }

    public void removeEntryByPermalink_url(String permalink_url){
        songList.removeIf(soundCloudData -> soundCloudData.getPermalink_url().equals(permalink_url));

    }
}
