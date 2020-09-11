package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private Button upTodo; // add 按钮
  private EditText inputTodo;// 输入控件
  public static List<String> todoListAll = new ArrayList<>(); // 存储所有todoList集合
  public static LinearLayout undoneListWrap, doneListWrap; // recyclerView控件父容器
  private RecyclerView undoneListContent, doneListContent;// recyclerView控件

  public static UndoneAdapter undoneAdapter;
  public static DoneAdapter doneAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    // 获取元素
    upTodo = findViewById(R.id.upTodo);
    inputTodo = findViewById(R.id.inputTodo);
    undoneListContent = findViewById(R.id.undoneListContent);
    doneListContent = findViewById(R.id.doneListContent);
    undoneListWrap = findViewById(R.id.undoneListWrap);
    doneListWrap = findViewById(R.id.doneListWrap);

    // 设置布局管理器
    undoneListContent.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    doneListContent.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    // 绑定事件
    upTodo.setOnClickListener(new onClick());
  }

  // 处理点击事件
  private class onClick implements View.OnClickListener {
    @Override
    public void onClick(View view) {
      switch (view.getId()) {
        case R.id.upTodo:
          // 获取EditView值传进todoList集合中
          todoListAll.add(0, inputTodo.getText().toString());
          // 生成Adapter
          undoneAdapter = new UndoneAdapter(MainActivity.this, undoneListContent, doneListContent);
          doneAdapter = new DoneAdapter(MainActivity.this, undoneListContent, doneListContent);
          undoneListContent.setAdapter(undoneAdapter);
          // 清空EditView内容
          inputTodo.setText("");
          break;
      }
    }
  }
}
