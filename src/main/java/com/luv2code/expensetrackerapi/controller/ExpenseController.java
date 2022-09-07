package com.luv2code.expensetrackerapi.controller;

import java.sql.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.expensetrackerapi.entity.Expense;
import com.luv2code.expensetrackerapi.service.ExpenseService;

@RestController
public class ExpenseController {

	@Autowired
	private ExpenseService expenseService;

	@GetMapping("/expenses")
	public List<Expense> getListExpense(Pageable page) {
		return expenseService.getAllList(page).toList();
	}

	@GetMapping("/expenses/{id}")
	public Expense getExpenseById(@PathVariable("id") Long id) {
		return expenseService.getExpenseById(id);
	}

	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@DeleteMapping("/expenses")
	public void deleteExpenseById(@RequestParam("id") Long id) {
		expenseService.deleteExpenseById(id);
	}

	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping("/expenses")
	public Expense saveExpenseDetails(@Valid @RequestBody Expense expense) {
		// just in case the pass an id in JSON ....set id to 0
		// to force to save as a new item...instead of update
		expense.setId(0l);
		return expenseService.saveExpenseDetails(expense);
	}

	@PutMapping("/expenses/{id}")
	public Expense updateExpenseDetails(@PathVariable Long id, @RequestBody Expense expense) {
		return expenseService.updateExpenseDetails(id, expense);
	}

	@GetMapping("/expenses/category")
	public List<Expense> getListByCategory(@RequestParam String category, Pageable page) {
		return expenseService.readByCategory(category, page);
	}

	@GetMapping("/expenses/name")
	public List<Expense> getListByName(@RequestParam String keyword, Pageable page) {
		return expenseService.readByName(keyword, page);
	}

	@GetMapping("/expenses/date")
	public List<Expense> getListByDate(
										@RequestParam(required = false) Date startDate,
										@RequestParam(required = false) Date endDate,
										Pageable page) {
		return expenseService.readByDate(startDate, endDate, page);
	}
}
