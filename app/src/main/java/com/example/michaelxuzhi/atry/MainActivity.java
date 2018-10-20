package com.example.michaelxuzhi.atry;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText nameText;
    private EditText sexText;
    private EditText totalCreditsText;
    private DBDefinition dboperation;
    private Context context = this;
    private TextView display;
    private TextView idEntry;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameText = (EditText) findViewById(R.id.name);
        sexText = (EditText) findViewById(R.id.sex);
        totalCreditsText = (EditText) findViewById(R.id.totalcredits);
        display = (TextView) findViewById(R.id.display);
        idEntry = (TextView) findViewById(R.id.id_query);


        Button addbtn = (Button) findViewById(R.id.add);
        Button queryAllbtn = (Button) findViewById(R.id.query_all);
        Button querybtn = (Button) findViewById(R.id.query);
        final Button clearbtn = (Button) findViewById(R.id.clear);
        Button deletebtn = (Button) findViewById(R.id.delete);
        Button updatebtn = (Button) findViewById(R.id.update);

        //创建并打开数据库
        dboperation = new DBDefinition(this);
        dboperation.open();


        //插入数据
        addbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Student student = new Student();
                student.Name = nameText.getText().toString();
                student.Sex = sexText.getText().toString();
                student.Totalcredits = Integer.parseInt(totalCreditsText.getText().toString());
                long column = dboperation.insert(student);
                if (column == -1) {
                    Toast.makeText(context, "插入数据失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "插入数据成功", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //查询所有数据
        queryAllbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student[] students = dboperation.queryAllData();
                String msg = "";
                for (int i=0;i<students.length;i++){
                    msg +=students[i].toString() + "\n";
                }
                display.setText(msg);
            }
        });


        //查询指定ID数据
        querybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id= Integer.parseInt(idEntry.getText().toString());
                Student[] student = dboperation.queryData(id);
                display.setText(student[0].toString());
            }
        });


        //清空和删除数据
        clearbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                display.setText("");
            }
        });
        deletebtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int id = Integer.parseInt(idEntry.getText().toString());
                long results = dboperation.deleteOneData(id);
                String msg = "删除ID为" + idEntry.getText().toString() + "的数据"
                        +(results>0? "成功":"失败");
                Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
            }
        });


        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student students = new Student();
                students.Name = nameText.getText().toString();
                students.Sex = sexText.getText().toString();
                students.Totalcredits = Integer.parseInt(totalCreditsText.getText().toString());
                int id = Integer.parseInt(idEntry.getText().toString());
                long count =dboperation.updateData(id, students);
                Toast.makeText(context,"更新成功",Toast.LENGTH_SHORT).show();
            }
        });


    }
}
