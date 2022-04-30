package com.myspring.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.myspring.domain.RememberMeToken;

@Repository
public class RememberMeTokenDAOImpl implements RememberMeTokenDAO {
	@Inject
	private SqlSession session;
	
	private static String namespace = "com.myspring.mapper.RememberMeToken";
	
	@Override
	public void save(RememberMeToken newToken) {
		session.insert(namespace + ".save", newToken);
	}

	@Override
	public RememberMeToken findBySeries(String series) {
		return session.selectOne(namespace + ".findBySeries", series);
	}

	@Override
	public List<RememberMeToken> findByUsername(String username) {
		return session.selectList(namespace + ".findByUsername", username);
	}

	@Override
	public void delete(List<RememberMeToken> tokens) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> list = new ArrayList<String>();
		for (RememberMeToken rememberMeToken : tokens) {
			list.add(rememberMeToken.getSeries());
		}
		param.put("list", list);
		
		session.delete(namespace + ".delete", param);
	}

}
