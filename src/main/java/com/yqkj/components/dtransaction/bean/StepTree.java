package com.yqkj.components.dtransaction.bean;

import com.yqkj.components.dtransaction.pojo.DTXStep;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StepTree {

    private DTXStep parent;

    private List<StepTree> childs = new ArrayList<>();

    public StepTree(DTXStep parent) {
        this.parent = parent;
    }

    public DTXStep getParent() {
        return parent;
    }

    public void setParent(DTXStep parent) {
        this.parent = parent;
    }

    public List<StepTree> getChilds() {
        return childs;
    }

    public void setChilds(List<StepTree> childs) {
        this.childs = childs;
    }
}
