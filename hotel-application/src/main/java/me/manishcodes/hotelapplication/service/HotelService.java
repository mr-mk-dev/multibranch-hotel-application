package me.manishcodes.hotelapplication.service;

import me.manishcodes.hotelapplication.entity.Hotel;
import me.manishcodes.hotelapplication.repository.HotelRepo;
import org.springframework.stereotype.Service;

@Service
public class HotelService {

    private final HotelRepo hotelRepo;

    public HotelService(HotelRepo hotelRepo){
        this.hotelRepo = hotelRepo;
    }

    public Hotel registerHotel(Hotel hotel){
        return hotelRepo.save(hotel);
    }
}
