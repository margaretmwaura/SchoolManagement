package com.android.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder>
{

    private List<Student> mStudentList;
    private Context mContext;
    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(R.layout.viewholder,parent,shouldAttachToParentImmediately);
        StudentViewHolder studentViewHolder = new StudentViewHolder(view);
        return studentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position)
    {
       Student student = mStudentList.get(position);
       holder.name.setText(student.getName());
       holder.email.setText(student.getEmail());
       holder.password.setText(student.getPassword());
    }

    @Override
    public int getItemCount() {
        return mStudentList.size();
    }

    public void setmStudentList(List<Student> mStudentList)
    {
        this.mStudentList = mStudentList;
        notifyDataSetChanged();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder
    {
        private TextView name , email , password ;
        public StudentViewHolder(View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.editText_name);
            email = itemView.findViewById(R.id.editText_email);
            password = itemView.findViewById(R.id.editText_password);
        }
    }
}
