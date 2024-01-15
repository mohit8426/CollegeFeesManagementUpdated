package com.example.collegefeesmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Payment extends AppCompatActivity {
    public static final String Extra_text="send.text";
    public static final String Extra_num="send.no";

    DatabaseReference dbref,admref;
    EditText node,dd;
    TextView name,amount;
    String payname,stramt,payno,payemail,payyear,paybranch,paycaste,paid="Online";
    CheckBox box;
    Button check,online,upload;
    RelativeLayout layout,layout1;
    int cnt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        node=(EditText)findViewById(R.id.sy_prn);
        check=(Button)findViewById(R.id.sy_check);
        layout=(RelativeLayout)findViewById(R.id.payment_layout);
        layout1=(RelativeLayout)findViewById(R.id.dd_layout);
        box=(CheckBox)findViewById(R.id.checkBox);
        dd=(EditText)findViewById(R.id.dd_no);



        String string=node.getText().toString();

        admref=FirebaseDatabase.getInstance().getReference().child("Admission");
        admref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    cnt= (int) dataSnapshot.getChildrenCount();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        name=(TextView)findViewById(R.id.found_paying_name);
        amount=(TextView)findViewById(R.id.found_amount);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                final int[] show = {0};
                dbref= FirebaseDatabase.getInstance().getReference().child("Student").child(node.getText().toString());
                dbref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            show[0] =1;
                            layout.setVisibility(View.VISIBLE);
                            Toast.makeText(Payment.this, "Student Found", Toast.LENGTH_SHORT).show();
                            payname=dataSnapshot.child("name").getValue().toString();
                            name.setText(payname);
                            stramt=dataSnapshot.child("fees").getValue().toString();
                            amount.setText(stramt);
                            payno=dataSnapshot.child("number").getValue().toString();
                            payemail=dataSnapshot.child("email").getValue().toString();
                            payyear=dataSnapshot.child("year").getValue().toString();
                            paybranch=dataSnapshot.child("branch").getValue().toString();
                            paycaste=dataSnapshot.child("caste").getValue().toString();

                            box.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(box.isChecked()){
                                        Toast.makeText(Payment.this, "Paying by DD", Toast.LENGTH_SHORT).show();
                                        layout1.setVisibility(View.VISIBLE);
                                        online.setEnabled(false);
                                    }
                                    else {
                                        online.setEnabled(true);
                                        layout1.setVisibility(View.INVISIBLE);
                                        paid="Online";
                                    }
                                }
                            });


                            online=(Button)findViewById(R.id.pay_onlinne);
                            online.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent=new Intent(Payment.this,PayOnline.class);
                                    intent.putExtra(Extra_text,payname);
                                    intent.putExtra(Extra_num,stramt);
                                    startActivity(intent);
                                }
                            });
                            upload=(Button)findViewById(R.id.done_payment);
                            upload.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if(box.isChecked()) {
                                        if (TextUtils.isEmpty(dd.getText())) {
                                            dd.setError("Enter DD Number");
                                            dd.requestFocus();
                                        } else {
                                            String st = dd.getText().toString();
                                            paid = "DD : ".concat(st);
                                            Admission adm = new Admission((cnt + 1), payname, payno, payemail, payyear, paybranch, paycaste, paid);
                                            admref.child(String.valueOf(cnt + 1)).setValue(adm);
                                            dbref.removeValue();
                                            finish();
                                        }
                                    }else {
                                        Admission adm = new Admission((cnt + 1), payname, payno, payemail, payyear, paybranch, paycaste, paid);
                                        admref.child(String.valueOf(cnt + 1)).setValue(adm);
                                        dbref.removeValue();
                                        finish();
                                    }

                                }
                            });
                        }
                        else{
                            layout.setVisibility(View.INVISIBLE);
                            if(show[0] ==0)
                            Toast.makeText(Payment.this, "Student Id Invalid", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

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
