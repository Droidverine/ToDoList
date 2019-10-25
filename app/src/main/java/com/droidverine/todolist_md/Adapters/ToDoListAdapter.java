package com.droidverine.todolist_md.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.droidverine.todolist_md.Activities.AddtodoitemActivity;
import com.droidverine.todolist_md.Activities.TasksActivity;
import com.droidverine.todolist_md.Models.TodoList;
import com.droidverine.todolist_md.R;
import com.droidverine.todolist_md.Utils.SQLiteDb;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
            sqLiteDb = new SQLiteDb(context);
            holder.txttaskname.setText(todoLists.get(position).getTaskCategory());
            String date = "";
            if (sqLiteDb.getDates(todoLists.get(position).getTaskCategory()) != null) {
                holder.txttaskdate.setText(date);
                date = sqLiteDb.getDates(todoLists.get(position).getTaskCategory());

            }
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
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
                Log.d("compare1", "status " + due.compareTo(nddate));

                if (due.compareTo(nddate) < 0) {
                    // IF DUE HAS PASSED
                    Log.d("compare1", "past " + due);

                    holder.carditem.setCardBackgroundColor(Color.parseColor("#D81B60"));
                } else if (due.compareTo(nddate) > 0) {
                    //IF DUE IS FUTURE
                    holder.carditem.setCardBackgroundColor(Color.parseColor("#FFFFFF"));

                } else if (nddate.compareTo(due) == 0) {
                    //IF DUE IS TODAY
                    holder.carditem.setCardBackgroundColor(Color.parseColor("#FFEB3B"));

                }
            }


            holder.checkBox.setVisibility(View.INVISIBLE);
            holder.optionsbtn.setVisibility(View.VISIBLE);
            holder.delbtn.setVisibility(View.GONE);
            holder.edtbtn.setVisibility(View.GONE);
            holder.movebtn.setVisibility(View.GONE);
            holder.txttaskcompletedcount.setVisibility(View.VISIBLE);
            holder.txttaskcount.setVisibility(View.VISIBLE);
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
            holder.txttaskdate.setText(todoLists.get(position).getTaskdate());
            holder.txttaskcompletedcount.setVisibility(View.GONE);
            holder.txttaskcount.setVisibility(View.GONE);

            holder.checkBox.setVisibility(View.VISIBLE);
            SimpleDateFormat sdf = new SimpleDateFormat();

            Date date = new Date();
            Date strDate = null;
            //Date today= sdf.format(str);
            try {
                strDate = sdf.parse(todoLists.get(position).getTaskdate());
            } catch (ParseException e) {
                e.printStackTrace();
            }


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
                                    Intent intent = new Intent(context, AddtodoitemActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("EditCategory", todoLists.get(getAdapterPosition()).getTaskCategory());
                                    context.startActivity(intent);
                                    return true;
                                case R.id.menu_delbtn:
                                    sqLiteDb.deletecategory(todoLists.get(getAdapterPosition()).getTaskCategory());

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
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    sqLiteDb.deletetodoitem(todoLists.get(getAdapterPosition()).getTaskCategory(), todoLists.get(getAdapterPosition()).getTaskname());
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder dgbuilder = new AlertDialog.Builder(itemView.getRootView().getContext());
                    dgbuilder.setMessage("You really want to delete it?")
                            .setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener)
                            .show();


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
