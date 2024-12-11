package ru.clevertec.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

/**
 * Review — отзыв.
 * o Поля: id, текст отзыва, рейтинг, клиент, автомобиль.
 * o Связи: ManyToOne с клиентом, ManyToOne с
 * автомобилем.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(schema = "car_showroom", name = "reviews")
public class Review {

    @Id
    @Column(name = "uuid")
    private UUID uuid;
    @Column(name = "review")
    private String review;
    @Column(name = "rating")
    private Integer rating;
    @ManyToOne
    @JoinColumn(name = "client_uuid", nullable = false)
    private Client client;
    @ManyToOne
    @JoinColumn(name = "car_uuid", nullable = false)
    private Car car;
}
