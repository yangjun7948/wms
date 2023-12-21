import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: 路由配置项
 *
 * hidden: true                     // 当设置 true 的时候该路由不会再侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
 * alwaysShow: true                 // 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
 *                                  // 只有一个时，会将那个子路由当做根路由显示在侧边栏--如引导页面
 *                                  // 若你想不管路由下面的 children 声明的个数都显示你的根路由
 *                                  // 你可以设置 alwaysShow: true，这样它就会忽略之前定义的规则，一直显示根路由
 * redirect: noRedirect             // 当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
 * name:'router-name'               // 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
 * query: '{"id": 1, "name": "ry"}' // 访问路由的默认传递参数
 * roles: ['admin', 'common']       // 访问路由的角色权限
 * permissions: ['a:a:a', 'b:b:b']  // 访问路由的菜单权限
 * meta : {
    noCache: true                   // 如果设置为true，则不会被 <keep-alive> 缓存(默认 false)
    title: 'title'                  // 设置该路由在侧边栏和面包屑中展示的名字
    icon: 'svg-name'                // 设置该路由的图标，对应路径src/assets/icons/svg
    breadcrumb: false               // 如果设置为false，则不会在breadcrumb面包屑中显示
    activeMenu: '/system/user'      // 当路由设置了该属性，则会高亮相对应的侧边栏。
  }
 */

// 公共路由
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/error/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error/401'),
    hidden: true
  },
  {
    path: '',
    component: Layout,
    redirect: 'index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/index'),
        name: 'Index',
        meta: { title: '首页', icon: 'dashboard', affix: true }
      }
    ]
  },
  {
    path: '/user',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [
      {
        path: 'profile',
        component: () => import('@/views/system/user/profile/index'),
        name: 'Profile',
        meta: { title: '个人中心', icon: 'user' }
      }
    ]
  }
]

