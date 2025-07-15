package com.busanit501.boot_project.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor // 모든 매개변수를 가지는 생성자
@NoArgsConstructor // 매개변수를 가지지 않는 생성자
public class BoardDTO {
    private Long bno;

    @NotEmpty
    @Size(min = 3, max=100)
    private String title;

    @NotEmpty
    @Size(min = 3, max=1000)
    private String content;

    @NotEmpty
    @Size(min = 3, max=100)
    private String writer;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
