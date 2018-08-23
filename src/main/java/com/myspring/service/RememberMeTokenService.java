package com.myspring.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;

import com.myspring.domain.RememberMeToken;
import com.myspring.persistence.RememberMeTokenDAO;

@Service
public class RememberMeTokenService implements PersistentTokenRepository {
	@Inject
	private RememberMeTokenDAO dao;

	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		RememberMeToken newToken = new RememberMeToken(token);
		dao.save(newToken);
	}

	@Override
	public void updateToken(String series, String tokenValue, Date lastUsed) {
		RememberMeToken token = dao.findBySeries(series);
		if(token != null) {
			token.setToken(tokenValue);
			token.setLastUsed(lastUsed);
			dao.save(token);
		}
		
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		RememberMeToken token = dao.findBySeries(seriesId);
		return new PersistentRememberMeToken(token.getUsername(), token.getSeries(), token.getToken(), token.getLastUsed());
	}

	@Override
	public void removeUserTokens(String username) {
		List<RememberMeToken> tokens = dao.findByUsername(username);
		dao.delete(tokens);
	}
}
