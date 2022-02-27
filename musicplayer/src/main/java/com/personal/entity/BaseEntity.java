package com.personal.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Data;

@Data
@MappedSuperclass
public class BaseEntity {

	@Column(name = "createddate", nullable = false)
	public LocalDateTime createdDate;
	
	@Column(name = "modifieddate")
	public LocalDateTime modifiedDate;
	
	@Column(name = "deleted")
	public boolean deleted = Boolean.FALSE;
	
	@PrePersist
    public void prePersist() {
    	setCreatedDate(LocalDateTime.now());
    }
    
    @PreUpdate
    public void preUpdate() {
    	setModifiedDate(LocalDateTime.now());
    }
	
}
