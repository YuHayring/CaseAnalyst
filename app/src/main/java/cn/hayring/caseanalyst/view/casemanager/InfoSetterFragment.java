package cn.hayring.caseanalyst.view.casemanager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.view.MainActivity;

public class InfoSetterFragment extends Fragment {


    /***
     * activity引用
     */
    protected MainActivity mainActivity;

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    /***
     * 保存按钮
     */
    protected Button saveButton;

    /***
     * 名称键入器
     */
    protected EditText nameInputer;

    /***
     * 信息键入器
     */
    protected EditText infoInputer;


    /***
     * 短期案件设置器
     */
    Spinner shortTimeCaseSetter;

    /***
     * 生命周期加载方法
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.info_setter_fragment, container, false);
    }

    /***
     * 初始化view
     */
    protected void initView(View view) {
        mainActivity = (MainActivity) getContext();

        //关闭旧入口
        view.findViewById(R.id.event_list_enter).setVisibility(View.GONE);
        view.findViewById(R.id.evidence_list_enter).setVisibility(View.GONE);
        view.findViewById(R.id.person_list_enter).setVisibility(View.GONE);
        view.findViewById(R.id.org_list_enter).setVisibility(View.GONE);


        //注册控件,信息显示
        nameInputer = view.findViewById(R.id.case_name_inputer);
        infoInputer = view.findViewById(R.id.case_info_inputer);
        saveButton = view.findViewById(R.id.case_save_button);
        shortTimeCaseSetter = view.findViewById(R.id.short_time_case_switcher);


        if (!mainActivity.isCreate()) {
            saveButton.setEnabled(false);
            saveButton.setVisibility(View.GONE);
            nameInputer.setText(mainActivity.getCaseInstance().getName());
            infoInputer.setText(mainActivity.getCaseInstance().getInfo());
            shortTimeCaseSetter.setSelection(mainActivity.getCaseInstance().isShortTimeCase() ? 0 : 1);
        } else {
            saveButton.setOnClickListener(new FinishEditListener());
        }


    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    /***
     * 保存按钮监听器
     */
    class FinishEditListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            writeInstance();
            mainActivity.setSaved(true);
            view.setVisibility(View.GONE);
        }

    }

    /***
     * 写入
     */
    public void writeInstance() {
        mainActivity.getCaseInstance().setName(nameInputer.getText().toString());
        mainActivity.getCaseInstance().setInfo(infoInputer.getText().toString());
        int index = shortTimeCaseSetter.getSelectedItemPosition();
        if (index == 0) {
            mainActivity.getCaseInstance().setShortTimeCase(true);
        } else if (index == 1) {
            mainActivity.getCaseInstance().setShortTimeCase(false);
        }
    }

}
