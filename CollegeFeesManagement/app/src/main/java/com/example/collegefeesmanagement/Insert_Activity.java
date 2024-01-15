package com.example.collegefeesmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Insert_Activity extends AppCompatActivity  {

    Spinner spin,spin1,spin2;
    EditText stname,stno,stemail;
    DatabaseReference studentref;
    Button stinsert;
    static int Maxid=1;
    String enterid;
    long no;
    Student studentchild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_);

        stname=(EditText)findViewById(R.id.student_name);
        stno=(EditText)findViewById(R.id.student_number);
        stemail=(EditText)findViewById(R.id.student_email);
        closeKeyboard();

        spin=(Spinner)findViewById(R.id.student_caste);

        spin1=(Spinner)findViewById(R.id.student_year);

        spin2=(Spinner)findViewById(R.id.student_branch);
        studentref= FirebaseDatabase.getInstance().getReference().child("Student");
        studentref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()) {
                no = dataSnapshot.getChildrenCount();
                enterid = dataSnapshot.child(String.valueOf(no)).child("id").getValue().toString();
                Maxid = Integer.parseInt(enterid);
                Maxid = Maxid + 1;
            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        stinsert=(Button)findViewById(R.id.insert_button);
        stinsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(stname.getText())){
                    stname.setError("Enter Student Name");
                    stname.requestFocus();
                }
                else if(TextUtils.isEmpty(stno.getText())){
                    stno.setError("Enter Student Number");
                    stno.requestFocus();
                }
                else if(TextUtils.isEmpty(stemail.getText())){
                    stemail.setError("Enter Student Email");
                    stemail.requestFocus();
                }
                else {
                    addStudent();
                    finish();
                }
            }
        });
    }

    private void addStudent(){
        int fees=0;
        String status="Not Paid";
        String name=stname.getText().toString().trim();
        String number=stno.getText().toString().trim();
        String email=stemail.getText().toString().trim();
        String year=spin1.getSelectedItem().toString();
        String branch=spin2.getSelectedItem().toString();
        String caste=spin.getSelectedItem().toString();
        if(caste.equals("OPEN")){
            fees=4;
        }
        else if(caste.equals("SC")) {
            fees=1;
        }
        else if(caste.equals("ST")) {
            fees=1;
        }
        else if(caste.equals("OBC")) {
            fees=3;
        }
        else if(caste.equals("VJNT")) {
            fees=2;
        }
        studentchild=new Student(Maxid,name,number,email,year,branch,caste,fees,status);
        studentref.child(String.valueOf(Maxid)).setValue(studentchild);
        Toast.makeText(this, "Student Registered Sucessful", Toast.LENGTH_SHORT).show();

    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
