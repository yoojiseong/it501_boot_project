package com.busanit501.boot_project.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

// 엔티티 클래스를 이용해서 마리아 디비에 테이블 생성
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board extends BaseEntitiy{

    @Id // DB의 pk와 같은 역할
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;
    private String content;
    private String writer;

    // 불변성으로 데이터직접 수정
    // 변경하려는 필드를 따로 메서드로분리 작업
    public void changTitleContent(String ltitle, String content){
        this.title = ltitle;
        this.content = content;
    }

}
