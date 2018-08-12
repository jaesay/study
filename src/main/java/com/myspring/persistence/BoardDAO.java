package com.myspring.persistence;

import java.util.List;

import com.myspring.domain.BoardVO;

public interface BoardDAO {
	public List<BoardVO> getAll() throws Exception;
}
