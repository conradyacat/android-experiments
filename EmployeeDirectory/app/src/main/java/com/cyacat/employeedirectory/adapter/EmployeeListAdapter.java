package com.cyacat.employeedirectory.adapter;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyacat.employeedirectory.R;
import com.cyacat.employeedirectory.config.AppContext;
import com.cyacat.employeedirectory.model.Employee;
import com.google.common.base.Strings;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by Conrad Yacat on 7/18/2016.
 */
public class EmployeeListAdapter extends BaseAdapter {

    private final Activity _context;
    private final List<Employee> _employees;

    public EmployeeListAdapter(Activity context, List<Employee> employees) {
        _context = context;
        _employees = employees;
    }

    @Override
    public int getCount() {
        return _employees.size();
    }

    @Override
    public Object getItem(int position) {
        return _employees.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Employee employee = _employees.get(position);
        View view = convertView;
        if (view == null) {
            view = _context.getLayoutInflater().inflate(R.layout.employee_list_item, null);
        }

        TextView tvName = (TextView)view.findViewById(R.id.name);
        tvName.setText(employee.get_firstName() + " " + employee.get_lastName());

        TextView tvTitle = (TextView)view.findViewById(R.id.title);
        tvTitle.setText(employee.get_title());

        String photoFileName = employee.get_photoFileName();
        if (!Strings.isNullOrEmpty(photoFileName)) {
            ImageView imgPhoto = (ImageView)view.findViewById(R.id.photo);
            File file = new File(AppContext.get_photoDirectory(), photoFileName);
            Picasso.with(_context)
                    .load(file)
                    .resize(50, 50)
                    .centerCrop()
                    .into(imgPhoto);
        }

        return view;
    }
}
