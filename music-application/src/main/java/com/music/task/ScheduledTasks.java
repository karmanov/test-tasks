package com.music.task;

import com.music.domain.AppUser;
import com.music.repository.AppUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;


/**
 * Class contains background tasks
 */
@Component
public class ScheduledTasks {

    @Autowired
    private AppUserRepository appUserRepository;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    /**
     * Each 30 seconds finds user with 'admin' username and changes it's password
     */
    @Scheduled(fixedRate = 30000)
    public void reportCurrentTime() {
        String newPassword = UUID.randomUUID().toString();
        log.info("New password: {}", newPassword);
        AppUser user = appUserRepository.findOneByUsername("user");
        user.setPassword(newPassword);
        appUserRepository.save(user);
    }
}
