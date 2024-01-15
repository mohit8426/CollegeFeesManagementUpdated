package com.example.collegefeesmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class pendinglist extends ArrayAdapter<Student> {
    private Activity context;
    List<Student> students;

    public pendinglist(Activity context, List<Student> students) {
        super(context, R.layout.activity_pendinglist ,students);
        this.context = context;
        this.students=students;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_pendinglist, null, true);

        TextView name= (TextView) listViewItem.findViewById(R.id.pend_name);
        TextView  number= (TextView) listViewItem.findViewById(R.id.pend_number);
        TextView  branch=(TextView) listViewItem.findViewById(R.id.pend_branch);

        Student student = students.get(position);
        name.setText(student.getName());
        number.setText(student.getNumber());
        branch.setText(student.getBranch());

        return listViewItem;
    }


}
