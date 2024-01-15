package com.example.collegefeesmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class All_Pending extends AppCompatActivity {
    ListView studentlist;
    List<Student> students;
    DatabaseReference admref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__pending);


        studentlist=(ListView)findViewById(R.id.pendlist_view);

        students=new ArrayList<>();
        admref= FirebaseDatabase.getInstance().getReference().child("Student");
    }


    @Override
    protected void onStart() {
        super.onStart();
        admref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                students.clear();
                for(DataSnapshot studentSnap : dataSnapshot.getChildren() ){
                    Student lists1= studentSnap.getValue(Student.class);

                    students.add(lists1);
                }

                pendinglist adapter=new pendinglist(All_Pending.this,students);
                studentlist.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
