package com.example.todolist;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private Button upTodo; // add 按钮
  private EditText inputTodo;// 输入控件
  public static List<String> todoListAll = new ArrayList<>(); // 存储所有todoList集合
  public static LinearLayout undoneListWrap, doneListWrap; // recyclerView控件父容器
  private RecyclerView undoneListContent, doneListContent;// recyclerView控件

  public static UndoneAdapter undoneAdapter;
  public static DoneAdapter doneAdapter;

  // 文件名
//  private String fileNameUnDoneAdapter = "fileNameUnDoneAdapter.txt";
//  private String fileNameDoneAdapter = "fileNameDoneAdapter.txt";
//  private static final int REQUEST_CODE = 1024; // 保存文件码


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // 申请文件读写权限
//    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
//    File doneFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/DataList", fileNameDoneAdapter);
//    File unDoneFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/DataList", fileNameUnDoneAdapter);
//    Log.d("path", Environment.getExternalStorageDirectory().getAbsolutePath());
//    if (doneFile.exists()) {
//      read(fileNameDoneAdapter, DoneAdapter.DoneList);
//    }
//    if (unDoneFile.exists()) {
//      read(fileNameUnDoneAdapter, UndoneAdapter.UnDoneList);
//      Log.d("data", UndoneAdapter.UnDoneList.toString());
//    }

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

  // 在数据销毁前一刻保存数据，有则保存没有就不保存
//  @RequiresApi(api = Build.VERSION_CODES.O)
//  @Override
//  protected void onDestroy() {
//    super.onDestroy();
//    if (UndoneAdapter.UnDoneList.size() != 0) {
//      save(fileNameUnDoneAdapter, UndoneAdapter.UnDoneList);
//    }
//    if (DoneAdapter.DoneList.size() != 0) {
//      save(fileNameDoneAdapter, DoneAdapter.DoneList);
//    }
//  }

  // 存储
//  @RequiresApi(api = Build.VERSION_CODES.O)
//  public void save(String filename, List<String> data) {
//    FileOutputStream fileOutputStream = null;
//    try {
//      // list转化字符串
//      String res = String.join(",", data);
//      // 设置存储路径
//      File dir = new File(Environment.getExternalStorageDirectory(), "DataList");
//      if (!dir.exists()) {
//        dir.mkdir();
//      }
//      // 设置保存的文件名
//      File file = new File(dir, filename);
//      if (!file.exists()) {
//        file.createNewFile();
//      }
//      // 输出到本地
//      fileOutputStream = new FileOutputStream(file);
//      fileOutputStream.write(res.getBytes());
//      fileOutputStream.close();
//
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//
//  }
//
//  // 读取
//  public void read(String filename, List<String> list) {
//    FileInputStream fileInputStream = null;
//    try {
//      File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/DataList", filename);
//      fileInputStream = new FileInputStream(file);
//      byte[] bytes = new byte[1024];
//      int len = 0;
//      StringBuilder sb = new StringBuilder();
//      while ((len = fileInputStream.read(bytes)) != -1) {
//        sb.append(new String(bytes, 0, len));
//        Log.d("data", sb.toString());
//      }
//      List<String> result = new ArrayList<>();
//      String str[] = sb.toString().split(",");
//      result = Arrays.asList(str);
//      for (String s : result) {
//        list.add(0, s);
//      }
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }
}
