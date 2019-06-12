package com.jk.service;

import com.jk.bean.Tree;

import java.util.List;

public interface TreeService {
    List<Tree> findTreeList();

    void savePoi(List<Tree> trees);

    List<Tree> findTreeListByIds(String ids);
}
