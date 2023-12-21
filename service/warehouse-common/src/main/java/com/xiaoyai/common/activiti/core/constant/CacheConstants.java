package com.xiaoyai.common.activiti.core.constant;

/**
 * @author tony
 * @date 2019-04-28
 * <p>
 * 缓存的key 常量
 */
public interface CacheConstants {

    /**
     * 用户信息缓存
     */
    String USER_DETAILS = "user_details";


    /**
     * oauth 客户端信息
     */
    String CLIENT_DETAILS_KEY = "zykj_oauth:client:details";

    /**
     * 菜单信息缓存
     */
    String MENU_DETAILS = "menu_details";

    /**
     * spring boot admin 事件key
     */
    String EVENT_KEY = "event_key";

    /**
     * 路由存放
     */
    String ROUTE_KEY = "gateway_route_key";

    /**
     * 字典缓存
     */
    String DICT_DETAILS = "dict_details";

    /**
     * 参数缓存
     */
    String PARAMS_DETAILS = "params_details";

    /**
     * 角色缓存
     */
    String ROLE_DETAILS = "role_details";

    /**
     * 角色缓存
     */
    String ROLE_EQUIP = "role_equip";

    /**
     * 小程序菜单缓存
     */
    String WECHART_MENU = "wechart_menu";

    /**
     * crm菜单缓存
     */
    String CRM_MENU = "crm_menu";
    /**
     * app菜单缓存
     */
    String APP_MENU = "app_menu";
    /**
     * 用户登录次数缓存
     */
    String LOGIN_COUNT = "zykj_login_count:loginname-";
    /**
     * 用户机构权限数据
     */
    String ORG_PERMISSIONG = "org_permission";

}
