<script type="text/javascript" charset="utf-8">
	var lockWindowFun = function() {
		$.post(ch.contextPath + '/auth/user/logout_doNotNeedSessionAndSecurity', function(result) {
			$('#loginDialog').dialog('open');
		}, 'json');
	};
	var logoutFun = function() {
		$.post(ch.contextPath + '/auth/user/logout_doNotNeedSessionAndSecurity', function(result) {
			location.replace(ch.contextPath);
		}, 'json');
	};
	var showMyInfoFun = function() {
		var dialog = parent.ch.modalDialog({
			title : '我的信息',
			url : ch.contextPath + '/userInfo.html'
		});
	};
</script>
<div id="sessionInfoDiv" style="position: absolute; right: 10px; top: 5px;">
	欢迎您，${loginUser.loginname}
</div>
<div style="position: absolute; right: 0px; bottom: 0px;">
	<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_pfMenu',iconCls:'ext-icon-rainbow'">更换皮肤</a> <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_kzmbMenu',iconCls:'ext-icon-cog'">控制面板</a> <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_zxMenu',iconCls:'ext-icon-disconnect'">注销</a>
</div>
<div id="layout_north_pfMenu" style="width: 120px; display: none;">
	<div onclick="ch.changeTheme('default');" title="default">default</div>
	<div onclick="ch.changeTheme('gray');" title="gray">gray</div>
	<div onclick="ch.changeTheme('metro');" title="metro">metro</div>
	<div onclick="ch.changeTheme('bootstrap');" title="bootstrap">bootstrap</div>
	<div onclick="ch.changeTheme('black');" title="black">black</div>
	<div class="menu-sep"></div>
	<div onclick="ch.changeTheme('metro-blue');" title="metro-blue">metro-blue</div>
	<div onclick="ch.changeTheme('metro-gray');" title="metro-gray">metro-gray</div>
	<div onclick="ch.changeTheme('metro-green');" title="metro-green">metro-green</div>
	<div onclick="ch.changeTheme('metro-orange');" title="metro-orange">metro-orange</div>
	<div onclick="ch.changeTheme('metro-red');" title="metro-red">metro-red</div>
	<div class="menu-sep"></div>
	<div onclick="ch.changeTheme('ui-cupertino');" title="ui-cupertino">ui-cupertino</div>
	<div onclick="ch.changeTheme('ui-dark-hive');" title="ui-dark-hive">ui-dark-hive</div>
	<div onclick="ch.changeTheme('ui-pepper-grinder');" title="ui-pepper-grinder">ui-pepper-grinder</div>
	<div onclick="ch.changeTheme('ui-sunny');" title="ui-sunny">ui-sunny</div>
</div>
<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
	<div data-options="iconCls:'ext-icon-user_edit'" onclick="$('#passwordDialog').dialog('open');">修改密码</div>
	<#--
	<div class="menu-sep"></div>
	<div data-options="iconCls:'ext-icon-user'" onclick="showMyInfoFun();">我的信息</div>
	-->
</div>
<div id="layout_north_zxMenu" style="width: 100px; display: none;">
	<div data-options="iconCls:'ext-icon-lock'" onclick="lockWindowFun();">锁定窗口</div>
	<div class="menu-sep"></div>
	<div data-options="iconCls:'ext-icon-door_out'" onclick="logoutFun();">退出系统</div>
</div>