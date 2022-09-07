package com.luv2code.expensetrackerapi.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.luv2code.expensetrackerapi.entity.Expense;
import com.luv2code.expensetrackerapi.exceptions.ResourceNotFoundException;
import com.luv2code.expensetrackerapi.repository.ExpenseRepository;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepo;
	
	@Autowired
	private UserService userService;

	@Override
	public Page<Expense> getAllList(Pageable page) {
		Page<Expense> result = expenseRepo.findByUserId(userService.getLoggedInUser().getId(), page);
		return result;
	}

	@Override
	public Expense getExpenseById(Long id) {

		Optional<Expense> findById = expenseRepo.findByUserIdAndId(userService.getLoggedInUser().getId(), id);
		if (findById.isPresent())
			return findById.get();

		throw new ResourceNotFoundException("Expense is not found for the id " + id);
	}

	@Override
	public void deleteExpenseById(Long id) {
		Expense expense = getExpenseById(id);
		expenseRepo.delete(expense);
	}

	@Override
	public Expense saveExpenseDetails(Expense expense) {
		expense.setUser(userService.getLoggedInUser());
		Expense expenseObj = expenseRepo.save(expense);
		return expenseObj;
	}

	@Override
	public Expense updateExpenseDetails(Long id, Expense expense) {
		Expense existingExpense = getExpenseById(id);

		existingExpense.setAmount(expense.getAmount() != null ? expense.getAmount() : existingExpense.getAmount());
		existingExpense
				.setCategory(expense.getCategory() != null ? expense.getCategory() : existingExpense.getCategory());
		existingExpense.setDate(expense.getDate() != null ? expense.getDate() : existingExpense.getDate());
		existingExpense.setDescription(
				expense.getDescription() != null ? expense.getDescription() : existingExpense.getDescription());
		existingExpense.setName(expense.getName() != null ? expense.getName() : existingExpense.getName());

		return expenseRepo.save(existingExpense);
	}

	@Override
	public List<Expense> readByCategory(String category, Pageable page) {

		return expenseRepo.findByUserIdAndCategory(userService.getLoggedInUser().getId(), category, page).toList();
	}

	@Override
	public List<Expense> readByName(String keyword, Pageable page) {
		return expenseRepo.findByUserIdAndNameContaining(userService.getLoggedInUser().getId(), keyword, page).toList();
	}

	@Override
	public List<Expense> readByDate(Date startDate, Date endDate, Pageable page) {
		if(startDate==null)
			startDate = new Date(0);
		if(endDate==null)
			endDate = new Date(System.currentTimeMillis());
		return expenseRepo.findByUserIdAndDateBetween(userService.getLoggedInUser().getId(), startDate, endDate, page).toList();
	}

}
