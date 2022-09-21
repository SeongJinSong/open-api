package com.assignment.openapi.web.searchcomp2.presentation.dto;

import com.sun.istack.Nullable;
import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter //setter가 있어야 argumentResolver에서 객체로 매핑 가능한거 같다.
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SearchComp2Request {
    @NotBlank
    @NotNull
    private String query;
    @Nullable
    @Pattern(regexp = "sim|date")
    private String sort;
    @Nullable
    private Integer start;
    @Nullable
    private Integer display;
}