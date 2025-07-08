package com.busanit501.boot_project.repository.search;

import com.busanit501.boot_project.domain.Board;
import com.busanit501.boot_project.domain.QBoard;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

//Interface 파일 이름 + Impl 네이밍 규칙
// QuerydslRepositrySupport : 부모 클래스, Querydsl 사용하기 위한 도구함.
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {

    public BoardSearchImpl() {
        // 부모 클래스에 엔티티 클래스 전달. 사용할 클래스 지정.
        super(Board.class);
    }

    @Override
    public Page<Board> search(Pageable pageable) {
        // QueryDsl, 사용법,
        // 순서 1
        // Q도메인 객체 : 엔티티 클래스 Board, 동적 쿼리 작업 하기 위한
        // 편하게 만든 클래스라고 생각하면 됨.
        // 기능이 향상된 버전이다.
        QBoard board = QBoard.board;

        // 2 조회
        JPQLQuery<Board> query = from(board); // select .. from board
        // 3 조건
        query.where(board.title.contains("1")); // where title like...

        // 4 가져오기
        List<Board> list = query.fetch(); //디비에서 데이터 가져오기
        // 5
        Long count = query.fetchCount(); // 가져온 디비의 갯수 확인
        // 6 페이징 조건 추가
        // 검색 쿼리에 페이징 조건 탑재(지정 범위 내에서 query조건 적용하기)
        this.getQuerydsl().applyPagination(pageable, query);
        // 7. 페이징 조건 적용 후 조회하기
        List<Board> list2 = query.fetch();
        // 순서8.
        // 페이징 조건 적용 + 조건 갯수
        long count2 = query.fetchCount();
        return null;
    }


    // 페이징 정보  + 검색 정보를 이용해서 자바 메서드로 -> sql 전달하기.
    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {
        // 순서 1
        QBoard board = QBoard.board;
        // 2 조회
        JPQLQuery<Board> query = from(board); // select .. from board
        // 3 where 조건절 , BooleanBuilder를 이용해서 조건 추가
        if(types != null && types.length > 0 && keyword != null){
            // BooleanBuilder를 사용하면 or , and 조건을 사용하기 쉽다
            BooleanBuilder builder = new BooleanBuilder();
            // types = {"t","w","c"} t:title w:writer c:content
            for(String type : types){
                switch(type) {
                    case "t":
                        builder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        builder.or(board.content.contains(keyword));
                        break;
                    case "w":
                        builder.or(board.writer.contains(keyword));
                }
            }

            query.where(builder);
        }
        // bno>0 조건 추가
        query.where(board.bno.gt(0L));

        //paging 조건 추가
        // 4.
        this.getQuerydsl().applyPagination(pageable , query);
        // 5 위의 검색 조건, 페이징 조건으로 sql 전달하기(데이터 가져오기)
        List<Board> list = query.fetch();
        // 순서6, 데이터 갯수
        long count = query.fetchCount();

        // 7 리턴 타입에 맞추기
        return new PageImpl<>(list,pageable,count);
    }
}
