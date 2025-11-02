package me.manishcodes.hotelapplication.repository;

import me.manishcodes.hotelapplication.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepo extends JpaRepository<Feedback,Long> {

}
