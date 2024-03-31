package com.example.jobala.board;

import com.example.jobala._core.errors.exception.Exception403;
import com.example.jobala._core.errors.exception.Exception404;
import com.example.jobala._user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardJPARepository boardJPARepository;

    // 글삭제하기
    public void boardDelete(int boardId, Integer sessionUserId) {
        Board board = boardJPARepository.findById(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다."));

        if (sessionUserId != board.getUser().getId()) {
            throw new Exception403("게시글을 삭제 할 권한이 없습니다.");
        }
        boardJPARepository.deleteById(boardId);
    }

    // 글상세보기
    public BoardResponse.DetailDTO boardDetail(int boardId, User sessionUser) {
        Board board = boardJPARepository.findByIdJoinUser(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다"));

        return new BoardResponse.DetailDTO(board, sessionUser);
    }

    // 글수정
    @Transactional
    public Board boardUpdate(int boardId, int sessionUserId, BoardRequest.UpdateDTO reqDTO) {
        //조회 및 예외처리
        Board board = boardJPARepository.findById(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다."));

        // 권한체크
        if (sessionUserId != board.getUser().getId()) {
            throw new Exception403("게시글을 수정할 권한이 없습니다.");
        }

        // 글 수정
        board.setTitle(reqDTO.getTitle());
        board.setContent(reqDTO.getContent());
        return board;
    }

    // 글조회
    public Board boardFindById(int boardId) {
        Board board = boardJPARepository.findById(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다."));

        return board;
    }

    // 글쓰기
    @Transactional
    public Board boardSave(BoardRequest.SaveDTO reqDTO, User sessionUser) {
        Board board = boardJPARepository.save(reqDTO.toEntity(sessionUser));
        return board;
    }

    // 글목록조회
    public Page<Board> 글목록조회(int page, int size) {
        Pageable pageable = (Pageable) PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        return boardJPARepository.findAll(pageable);
    }

    public List<BoardResponse.MainDetailDTO> boardFindAll() { // 글목록조회
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<Board> boardList = boardJPARepository.findAll(sort);
        return boardList.stream().map(board -> new BoardResponse.MainDetailDTO(board)).toList();
        // return boardList.stream().map(BoardResponse.MainDTO::new).toList();와 같은 것
    }
}
