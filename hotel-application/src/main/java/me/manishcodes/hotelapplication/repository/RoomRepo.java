package me.manishcodes.hotelapplication.repository;

import me.manishcodes.hotelapplication.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepo extends JpaRepository<Room,Long> {

}
