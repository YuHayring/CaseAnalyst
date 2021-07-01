package cn.hayring.caseanalyst.view.listener;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

public abstract class RecyclerItemDeleteDialogListener implements View.OnLongClickListener {

    private Context context;

    public RecyclerItemDeleteDialogListener(Context context) {
        this.context = context;
    }


    protected String message = "是否要删除？";

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean onLongClick(View view) {
        isRecyclerView(view);
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle("警告！")
                .setMessage(message)
                .setPositiveButton("确定", new ConfirmDeleteItemListener(view))
                .setNegativeButton("取消", null)
                .create();
        alertDialog.show();
        onDelete(view);/*
        Log.i("view",view.toString());
        ViewParent viewP = view.getParent();
        Log.i("view",viewP.toString());
        viewP = viewP.getParent();
        Log.i("view",viewP.toString());
        viewP = viewP.getParent();
        Log.i("view",viewP.toString());*/
        return true;
    }

    /***
     * 确认删除监听器
     */
    class ConfirmDeleteItemListener implements DialogInterface.OnClickListener {//添加"Yes"按钮
        View view;

        public ConfirmDeleteItemListener(View view) {
            super();
            this.view = view;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            int position = ((RecyclerView) view.getParent()).getChildAdapterPosition(view);
            delete(position);
        }
    }

    protected void onDelete(View view) {

    }


    protected abstract void delete(int index);


    protected boolean isRecyclerView(View view) {
        if (view.getParent() instanceof RecyclerView) {
            return true;
        } else {
            throw new IllegalArgumentException("Error view type! It is not an instance of Recycler view!");
        }
    }
}



