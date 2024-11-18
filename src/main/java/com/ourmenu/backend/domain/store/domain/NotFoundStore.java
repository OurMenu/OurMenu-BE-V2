package com.ourmenu.backend.domain.store.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class NotFoundStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @NotNull
    private String title;

    @NotNull
    private String address;

    @NotNull
    private Double mapX;

    @NotNull
    private Double mapY;

    @ManyToOne
    private Store store;
}
