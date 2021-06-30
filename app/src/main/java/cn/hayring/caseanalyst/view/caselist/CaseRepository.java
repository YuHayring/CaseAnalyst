package cn.hayring.caseanalyst.view.caselist;

import java.util.List;

import cn.hayring.caseanalyst.domain.Case;
import cn.hayring.caseanalyst.viewmodel.AsyncCallBack;

public interface CaseRepository {

    void getCases(AsyncCallBack<List<Case>> callBack);

    void deleteCase(Long id, AsyncCallBack<Boolean> callBack);

    void getCase(Long id, AsyncCallBack<Case> callBack);

    void addCase(AsyncCallBack<Long> callBack);

    void updateCase(Case caxe, AsyncCallBack<Boolean> callBack);

}
