package com.personal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.personal.entity.PlayList;

@Repository
public interface PlayListRepository extends JpaRepository<PlayList, Integer>, JpaSpecificationExecutor<PlayList>{

}
