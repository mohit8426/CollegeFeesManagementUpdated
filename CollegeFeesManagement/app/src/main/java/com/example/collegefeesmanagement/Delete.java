package com.example.collegefeesmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Delete extends AppCompatActivity {

    DatabaseReference dbref;
    EditText node;
    Button check,del;
    TextView dname,damount;
    RelativeLayout layout;
    String delname,delyear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        node=(EditText)findViewById(R.id.ty_prn);

        layout=(RelativeLayout)findViewById(R.id.delete_layout);


        dname=(TextView)findViewById(R.id.found_name);
        damount=(TextView)findViewById(R.id.found_year);

        check=(Button)findViewById(R.id.ty_check);
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
                            show[0]=1;
                            layout.setVisibility(View.VISIBLE);
                            Toast.makeText(Delete.this, "Student Found", Toast.LENGTH_SHORT).show();
                            delname=dataSnapshot.child("name").getValue().toString();
                            dname.setText(delname);
                            delyear=dataSnapshot.child("year").getValue().toString();
                            damount.setText(delyear);
                            delStudent();
                        }
                        else{
                            layout.setVisibility(View.INVISIBLE);
                            if(show[0]==0)
                            Toast.makeText(Delete.this, "Student Id Invalid", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }

    private void delStudent(){
        del=(Button)findViewById(R.id.button_del);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Delete.this, "Student Admission Cancelled", Toast.LENGTH_SHORT).show();
                dbref.removeValue();
                finish();
            }
        });

    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
