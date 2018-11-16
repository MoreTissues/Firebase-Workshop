package com.fred.firebaseworkshop;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = RecyclerViewHolder.class.getSimpleName();
    public ImageView deleteicon;
    public TextView categoryTitle;
    public List<Task> taskObject;

    //Constructor
    public RecyclerViewHolder(@NonNull View itemView, final List<Task> taskObject) {
        super(itemView);
        this.taskObject = taskObject;
        categoryTitle = itemView.findViewById(R.id.txt_task_title);
        deleteicon = itemView.findViewById(R.id.task_delete);

        //Event For Button Delete
        deleteicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Delete Icon is Triggered", Toast.LENGTH_LONG).show();
                String taskTitle = taskObject.get(getAdapterPosition()).getTask();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                final Query applesQuery = ref.orderByChild("task").equalTo(taskTitle);
                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot applesnapshots : dataSnapshot.getChildren()){
                            applesnapshots.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e(TAG, "onCanceled", databaseError.toException());
                    }
                });
            }
        });
    }
}
