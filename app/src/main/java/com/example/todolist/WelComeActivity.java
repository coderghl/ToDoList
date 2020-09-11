package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class WelComeActivity extends AppCompatActivity {

  private Handler handler = new Handler() {
    @Override
    public void handleMessage(@NonNull Message msg) {
      super.handleMessage(msg);
      goHome();
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_wel_come);
    // 3秒后跳转至MainActivity
    handler.sendEmptyMessageDelayed(1, 1500);
  }


  private void goHome() {
    Intent intent = new Intent(WelComeActivity.this, MainActivity.class);
    startActivity(intent);
    // 销毁当前Activity
    finish();
  }
}
