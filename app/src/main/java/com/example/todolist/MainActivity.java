package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
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

  public File DoneFile;
  public File UnDoneFile;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    DoneFile = new File(getFilesDir() + "/Done.txt");
    UnDoneFile = new File(getFilesDir() + "/UnDone.txt");

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

    if (DoneFile.exists()) {
      doneAdapter = new DoneAdapter(MainActivity.this, undoneListContent, doneListContent);
      showDoneList();
    }

    if (UnDoneFile.exists()) {
      undoneAdapter = new UndoneAdapter(MainActivity.this, undoneListContent, doneListContent);
      showUnDoneList();
    }
  }

  // 处理点击事件
  private class onClick implements View.OnClickListener {
    @Override
    public void onClick(View view) {
      switch (view.getId()) {
        case R.id.upTodo:
          // 获取EditView值传进todoList集合中
          todoListAll.add(0, inputTodo.getText().toString());
          if (UndoneAdapter.UnDoneList.size() == 0) {
            undoneAdapter = new UndoneAdapter(MainActivity.this, undoneListContent, doneListContent);
            doneAdapter = new DoneAdapter(MainActivity.this, undoneListContent, doneListContent);
            undoneListContent.setAdapter(undoneAdapter);
            doneListContent.setAdapter(doneAdapter);
          }
          // 生成Adapter
          undoneListContent.setAdapter(undoneAdapter);
          // 清空EditView内容
          inputTodo.setText("");
          break;
      }
    }
  }

  // 数据持久化
  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (UndoneAdapter.UnDoneList.size() != 0) {
      savaData("UnDone.txt", false);
    }

    if (DoneAdapter.DoneList.size() != 0) {
      savaData("Done.txt", true);
    }

    // 如果数据为空，删除文件夹
    if (UndoneAdapter.UnDoneList.size() == 0) {
      UnDoneFile.delete();
    }

    if (DoneAdapter.DoneList.size() == 0) {
      DoneFile.delete();
    }
  }

  public void savaData(String fileName, Boolean isDone) {
    try {
      FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE);
      if (isDone) {
        for (String s : DoneAdapter.DoneList) {
          fos.write((s + "|").getBytes());
        }
      } else {
        for (String s : UndoneAdapter.UnDoneList) {
          fos.write((s + "|").getBytes());
        }
      }
      fos.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void showDoneList() {
    try {
      Reader readr = new FileReader(DoneFile);
      char[] chars = new char[1024];
      int count = 0;
      StringBuffer sb = new StringBuffer();
      while ((count = readr.read(chars)) != -1) {
        sb.append(new String(chars, 0, count));
      }
      String[] data = sb.toString().split("\\|");
      for (String s : data) {
        DoneAdapter.DoneList.add(s);
      }
      doneListContent.setAdapter(MainActivity.doneAdapter);
      readr.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void showUnDoneList() {
    try {
      Reader readr = new FileReader(UnDoneFile);
      char[] chars = new char[1024];
      int count = 0;
      StringBuffer sb = new StringBuffer();
      while ((count = readr.read(chars)) != -1) {
        sb.append(new String(chars, 0, count));
      }
      String[] data = sb.toString().split("\\|");
      for (String s : data) {
        UndoneAdapter.UnDoneList.add(s);
      }
      undoneListContent.setAdapter(MainActivity.undoneAdapter);
      readr.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
