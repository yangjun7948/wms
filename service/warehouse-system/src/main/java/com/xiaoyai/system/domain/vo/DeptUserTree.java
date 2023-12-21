package com.xiaoyai.system.domain.vo;

import com.xiaoyai.common.core.domain.TreeNode;
import lombok.Data;

import java.util.List;

/**
 * @author junyang
 * @date 2023/10/11
 */
@Data
public class DeptUserTree extends TreeNode {
    //类型
    private String type;
    //是否是人员
    private Boolean userFlag;
}
