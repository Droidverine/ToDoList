package com.droidverine.todolist_md.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.droidverine.todolist_md.Activities.AddtodoitemActivity;
import com.droidverine.todolist_md.Activities.DialogActivity;
import com.droidverine.todolist_md.Activities.TasksActivity;
import com.droidverine.todolist_md.Models.TodoList;
import com.droidverine.todolist_md.R;
import com.droidverine.todolist_md.Utils.SQLiteDb;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.TodoViewHolder> {
    List<TodoList> todoLists;
    Context context;
    String act;
    Dialog customDialog;
    SQLiteDb sqLiteDb;
    List<TodoList> dateslist;

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
            //executes this part if the previous activity was Home
            sqLiteDb = new SQLiteDb(context);

            holder.txttaskname.setText(todoLists.get(position).getTaskCategory());
            String date = "";
            if (sqLiteDb.getDates(todoLists.get(position).getTaskCategory()) != null) {
                holder.txttaskdate.setText(date);
                date = sqLiteDb.getDates(todoLists.get(position).getTaskCategory());

            }

            DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            Date date1 = new Date();
            Date nddate = null;
            String nd = df.format(date1);

            Date due = null;
            try {
                due = df.parse(date);
                nddate = df.parse(nd);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (due != null) {
                //  Log.d("aja", due.toString());
                Log.d("compare1", "ali " + due);

                if (due.compareTo(nddate) < 0) {
                    // If due has passed
                    Log.d("compare1", "past " + due);
                    holder.txttaskdate.setText("Some item’s due date is past ");

                    holder.carditem.setCardBackgroundColor(Color.parseColor("#D81B60"));
                    holder.txttaskname.setTextColor(Color.parseColor("#000000"));
                    holder.txttaskcount.setTextColor(Color.parseColor("#000000"));
                    holder.txttaskcompletedcount.setTextColor(Color.parseColor("#ffffff"));

                } else if (due.compareTo(nddate) > 0) {
                    //If due is not present/it's not today.
                    Log.d("compare1", "date" + nddate);
                    holder.txttaskdate.setText("Nearest Due: " + date);

                    holder.carditem.setCardBackgroundColor(Color.parseColor("#3390A4AE"));


                } else if (nddate.compareTo(due) == 0) {
                    //if due is today
                    Log.d("compare1", "date" + nddate);
                    holder.txttaskdate.setText("Some item's due today");
                    holder.txttaskdate.setTextColor(Color.parseColor("#D81B60"));

                    holder.carditem.setCardBackgroundColor(Color.parseColor("#ffee58"));
                    holder.txttaskname.setTextColor(Color.parseColor("#000000"));
                    holder.txttaskcount.setTextColor(Color.parseColor("#000000"));

                }
            }


            holder.checkBox.setVisibility(View.INVISIBLE);
            holder.optionsbtn.setVisibility(View.VISIBLE);
            holder.delbtn.setVisibility(View.GONE);
            holder.edtbtn.setVisibility(View.GONE);
            holder.movebtn.setVisibility(View.GONE);
            holder.txttaskcompletedcount.setVisibility(View.VISIBLE);
            holder.txttaskcount.setVisibility(View.VISIBLE);
            holder.txttaskdate.setVisibility(View.VISIBLE);
            int count = sqLiteDb.countitems(todoLists.get(position).getTaskCategory());
            int comcount = sqLiteDb.getcompletedcount(todoLists.get(position).getTaskCategory());
            holder.txttaskcount.setText("No. Of tasks: " + count);
            holder.txttaskcompletedcount.setText("Completed till now: " + comcount);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            holder.carditem.setOnClickListener(new View.OnClickListener() {
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
            String ed_text = todoLists.get(position).getTaskdate().trim();

            if (ed_text.equals("Infinity")) {
                holder.txttaskdate.setText("");

            } else {
                holder.txttaskdate.setText(todoLists.get(position).getTaskdate());

            }

            holder.txttaskcompletedcount.setVisibility(View.GONE);
            holder.txttaskcount.setVisibility(View.GONE);

            holder.checkBox.setVisibility(View.VISIBLE);


            if (todoLists.get(position).getTaskstatus().equals("0")) {
                holder.checkBox.setChecked(false);

            } else {
                holder.checkBox.setChecked(true);


            }


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
        TextView txttaskname, txttaskdate, txttaskcount, txttaskcompletedcount;
        CheckBox checkBox;
        ImageView edtbtn, delbtn, movebtn;
        Button optionsbtn;
        CardView carditem;

        public TodoViewHolder(@NonNull final View itemView) {
            super(itemView);
            txttaskname = itemView.findViewById(R.id.itemname);
            txttaskdate = itemView.findViewById(R.id.itemdate);
            checkBox = itemView.findViewById(R.id.itemcheckbox);
            txttaskcount = itemView.findViewById(R.id.txtitemscount);
            txttaskcompletedcount = itemView.findViewById(R.id.txtitemscompletecount);
            movebtn = itemView.findViewById(R.id.movebtn);
            edtbtn = itemView.findViewById(R.id.edtbtn);
            delbtn = itemView.findViewById(R.id.deletebtn);
            carditem = itemView.findViewById(R.id.cardItem);
            sqLiteDb = new SQLiteDb(context);
            optionsbtn = itemView.findViewById(R.id.buttonOptions);
            optionsbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(itemView.getRootView().getContext(), itemView);

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.menu_edtbtn:
                                    //To edit category
                                    Intent intent = new Intent(context, AddtodoitemActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("EditCategory", todoLists.get(getAdapterPosition()).getTaskCategory());
                                    context.startActivity(intent);
                                    return true;
                                case R.id.menu_delbtn:
                                    //To delete Category.
                                    Intent intent1 = new Intent(context, DialogActivity.class);
                                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent1.putExtra("OperationType", "DeleteCategory");
                                    intent1.putExtra("TaskCat", todoLists.get(getAdapterPosition()).getTaskCategory());
                                    context.startActivity(intent1);
                                    return true;

                                default:
                                    return false;
                            }
                        }
                    });

                    popup.inflate(R.menu.menu_item);
                    popup.setGravity(Gravity.RIGHT);
                    popup.show();
                }
            });
            edtbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AddtodoitemActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("Edititem", todoLists.get(getAdapterPosition()).getTaskname());
                    context.startActivity(intent);
                }
            });
            movebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AddtodoitemActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("MovetoCat", todoLists.get(getAdapterPosition()).getTaskname());
                    context.startActivity(intent);
                }
            });
            delbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DialogActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("OperationType", "Delete");
                    intent.putExtra("TaskName", todoLists.get(getAdapterPosition()).getTaskname());
                    intent.putExtra("TaskCat", todoLists.get(getAdapterPosition()).getTaskCategory());
                    context.startActivity(intent);


                }
            });

            checkBox.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DialogActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("OperationType", "Checkbox");
                    intent.putExtra("TaskName", todoLists.get(getAdapterPosition()).getTaskname());
                    intent.putExtra("TaskCat", todoLists.get(getAdapterPosition()).getTaskCategory());
                    intent.putExtra("Checkboxstat", checkBox.isChecked());
                    context.startActivity(intent);

                }
            });

        }
    }

}
