package cn.hayring.caseanalyst.view.caselist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import cn.hayring.caseanalyst.domain.Case;

/**
 * @author hayring
 * @date 6/21/21 7:26 PM
 */
public class CaseViewModel extends ViewModel {

    /**
     * model 层单例
     */
    CaseDBModel caseModel = CaseDBModel.getInstance();

    /**
     * 带有案件数据的 livedata
     */
    MutableLiveData<List<Case>> caseListData = new MutableLiveData<>();

    public void getCaseList() {
        List<Case> cases = caseModel.getCases();
        caseListData.postValue(cases);
    }


    public LiveData<List<Case>> getCaseListData() {
        return caseListData;
    }
}
