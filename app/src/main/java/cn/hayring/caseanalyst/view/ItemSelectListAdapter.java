package cn.hayring.caseanalyst.view;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.domain.Listable;
import cn.hayring.caseanalyst.utils.Pointer;

/***
 * 元素选择列表设置器
 * @author Hayring
 */
public class ItemSelectListAdapter extends RecyclerView.Adapter<ListableViewHolder> {

    /***
     * 名字显示长度限制
     */
    public static final int NAME_CHAR_LENGTH = 7;

    /***
     * 信息显示长度限制
     */
    public static final int INFO_CHAR_LENGTH = 16;

    /***
     * 案件集合，显示数据源
     */
    private List items;

    /***
     * 数据源getter
     */
    public List getItems() {
        return items;
    }

    /***
     * Activity引用
     */
    private ItemSelectListActivity mActivity;

    /***
     * 输入案件
     * @param items
     */
    public ItemSelectListAdapter(ItemSelectListActivity mActivity, List items) {
        this.mActivity = mActivity;
        this.items = items;
    }

    /***
     * Layout绑定
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ListableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //注册单个元素的layout
        View v = LayoutInflater.from(mActivity).inflate(R.layout.single_item_list_frame, parent, false);
        return new ListableViewHolder(v);
    }

    /***
     * 显示内容的绑定
     * Bind data and view；
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ListableViewHolder holder, int position) {
        //缩减并绑定名称
        String name = ((Listable) items.get(position)).getName();
        if (name.length() > NAME_CHAR_LENGTH) {
            name = name.substring(0, NAME_CHAR_LENGTH - 2) + "...";
        }
        holder.name.setText(name);

        //缩减并绑定信息
        String info = ((Listable) items.get(position)).getInfo();
        if (info.length() > INFO_CHAR_LENGTH) {
            info = info.substring(0, INFO_CHAR_LENGTH - 2) + "...";
        }
        holder.info.setText(info);

        //注册点击监听器
        holder.itemView.setOnClickListener(new ItemSelectedListener());  //单例即可
    }


    /***
     * 元素选中监听器
     */
    public class ItemSelectedListener implements View.OnClickListener {


        /***
         * 选中元素
         * Edit Item
         * @param view
         */
        public void onClick(View view) {
            //判断点击位置
            int position = mActivity.getItemListRecycler().getChildAdapterPosition(view);
            //从表中取出对应的元素
            Listable item = (Listable) items.get(position);
            //传输引用
            // 旧方法 mActivity.getRequestInfo().putExtra(ValueSetter.DATA, item);
            Pointer.setPoint(item);

            mActivity.returnIntent();
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


}
