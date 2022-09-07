package com.luv2code.expensetrackerapi.repository;


import java.sql.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luv2code.expensetrackerapi.entity.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

	// behind the scene- Jpa will execute the following query for the method
	// SELECT * FROM tbl_expenses WHERE user_id = ? AND category=?
	Page<Expense> findByUserIdAndCategory(Long userId, String cateogry, Pageable page);

	// behind the scene- Jpa will execute the following query for the method
	// SELECT * FROM tbl_expenses WHERE user_id = ? AND name LIKE '%keyword%'
	Page<Expense> findByUserIdAndNameContaining(Long userId, String keyword, Pageable page);

	// behind the scene- Jpa will execute the following query for the method
	// SELECT * FROM tbl_expenses WHERE user_id = ? AND date BETWEEN startDate AND endDate
	Page<Expense> findByUserIdAndDateBetween(Long userId, Date startDate, Date endDate, Pageable page);
	
	Page<Expense> findByUserId(Long userId, Pageable page);
	
	Optional<Expense> findByUserIdAndId(Long userId, Long id);
}
