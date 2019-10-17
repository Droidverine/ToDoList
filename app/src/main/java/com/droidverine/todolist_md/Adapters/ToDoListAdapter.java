package com.droidverine.todolist_md.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.droidverine.todolist_md.Models.TodoList;
import com.droidverine.todolist_md.R;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.TodoViewHolder> {
    List<TodoList> todoLists;
    Context context;

    public ToDoListAdapter(Context context, List<TodoList> todoLists) {
        this.context = context;
        this.todoLists = todoLists;

    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.todolistitem, parent, false);
        return new ToDoListAdapter.TodoViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        holder.txttaskname.setText(todoLists.get(position).getTaskname());
        holder.txttaskdate.setText(todoLists.get(position).getTaskdate());
        Log.d("array", todoLists.get(0).getTaskname());

    }

    @Override
    public int getItemCount() {
        return todoLists.size();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {
        TextView txttaskname, txttaskdate;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            txttaskname = itemView.findViewById(R.id.itemname);
            txttaskdate = itemView.findViewById(R.id.itemdate);

        }
    }
}
