package com.myspring.service;

import java.util.List;

import com.myspring.domain.BoardVO;

public interface BoardService {
	public List<BoardVO> getAll() throws Exception;
}
