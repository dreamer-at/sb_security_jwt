package com.mysbdemos.security_v1_demo.repository;

import com.mysbdemos.security_v1_demo.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity, ID extends Serializable>
        extends JpaRepository<T, ID> {

    @Query("UPDATE #{#entityName} e SET e.isEnabled = FALSE WHERE e.id = :id")
    void deleteById(ID id);
}
