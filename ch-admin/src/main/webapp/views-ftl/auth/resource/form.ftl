
<!DOCTYPE html>
<html>
<head>
<title></title>
<#include "/base_inc.ftl">
<script type="text/javascript">
	var submitForm = function($dialog, $grid, $pjq, $mainMenu) {
		if ($('form').form('validate')) {
			var url;
			if ($(':input[name="id"]').val().length > 0) {
				url = ch.contextPath + '/auth/resource/update';
			} else {
				url = ch.contextPath + '/auth/resource/save';
			}
			$.post(url, ch.serializeObject($('form')), function(result) {
				if (result.success) {
					$grid.treegrid('reload');
					$dialog.dialog('destroy');
					$mainMenu.tree('reload');
				} else {
					$pjq.messager.alert('提示', result.msg, 'error');
				}
			}, 'json');
		}
	};
	var showIcons = function() {
		var dialog = parent.ch.modalDialog({
			title : '浏览小图标',
			url : ch.contextPath + '/style/icons.jsp',
			buttons : [ {
				text : '确定',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.selectIcon(dialog, $('#iconCls'));
				}
			} ]
		});
	};
	$(function() {
		if ($(':input[name="id"]').val().length > 0) {
			
			parent.$.messager.progress({
				text : '数据加载中....'
			});
			
			$.post(ch.contextPath + '/auth/resource/get', {
				id : $(':input[name="id"]').val(),
			}, function(result) {
				if (result.id != undefined) {
					$('form').form('load', {
						'id' : result.id,
						'name' : result.name,
						'url' : result.url,
						'resourcetype.id' : result.resourcetype.id,
						'description' : result.description,
						'resource.id' : result.resource ? result.resource.id : '',
						'iconCls' : result.iconCls,
						'seq' : result.seq,
						'target' : result.target
					});
					$('#iconCls').attr('class', result.iconCls);//设置背景图标
				}
				parent.$.messager.progress('close');
			}, 'json');
		}
	});
</script>
</head>
<body>
	<form method="post" class="form">
		<fieldset>
			<legend>资源基本信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>编号</th>
					<td><input name="id" value="${tempvar["id"]?if_exists}" readonly="readonly" /></td>
					<th>资源名称</th>
					<td><input name="name" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<th>资源路径</th>
					<td><input name="url" /></td>
					<th>资源类型</th>
					<td><select name="resourcetype.id" class="easyui-combobox" data-options="required:true,editable:false,valueField:'id',textField:'name',url:'${contextPath}/auth/resourcetype/combobox_doNotNeedSecurity',panelHeight:'auto'" style="width: 155px;"></select></td>
				</tr>
				<tr>
					<th>上级资源</th>
					<td><select id="resource_id" name="resource.id" class="easyui-combotree" data-options="editable:false,idField:'id',textField:'text',parentField:'pid',url:'${contextPath}/auth/resource/getMainMenu_doNotNeedSecurity'" style="width: 155px;"></select><img class="iconImg ext-icon-cross" onclick="$('#resource_id').combotree('clear');" title="清空" /></td>
					<th>资源图标</th>
					<td><input id="iconCls" name="iconCls" readonly="readonly" style="padding-left: 18px; width: 134px;" /><img class="iconImg ext-icon-zoom" onclick="showIcons();" title="浏览图标" />&nbsp;<img class="iconImg ext-icon-cross" onclick="$('#iconCls').val('');$('#iconCls').attr('class','');" title="清空" /></td>
				</tr>
				<tr>
					<th>顺序</th>
					<td><input name="seq" class="easyui-numberspinner" data-options="required:true,min:0,max:100000,editable:false" style="width: 155px;" value="100" /></td>
					<th>目标</th>
					<td><input name="target" /></td>
				</tr>
				<tr>
					<th>资源描述</th>
					<td><textarea name="description"></textarea></td>
					<th></th>
					<td></td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>