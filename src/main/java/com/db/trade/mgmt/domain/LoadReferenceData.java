/**
 * 
 */
package com.db.trade.mgmt.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nishikanta
 *
 */
public class LoadReferenceData<T, R extends JpaRepository<T, ? extends Object>> {

	List<T> refLst;

	R repo;

	public LoadReferenceData(R repo) {
		super();
		this.repo = repo;
	}

	public List<T> getAllRefLst() {
		return repo.findAll();
	}

}
