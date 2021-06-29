package cn.hayring.caseanalyst.view.caselist;

import java.util.List;

import cn.hayring.caseanalyst.domain.Case;

public interface CaseRepository {

    List<Case> getCases();

    void deleteCase(Long id);

    Case getCase(Long id);

    long addCase();

    void updateCase(Case caxe);

}
