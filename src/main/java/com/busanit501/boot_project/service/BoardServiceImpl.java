package com.busanit501.boot_project.service;

import com.busanit501.boot_project.domain.Board;
import com.busanit501.boot_project.dto.BoardDTO;
import com.busanit501.boot_project.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional()
public class BoardServiceImpl implements BoardService{
    //화면에서 전달 받은 데이터 DTO를 엔티티 클래스 타입으로 변환해서repository에게 외주 주는 업무
    private final ModelMapper modelMapper;
    private final BoardRepository boardRepository;

    //회원 가입
    @Override
    public Long register(BoardDTO boardDTO) {
        //DTO -> entity클래스로 변환
        Board board = modelMapper.map(boardDTO, Board.class);
        // 실제 디비에 쓰기 작업
        Long bno = boardRepository.save(board).getBno();
        return bno;
    }
}
