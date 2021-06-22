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

    public CaseViewModel() {
    }

    /**
     * model 层单例
     */
    CaseDBModel caseModel = CaseDBModel.getInstance();

    /**
     * 带有案件列表的 livedata
     */
    MutableLiveData<List<Case>> caseListData = new MutableLiveData<>();

    /**
     * 单个案件 livedata
     */
    MutableLiveData<Case> singleCase = new MutableLiveData<>();


    /**
     * 获取案件列表
     */
    public void getCaseList() {
        List<Case> cases = caseModel.getCases();
        caseListData.postValue(cases);
    }


    /**
     * 删除案件
     *
     * @param id 案件 id
     */
    public void deleteCase(Long id) {
        caseModel.deleteCase(id);
    }

    /**
     * 查询单个案件
     *
     * @param id
     */
    public void getCase(Long id) {
        Case caxe = caseModel.getCase(id);
        singleCase.postValue(caxe);
    }


    public void updateCase(Case caxe) {
        caseModel.updateCase(caxe);
    }

    public void addCase(Case caxe) {
        caseModel.addCase(caxe);
    }


    public LiveData<List<Case>> getCaseListData() {
        return caseListData;
    }

    public MutableLiveData<Case> getSingleCase() {
        return singleCase;
    }
}
