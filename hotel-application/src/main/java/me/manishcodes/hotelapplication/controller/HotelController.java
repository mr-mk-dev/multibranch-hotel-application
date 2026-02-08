package me.manishcodes.hotelapplication.controller;

import me.manishcodes.hotelapplication.entity.Hotel;
import me.manishcodes.hotelapplication.service.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;

@RestController
public class HotelController {

    private  final HotelService hotelService;

    public HotelController (HotelService hotelService){
        this.hotelService = hotelService;
    }

    @PostMapping("admin/add-hotel")
    public ResponseEntity<?> registerHotel(@RequestBody Hotel hotel){
        return ResponseEntity.ok(hotelService.registerHotel(hotel));
    }
    Iterable<String> stringIterable = new Iterable<String>() {
        @Override
        public Iterator<String> iterator() {

        }
    };


}
