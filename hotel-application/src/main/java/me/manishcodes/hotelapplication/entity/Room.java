package me.manishcodes.hotelapplication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.manishcodes.hotelapplication.enums.AcType;
import me.manishcodes.hotelapplication.enums.BedType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    private int roomNo;
    private AcType acType;
    private BedType bedType;
    private Double basePrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomImages> images = new ArrayList<>();

    private boolean isAvailable;
    private int maxOccupancy;
    private LocalDate createAt;
    private LocalDate updatedAt;
}
