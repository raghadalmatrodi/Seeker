package com.example.seeker.EmployerMainPages.AccountRelatedActivities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.seeker.Database.ApiClients;
import com.example.seeker.Database.ApiMethods;
import com.example.seeker.Model.StorageDocument;
import com.example.seeker.PostProject.AttachmentAdapter;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SampleWorkAdapter extends RecyclerView.Adapter<SampleWorkAdapter.MyViewHolder>  {

    private Context mContext;
    private List<StorageDocument> eventList;

    private SampleWorkAdapter.OnItemClickListener mListener;
    private boolean showDeleteButton = false;



    public void showDelete(){
        showDeleteButton = true;
    }
    public void showDialog(final String msg  , int position ) {
        final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        // alertDialog.getWindow().setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.dialogbackground));
        alertDialog.setMessage(msg);
        alertDialog.setIcon(R.mipmap.ic_launcher);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Delete",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Long user_id = MySharedPreference.getLong(mContext, Constants.Keys.USER_ID,-1);
                        ApiClients.getAPIs().deleteSampleWork(user_id, eventList.get(position).getId()).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.isSuccessful()) {
                                    Log.i("SampleWorkAdapter", "onResponse : Success" + response.message());
                                    eventList.remove(position);
                                    notifyDataSetChanged();
                                }else{
                                    Log.i("SampleWorkAdapter", "onResponse : NotSuccess" + response.message());

                                }

                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.i("SampleWorkAdapter", "onFailure : NotSuccess" + t.getMessage());

                            }
                        });



                    }//End onClick()
                });//End BUTTON_POSITIVE


        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"Cancel", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();


            }
        });


        alertDialog.show();

    }//end showDialog

    public class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener   {
        public RecyclerView recyclerView;
        public TextView name ;
        public RelativeLayout color;
        ImageView delete;
        ImageView attachmentPicture;


        public MyViewHolder(View view , final SampleWorkAdapter.OnItemClickListener listener) {
            super(view);

            delete = view.findViewById(R.id.deleteEvent);
            attachmentPicture = view.findViewById(R.id.attachmentpic);

             if(showDeleteButton){
                 delete.setVisibility(View.VISIBLE);
             }
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showDialog("Are you sure you want to delete this Photo?"  , getAdapterPosition() );
                }
            });
             attachmentPicture.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     listener.onItemClick(eventList.get(getAdapterPosition()));
                 }
             });


            recyclerView =  view.findViewById(R.id.attachment_recycle_view);
            //todo



//            itemView.setOnCreateContextMenuListener(this);
        } //end MyViewHolder

//        @Override
//        public boolean onMenuItemClick(MenuItem item) {
//            if (mListener != null) {
//                int position = getAdapterPosition();
//                if (position != RecyclerView.NO_POSITION) {
//
//                    switch (item.getItemId()) {
//                        case 1:
//                            mListener.onDeleteItemClick(position);
//                            return true;
//                        case 2:
////                            mListener.onDeleteItemClick(position);
//                            return true;
//                    }
//                }
//            }
//            return false;
//        }

//        @Override
//        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//            //menu.setHeaderTitle("Select Action");
//            MenuItem deleteItem = menu.add(Menu.NONE, 1, 1, "Delete");
//            deleteItem.setOnMenuItemClickListener(this);
//
//        }

        @Override
        public void onClick(View v) {

        }


    }
    public SampleWorkAdapter(Context mContext, List<StorageDocument> departmentList) {
        this.mContext = mContext;
        this.eventList = departmentList;
    } // end ClassAdapter

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_sample_work, parent, false);

        return new SampleWorkAdapter.MyViewHolder(itemView ,mListener);
    } // end onCreateViewHolder

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        StorageDocument file = eventList.get(position);

            Glide.with(mContext)
                    .load(file.getUrl())
                    .into(holder.attachmentPicture);


    } //  end onBindViewHolder



    public interface OnItemClickListener {
        void onItemClick( StorageDocument storageDocument);

    } // end OnItemClickListener

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    } // end OnItemClickListener

    @Override
    public int getItemCount() {
        return eventList.size();
    } // end getItemCount

}
