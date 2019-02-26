package cn.hayring.caseanalyst.activity.ValueSetter;

import android.view.LayoutInflater;
import android.widget.ScrollView;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.pojo.Organization;

public class OrganizationValueSetter extends ValueSetter {
    Organization orgInstance;


    /***
     * 加载页面
     */
    @Override
    public void loadView() {
        //加载layout实例
        LayoutInflater inflater = getLayoutInflater();
        sonView = (ScrollView) inflater.inflate(R.layout.org_value_setter, null);
        rootLayout.addView(sonView);

        //注册控件,信息显示
        nameInputer = findViewById(R.id.org_name_inputer);
        infoInputer = findViewById(R.id.org_info_inputer);
        saveButton = findViewById(R.id.org_save_button);
        if (!requestInfo.getBooleanExtra(CREATE_OR_NOT, true)) {
            orgInstance = (Organization) requestInfo.getSerializableExtra(DATA);
            nameInputer.setText(orgInstance.getName());
            infoInputer.setText(orgInstance.getInfo());
        } else {
            orgInstance = caseInstance.createOrganization();
        }

        //设置监听器
        saveButton.setOnClickListener(new OrganizationFinishEditListener());


    }

    /***
     * 保存按钮监听器
     */
    class OrganizationFinishEditListener extends FinishEditListener {
        @Override
        void editReaction() {
            orgInstance.setName(nameInputer.getText().toString());
            orgInstance.setInfo(infoInputer.getText().toString());
            requestInfo.putExtra(DATA, orgInstance);
            requestInfo.putExtra(CHANGED, true);
        }
    }


    /***
     * 返回键不保存，返回未改动
     */
    @Override
    public void finish() {
        requestInfo.putExtra(CHANGED, false);
        setResult(2, requestInfo);
        super.finish();
    }


}
