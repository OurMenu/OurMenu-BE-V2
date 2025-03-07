package com.ourmenu.backend.domain.home.dto;

import com.ourmenu.backend.domain.home.domain.Answer;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SaveAnswerRequest {

    private Answer answer;
}
