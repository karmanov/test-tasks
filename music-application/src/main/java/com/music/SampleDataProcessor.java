package com.music;

import com.music.domain.Song;
import com.music.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Class contains logic to insert sample data to the embedded MongoDB
 */
@Component
public class SampleDataProcessor implements CommandLineRunner {

    @Autowired
    private SongRepository songRepository;

    @Override
    public void run(String... args) {
        Song song = new Song();
        song.setId("4f18defb-76b9-4c12-98ff-86584e444201");
        song.setArtist("artist1");
        song.setGenre("genre1");
        song.setImage("https://www.nasa.gov/sites/default/files/styles/full_width_feature/public/thumbnails/image/dbpbyitxcaa5ttg.jpg_orig.jpg");

        songRepository.save(song);

        Song song2 = new Song();
        song2.setId("082fe0c4-42c7-4763-91e5-bb26e280054f");
        song2.setArtist("artist2");
        song2.setGenre("genre2");
        song2.setImage("https://www.nasa.gov/sites/default/files/styles/full_width_feature/public/thumbnails/image/dbpbyitxcaa5ttg.jpg_orig.jpg");

        songRepository.save(song2);
    }
}
