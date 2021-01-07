package cn.hayring.caseanalyst.view.magic;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.view.casemanager.casevaluesetter.EventClipValueSetter;
import cn.hayring.caseanalyst.view.ValueSetter;
import cn.hayring.caseanalyst.view.listener.RecyclerItemDeleteDialogListener;
import cn.hayring.caseanalyst.domain.EventClip;
import cn.hayring.caseanalyst.utils.Pointer;

public class TimeAxisAdapter extends RecyclerView.Adapter<TimeAxisAdapter.TimeAxisViewHolder> {
    private TimeAxis mContext;
    private ArrayList<EventClip> mDataList;

    public TimeAxisAdapter(TimeAxis context, ArrayList<EventClip> dataList) {
        this.mContext = context;
        this.mDataList = dataList;
    }

    @Override
    public TimeAxisViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //设置根才能填充
        TimeAxisViewHolder holder = new TimeAxisViewHolder(LayoutInflater.from(mContext).inflate(R.layout.single_event_clip, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(TimeAxisViewHolder holder, int position) {
        EventClip bean = mDataList.get(position);
        if (null == bean) {
            return;
        }
        holder.tv_date.setText(ValueSetter.dateFormatter.format(bean.getTimePoint().getTime()));
        holder.tv_time.setText(ValueSetter.timeFormatter.format(bean.getTimePoint().getTime()));
        holder.tv_desc.setText(bean.getInfo());
        //处理顶部条目
        /*if (position == 0) {
            holder.tv_line_top.setVisibility(View.INVISIBLE);
        } else if (position == mDataList.size() - 1) {
            //底部数据
            holder.tv_line_bottom.setVisibility(View.INVISIBLE);
        } else {
            //设置圆点背景
            holder.tv_dot.setBackgroundResource(R.drawable.dot_normal);
        }*/

        holder.singleEventClipItem.setOnClickListener(view -> {
            int index = mContext.getRecyclerView().getChildAdapterPosition(view);
            Intent intent = new Intent(mContext, EventClipValueSetter.class);
            intent.putExtra(ValueSetter.CREATE_OR_NOT, false);
            intent.putExtra(ValueSetter.INDEX, index);
            Pointer.setPoint(mDataList.get(index));
            mContext.startActivity(intent);

        });
        holder.singleEventClipItem.setOnLongClickListener(new DeleteTimeAxis(mContext));
        holder.singleEventClipItem.setOnTouchListener(new onSelectColorChanged());

    }


    /***
     * 元素删除监听器
     */
    class DeleteTimeAxis extends RecyclerItemDeleteDialogListener {

        @Override
        protected void onDelete(View view) {
            view.setBackgroundResource(0);
        }

        public DeleteTimeAxis(Context context) {
            super(context);
        }

        @Override
        protected void delete(int index) {
            deleteItem(index);
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class TimeAxisViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout singleEventClipItem;
        private final TextView tv_date;
        private final TextView tv_time;
        private final TextView tv_desc;
        private final View tv_line_top;
        private final View tv_line_bottom;
        private final View tv_dot;

        public TimeAxisViewHolder(View itemView) {
            super(itemView);
            //全局
            singleEventClipItem = itemView.findViewById(R.id.single_event_clip_item);
            //日期
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            //时间
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            //描述
            tv_desc = (TextView) itemView.findViewById(R.id.tv_desc);
            //顶部线
            tv_line_top = itemView.findViewById(R.id.tv_line_top);
            //圆点
            tv_dot = itemView.findViewById(R.id.tv_dot);
            //底部线
            tv_line_bottom = itemView.findViewById(R.id.tv_line_bottom);
        }
    }


    public void addItem(EventClip item) {
        int i = 0;
        for (EventClip clip : mDataList) {
            if (item.getTimePoint().before(clip.getTimePoint())) {
                mDataList.add(i, item);
                break;
            }
            i++;
        }
        if (i == mDataList.size()) {
            mDataList.add(item);
        }
        notifyDataSetChanged();
    }

    public void setItem(int position, EventClip item) {
        mDataList.set(position, item);
        notifyItemChanged(position);
    }

    public void deleteItem(int position) {
        mDataList.remove(position);
        notifyItemRemoved(position);
    }

    public void addAllItem(ArrayList<EventClip> items) {
        this.mDataList.addAll(items);
        notifyDataSetChanged();
    }

    class onSelectColorChanged implements View.OnTouchListener {

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                view.setBackgroundColor(mContext.getColor(R.color.colorSelected));
            } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_MOVE) {
                view.setBackgroundResource(0);
                return false;
            }
            //事件没被消耗
            return false;
        }
    }
}
