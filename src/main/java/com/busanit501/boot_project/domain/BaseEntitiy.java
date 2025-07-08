package com.busanit501.boot_project.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

//모든 엔티티 테이블에 공통으로 들어가는 필드를 분리
// 데이터 추가 된 시간, 수정 시간
// 컬럼이 매번 다른 엔티티 클래스에서 중복 사용하면 비효율적이다.
// 공통으로 분리해서 사용한다.
@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
abstract class BaseEntitiy {

    @CreatedDate
    @Column(name = "regDate", updatable = false) //업데이트 불가
    private LocalDateTime regDate;

    @LastModifiedDate //마지막으로 수정 한 날짜로 사용
    @Column(name = "modDate")
    private LocalDateTime modDate;// 수정날짜
}
