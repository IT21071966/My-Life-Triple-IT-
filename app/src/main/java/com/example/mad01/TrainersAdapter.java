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

public class TrainersAdapter extends FirebaseRecyclerAdapter<TrainersModel,TrainersAdapter.myViewholder2> {


      /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public TrainersAdapter(@NonNull FirebaseRecyclerOptions<TrainersModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewholder2 holder, @SuppressLint("RecyclerView") final int position, @NonNull TrainersModel model) {

        holder.name.setText(model.getName());
        holder.time.setText(model.getTime());
        holder.price.setText(model.getPrice());

        Glide.with(holder.iamge2.getContext())
                .load(model.getUrl())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.iamge2);




        //bind the edit button
        holder.btntredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.iamge2.getContext())
                        .setContentHolder(new ViewHolder(R.layout.updatetr_popup))
                        .setExpanded(true,1700)
                        .create();


                //fetching the names that we used from layout to this to update the Details
                View view = dialogPlus.getHolderView();
                EditText name = view.findViewById(R.id.trtxtName);
                EditText time = view.findViewById(R.id.trtxtTime);
                EditText price = view.findViewById(R.id.trtxtPrice);
                EditText url = view.findViewById(R.id.trtxtUrl);

                Button trbtnUpdate = view.findViewById(R.id.trbtnUpdate);

                name.setText(model.getName());
                time.setText(model.getTime());
                price.setText(model.getPrice());
                url.setText(model.getUrl());

                dialogPlus.show();


                trbtnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("name",name.getText().toString());
                        map.put("time",time.getText().toString());
                        map.put("price",price.getText().toString());
                        map.put("url",url.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Trainers")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.name.getContext(), "Data Updated Successfully in Trainers Database", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.name.getContext(), "Error While Updating data to the Trainers Database", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();//this will ensure that pop up will be closed after clicking update button
                                    }
                                });

                    }
                });

            }
        });


        //delete Button
        //delete button
        holder.btntrdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Are you sure ?");
                builder.setMessage("Deleted Data Can't be Undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        FirebaseDatabase.getInstance().getReference().child("Trainers")
                                .child(getRef(position).getKey()).removeValue();//this is to get thte ExID
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

    //to add the one card method we created
    @NonNull
    @Override
    public myViewholder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trainer_item,parent,false);
        return new TrainersAdapter.myViewholder2(view);

    }

    class myViewholder2 extends RecyclerView.ViewHolder{

        CircleImageView iamge2;
        TextView name,time,price;
        Button btntredit,btntrdelete;

        public myViewholder2(@NonNull View itemView) {
            super(itemView);
            iamge2 = (CircleImageView)itemView.findViewById(R.id.iamge2);
            name = (TextView)itemView.findViewById(R.id.trnametext);
            time=(TextView)itemView.findViewById(R.id.trtimetext);
            price=(TextView)itemView.findViewById(R.id.trpricetext);


            //adding the buttons
            btntredit=(Button) itemView.findViewById(R.id.btntredit);
            btntrdelete=(Button) itemView.findViewById(R.id.btntrdelete);

        }
    }
}
