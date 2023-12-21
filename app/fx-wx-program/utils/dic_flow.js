export default {
	FLOW_OPTIONS_01: [{ //suspensionState 当处于审批界面时 值不为”1”时 操作权限为”批准，退回、转发” ，
		name: '批准'
	}, {
		name: '转发'
	}],
	FLOW_OPTIONS_02: [{ //suspensionState 当处于审批界面时 当值等于”1”且delegation=’PENDING’时 操作权限为”批准，退回、意见征询、转办、转发” ，
			name: '批准'
		}, {
			name: '退回'
		}, {
			name: '转办'
		},
		{
			name: '转发'
		}
	],
	FLOW_OPTIONS_04: [{ //当 submitAuthority 为true时
		name: '撤回'
	}, {
		name: '转发'
	}],
	FLOW_OPTIONS_05: [{
		name: '转发'
	}],
}