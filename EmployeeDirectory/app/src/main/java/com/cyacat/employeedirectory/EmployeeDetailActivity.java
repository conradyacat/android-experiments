package com.cyacat.employeedirectory;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyacat.employeedirectory.config.AppContext;
import com.cyacat.employeedirectory.constant.IntentExtraKey;
import com.cyacat.employeedirectory.database.DbHelper;
import com.cyacat.employeedirectory.model.Employee;
import com.google.common.base.Strings;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.sql.SQLException;

public class EmployeeDetailActivity extends AppCompatActivity {

    private DbHelper _dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        TextView tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText(employee.get_firstName() + " " + employee.get_lastName());

        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(employee.get_title());

        String photoFileName = employee.get_photoFileName();
        if (!Strings.isNullOrEmpty(photoFileName)) {
            ImageView imgPhoto = (ImageView) findViewById(R.id.photo);
            Picasso.with(this)
                    .load(new File(AppContext.get_photoDirectory(), photoFileName))
                    .resize(200, 200)
                    .centerCrop()
                    .into(imgPhoto);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_emp, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
