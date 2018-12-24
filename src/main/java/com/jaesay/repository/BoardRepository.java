package com.jaesay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.jaesay.domain.Board;
import com.jaesay.domain.QBoard;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface BoardRepository extends JpaRepository<Board, Long>, QuerydslPredicateExecutor<Board>{

	public default Predicate getPredicate(String type, String keyword) {
		
		BooleanBuilder builder = new BooleanBuilder();
		QBoard board = QBoard.board;

		String safeType = Optional.ofNullable(type).orElse("");
		
		if(!safeType.trim().isEmpty()) {
			if(safeType.equals("t")) {
				builder.and(board.title.like("%" + keyword + "%"));
			}
			if(safeType.equals("w")) {
				builder.and(board.member.memberName.like("%" + keyword + "%"));
			}
			if(safeType.equals("c")) {
				builder.and(board.content.like("%" + keyword + "%"));
			}						
		}
		
		builder.and(board.boardId.gt(0));
		
		return builder;
	}
}
