package dao;

import service.DBManager;

import java.util.List;

public interface DaoController<T> {
	
    void setDBManager(DBManager manager);
    void add(T entity);
    void update(T entity);
    int delete(long id);
    List<T> getAll();
    T getById(long id);
    
}
