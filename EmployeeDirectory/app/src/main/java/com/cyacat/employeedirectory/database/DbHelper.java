package com.cyacat.employeedirectory.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cyacat.employeedirectory.model.Employee;
import com.google.common.base.Strings;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Conrad Yacat on 7/15/2016.
 */
public class DbHelper extends OrmLiteSqliteOpenHelper {

    private Context _context;

    public DbHelper(Context context) {
        super(context, "empdir.db", null, 1);
        _context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        Log.v(DbHelper.class.getName(), "Creating db");
        try {
            TableUtils.createTable(connectionSource, Employee.class);
        } catch (SQLException e) {
            Log.e(DbHelper.class.getName(), "Error creating db", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        Log.v(DbHelper.class.getName(), "Upgrading db");
    }

    public <T> List<T> getAll(Class<T> clazz) throws SQLException {
        Dao<T, ?> dao = getDao(clazz);
        List<T> entities = dao.queryForAll();
        return entities;
    }

    private <T> List<T> getAll(Class<T> clazz, PreparedQueryFunc<T> preparedQueryFuncfunc) throws SQLException {
        Dao<T, ?> dao = getDao(clazz);
        PreparedQuery<T> preparedQuery = preparedQueryFuncfunc.get(dao);
        List<T> entities = dao.query(preparedQuery);
        return entities;
    }

    public <T> void insert(T entity, Class<T> clazz) throws SQLException {
        Dao<T, ?> dao = getDao(clazz);
        dao.create(entity);
    }

    public <T> void update(T entity, Class<T> clazz) throws SQLException {
        Dao<T, ?> dao = getDao(clazz);
        dao.update(entity);
    }

    public <T> void delete(T entity, Class<T> clazz) throws SQLException {
        Dao<T, ?> dao = getDao(clazz);
        dao.delete(entity);
    }

    public List<Employee> getEmployees(final String keyword) throws SQLException {
        List<Employee> employees;
        if (Strings.isNullOrEmpty(keyword)) {
            employees = getAll(Employee.class);
        } else {
            PreparedQueryFunc func = new PreparedQueryFunc<Employee>() {
                @Override
                public PreparedQuery<Employee> get(Dao<Employee, ?> dao) throws SQLException {
                    QueryBuilder<Employee, ?> qb = dao.queryBuilder();
                    qb.where().like(Employee.FIRST_NAME, keyword).or().like(Employee.LAST_NAME, keyword);
                    return qb.prepare();
                }
            };
            employees = getAll(Employee.class, func);
        }

        return employees;
    }

    private interface PreparedQueryFunc<T> {
        PreparedQuery<T> get(Dao<T, ?> dao) throws SQLException;
    }
}
