<h1>仓储管理系统-WMS</h1>
<h4>基于SpringBoot+vue.js开发的仓储管理平台</h4>

## 简介

本系统是一个支持物品追踪溯源的仓储管理平台，主要模块包括`基础数据`,`入库管理`,`出库管理`,`调拨管理`,`要货管理`,`报表中心`,`流程中心`。
####  技术路线
采用前后端分离模式，基于  [RuoYi框架进行二次开发](https://gitee.com/y_project/RuoYi-Vue/)；小程序端代码使用[uniapp](https://uniapp.dcloud.net.cn//)开发，可转换成微信小程序，支付宝小程序，百度小程序等(扫码功能仅在微信小程序测试，其他小程序需自行处理)

## 快速体验

##### WEB端体验

演示地址: <https://wms.xiaoy-ai.com> （已停用，请自行本地运行）

体验账号: test

密码：123456

##### 微信小程序体验码

<img src="http://file.xiaoy-ai.com/wechat.png" width="200" height="200">

## 演示图

<table>
    <tr>
        <img src="http://file.xiaoy-ai.com/warehouse/1.png" />
    </tr>
    <tr>
        <img src="http://file.xiaoy-ai.com/warehouse/3.png" />
    </tr>  
    <tr>
        <img src="http://file.xiaoy-ai.com/warehouse/4.png" />
    </tr>  
    <tr>
        <img src="http://file.xiaoy-ai.com/warehouse/5.png" />
    </tr>
    <tr>
        <img src="http://file.xiaoy-ai.com/warehouse/6.png" />
    </tr>
    <tr>
       <img src="http://file.xiaoy-ai.com/warehouse/2.png" />
    </tr>
     <tr>
       <img src="http://file.xiaoy-ai.com/warehouse/7.png" />
    </tr>
</table>

## 系统介绍

##### 1. 基础数据

1.  门店信息：门店信息的增删改查
2.  物品信息：维护物料基础信息，支持设置最大库存，最小库存，质保时间，是否重点物资，物品图片等信息
3.  物料分类：支持按照层级结构维护物品的分类信息
4.  仓库信息：维护仓储基础信息，支持区分个人仓库和公司类型仓库
5.  供应商库：供应商基础信息维护
6.  客户信息：客户基础信息维护

##### 2. 入库管理

1. 入库单管理：支持入库单新增，打印。入库单支持审核，物品扫码入库等功能，物品入库逻辑如下：
<table>
<tr>
    <td><img src="http://file.xiaoy-ai.com/warehouse/扫码入库流程.png" /></td>
</tr>
</table>

##### 3. 出库管理
1. 出库单管理：支持出库单新增，打印。出库单支持审核，物品扫码出库等功能
##### 4. 调拨管理
1. 调拨单管理：创建物品调拨单据，出库仓库生成出库单，入库仓库生成入库单
##### 5. 要货管理
1. 要货单管理：个人可通过移动端生成要货申请，管理人员针对要货单据进行审核，审核通过生成出库单
##### 6. 报表查询
1. 库存查询：支持按照仓库，入库单价显示仓库物品库存，支持Excel导出
2. 物品溯源：支持按照溯源码，物品编码，名称查询物品溯源信息，追踪物品从入库，出库到使用的全流程记录
3. 库存盘点：物品盘点
##### 7. 流程中心
> 流程中心集成Activiti6.0工作流引擎，在次基础上进行二次开发，流程审批节点审批人支持按照角色、指定人员、部门矩阵、机构矩阵等多种方式。审批方式支持顺序会签，同步会签等多种方式。流程模板文件采用BPMN格式，系统集成流程模板制作功能

1. 我的待办：查询我的待办列表
2. 新建流程：新建审批流程
3. 已办流程: 查询我的已办流程
4. 我的请求：查询我的请求流程
5. 流程模板制作：流程模板制作，部署
##### 8. 系统管理
> 用户，部门，角色，权限等基础模块。支持多种数据权限设置

## 部署方式
1.  传统部署
> 前端使用Nginx等web服务器进行代理，后端服务使用java -jar warehouse-admin.jar 命令启动
2.  Docker部署
> 使用Dockerfile文件自行构建Docker镜像运行

## 联系作者
> 本项目可供个人学习，或有能力自主开发的企业或个人免费使用（须遵循开源协议）。如需协助本地化部署或其他使用过程中遇到的问题可联系作者反馈
#### 微信

<img width="200" height="300" src="http://file.xiaoy-ai.com/person.jpg" />

#### 邮箱

<yangjun7948@163.com>
