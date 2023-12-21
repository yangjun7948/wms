package com.xiaoyai.common.core.domain;

import lombok.Data;

import java.util.List;

/**
 * @author junyang
 * @date 2023/10/11
 */
@Data
public class TreeNode {
    private String id;
    private String parentId;
    private String name;
    private List<TreeNode> children;
    public void add(TreeNode node) {
        children.add(node);
    }
}
