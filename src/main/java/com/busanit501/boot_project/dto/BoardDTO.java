package com.busanit501.boot_project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor // 모든 매개변수를 가지는 생성자
@NoArgsConstructor // 매개변수를 가지지 않는 생성자
public class BoardDTO {
    private Long bno;


    private String title;
    private String content;
    private String writer;
    private LocalDate regDate;
    private LocalDate modDate;
}