// 动态路由，基于用户权限动态去加载
export const dynamicRoutes = [
  {
    path: '/system/user-auth',
    component: Layout,
    hidden: true,
    permissions: ['system:user:edit'],
    children: [
      {
        path: 'role/:userId(\\d+)',
        component: () => import('@/views/system/user/authRole'),
        name: 'AuthRole',
        meta: { title: '分配角色', activeMenu: '/system/user' }
      }
    ]
  },
  {
    path: '/system/role-auth',
    component: Layout,
    hidden: true,
    permissions: ['system:role:edit'],
    children: [
      {
        path: 'user/:roleId(\\d+)',
        component: () => import('@/views/system/role/authUser'),
        name: 'AuthUser',
        meta: { title: '分配用户', activeMenu: '/system/role' }
      }
    ]
  },
  {
    path: '/system/user-auth',
    component: Layout,
    hidden: true,
    permissions: ['system:user:edit'],
    children: [
      {
        path: 'warehouse/:userId(\\d+)',
        component: () => import('@/views/system/user/authWarehouse'),
        name: 'AuthWarehouse',
        meta: { title: '分配仓库', activeMenu: '/system/user' }
      }
    ]
  },
  {
    path: '/system/dict-data',
    component: Layout,
    hidden: true,
    permissions: ['system:dict:list'],
    children: [
      {
        path: 'index/:dictId(\\d+)',
        component: () => import('@/views/system/dict/data'),
        name: 'Data',
        meta: { title: '字典数据', activeMenu: '/system/dict' }
      }
    ]
  },
  {
    path: '/monitor/job-log',
    component: Layout,
    hidden: true,
    permissions: ['monitor:job:list'],
    children: [
      {
        path: 'index',
        component: () => import('@/views/monitor/job/log'),
        name: 'JobLog',
        meta: { title: '调度日志', activeMenu: '/monitor/job' }
      }
    ]
  },
  {
    path: '/tool/gen-edit',
    component: Layout,
    hidden: true,
    permissions: ['tool:gen:edit'],
    children: [
      {
        path: 'index/:tableId(\\d+)',
        component: () => import('@/views/tool/gen/editTable'),
        name: 'GenEdit',
        meta: { title: '修改生成配置', activeMenu: '/tool/gen' }
      }
    ]
  },
  {
    path: '/in/inreceipt-edit',
    component: Layout,
    hidden: true,
    permissions: ['warehouse:inreceipt:add'],
    children: [
      {
        path: 'edit/:inreceiptId(\\d+)',
        component: () => import('@/views/warehouse/inreceipt/edit'),
        name: 'InReceiptEdit',
        meta: { title: '修改入库单', activeMenu: '/in/inreceipt' }
      }
    ]
  },
  {
    path: '/in/inreceipt-view',
    component: Layout,
    hidden: true,
    permissions: ['warehouse:inreceipt:query'],
    children: [
      {
        path: 'view/:inreceiptId(\\d+)',
        component: () => import('@/views/warehouse/inreceipt/detail'),
        name: 'InReceiptView',
        meta: { title: '入库单详情', activeMenu: '/in/inreceipt' }
      }
    ]
  },
  {
    path: '/out/outreceipt-edit',
    component: Layout,
    hidden: true,
    permissions: ['warehouse:outreceipt:add'],
    children: [
      {
        path: 'edit/:outreceiptId(\\d+)',
        component: () => import('@/views/warehouse/outreceipt/edit'),
        name: 'OutReceiptEdit',
        meta: { title: '修改出库单', activeMenu: '/out/outreceipt' }
      }
    ]
  },
  {
    path: '/out/outreceipt-view',
    component: Layout,
    hidden: true,
    permissions: ['warehouse:outreceipt:query'],
    children: [
      {
        path: 'view/:outreceiptId(\\d+)',
        component: () => import('@/views/warehouse/outreceipt/detail'),
        name: 'OutReceiptView',
        meta: { title: '出库单详情', activeMenu: '/out/outreceipt' }
      }
    ]
  },
  {
    path: '/transfer/transfer-edit',
    component: Layout,
    hidden: true,
    permissions: ['warehouse:transfer:add'],
    children: [
      {
        path: 'edit/:transferId(\\d+)',
        component: () => import('@/views/warehouse/transfer/edit'),
        name: 'TransferEdit',
        meta: { title: '修改调拨单', activeMenu: '/transfer/transfer' }
      }
    ]
  },
  {
    path: '/transfer/transfer-view',
    component: Layout,
    hidden: true,
    permissions: ['warehouse:transfer:query'],
    children: [
      {
        path: 'view/:transferId(\\d+)',
        component: () => import('@/views/warehouse/transfer/detail'),
        name: 'TransferView',
        meta: { title: '调拨单详情', activeMenu: '/transfer/transfer' }
      }
    ]
  },
  {
    path: '/apply/apply-edit',
    component: Layout,
    hidden: true,
    permissions: ['warehouse:apply:add'],
    children: [
      {
        path: 'edit/:applyId(\\d+)',
        component: () => import('@/views/warehouse/apply/edit'),
        name: 'ApplyEdit',
        meta: { title: '生成出库单', activeMenu: '/apply/apply' }
      }
    ]
  },
  {
    path: '/apply/apply-view',
    component: Layout,
    hidden: true,
    permissions: ['warehouse:apply:query'],
    children: [
      {
        path: 'view/:applyId(\\d+)',
        component: () => import('@/views/warehouse/apply/detail'),
        name: 'ApplyView',
        meta: { title: '要货申请详情', activeMenu: '/apply/apply' }
      }
    ]
  },
  {
    path: '/report/code-view',
    component: Layout,
    hidden: true,
    permissions: ['warehouse:report:code'],
    children: [
      {
        path: 'view/:traceId',
        component: () => import('@/views/warehouse/report/code/detail'),
        name: 'CodeView',
        meta: { title: '物品溯源详情', activeMenu: '/report/code' }
      }
    ]
  },
  {
    path: '/report/check-edit',
    component: Layout,
    hidden: true,
    permissions: ['warehouse:report:check'],
    children: [
      {
        path: 'edit/:receiptId',
        component: () => import('@/views/warehouse/report/check/edit'),
        name: 'CheckEdit',
        meta: { title: '修改库存盘点单', activeMenu: '/report/check' }
      }
    ]
  },
  {
    path: '/report/check-view',
    component: Layout,
    hidden: true,
    permissions: ['warehouse:report:check'],
    children: [
      {
        path: 'view/:receiptId',
        component: () => import('@/views/warehouse/report/check/detail'),
        name: 'CheckView',
        meta: { title: '查看库存盘点单', activeMenu: '/report/check' }
      }
    ]
  },
  {
    path: '/flow/new-add',
    component: Layout,
    hidden: true,
    permissions: ['process:new:add'],
    children: [
      {
        path: 'add',
        component: () => import('@/views/process/new/create/index'),
        name: 'ProcessNew',
        meta: { title: '流程新建', activeMenu: '/flow/new' }
      }
    ]
  },
  {
    path: '/flow/new-view',
    component: Layout,
    hidden: true,
    permissions: ['process:new:index'],
    children: [
      {
        path: 'view',
        component: () => import('@/views/process/info/index'),
        name: 'ProcessNew',
        meta: { title: '流程编辑', activeMenu: '/flow/todo' }
      }
    ]
  },
  {
    path: '/flow/template-edit',
    component: Layout,
    hidden: true,
    permissions: ['process:template:index'],
    children: [
      {
        path: 'edit',
        component: () => import('@/views/process/template/edit'),
        name: 'ProcessTemplateEdit',
        meta: { title: '模板编辑', activeMenu: '/flow/template' }
      }
    ]
  },
  {
    path: '/flow/template-add',
    component: Layout,
    hidden: true,
    permissions: ['process:template:index'],
    children: [
      {
        path: 'add',
        component: () => import('@/views/process/template/create'),
        name: 'ProcessTemplateAdd',
        meta: { title: '模板新建', activeMenu: '/flow/template' }
      }
    ]
  },
]

// 防止连续点击多次路由报错
let routerPush = Router.prototype.push;
Router.prototype.push = function push(location) {
  return routerPush.call(this, location).catch(err => err)
}

export default new Router({
  mode: 'history', // 去掉url中的#
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})
