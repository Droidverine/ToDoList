package com.droidverine.todolist_md.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.droidverine.todolist_md.Activities.AddtodoitemActivity;
import com.droidverine.todolist_md.Activities.TasksActivity;
import com.droidverine.todolist_md.Models.TodoList;
import com.droidverine.todolist_md.R;
import com.droidverine.todolist_md.Utils.SQLiteDb;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.TodoViewHolder> {
    List<TodoList> todoLists;
    Context context;
    String act;
    Dialog customDialog;
    SQLiteDb sqLiteDb;

    public ToDoListAdapter(Context context, List<TodoList> todoLists, String act) {
        this.context = context;
        this.todoLists = todoLists;
        this.act = act;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.todolistitem, parent, false);
        return new ToDoListAdapter.TodoViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TodoViewHolder holder, final int position) {
        if (todoLists != null && act == "Home") {
            holder.txttaskname.setText(todoLists.get(position).getTaskCategory());
            holder.txttaskdate.setText("");
            holder.checkBox.setVisibility(View.INVISIBLE);
            holder.txttaskname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, TasksActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("Category", todoLists.get(position).getTaskCategory());
                    context.startActivity(intent);
                }
            });
            holder.edtbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AddtodoitemActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("EditCategory", todoLists.get(position).getTaskCategory());
                    context.startActivity(intent);
                }
            });

        } else if (todoLists != null && act == "taks") {
            holder.txttaskname.setText(todoLists.get(position).getTaskname());
            holder.txttaskdate.setText(todoLists.get(position).getTaskdate());
            holder.checkBox.setVisibility(View.VISIBLE);
            if (todoLists.get(position).getTaskstatus().equals("0")) {
                holder.checkBox.setChecked(false);

            } else {
                holder.checkBox.setChecked(true);


            }
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                }
            });


        }


    }

    @Override
    public int getItemCount() {
        if (todoLists != null) {
            return todoLists.size();
        } else {
            return 0;
        }

    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {
        TextView txttaskname, txttaskdate;
        CheckBox checkBox;
        ImageView edtbtn;

        public TodoViewHolder(@NonNull final View itemView) {
            super(itemView);
            txttaskname = itemView.findViewById(R.id.itemname);
            txttaskdate = itemView.findViewById(R.id.itemdate);
            checkBox = itemView.findViewById(R.id.itemcheckbox);
            edtbtn= itemView.findViewById(R.id.edtbtn);
            sqLiteDb = new SQLiteDb(context);
            edtbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AddtodoitemActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("Edititem", todoLists.get(getAdapterPosition()).getTaskname());
                    context.startActivity(intent);
                }
            });
            txttaskname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, txttaskdate.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    if (checkBox.isChecked()) {
                                        sqLiteDb.checkboxset(todoLists.get(getAdapterPosition()).getTaskCategory(), todoLists.get(getAdapterPosition()).getTaskname(), "1");

                                    } else {
                                        sqLiteDb.checkboxset(todoLists.get(getAdapterPosition()).getTaskCategory(), todoLists.get(getAdapterPosition()).getTaskname(), "0");

                                    }
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder dgbuilder = new AlertDialog.Builder(itemView.getRootView().getContext());
                    dgbuilder.setMessage("You sure about this?")
                            .setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener)
                            .show();

                }
            });

        }
    }
}
