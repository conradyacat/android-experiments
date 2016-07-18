package com.cyacat.employeedirectory;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.cyacat.employeedirectory.config.AppContext;
import com.cyacat.employeedirectory.constant.RequestCode;
import com.cyacat.employeedirectory.database.DbHelper;
import com.cyacat.employeedirectory.model.Employee;
import com.google.common.base.Strings;

import java.io.File;
import java.sql.SQLException;
import java.util.UUID;

public class NewEmployeeActivity extends AppCompatActivity {

    private DbHelper _dbHelper;
    private String _filePath;
    private ImageButton _photoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_employee);

        _dbHelper = new DbHelper(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        _photoButton = (ImageButton) findViewById(R.id.photo);
        _photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(AppContext.get_photoDirectory(), UUID.randomUUID().toString() + ".jpg");
                _filePath = file.getAbsolutePath();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(cameraIntent, RequestCode.CAPTURE_IMAGE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_save:
                saveEmployee();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RequestCode.CAPTURE_IMAGE:
                _photoButton.setImageURI(Uri.fromFile(new File(_filePath)));
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_emp, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        if (_dbHelper != null)
            _dbHelper.close();

        super.onDestroy();
    }

    private void saveEmployee() {
        boolean hasError = false;
        TextInputLayout tilFirstName = (TextInputLayout) findViewById(R.id.tilFirstName);
        String firstName = tilFirstName.getEditText().getText().toString();
        if (Strings.isNullOrEmpty(firstName)) {
            tilFirstName.setError("Enter First Name");
            tilFirstName.setErrorEnabled(true);
            hasError = true;
        }

        TextInputLayout tilLastName = (TextInputLayout) findViewById(R.id.tilLastName);
        String lastName = tilLastName.getEditText().getText().toString();
        if (Strings.isNullOrEmpty(lastName)) {
            tilLastName.setError("Enter Last Name");
            tilLastName.setErrorEnabled(true);
            hasError = true;
        }

        TextInputLayout tilTitle = (TextInputLayout) findViewById(R.id.tilTitle);
        String title = tilTitle.getEditText().getText().toString();
        if (Strings.isNullOrEmpty(title)) {
            tilTitle.setError("Enter Title");
            tilTitle.setErrorEnabled(true);
            hasError = true;
        }

        TextInputLayout tilOfficePhoneNo = (TextInputLayout) findViewById(R.id.tilOfficePhoneNo);
        String officePhoneNo = tilOfficePhoneNo.getEditText().getText().toString();
        if (Strings.isNullOrEmpty(officePhoneNo)) {
            tilOfficePhoneNo.setError("Enter Office Phone No.");
            tilOfficePhoneNo.setErrorEnabled(true);
            hasError = true;
        }

        TextInputLayout tilMobileNo = (TextInputLayout) findViewById(R.id.tilMobileNo);
        String mobileNo = tilMobileNo.getEditText().getText().toString();
        if (Strings.isNullOrEmpty(mobileNo)) {
            tilMobileNo.setError("Enter Mobile No.");
            tilMobileNo.setErrorEnabled(true);
            hasError = true;
        }

        TextInputLayout tilEmail = (TextInputLayout) findViewById(R.id.tilEmail);
        String email = tilEmail.getEditText().getText().toString();
        if (Strings.isNullOrEmpty(email)) {
            tilEmail.setError("Enter Email");
            tilEmail.setErrorEnabled(true);
            hasError = true;
        }

        if (hasError) {
            return;
        }

        Employee newEmployee = new Employee();
        newEmployee.set_email(email);
        newEmployee.set_firstName(firstName);
        newEmployee.set_lastName(lastName);
        newEmployee.set_mobilePhone(mobileNo);
        newEmployee.set_officePhone(officePhoneNo);
        newEmployee.set_title(title);

        File file = new File(_filePath);
        if (file.exists()) {
            newEmployee.set_photoFileName(file.getName());
        }

        try {
            _dbHelper.insert(newEmployee, Employee.class);
            setResult(RESULT_OK);
            finish();
        } catch (SQLException e) {
            final Snackbar snackbar = Snackbar.make(findViewById(R.id.addEmpLayout), "Error encountered while saving new employee", Snackbar.LENGTH_LONG);
            snackbar.setAction("Dismiss", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackbar.dismiss();
                }
            });
            snackbar.show();
        }
    }
}
