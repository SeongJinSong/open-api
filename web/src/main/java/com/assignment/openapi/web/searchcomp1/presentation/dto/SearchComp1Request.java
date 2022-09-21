package com.assignment.openapi.web.searchcomp1.presentation.dto;

import com.sun.istack.Nullable;
import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter //setter가 있어야 argumentResolver에서 객체로 매핑 가능한거 같다.
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@ToString
public class SearchComp1Request {
    @NotBlank
    @NotNull
    private String query;
    @Nullable
    @Pattern(regexp = "accuracy|recency")
    private String sort;
    @Nullable
    private Integer page;
    @Nullable
    private Integer size;
}
