package com.example.collegefeesmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.collegefeesmanagement.Insert_Activity.Maxid;

public class Update extends AppCompatActivity {

    DatabaseReference dbref,studentref;
    EditText node;
    Button check,upte;
    Spinner spin,spin1,spin2;
    EditText stname,stno,stemail;
    RelativeLayout layout;
    String stat;
    Student studentchild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);

        layout=(RelativeLayout)findViewById(R.id.update_layout);

        stname=(EditText)findViewById(R.id.student_name);
        stno=(EditText)findViewById(R.id.student_number);
        stemail=(EditText)findViewById(R.id.student_email);


        spin=(Spinner)findViewById(R.id.student_caste);

        spin1=(Spinner)findViewById(R.id.student_year);

        spin2=(Spinner)findViewById(R.id.student_branch);

        node=(EditText)findViewById(R.id.up_prn);
        check=(Button)findViewById(R.id.up_check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                dbref= FirebaseDatabase.getInstance().getReference().child("Student").child(node.getText().toString());
                final int[] show = {0};
                dbref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            layout.setVisibility(View.VISIBLE);
                            if(show[0]==0)
                            Toast.makeText(Update.this, "Student Found", Toast.LENGTH_SHORT).show();
                            String n=dataSnapshot.child("name").getValue().toString();
                            stname.setText(n);
                            String no=dataSnapshot.child("number").getValue().toString();
                            stno.setText(no);
                            String em=dataSnapshot.child("email").getValue().toString();
                            stemail.setText(em);
                            String yr=dataSnapshot.child("year").getValue().toString();
                            stat=dataSnapshot.child("status").getValue().toString();

                            upte=(Button)findViewById(R.id.update_button);
                            upte.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
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
                                        show[0]=1;
                                        //Toast.makeText(Update.this, "Student Info Updated", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }
                            });
                        }else {
                            layout.setVisibility(View.INVISIBLE);
                            if(show[0]==0)
                            Toast.makeText(Update.this, "Student Id Invalid", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


    }

    private void addStudent(){


        studentref= FirebaseDatabase.getInstance().getReference().child("Student");
        int fees=0;
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
        studentchild=new Student(Integer.parseInt(node.getText().toString()),name,number,email,year,branch,caste,fees,stat);
        studentref.child(node.getText().toString()).setValue(studentchild);

        Toast.makeText(this, "Student Info Updated", Toast.LENGTH_SHORT).show();

    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
