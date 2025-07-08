package com.busanit501.boot_project.repository;

import com.busanit501.boot_project.domain.Board;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {
    @Autowired
    private BoardRepository boardRepository;

    //JpaRepository이용해서 기본 crud확인(sql언어 몰라도 자바 메서드로만 가능)
    // 1. insert
    @Test
    public void testInsert(){
        //더미 데이터 100개 병렬 처리로 하드 코딩 하기
        IntStream.rangeClosed(1,100).forEach(i -> {
            // 엔티티 클래스, 임시 객체 생성을 반복문에 맞춰 100개 생성
            Board board = Board.builder()
                    .title("title_" + i)
                    .content("content_" + i)
                    .writer("user_" + (i%10))
                    .build();
            //디비에 반영하기. save = insert(sql) 기능 동일
            Board result = boardRepository.save(board); // 그냥 삽입 할 수도 있고 삽입 후 result변수에 넣을 수도 있음
            log.info("bno : " + result.getBno());
        });
    }

    @Test
    public void testSelect(){
        Long tno = 100L;
        //====================== JpaRepository에서 확인 하는 부분은 여기==================================
        Optional<Board> result = boardRepository.findById(tno);
        //====================== JpaRepository에서 확인 하는 부분은 여기==================================
        Board board = result.orElseThrow();
        log.info("bno : " + board);
    }

    @Test
    public void testUpdate(){
        Long bno = 100L;
        // 디비로부터 조회된 데이터를 임시 객체에 담기
        Optional<Board> result = boardRepository.findById(bno);
        // 반복되는 패턴임 있으면 클래스 타입으로 받고, 없으면 예외 발생
        Board board = result.orElseThrow();
        // 변경할 제목, 내용만 교체
        board.changTitleContent("수정제목","수정, 오늘 점심 뭐 먹지?");
        // 실제 디비에 반영하기
        boardRepository.save(board);
    }

    // 4. 삭제 기능
    @Test
    public void testDelete(){
        Long bno = 1L;
        boardRepository.deleteById(bno);
    }

    //5.  페이징 테스트
    @Test
    public void testPage(){
        // 1페이지 에서 bno 기준으로 내림차순.
        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());
                // JpaRepository 이용해서 페이징 처리된 데이터 받기
        Page<Board> result = boardRepository.findAll(pageable);
        // 페이징 관련 기본 정보 출력 가능
        // 1) 전체 갯수 2) 전체 페이지 3) 현재 페이지 번호 4) 보여줄 사이즈 크기(10개)
        // 5) 페이징 되어 조회될 데이터 10개 - voList 이런 형식으로
        log.info("전체 갯수(total count) : "+ result.getTotalElements());
        log.info("전체 갯수(total pages) : "+ result.getTotalPages());
        log.info("현재 페이지 번호(Page number) : "+ result.getNumber());
        log.info("보여줄 사이즈 크기(page size) : "+ result.getSize());
        //임시 리스트 생성해서, 디비에ㅓㅅ 전달 받은 데이터를 담아두기.
        List<Board> todoList = result.getContent();
        log.info("조회될 데이터 10개 : "+ result.getContent());
        todoList.forEach(board -> log.info(board));

    }

    // 6. QueryDSL 확인, 자바 문법 -> SQL 로 전달하기
    @Test
    public void testSearch(){
        // 화면에서 전달 받을 페이징 정보, 더미 데이터 필요함
        Pageable pageable = PageRequest.of(0,10,Sort.by("bno").descending());
        // 준비물을 통해 QueryDSL의 백그라운드 동작 과정 확인 해보기
        boardRepository.search(pageable);
    }

    // 7. QueryDSL 확인
    // 페이징 정보 + 검색 정보를 같이 전달해서 조회
    @Test
    public void testSearchAll(){
        // 더미 데이터 2개 필요
        // 1) 페이징 정보ㅡ 2) 검색 정보
        // 검색 정보 더미 데이터 만들기.

        //화면의 체크박스에서 작성자, 내용, 제목 다 체크했다는 가정
        String[] types = {"t","c","w"};
        // 검색어
        String keyword = "1";
        // 페이징 정보,
        Pageable pageable = PageRequest.of(0,10,Sort.by("bno").descending());

        // 실제 디비 가져오기
        Page<Board> result = boardRepository.searchAll(types,keyword,pageable);
        // 단위 테스트 실행 한 후, sql 전달 여부, 콘솔에서 sql출력 확인
        // 자바 문법으로 sql을 어덯게 전달하는지 확인함

        // 결과 값 콘솔에서 확인
        log.info("전체 갯수(total count) : "+ result.getTotalElements());
        log.info("전체 갯수(total pages) : "+ result.getTotalPages());
        log.info("현재 페이지 번호(Page number) : "+ result.getNumber());
        log.info("보여줄 사이즈 크기(page size) : "+ result.getSize());

        log.info("이전 페이지 유무 : " + result.hasPrevious());
        log.info("이전 페이지 유무 : " + result.hasNext());
        //임시 리스트 생성해서, 디비에ㅓㅅ 전달 받은 데이터를 담아두기.
        List<Board> todoList = result.getContent();
        log.info("조회될 데이터 10개 : "+ result.getContent());
        todoList.forEach(board -> log.info(board));
    }
}
