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

public class All_Admission extends AppCompatActivity {
    ListView studentlist;
    List<Admission> students;
    DatabaseReference admref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__admission);

        studentlist=(ListView)findViewById(R.id.adminlist_view);

        students=new ArrayList<>();
        admref= FirebaseDatabase.getInstance().getReference().child("Admission");
    }

    @Override
    protected void onStart() {
        super.onStart();
        admref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                students.clear();
                for(DataSnapshot studentSnap : dataSnapshot.getChildren() ){
                    Admission lists= studentSnap.getValue(Admission.class);

                    students.add(lists);
                }

                adminlist adapter=new adminlist(All_Admission.this,students);
                studentlist.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
