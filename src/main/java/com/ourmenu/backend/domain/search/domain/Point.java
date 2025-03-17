package com.ourmenu.backend.domain.search.domain;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class Point {

    private List<Double> coordinates;
}
