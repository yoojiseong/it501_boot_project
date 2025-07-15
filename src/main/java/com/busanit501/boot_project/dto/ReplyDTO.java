package com.busanit501.boot_project.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {
    private Long rno; // 댓글 번호

    @NotNull
    private Long bno; // 게시글 번호
    @NotEmpty
    private String replyText;
    @NotEmpty
    private String replyer;//작성자
    private LocalDateTime regDate, modDate;
}
