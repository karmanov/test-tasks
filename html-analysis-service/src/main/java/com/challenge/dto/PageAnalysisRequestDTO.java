package com.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageAnalysisRequestDTO {

    @NotNull(message = "URL must not be null")
    @NotEmpty(message = "URL must not be empty")
    private String url;
}
