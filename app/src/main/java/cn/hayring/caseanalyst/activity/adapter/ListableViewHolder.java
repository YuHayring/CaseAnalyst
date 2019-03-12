package cn.hayring.caseanalyst.activity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import cn.hayring.caseanalyst.R;

/***
 * holder对应两个TextView
 * holder include two TextView
 * @author Hayring
 */
public class ListableViewHolder extends RecyclerView.ViewHolder {
    //名称显示View
    public final TextView name;
    //信息显示View
    public final TextView info;

    //注册
    public ListableViewHolder(View v) {
        super(v);
        name = v.findViewById(R.id.item_list_name_item);
        info = v.findViewById(R.id.item_list_info_item);
    }
}
