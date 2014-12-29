<!DOCTYPE html>
<html>
<head>
<title>系统登录</title>
<#include "base_inc.ftl">
<script type="text/javascript">
	$(function() {

		var loginFun = function() {
			var loginTabs = $('#loginTabs').tabs('getSelected');//当前选中的tab
			var $form = loginTabs.find('form');//选中的tab里面的form
			if ($form.length == 1 && $form.form('validate')) {
				$('#loginBtn').linkbutton('disable');
				$.post(ch.contextPath + '/auth/user/login_doNotNeedSessionAndSecurity', $form.serialize(), function(result) {
					if (result.success) {
						location.replace(ch.contextPath);
					} else {
						$.messager.alert('提示', result.msg, 'error', function() {
							$('#loginBtn').linkbutton('enable');
						});
					}
				}, 'json');
			}
		};

		$('#loginDialog').show().dialog({
			modal : false,
			closable : false,
			iconCls : 'ext-icon-lock_open',
			buttons : [ <#--{
				text : '注册',
				handler : function() {
					location.replace(ch.contextPath + '/reg.html');
				}
			},--> {
				id : 'loginBtn',
				text : '登录',
				handler : function() {
					loginFun();
				}
			} ],
			onOpen : function() {
				$('form :input:first').focus();
				$('form :input').keyup(function(event) {
					if (event.keyCode == 13) {
						loginFun();
					}
				});
			}
		});

	});
</script>
</head>
<body>
	
	<#--
	<a href="${contextPath}/init.html">初始化数据库(${contextPath}/init.html)</a>
	-->

	<div id="loginDialog" title="系统登录" style="display: none; width: 320px; height: 180px; overflow: hidden;">
		<div id="loginTabs" class="easyui-tabs" data-options="fit:true,border:false">
			<div title="用户输入模式" style="overflow: hidden; padding: 10px;">
				<form method="post" class="form">
					<table class="table" style="width: 100%; height: 100%;">
						<tr>
							<th width="50">登录名</th>
							<td><input name="loginname" class="easyui-validatebox" data-options="required:true" value="admin" style="width: 210px;" /></td>
						</tr>
						<tr>
							<th>密码</th>
							<td><input name="pwd" type="password" class="easyui-validatebox" data-options="required:true" value="123456" style="width: 210px;" /></td>
						</tr>
					</table>
				</form>
			</div>
			
		</div>
	</div>
</body>
</html>