package com.example.mad01;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewHolder1>{
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder1 holder, @SuppressLint("RecyclerView") final int position, @NonNull MainModel model) {
//to fetch the data from the database
        holder.name.setText(model.getName());
        holder.duration.setText(model.getDuration());
        holder.sets.setText(model.getSets());

        Glide.with(holder.image1.getContext())
                .load(model.getUrl())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.image1);



        // EDIT<- by accessing these text fields and assign values for them
        holder.btnexEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onclick of edit button the pop up will come to edit details
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.image1.getContext())
                        .setContentHolder(new ViewHolder(R.layout.updateex_popup))
                        .setExpanded(true,1700)
                        .create();

                //fetching the names that we used from layout to this to update the Details
                View view = dialogPlus.getHolderView();
                EditText name = view.findViewById(R.id.extxtName);
                EditText duration = view.findViewById(R.id.extxtDuration);
                EditText sets = view.findViewById(R.id.extxtSets);
                EditText url = view.findViewById(R.id.extxtUrl);

                Button exbtnUpdate = view.findViewById(R.id.exbtnUpdate);

                name.setText(model.getName());
                duration.setText(model.getDuration());
                sets.setText(model.getSets());
                url.setText(model.getUrl());

                dialogPlus.show();

                //update button perform the data changes when we pass the data
                exbtnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Map<String,Object> map = new HashMap<>();
                        map.put("name",name.getText().toString());
                        map.put("duration",duration.getText().toString());
                        map.put("sets",sets.getText().toString());
                        map.put("url",url.getText().toString());

                        //postion is in the onBinViewHolder
                        //get a instance from the fire base
                        FirebaseDatabase.getInstance().getReference().child("Exercise")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        Toast.makeText(holder.name.getContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.name.getContext(), "Error Occured", Toast.LENGTH_SHORT).show();
                                       //this method will make sure after clicking update method the popup will be gone
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }
        });

        //delete button
        holder.btnexDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Are you sure ?");
                builder.setMessage("Deleted Data Can't be undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        FirebaseDatabase.getInstance().getReference().child("Exercise")
                                .child(getRef(position).getKey()).removeValue();//this is to get th ExID
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(holder.name.getContext(), "Canceled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //using this to access the card view which created in exercise_item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item,parent,false);
        return new myViewHolder1(view);
    }

    class myViewHolder1 extends RecyclerView.ViewHolder{

      CircleImageView image1;
      TextView duration,name,sets;
      Button btnexEdit,btnexDelete;

    //catching the fields using IDs
      public myViewHolder1(@NonNull View itemView) {
          super(itemView);
          image1 = (CircleImageView)itemView.findViewById(R.id.image1);
          name = (TextView)itemView.findViewById(R.id.exnametext);
          duration=(TextView)itemView.findViewById(R.id.exdurationtext);
          sets=(TextView)itemView.findViewById(R.id.exsetstext);
          //adding the buttons
          btnexEdit=(Button) itemView.findViewById(R.id.btnexedit);
          btnexDelete=(Button) itemView.findViewById(R.id.btnexdelete);


      }
  }

}
