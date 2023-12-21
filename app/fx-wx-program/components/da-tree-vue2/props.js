// @ts-nocheck
export default {
  /**
   * 树的数据
   */
  data: {
    type: Array,
    default: () => [],
  },
  /**
   * 主题色
   */
  themeColor: {
    type: String,
    default: '#007aff',
  },
  /**
   * 是否开启多选，默认单选
   */
  showCheckbox: {
    type: Boolean,
    default: false,
  },
  /**
   * 默认选中的节点，注意单选时为单个key，多选时为key的数组
   */
  defaultCheckedKeys: {
    type: [Array, String, Number],
    default: null,
  },
  /**
   * 选择框的位置，可选 left/right
   */
  checkboxPlacement: {
    type: String,
    default: 'left',
  },
  /**
   * 是否默认展开全部
   */
  defaultExpandAll: {
    type: Boolean,
    default: false,
  },
  /**
   * 默认展开的节点
   */
  defaultExpandedKeys: {
    type: Array,
    default: null,
  },
  /**
   * 是否自动展开到选中的节点，默认不展开
   */
  expandChecked: {
    type: Boolean,
    default: false,
  },
  /**
   * 子项缩进距离，默认40，单位rpx
   */
  indent: {
    type: Number,
    default: 40,
  },
  /**
   * (旧)字段对应内容，默认为 {label: 'label',key: 'key', children: 'children', disabled: 'disabled', append: 'append'}
   * 注意：1.5.0版本后不再兼容
   */
  field: {
    type: Object,
    default: null,
  },
  /**
   * 标签字段(新，拆分了)
   */
  labelField: {
    type: String,
    default: 'label',
  },
  /**
   * 值字段(新，拆分了)
   */
  valueField: {
    type: String,
    default: 'value',
  },
  /**
   * 下级字段(新，拆分了)
   */
  childrenField: {
    type: String,
    default: 'children',
  },
  /**
   * 禁用字段(新，拆分了)
   */
  disabledField: {
    type: String,
    default: 'disabled',
  },
  /**
   * 末级节点字段(新，拆分了)
   */
  leafField: {
    type: String,
    default: 'leaf',
  },
  /**
   * 副标签字段(新，拆分了)
   */
  appendField: {
    type: String,
    default: 'append',
  },
  /**
   * 排序字段(新，拆分了)
   */
  sortField: {
    type: String,
    default: 'sort',
  },
  isLeafFn: {
    type: Function,
    default: null,
  },
  /**
   * 是否显示单选图标，默认显示
   */
  showRadioIcon: {
    type: Boolean,
    default: true,
  },
  /**
   * 单选时只允许选中末级，默认可随意选中
   */
  onlyRadioLeaf: {
    type: Boolean,
    default: false,
  },
  /**
   * 多选时，是否执行父子不关联的任意勾选，默认父子关联
   */
  checkStrictly: {
    type: Boolean,
    default: false,
  },
  /**
   * 为 true 时，空的 children 数组会显示展开图标
   */
  loadMode: {
    type: Boolean,
    default: false,
  },
  /**
   * 异步加载接口
   */
  loadApi: {
    type: Function,
    default: null,
  },
  /**
   * 是否总在首次的时候加载一下内容，来比对是否一致
   */
  alwaysFirstLoad: {
    type: Boolean,
    default: false,
  },
  /**
   * 是否渲染(操作)禁用值
   */
  checkedDisabled: {
    type: Boolean,
    default: false,
  },
  /**
   * 是否返回已禁用的但已选中的key
   */
  packDisabledkey: {
    type: Boolean,
    default: true,
  },
}
