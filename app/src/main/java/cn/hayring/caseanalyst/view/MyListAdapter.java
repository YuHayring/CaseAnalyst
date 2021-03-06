package cn.hayring.caseanalyst.view;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.hayring.caseanalyst.domain.Avatars;
import cn.hayring.caseanalyst.domain.Case;
import cn.hayring.caseanalyst.domain.Evidence;
import cn.hayring.caseanalyst.domain.Listable;
import cn.hayring.caseanalyst.domain.Organization;
import cn.hayring.caseanalyst.domain.Person;
import cn.hayring.caseanalyst.domain.Relationable;
import cn.hayring.caseanalyst.utils.Pointer;
import cn.hayring.caseanalyst.view.listener.RecyclerItemDeleteDialogListener;

/***
 * 案件列表设置器
 * @author Hayring
 */
public class MyListAdapter<T extends Listable> extends AbstractListAdapter {


    /***
     * Activity引用
     */
    private MyListActivity mActivity;

    /***
     * 输入数据源构造器
     * @param items
     */
    public MyListAdapter(MyListActivity mActivity, List<Listable> items) {
        super(items);
        this.mActivity = mActivity;
    }

    /**
     * 绑定监听器
     *
     * @param holder
     */
    @Override
    public void bindListener(ListableViewHolder holder) {
        holder.itemView.setOnClickListener(editItemListener);
        holder.itemView.setOnLongClickListener(deleteItemListener);
    }

    EditItemListener editItemListener = new EditItemListener();

    /***
     * 元素编辑点击监听器
     */
    public class EditItemListener implements View.OnClickListener {


        /***
         * 编辑元素
         * Edit Item
         * @param view
         */
        @Override
        public void onClick(View view) {
            //取出元素
            int position = mActivity.getItemListRecycler().getChildAdapterPosition(view);
            T item = (T) items.get(position);


            //注册Activity，ValueSetter
            Intent itemTransporter = new Intent(mActivity, mActivity.getValueSetterClass());


            //绑定参数
            itemTransporter.putExtra(ValueSetter.CREATE_OR_NOT, false);

            Pointer.setPoint(item);
            //itemTransporter.putExtra(ValueSetter.DATA, item);
            //itemTransporter.putExtra(ValueSetter.POSITION, position);

            //启动ValueSetter
            mActivity.startActivityForResult(itemTransporter, MyListActivity.REQUEST_CODE);

        }
    }


    DeleteItemListener deleteItemListener = new DeleteItemListener(mActivity);

    /***
     * 元素删除监听器
     */
    class DeleteItemListener extends RecyclerItemDeleteDialogListener {

        public DeleteItemListener(Context context) {
            super(context);
        }

        @Override
        protected void delete(int index) {
            Class clazz = mActivity.getTClass();

            //若删除的是的有头像的类,删除其图片
            if (clazz == Evidence.class || clazz == Person.class || clazz == Organization.class) {
                Avatars instance = (Avatars) items.get(index);
                if (instance.getImageIndex() != null) {
                    mActivity.deleteFile(instance.getImageIndex() + ".jpg");
                }
            }
            Listable listable = (Listable) items.get(index);
            if (!(listable instanceof Case)) {
                Relationable relationable = (Relationable) listable;
                relationable.removeSelf();
            }
            deleteItem(index);
        }
    }


    /***
     *
     * @return count 总数
     */
    @Override
    public int getItemCount() {
        return items.size();
    }

    /***
     * Layout绑定
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ListableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //注册单个元素的layout
        View v;
        v = LayoutInflater.from(mActivity).inflate(mActivity.getSingleLayoutId(), parent, false);
        return new ListableViewHolder(v);
    }



}
