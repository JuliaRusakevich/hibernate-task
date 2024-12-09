package ru.clevertec.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Client — клиент.
 * o Поля: id, имя, контакты, дата регистрации.
 * o Связи: ManyToMany с автомобилями, OneToMany с
 * отзывами.
 * o Использовать @ElementCollection для хранения
 * контактов.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
@Entity
@Table(schema = "car_showroom", name = "clients")
public class Client {

    @Id
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "fullname")
    private String fullName;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "client_contacts", joinColumns = @JoinColumn(name = "client_uuid"))
    @Column(name = "contact")
    @Builder.Default
    private Set<String> contact = new HashSet<>();

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "client_cars",
            joinColumns = @JoinColumn(name = "client_uuid"),
            inverseJoinColumns = @JoinColumn(name = "car_uuid"))
    @Builder.Default
    private List<Car> cars = new ArrayList<>();


}
