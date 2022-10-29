package com.example.finalmyfinance;

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
import android.widget.SearchView;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView")final int position, @NonNull MainModel model) {

        holder.name.setText(model.getName());
        holder.amount.setText(model.getAmount());
        holder.note.setText(model.getNote());

        Glide.with(holder.img.getContext())
                .load(model.getUrl())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);


        // EDIT<- by accessing these text fields and assign values for them
        holder.btnexpedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onclick of edit button the pop up will come to edit details
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_expense_popup))
                        .setExpanded(true,1325)
                        .create();

                //fetching the names that we used from layout to this to update the Details
                View view = dialogPlus.getHolderView();
                EditText name = view.findViewById(R.id.exptxtName);
                EditText amount = view.findViewById(R.id.exptxtAmount);
                EditText note = view.findViewById(R.id.exptxtdes);
                EditText url = view.findViewById(R.id.exptxtUrl);

                Button exbtnUpdate = view.findViewById(R.id.expbtnUpdate);

                name.setText(model.getName());
                amount.setText(model.getAmount());
                note.setText(model.getNote());
                url.setText(model.getUrl());

                dialogPlus.show();

                //update button perform the data changes when we pass the data
                exbtnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Map<String,Object> map = new HashMap<>();
                        map.put("name",name.getText().toString());
                        map.put("amount",amount.getText().toString());
                        map.put("note",note.getText().toString());
                        map.put("url",url.getText().toString());

                        //postion is in the onBinViewHolder
                        //get a instance from the fire base
                        FirebaseDatabase.getInstance().getReference().child("bugdetexp")
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
        holder.btnexpdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Are you sure ?");
                builder.setMessage("Deleted Data Can't be undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        FirebaseDatabase.getInstance().getReference().child("bugdetexp")
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

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView amount,name,note;
        Button btnexpedit,btnexpdelete;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);



            img = (CircleImageView)itemView.findViewById(R.id.img1);
            amount =(TextView)itemView.findViewById(R.id.amounttxt);
            name=(TextView)itemView.findViewById(R.id.nametxt);
            note=(TextView)itemView.findViewById(R.id.notetxt);


            //adding the buttons
            btnexpedit=(Button) itemView.findViewById(R.id.btnexpedit);
            btnexpdelete=(Button) itemView.findViewById(R.id.btnexpdelete);


        }
    }



}
