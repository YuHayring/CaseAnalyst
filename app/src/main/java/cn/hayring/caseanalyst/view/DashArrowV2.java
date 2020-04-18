package cn.hayring.caseanalyst.view;

import android.content.Context;

import cn.hayring.caseanalyst.bean.Person;
import cn.hayring.caseanalyst.bean.Relationship;

public class DashArrowV2 extends DashArrow {
    public DashArrowV2(Context context, Circle startView, Circle endView, int height, HVScrollView scroller) {
        super(context, startView, endView, height);
        this.scroller = scroller;
    }

    public Relationship<Person, Person> getRelationship() {
        return relationship;
    }

    public void setRelationship(Relationship<Person, Person> relationship) {
        this.relationship = relationship;
    }

    protected Relationship<Person, Person> relationship;

    protected HVScrollView scroller;


    @Override
    protected void calculate() {
        int[] location = new int[2];
        startView.getLocationInWindow(location);
        x1 = location[0] + startView.getWidth() / 2;
        y1 = location[1] - height + startView.getHeight() / 2;
        endView.getLocationInWindow(location);
        x2 = location[0] + endView.getWidth() / 2;
        y2 = location[1] - height + endView.getHeight() / 2;

        x1 += scroller.getScrollX();
        x2 += scroller.getScrollX();
        y1 += scroller.getScrollY();
        y2 += scroller.getScrollY();
    }


}
