package com.personal.schedule;

import com.personal.service.ISongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class JobResetListTrending {
    @Autowired
    private ISongService iSongService;
    @Scheduled(cron = "0 0 12 1 * *")
    public void resetListTrending() {
        System.out.println(" Reset  list song trending");
        iSongService.resetListTrending();
    }
}
