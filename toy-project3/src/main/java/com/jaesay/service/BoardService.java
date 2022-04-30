package com.jaesay.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jaesay.domain.Board;
import com.jaesay.repository.BoardRepository;
import com.querydsl.core.types.Predicate;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	public Page<Board> findAll(Predicate predicate, Pageable pageable) {
		return boardRepository.findAll(predicate, pageable);
	}
	
	public Predicate getPredicate(String type, String keyword) {
		return boardRepository.getPredicate(type, keyword);
	}

	public void save(Board board) {
		boardRepository.save(board);
	}

	public Optional<Board> findById(Long boardId) {
		return boardRepository.findById(boardId);
	}
	
}