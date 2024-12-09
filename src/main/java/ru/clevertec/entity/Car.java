package ru.clevertec.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * • Car — автомобиль.
 * o Поля: id, модель, марка, год выпуска, цена, категория.
 * o Связи: ManyToOne с автосалоном, OneToMany с
 * отзывами.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
@Entity
@Table(schema = "car_showroom", name = "cars")
public class Car {

    @Id
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "model")
    private String model;

    @Column(name = "make")
    private String make;

    @Column(name = "year")
    private Integer year;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne
    private Category category;

    @ManyToOne
    @JoinColumn(name = "car_showroom_uuid")
    private CarShowroom carShowroom;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "client_cars",
            joinColumns = @JoinColumn(name = "car_uuid"),
            inverseJoinColumns = @JoinColumn(name = "client_uuid"))
    @Builder.Default
    private List<Car> cars = new ArrayList<>();

}
