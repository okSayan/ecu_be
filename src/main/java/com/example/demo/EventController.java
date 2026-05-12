package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventRepository eventRepo;
    private final UserRepository userRepo;

    public EventController(
            EventRepository eventRepo,
            UserRepository userRepo) {

        this.eventRepo = eventRepo;
        this.userRepo = userRepo;
    }

    // GET USER EVENTS
    @GetMapping
    public List<Event> getEvents(

            @RequestHeader("Authorization")
            String authHeader) {

        String token =
            authHeader.replace("Bearer ", "");

        String username =
            JwtUtil.extractUsername(token);

        User user =
            userRepo.findByUsername(username);

        return eventRepo.findByUser(user);
    }

    // CREATE EVENT
    @PostMapping
    public Event createEvent(

            @RequestHeader("Authorization")
            String authHeader,

            @RequestBody Event event) {

        String token =
            authHeader.replace("Bearer ", "");

        String username =
            JwtUtil.extractUsername(token);

        User user =
            userRepo.findByUsername(username);

        event.setUser(user);

        return eventRepo.save(event);
    }
}