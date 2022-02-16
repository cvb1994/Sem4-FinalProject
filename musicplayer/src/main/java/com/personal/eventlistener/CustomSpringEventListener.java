package com.personal.eventlistener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.personal.event.CustomSpringEvent;

@Component
public class CustomSpringEventListener {

	@EventListener
    public void handleContextStart(CustomSpringEvent event) {
        System.out.println("Handling context started event." + event.getMessage());
    }
}
