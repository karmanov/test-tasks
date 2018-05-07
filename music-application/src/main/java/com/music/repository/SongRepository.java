package com.music.repository;

import com.music.domain.Song;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Songs data access
 */
public interface SongRepository extends MongoRepository<Song, String> {


}
