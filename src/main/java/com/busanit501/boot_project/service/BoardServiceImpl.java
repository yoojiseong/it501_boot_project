package com.busanit501.boot_project.service;

import com.busanit501.boot_project.domain.Board;
import com.busanit501.boot_project.dto.BoardDTO;
import com.busanit501.boot_project.dto.BoardListReplyCountDTO;
import com.busanit501.boot_project.dto.PageRequestDTO;
import com.busanit501.boot_project.dto.PageResponseDTO;
import com.busanit501.boot_project.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Override
    public BoardDTO readOne(Long bno) {
        // 본인 또 기능 만들어서 구현 하는게 아니라,
        // 다른 누군가 만들어 둔 기능을 이용하기.
        // 외주 주기.->boardRepository
        // 패턴 고정, findById -> 받을 때, Optional 받기
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();
        // 엔티티 클래스 타입(VO) -> DTO 타입 변환.
        BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);
        return boardDTO;
    }

    @Override
    public void modify(BoardDTO boardDTO){
        Optional<Board> result = boardRepository.findById(boardDTO.getBno());
        Board board = result.orElseThrow();
        board.changTitleContent(boardDTO.getTitle(), boardDTO.getContent());
        boardRepository.save(board);

    }

    @Override
    public void remove(Long bno) {
        boardRepository.deleteById(bno);
    }

    @Override
    public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) {
        //화면으로 부터 전달 받은 검색 조건과 페이징 정보
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");

        // 준비된 재료로 서버에서 데이터를 가져오고, 페이징 정보도 가져오기
        Page<Board> result = boardRepository.searchAll(types,keyword,pageable);

        //result.getContent() => List<Board>
        // .stream()f.map(): 리스트의 모든 요소를 하나씩 순회 하면서 타입 변환시키고 전부 순회
        //board -> modelMapper.map(board, BoardDTO.class)
        // collect => 변횐된 DTO를 다시 리스트로 변환
        List<BoardDTO> dtoList = result.getContent().stream().map(board-> modelMapper.map(board, BoardDTO.class)).collect(Collectors.toList());

        //생성자 호출임
        return PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO) {
        // type = "twc" -> getTypes -> {"t","c","w"}
        // 화면으로 부터 전달 받은,
        // 1)검색 조건과 2)페이징 정보
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");

        //2) result : 보드 레포지토리 테스트에서, 관련 정보 확인 해주세요.
        Page<BoardListReplyCountDTO> result = boardRepository.searchWithReplyCount(types,keyword,pageable);


        return PageResponseDTO.<BoardListReplyCountDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int)result.getTotalElements())
                .build();
    }

}
