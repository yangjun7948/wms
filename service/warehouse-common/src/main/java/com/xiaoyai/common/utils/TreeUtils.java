package com.xiaoyai.common.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xiaoyai.common.core.domain.TreeEntity;
import com.xiaoyai.common.core.domain.TreeNode;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author junyang
 * @date 2023/9/7
 */
@UtilityClass
public class TreeUtils {
    /**
     * 两层循环实现建树
     *
     * @param treeNodes 传入的树节点列表
     * @return
     */
    public <T extends TreeEntity> List<T>  build(List<T> treeNodes, Object root) {

        List<T> trees = new ArrayList<>();

        for (T treeNode : treeNodes) {

            if (root.toString().equals(treeNode.getParentId().toString())) {
                trees.add(treeNode);
            }

            for (T it : treeNodes) {
                if (it.getParentId().toString().equals(treeNode.getTreeId().toString())) {
                    if (treeNode.getChildren() == null) {
                        treeNode.setChildren(new ArrayList<>());
                    }
                    treeNode.add(it);
                }
            }
        }
        return trees;
    }

    /**
     * 两层循环实现建树
     *
     * @param treeNodes 传入的树节点列表
     * @return
     */
    public <T extends TreeNode> List<T>  buildNode(List<T> treeNodes, Object root) {

        List<T> trees = new ArrayList<>();

        for (T treeNode : treeNodes) {

            if (root.toString().equals(treeNode.getParentId())) {
                trees.add(treeNode);
            }

            for (T it : treeNodes) {
                if (it.getParentId().equals(treeNode.getId())) {
                    if (treeNode.getChildren() == null) {
                        treeNode.setChildren(new ArrayList<>());
                    }
                    treeNode.add(it);
                }
            }
        }
        return trees;
    }
    /**
     * 构建树结构，给主干增加支杆
     *
     * @param parents 父节点
     * @param childs  子节点
     * @param <T>     类型
     * @return 树结构
     */
    public <T extends TreeNode> List<T> appendChildNode(List<T> parents, List<T> childs) {
        for (T parent : parents) {
            if(parent.getChildren()==null||parent.getChildren().size()==0){
                for (T child : childs) {
                    if(child.getParentId().equals(parent.getId())){
                        if(parent.getChildren()==null)
                            parent.setChildren(new ArrayList<>());
                        parent.getChildren().add(child);
                    }
                }
            }else{
                appendChildNode((List<T>) parent.getChildren(),childs);
                for (T child : childs) {
                    if(child.getParentId().equals(parent.getId())){
                        if(parent.getChildren()==null)
                            parent.setChildren(new ArrayList<>());
                        parent.getChildren().add(child);
                    }
                }
            }

        }
        return parents;
    }

//    private <T extends TreeNode> T find(T node,List<T> childs){
//        for (T child : childs) {
//            if(node.getChildren()!=null){
//                find()
//            }
//        }
//    }
    /**
     * 构建树结构，给主干增加支杆
     *
     * @param parents 父节点
     * @param childs  子节点
     * @param <T>     类型
     * @return 树结构
     */
    public <T extends TreeEntity> List<T> appendChild(List<T> parents, List<T> childs) {
        for (T child : childs) {
            for (T parent : parents) {
                if (child.getParentId()>=0 && parent.getParentId()>=0
                        && child.getParentId().toString().equals(parent.getTreeId().toString())) {
                    parent.add(child);
                    break;
                }
            }
        }
        return parents;
    }

    /**
     * 使用递归方法建树
     *
     * @param treeNodes
     * @return
     */
    public <T extends TreeNode> List<T> buildByRecursiveNode(List<T> treeNodes, Object root) {
        List<T> trees = new ArrayList<T>();
        for (T treeNode : treeNodes) {
            if (root.toString().equals(treeNode.getParentId())) {
                trees.add(findChildrenNode(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNodes
     * @return
     */
    public <T extends TreeNode> T findChildrenNode(T treeNode, List<T> treeNodes) {
        for (T it : treeNodes) {
            if (treeNode.getId().equals(it.getParentId())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.add(findChildrenNode(it, treeNodes));
            }
        }
        return treeNode;
    }
    /**
     * 使用递归方法建树
     *
     * @param treeNodes
     * @return
     */
    public <T extends TreeEntity> List<T> buildByRecursive(List<T> treeNodes, Object root) {
        List<T> trees = new ArrayList<T>();
        for (T treeNode : treeNodes) {
            if (root.toString().equals(treeNode.getParentId().toString())) {
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNodes
     * @return
     */
    public <T extends TreeEntity> T findChildren(T treeNode, List<T> treeNodes) {
        for (T it : treeNodes) {
            if (treeNode.getTreeId().toString().equals(it.getParentId().toString())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }


}
