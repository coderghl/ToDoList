package com.example.todolist;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UndoneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private Context mContext;
  public static List<String> UnDoneList = new ArrayList<>();
  public static UndoneAdapter StaticUnDoneThis;
  private RecyclerView undoneListContent, doneListContent;
  private TextView countUndone = MainActivity.undoneListWrap.findViewById(R.id.countUndone); // 未完成条目总数

  public UndoneAdapter(Context context, RecyclerView undoneListContent, RecyclerView doneListContent) {
    UnDoneList = MainActivity.todoListAll;
    StaticUnDoneThis = this;
    this.mContext = context;
    this.undoneListContent = undoneListContent;
    this.doneListContent = doneListContent;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new itemWarp(LayoutInflater.from(mContext).inflate(R.layout.layout_list_item, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
    ((itemWarp) holder).item.setText(UnDoneList.get(position));
    ((itemWarp) holder).isDone.setChecked(false);
    // 点击delete
    ((itemWarp) holder).delete.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        UnDoneList.remove(position);
        // 刷新数据
        undoneListContent.setAdapter(MainActivity.undoneAdapter);
        countUndone.setText(String.valueOf(UnDoneList.size()));
      }
    });

    // 点击CheckBox
    ((itemWarp) holder).isDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
          DoneAdapter.DoneList.add(String.valueOf(UnDoneList.get(position)));
          UnDoneList.remove(position);
          doneListContent.setAdapter(MainActivity.doneAdapter);
          undoneListContent.setAdapter(MainActivity.undoneAdapter);
          countUndone.setText(String.valueOf(UnDoneList.size()));
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    countUndone.setText(String.valueOf(UnDoneList.size()));
    return UnDoneList.size();
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
