package com.music.controller;

import com.music.domain.Song;
import com.music.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Class contains songs related endpoints
 */

@RestController
public class SongController {

    @Autowired
    private SongService songService;

    /**
     * Get all songs endpoint
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/song-application/songs", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Song> findAll() {
        return songService.findAll();
    }

    /**
     * Get song by id endpoint
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/song-application/songs/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Song findById(@PathVariable("id") String id) {
        return songService.findById(id);
    }

    /**
     * Creation new song
     *
     * @param song
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/song-application/songs", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Song create(@Valid @RequestBody Song song) {
        return songService.create(song);
    }

    /**
     * Update existing song
     *
     * @param id
     * @param song
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/song-application/songs/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Song update(@PathVariable("id") String id, @Valid @RequestBody Song song) {
        return songService.update(id, song);
    }

    /**
     * Delete song
     *
     * @param id
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/song-application/songs/{id}")
    public void delete(@PathVariable("id") String id) {
        songService.delete(id);
    }
}
