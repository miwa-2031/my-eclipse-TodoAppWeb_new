package dao;

import java.util.List;

import model.Todo;

public interface TodoRepository {

	void insert(Todo todo);

	List<Todo> findAll(String keyword);

	void deleteById(int id);

	Todo findById(int id);

}
