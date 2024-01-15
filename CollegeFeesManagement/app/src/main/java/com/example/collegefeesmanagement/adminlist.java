package com.example.collegefeesmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class adminlist extends ArrayAdapter<Admission> {
    private Activity context;
    List<Admission> students;

    public adminlist(Activity context, List<Admission> students) {
        super(context, R.layout.activity_admin ,students);
        this.context = context;
        this.students=students;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_admin, null, true);

        TextView  name= (TextView) listViewItem.findViewById(R.id.text_name);
        TextView  number= (TextView) listViewItem.findViewById(R.id.text_number);
        TextView  branch=(TextView) listViewItem.findViewById(R.id.text_branch);

        Admission student = students.get(position);
        name.setText(student.getName());
        number.setText(student.getNumber());
        branch.setText(student.getBranch());

        return listViewItem;
    }

}

