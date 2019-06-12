package com.jk.service;

import com.jk.bean.Tree;
import com.jk.dao.TreeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreeServiceImpl implements  TreeService {
    @Autowired
    private TreeMapper treeMapper;

    @Override
    public List<Tree> findTreeList() {
        return treeMapper.findTreeList();
    }

    @Override
    public void savePoi(List<Tree> trees) {
        treeMapper.savePoi(trees);
    }

    @Override
    public List<Tree> findTreeListByIds(String ids) {
        String[] split = ids.split(",");
        return  treeMapper.findTreeListByIds(split);
    }
}
