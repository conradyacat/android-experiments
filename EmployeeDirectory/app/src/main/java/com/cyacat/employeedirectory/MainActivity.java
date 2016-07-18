package com.cyacat.employeedirectory;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

import com.cyacat.employeedirectory.adapter.EmployeeListAdapter;
import com.cyacat.employeedirectory.config.AppContext;
import com.cyacat.employeedirectory.constant.RequestCode;
import com.cyacat.employeedirectory.database.DbHelper;
import com.cyacat.employeedirectory.model.Employee;
import com.google.common.base.Strings;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private DbHelper _dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppContext.init(this);
        _dbHelper = new DbHelper(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewEmployeeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, RequestCode.ADD_EMPLOYEE);
            }
        });

        searchEmployees(null);
    }

    private void searchEmployees(String keyword) {
        try {
            List<Employee> employees;
            if ( Strings.isNullOrEmpty(keyword)) {
                employees = _dbHelper.getAll(Employee.class);;
            } else {
                employees = _dbHelper.getEmployees(keyword);
            }
            ListView listViewEmployees = (ListView) findViewById(R.id.listViewEmployees);
            listViewEmployees.setAdapter(new EmployeeListAdapter(this, employees));
        } catch (SQLException e) {
            final Snackbar snackbar = Snackbar.make(findViewById(R.id.mainLayout), "Error encountered while loading employees", Snackbar.LENGTH_LONG);
            snackbar.setAction("Dismiss", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackbar.dismiss();
                        }
                    });
            snackbar.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RequestCode.ADD_EMPLOYEE:
                if (resultCode == RESULT_OK) {
                    Snackbar.make(findViewById(R.id.mainLayout), "New employee saved successfully", Snackbar.LENGTH_SHORT).show();
                    searchEmployees(null);
                }
                break;
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchEmployees(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (Strings.isNullOrEmpty(newText)) {
            searchEmployees(null);
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        if (_dbHelper != null)
            _dbHelper.close();

        super.onDestroy();
    }
}
