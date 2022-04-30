package com.myspring.persistence;

import java.util.List;

import com.myspring.domain.RememberMeToken;

public interface RememberMeTokenDAO {

	public void save(RememberMeToken newToken);

	public RememberMeToken findBySeries(String series);

	public List<RememberMeToken> findByUsername(String username);

	public void delete(List<RememberMeToken> tokens);

}
