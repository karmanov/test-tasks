package com.music.service;

import com.music.domain.Song;
import com.music.exception.SongNotFoundException;
import com.music.repository.SongRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Class contains song related CRUD operations
 */
@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;

    /**
     * Create song
     *
     * @param song
     * @return
     */
    public Song create(Song song) {
        String id = UUID.randomUUID().toString();
        song.setId(id);
        return songRepository.save(song);
    }

    /**
     * Find song by given id
     *
     * @param id
     * @return
     */
    public Song findById(String id) {
        return songRepository.findById(id).orElseThrow(SongNotFoundException::new);
    }

    /**
     * Find all songs from database
     *
     * @return
     */
    public List<Song> findAll() {
        return songRepository.findAll();
    }


    /**
     * Delete song
     *
     * @param id
     */
    public void delete(String id) {
        songRepository.deleteById(id);
    }

    /**
     * Update song with given id, with given information
     *
     * @param id
     * @param song
     * @return
     */
    public Song update(String id, Song song) {
        Song songToUpdate = findById(id);
        BeanUtils.copyProperties(song, songToUpdate, "id");
        return songRepository.save(songToUpdate);
    }
}
