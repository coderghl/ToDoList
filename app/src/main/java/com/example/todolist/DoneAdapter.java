package com.example.todolist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DoneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private Context mContext;
  public static List<String> DoneList = new ArrayList<>();
  public static DoneAdapter StaticDoneThis;
  public RecyclerView doneListContent, undoneListContent;
  private TextView countDone = MainActivity.doneListWrap.findViewById(R.id.countDone);

  public DoneAdapter(Context context, RecyclerView undoneListContent, RecyclerView doneListContent) {
    this.mContext = context;
    StaticDoneThis = this;
    this.doneListContent = doneListContent;
    this.undoneListContent = undoneListContent;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new itemWarp(LayoutInflater.from(mContext).inflate(R.layout.layout_list_item, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
    ((itemWarp) holder).item.setText(DoneList.get(position));
    ((itemWarp) holder).isDone.setChecked(true);
    // 点击delete
    ((itemWarp) holder).delete.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        DoneList.remove(position);
        // 刷新数据
        doneListContent.setAdapter(MainActivity.doneAdapter);
        countDone.setText(String.valueOf(DoneList.size()));
      }
    });
    // 点击checkbox
    ((itemWarp) holder).isDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (!b) {
          UndoneAdapter.UnDoneList.add(String.valueOf(DoneList.get(position)));
          DoneList.remove(position);
          doneListContent.setAdapter(MainActivity.doneAdapter);
          undoneListContent.setAdapter(MainActivity.undoneAdapter);
          countDone.setText(String.valueOf(DoneList.size()));
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    countDone.setText(String.valueOf(DoneList.size()));
    return DoneList.size();
  }

  public class itemWarp extends RecyclerView.ViewHolder {
    private CheckBox isDone; // 选择框
    private TextView item; // list内容
    private Button delete;// 删除按钮

    public itemWarp(@NonNull View itemView) {
      super(itemView);
      isDone = itemView.findViewById(R.id.isDone);
      item = itemView.findViewById(R.id.item);
      delete = itemView.findViewById(R.id.delete);
    }
  }
}
