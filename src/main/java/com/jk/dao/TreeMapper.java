package com.jk.dao;

import com.jk.bean.Tree;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TreeMapper {
    @Select("select * from t_tree")
    List<Tree> findTreeList();

    void savePoi(List<Tree> trees);

    List<Tree> findTreeListByIds(String[] split);
}
