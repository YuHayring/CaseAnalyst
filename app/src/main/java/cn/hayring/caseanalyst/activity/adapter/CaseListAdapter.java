package cn.hayring.caseanalyst.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.activity.CaseListActivity;
import cn.hayring.caseanalyst.activity.MainActivity;
import cn.hayring.caseanalyst.activity.ValueSetter;
import cn.hayring.caseanalyst.pojo.Case;

/***
 * 案件列表设置器
 * @author Hayring
 */
public class CaseListAdapter extends RecyclerView.Adapter<CaseListAdapter.VH> {
    /***
     * holder对应两个TextView
     * holder include two TextView
     * @author Hayring
     */
    public static class VH extends RecyclerView.ViewHolder {
        public final TextView name;
        public final TextView info;

        public VH(View v) {
            super(v);
            name = v.findViewById(R.id.case_name_item);
            info = v.findViewById(R.id.case_info_item);
        }
    }

    private int changedPosition;

    /***
     * 案件集合
     */
    private List<Case> cases;

    private CaseListActivity mActivity;

    /***
     * 输入案件
     * @param cases
     */
    public CaseListAdapter(CaseListActivity mActivity, List<Case> cases) {
        this.mActivity = mActivity;
        this.cases = cases;
    }


    /***
     * 显示内容的绑定
     * Bind data and view；
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.name.setText(cases.get(position).getName());
        String info = cases.get(position).getInfo();
        if (info.length() > 16) {
            info = info.substring(0, 14) + "...";
        }
        holder.info.setText(info);

        holder.itemView.setOnClickListener(new EditCaseListener());
    }


    /***
     * 案件编辑点击监听器
     */
    public class EditCaseListener implements View.OnClickListener {


        /***
         * 编辑案件
         * Edit Case
         * @param view
         */
        @Override
        public void onClick(View view) {

            Intent caseTransporter = new Intent(mActivity, ValueSetter.class);

            int position = mActivity.getCaseList().getChildAdapterPosition(view);

            Case caseInstance = cases.get(position);

            //交流类型为案件
            caseTransporter.putExtra(ValueSetter.TYPE, ValueSetter.CASE);
            //行为:修改数据行为
            caseTransporter.putExtra(ValueSetter.CREATE_OR_NOT, false);
            //绑定Case
            caseTransporter.putExtra(ValueSetter.DATA, caseInstance);
            //点击位置
            caseTransporter.putExtra(ValueSetter.POSITION, position);
            mActivity.startActivityForResult(caseTransporter, CaseListActivity.REQUESTCODE);

        }
    }


    /***
     *
     * @return count 总数
     */
    @Override
    public int getItemCount() {
        return cases.size();
    }

    /***
     * Layout绑定
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(mActivity).inflate(R.layout.single_case_frame, parent, false);
        return new VH(v);
    }

    public void addItem(Case caseInstance) {
        cases.add(caseInstance);
        ////更新数据集不是用adapter.notifyDataSetChanged()而是notifyItemInserted(position)与notifyItemRemoved(position) 否则没有动画效果。
        notifyItemInserted(cases.size() - 1);
    }

    public void setItem(int position, Case caseInstance) {
        cases.set(position, caseInstance);
        notifyItemChanged(position);
    }

    public void deleteItem(int position) {
        cases.remove(position);
        notifyItemRemoved(position);
    }
}
