package com.cyacat.employeedirectory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cyacat.employeedirectory.constant.IntentExtraKey;
import com.cyacat.employeedirectory.database.DbHelper;
import com.cyacat.employeedirectory.model.Employee;

import java.sql.SQLException;

public class EmployeeDetailActivity extends AppCompatActivity {

    private DbHelper _dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_detail);

        _dbHelper = new DbHelper(this);
        long employeeId = getIntent().getLongExtra(IntentExtraKey.EMPLOYEE_ID, -1);

        if (employeeId == -1) {
            // TODO: handle this properly
            throw new RuntimeException("Invalid employee id");
        }

        Employee employee = null;
        try {
            employee = _dbHelper.get(employeeId, Employee.class, long.class);
        } catch (SQLException e) {
            // TODO: handle this properly
            throw new RuntimeException("Error encountered while fetching employee");
        }

        
    }
}
