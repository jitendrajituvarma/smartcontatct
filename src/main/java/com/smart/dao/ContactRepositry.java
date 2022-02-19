package com.smart.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smart.entity.Contact;

@Repository
public interface ContactRepositry extends JpaRepository<Contact, Integer> {
	@Query("from Contact as c where c.user.id=:userId")
  Page<Contact> findContactsByUserId(@Param("userId")int userId ,Pageable pageable);
}
