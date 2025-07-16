package com.busanit501.boot_project.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

// 엔티티 클래스를 이용해서 마리아 디비에 테이블 생성
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "imageSet")
public class Board extends BaseEntitiy{

    @Id // DB의 pk와 같은 역할
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;
    private String content;
    private String writer;

    //연관 관계 설정1
    @OneToMany(mappedBy = "board", cascade = {CascadeType.ALL},fetch = FetchType.LAZY
    ,orphanRemoval = true) // BoardImage 의 board 변수를 가르킴
    @Builder.Default
    private Set<BoardImage> imageSet = new HashSet<>();

    public void addImage(String uuid, String fileName){
        BoardImage boardImage = BoardImage.builder()
                .uuid(uuid)
                .fileName(fileName)
                .board(this)
                .ord(imageSet.size())
                .build();
        imageSet.add(boardImage);
    }

    public void clearImages(){
        // 부모테이블(Board)가 null이 된다면 자식(BoardImage)객체가 자동 삭제됨
        imageSet.forEach(boardImage -> boardImage.changeBoard(null));
    }

    // 불변성으로 데이터직접 수정
    // 변경하려는 필드를 따로 메서드로분리 작업
    public void changTitleContent(String ltitle, String content){
        this.title = ltitle;
        this.content = content;
    }

}
