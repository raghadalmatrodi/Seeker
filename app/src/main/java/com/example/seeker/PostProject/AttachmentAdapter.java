package com.example.seeker.PostProject;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.seeker.R;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

public class AttachmentAdapter extends RecyclerView.Adapter<AttachmentAdapter.MyViewHolder>  {

    private Context mContext;
    private List<File> eventList;

    private OnItemClickListener mListener;


    public void showDialog(final String msg  , int position) {
        final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
       // alertDialog.getWindow().setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.dialogbackground));
        alertDialog.setMessage(msg);
        alertDialog.setIcon(R.mipmap.ic_launcher);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Delete",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                                eventList.remove(position);
                                notifyDataSetChanged();

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


        public MyViewHolder(View view , final OnItemClickListener listener) {
            super(view);
            name =  view.findViewById(R.id.attachment_name);

            delete = view.findViewById(R.id.deleteEvent);
            attachmentPicture = view.findViewById(R.id.attachmentpic);


                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                   showDialog("Are you sure you want to delete this attachment?"  , getAdapterPosition());
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
    public AttachmentAdapter(Context mContext, List<File> departmentList) {
        this.mContext = mContext;
        this.eventList = departmentList;
    } // end ClassAdapter

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_attachment, parent, false);

        return new MyViewHolder(itemView ,mListener);
    } // end onCreateViewHolder

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        File file = eventList.get(position);

        holder.name.setText(eventList.get(position).getAbsolutePath());

        ImageFileFilter picture = new ImageFileFilter(file);

        if(picture.accept(file)){
            Glide.with(mContext)
                    .load(file)
                    .into(holder.attachmentPicture);
        }

    } //  end onBindViewHolder

    public class ImageFileFilter implements FileFilter {
        File file;
        private final String[] okFileExtensions = new String[] {
                "jpg",
                "png",
                "gif",
                "jpeg"
        };

        public ImageFileFilter(File newfile) {
            this.file = newfile;
        }

        public boolean accept(File file) {
            for (String extension: okFileExtensions) {
                if (file.getName().toLowerCase().endsWith(extension)) {
                    return true;
                }
            }
            return false;
        }

    }

    public interface OnItemClickListener {
        void onItemClick( String name, String id);
        void onDeleteItemClick(int position);

    } // end OnItemClickListener

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    } // end OnItemClickListener

    @Override
    public int getItemCount() {
        return eventList.size();
    } // end getItemCount

}
