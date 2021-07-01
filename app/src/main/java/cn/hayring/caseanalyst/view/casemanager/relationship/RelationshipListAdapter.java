package cn.hayring.caseanalyst.view.casemanager.relationship;


import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.view.ValueSetter;
import cn.hayring.caseanalyst.view.listener.RecyclerItemDeleteDialogListener;
import cn.hayring.caseanalyst.domain.Relationship;
import cn.hayring.caseanalyst.utils.Pointer;

/***
 * 案件列表设置器
 * @author Hayring
 */
public class RelationshipListAdapter<T extends Relationship> extends RecyclerView.Adapter<RelationshipListAdapter.RelationshipViewHolder> {

    /***
     * 列表文字显示长度限制
     */
    public static final int HEAD_CHAR_LENGTH = 24;

    /***
     * holder对应两个TextView
     * holder include two TextView
     * @author Hayring
     */
    public static class RelationshipViewHolder extends RecyclerView.ViewHolder {
        //名称显示View
        public final TextView head;

        //注册
        public RelationshipViewHolder(View v) {
            super(v);
            head = v.findViewById(R.id.relationship_list_head_item);
        }
    }


    /***
     * 元素集合，显示数据源
     */
    private List<T> relationships;

    /***
     * 数据源getter
     */
    public List<T> getRelationships() {
        return relationships;
    }

    /***
     * Activity引用
     */
    private RelationshipListActivity mActivity;

    /***
     * 输入案件
     * @param relationships
     */
    public RelationshipListAdapter(RelationshipListActivity mActivity, List<T> relationships) {
        this.mActivity = mActivity;
        this.relationships = relationships;
    }


    /***
     * 显示内容的绑定
     * Bind data and view；
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RelationshipViewHolder holder, int position) {
        //缩减头并绑定名称
        String name = relationships.get(position).toString();
        if (name.length() > HEAD_CHAR_LENGTH) {
            name = name.substring(0, HEAD_CHAR_LENGTH - 3) + "...";
        }
        holder.head.setText(name);


        //注册点击监听器
        holder.itemView.setOnClickListener(new EditRelationshipListener());
        holder.itemView.setOnLongClickListener(new DeleteRelationShipListener(mActivity));
    }


    /***
     * 案件编辑点击监听器
     */
    public class EditRelationshipListener implements View.OnClickListener {


        /***
         * 编辑案件
         * Edit Item
         * @param view
         */
        @Override
        public void onClick(View view) {
            //取出元素
            int position = mActivity.getRelationshipListRecycler().getChildAdapterPosition(view);
            T relationship = relationships.get(position);


            //注册Activity，ValueSetter
            Intent relationshipTransporter;
            relationshipTransporter = new Intent(mActivity, RelationshipValueSetter.class);


            //绑定参数
            relationshipTransporter.putExtra(ValueSetter.CREATE_OR_NOT, false);/*
            relationshipTransporter.putExtra(ValueSetter.DATA, relationship);
            relationshipTransporter.putExtra(ValueSetter.POSITION, position);*/

            relationshipTransporter.putExtra(ValueSetter.RELATIONSHIP_TYPE, mActivity.getRelationshipType());

            Pointer.setPoint(relationship);
            Pointer.setConnector(mActivity.getConnector());

            //启动ValueSetter
            mActivity.startActivityForResult(relationshipTransporter, 1);

        }
    }


    /***
     * 关系删除监听器
     */
    class DeleteRelationShipListener extends RecyclerItemDeleteDialogListener {

        public DeleteRelationShipListener(Context context) {
            super(context);
        }

        @Override
        protected void delete(int index) {
            deleteItem(index);
        }
    }


    /***
     *
     * @return count 总数
     */
    @Override
    public int getItemCount() {
        return relationships.size();
    }

    /***
     * Layout绑定
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RelationshipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //注册单个元素的layout
        View v = LayoutInflater.from(mActivity).inflate(R.layout.single_relationship_list_frame, parent, false);
        return new RelationshipViewHolder(v);
    }


    //改变数据源的四个方法


    public void addItem(T item) {
        relationships.add(item);
        ////更新数据集不是用adapter.notifyDataSetChanged()而是notifyItemInserted(position)与notifyItemRemoved(position) 否则没有动画效果。
        notifyItemInserted(relationships.size() - 1);
    }

    public void setItem(int position, T item) {
        relationships.set(position, item);
        notifyItemChanged(position);
    }

    public void deleteItem(int position) {
        relationships.remove(position);
        Relationship relationship = (Relationship) mActivity.getMainRelationshipListAdapter().getRelationships().get(position);
        relationship.removeSelf();
        notifyItemRemoved(position);
    }

    public void addAllItem(ArrayList<T> items) {
        this.relationships.addAll(items);
        notifyDataSetChanged();
    }


}
