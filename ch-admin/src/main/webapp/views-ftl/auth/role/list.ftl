
<!DOCTYPE html>
<html>
<head>
<title></title>
<#include "/base_inc.ftl">
<script type="text/javascript">
	var grid;
	var addFun = function() {
		var dialog = parent.ch.modalDialog({
			title : '添加角色信息',
			url : ch.contextPath + '/page/auth/role/form',
			buttons : [ {
				text : '添加',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	var showFun = function(id) {
		var dialog = parent.ch.modalDialog({
			title : '查看角色信息',
			url : ch.contextPath + '/page/auth/role/form?id=' + id
		});
	};
	var editFun = function(id) {
		var dialog = parent.ch.modalDialog({
			title : '编辑角色信息',
			url : ch.contextPath + '/page/auth/role/form?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	var removeFun = function(id) {
		parent.$.messager.confirm('询问', '您确定要删除此记录？', function(r) {
			if (r) {
				$.post(ch.contextPath + '/auth/role/delete', {
					id : id
				}, function() {
					grid.datagrid('reload');
				}, 'json');
			}
		});
	};
	var grantFun = function(id) {
		var dialog = parent.ch.modalDialog({
			title : '角色授权',
			url : ch.contextPath + '/page/auth/role/grant?id='+id,
			buttons : [ {
				text : '授权',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	$(function() {
		grid = $('#grid').datagrid({
			title : '',
			url : ch.contextPath + '/auth/role/grid',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'seq',
			sortOrder : 'asc',
			frozenColumns : [ [ {
				width : '100',
				title : '角色名称',
				field : 'name',
				sortable : true
			} ] ],
			columns : [ [ {
				width : '150',
				title : '创建时间',
				field : 'createdatetime',
				sortable : true
			}, {
				width : '150',
				title : '修改时间',
				field : 'updatedatetime',
				sortable : true
			}, {
				width : '300',
				title : '资源描述',
				field : 'description'
			}, {
				width : '60',
				title : '排序',
				field : 'seq',
				hidden : true,
				sortable : true
			}, {
				title : '操作',
				field : 'action',
				width : '80',
				formatter : function(value, row) {
					var str = '';
					<#if securityUtil.havePermission(loginUser,"/auth/role/get")>
						str += ch.formatString('<img class="iconImg ext-icon-note" title="查看" onclick="showFun(\'{0}\');"/>', row.id);
					</#if>
					<#if securityUtil.havePermission(loginUser,"/auth/role/update")>
						str += ch.formatString('<img class="iconImg ext-icon-note_edit" title="编辑" onclick="editFun(\'{0}\');"/>', row.id);
					</#if>
					<#if securityUtil.havePermission(loginUser,"/auth/role/grant")>
						str += ch.formatString('<img class="iconImg ext-icon-key" title="授权" onclick="grantFun(\'{0}\');"/>', row.id);
					</#if>
					<#if securityUtil.havePermission(loginUser,"/auth/role/delete")>
						str += ch.formatString('<img class="iconImg ext-icon-note_delete" title="删除" onclick="removeFun(\'{0}\');"/>', row.id);
					</#if>
					return str;
				}
			} ] ],
			toolbar : '#toolbar',
			onBeforeLoad : function(param) {
				parent.$.messager.progress({
					text : '数据加载中....'
				});
			},
			onLoadSuccess : function(data) {
				$('.iconImg').attr('src', ch.pixel_0);
				parent.$.messager.progress('close');
			}
		});
	});
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="toolbar" style="display: none;">
		<table>
			<tr>
				<#if securityUtil.havePermission(loginUser,"/auth/role/save")>
				<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="addFun();">添加</a></td>
				</#if>
				<td><div class="datagrid-btn-separator"></div></td>
				<td><input id="searchBox" class="easyui-searchbox" style="width: 150px" data-options="searcher:function(value,name){grid.datagrid('load',{'QUERY_t#name_S_LK':value});},prompt:'搜索角色名称'"></input></td>
				<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom_out',plain:true" onclick="$('#searchBox').searchbox('setValue','');grid.datagrid('load',{});">清空查询</a></td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',fit:true,border:false">
		<table id="grid" data-options="fit:true,border:false"></table>
	</div>
</body>
</html>