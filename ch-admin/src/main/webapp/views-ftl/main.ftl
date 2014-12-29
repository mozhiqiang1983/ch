
<#assign sessionInfo = Session["sessionInfo"]>
<!DOCTYPE html>
<html>
<head>
<title></title>
<#include "base_inc.ftl">
<script type="text/javascript">
	var mainMenu;
	var mainTabs;
	var mainPage = "首页";
	
	$(function() {
		
		tabClose();
		tabCloseEven();

		
		$("#ul_guid").find("li").click(function(){	
			$(this).addClass("hover").siblings("li").removeClass("hover");
		})
		
		var loginFun = function() {
			if ($('#loginDialog form').form('validate')) {
				$('#loginBtn').linkbutton('disable');
				$.post(ch.contextPath + '/auth/user/login_doNotNeedSessionAndSecurity', $('#loginDialog form').serialize(), function(result) {
					if (result.success) {
						$('#loginDialog').dialog('close');
					} else {
						$.messager.alert('提示', result.msg, 'error', function() {
							$('#loginDialog form :input:eq(1)').focus();
						});
					}
					$('#loginBtn').linkbutton('enable');
				}, 'json');
			}
		};
		$('#loginDialog').show().dialog({
			modal : true,
			closable : false,
			iconCls : 'ext-icon-lock_open',
			buttons : [ {
				id : 'loginBtn',
				text : '登录',
				handler : function() {
					loginFun();
				}
			} ],
			onOpen : function() {
				$('#loginDialog form :input[name="data.pwd"]').val('');
				$('form :input').keyup(function(event) {
					if (event.keyCode == 13) {
						loginFun();
					}
				});
			}
		}).dialog('close');

		$('#passwordDialog').show().dialog({
			modal : true,
			closable : true,
			iconCls : 'ext-icon-lock_edit',
			buttons : [ {
				text : '修改',
				handler : function() {
					if ($('#passwordDialog form').form('validate')) {
						$.post(ch.contextPath + '/auth/user/updateCurrentPwd_doNotNeedSecurity', {
							'data.pwd' : $('#pwd').val()
						}, function(result) {
							if (result.success) {
								$.messager.alert('提示', '密码修改成功！', 'info');
								$('#passwordDialog').dialog('close');
							}
						}, 'json');
					}
				}
			} ],
			onOpen : function() {
				$('#passwordDialog form :input').val('');
			}
		}).dialog('close');
		
		 
		$.ajax({  
		    url:ch.contextPath + "/auth/resource/getMainMenu_doNotNeedSecurity",  
		    type:'post',  
		    async: true,  
		    dataType:'json',  
		    success:function(data){  
		        $.each(data, function(i, item){
		        	$("#mainMenu").accordion('add', {
		        		id:"tree_"+i,
	                    title: item.text,
	                    iconCls:item.iconCls
	                }); 
		            
		            $('#tree_'+i).html('<ul class="navlist" id="ctrltree_'+i+'" style="margin-top:2px;"></ul>');  
		            $('#ctrltree_'+i).tree({  
		            	
		            	data:eval(item.children),
		            	parentField : 'pid',
		                onClick : function(node){  
		                    
		                	if (node.attributes.url) {
		    					var src = ch.contextPath + node.attributes.url;
		    					if (!ch.startWith(node.attributes.url, '/')) {
		    						src = node.attributes.url;
		    					}
		    					if (node.attributes.target && node.attributes.target.length > 0) {
		    						window.open(src, node.attributes.target);
		    					} else {
		    						var tabs = $('#mainTabs');
		    						var opts = {
		    							title : node.text,
		    							closable : true,
		    							iconCls : node.iconCls,
		    							content : ch.formatString('<iframe src="{0}" allowTransparency="true" style="border:0;width:100%;height:98%;" frameBorder="0"></iframe>', src),
		    							border : false,
		    							fit : true
		    						};
		    						if (tabs.tabs('exists', opts.title)) {
		    							tabs.tabs('select', opts.title);
		    						} else {
		    							tabs.tabs('add', opts);
		    							tabClose();		    							
		    						}
		    					}
		    				} 
		                }
		                
		            });  
		            
		        });           
		    }  
		});  
		
		$('#mainLayout').layout('panel', 'center').panel({
			onResize : function(width, height) {
				ch.setIframeHeight('centerIframe', $('#mainLayout').layout('panel', 'center').panel('options').height - 5);
			}
		});
		
		mainTabs = $('#mainTabs').tabs({
			fit : true,
			border : false
	
		});
		
		

		
		function tabClose()
		{
		    /*双击关闭TAB选项卡*/
		    $(".tabs-inner").dblclick(function(){
		        var subtitle = $(this).children("span").text();
		        if(subtitle!=mainPage)
		        	$('#mainTabs').tabs('close',subtitle);

		    })

		    $(".tabs-inner").bind('contextmenu',function(e){
		        $('#tabsMenu').menu('show', {
		            left: e.pageX,
		            top: e.pageY,
		        });
		        var subtitle =$(this).children("span").text();
		        $('#tabsMenu').data("currtab",subtitle);
				//$('#mainTabs').tabs('select',subtitle);
		        return false;
		    });
		}
		//绑定右键菜单事件
		function tabCloseEven()
		{
			$('#tabsMenu-refresh').click(function(){
		        var panel = mainTabs.tabs('getSelected').panel('panel');
				var frame = panel.find('iframe');
				try {
					if (frame.length > 0) {
						for (var i = 0; i < frame.length; i++) {
							frame[i].contentWindow.document.write('');
							frame[i].contentWindow.close();
							frame[i].src = frame[i].src;
						}
						if (navigator.userAgent.indexOf("MSIE") > 0) {// IE特有回收内存方法
							try {
								CollectGarbage();
							} catch (e) {
							}
						}
					}
				} catch (e) {
				}

		    })
		    //关闭当前
		    $('#tabsMenu-tabclose').click(function(){
		        var currtab_title = $('#tabsMenu').data("currtab");
		        if(currtab_title!=mainPage)
		        	$('#mainTabs').tabs('close',currtab_title);
		        else
		        	parent.$.messager.alert('提示','首页不能关闭哦~~');
		        
		    })
		    //全部关闭
		    $('#tabsMenu-tabcloseall').click(function(){
		        $('.tabs-inner span').each(function(i,n){
		            var t = $(n).text();
		            if(t!=mainPage)
		            $('#mainTabs').tabs('close',t);
		        });    
		    });
		    //关闭除当前之外的TAB
		    $('#tabsMenu-tabcloseother').click(function(){
		        var currtab_title = $('#tabsMenu').data("currtab");
		        $('.tabs-inner span').each(function(i,n){
		            var t = $(n).text();
		            if(t!=currtab_title && t!=mainPage)
		                $('#mainTabs').tabs('close',t);
		        });    
		    });
		    //关闭当前右侧的TAB
		    $('#tabsMenu-tabcloseright').click(function(){
		        var nextall = $('.tabs-selected').nextAll();
		        if(nextall.length==0){
		            //msgShow('系统提示','后边没有啦~~','error');
		            parent.$.messager.alert('提示','后边没有啦~~');
		            return false;
		        }
		        nextall.each(function(i,n){
		            var t=$('a:eq(0) span',$(n)).text();
		            if(t!=mainPage)
		            	$('#mainTabs').tabs('close',t);
		        });
		        return false;
		    });
		    //关闭当前左侧的TAB
		    $('#tabsMenu-tabcloseleft').click(function(){
		        var prevall = $('.tabs-selected').prevAll();
		        if(prevall.length==0){
		            parent.$.messager.alert('提示','到头了，前边没有啦~~');
		            return false;
		        }
		        prevall.each(function(i,n){
		            var t=$('a:eq(0) span',$(n)).text();
		            if(t!=mainPage)
		            	$('#mainTabs').tabs('close',t);
		        });
		        return false;
		    });
		    //退出
		    $('#tabsMenu-exit').click(function(){
		        
		    	$('#tabsMenu').menu('hide');
		        return false;
		    });
		    
		}

	});
	
	
</script>
</head>
<body id="mainLayout" class="easyui-layout">
	<div data-options="region:'north',href:'${contextPath}/page/north'" style="height: 70px; overflow: hidden;" class="logo"></div>
	
	
	<div class="easyui-accordion" border="false" id="mainMenu" data-options="region:'west',href:'',split:true" title="导航" style="width:200px;height:400px;">
		
	</div>
	
	<div data-options="region:'center'" style="overflow: hidden;">
		<div id="mainTabs">
			<div title="首页">
				<iframe src="" allowTransparency="true" style="border: 0; width: 100%; height: 99%;" frameBorder="0"></iframe>
			</div>
		</div>
	</div>
	<div data-options="region:'south',href:'${contextPath}/page/south',border:false" style="height: 30px; overflow: hidden;"></div>
	
	<div id="tabsMenu" class="easyui-menu" style="width:150px;">
		<div id="tabsMenu-refresh">刷新</div>
		<div class="menu-sep"></div>
        <div id="tabsMenu-tabclose">关闭</div>
        <div id="tabsMenu-tabcloseall">全部关闭</div>
        <div id="tabsMenu-tabcloseother">除此之外全部关闭</div>
        <div class="menu-sep"></div>
        <div id="tabsMenu-tabcloseright">当前页右侧全部关闭</div>
        <div id="tabsMenu-tabcloseleft">当前页左侧全部关闭</div>
        <div class="menu-sep"></div>
        <div id="tabsMenu-exit">退出</div>
	</div>

	<div id="loginDialog" title="解锁登录" style="display: none;">
		<form method="post" class="form" onsubmit="return false;">
			<table class="table">
				<tr>
					<th width="50">登录名</th>
					<td>${loginUser.getLoginname()}<input name="loginname" readonly="readonly" type="hidden" value="${sessionInfo.getUser().getLoginname()}" /></td>
				</tr>
				<tr>
					<th>密码</th>
					<td><input name="pwd" type="password" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
			</table>
		</form>
	</div>

	<div id="passwordDialog" title="修改密码" style="display: none;">
		<form method="post" class="form" onsubmit="return false;">
			<table class="table">
				<tr>
					<th>新密码</th>
					<td><input id="pwd" name="pwd" type="password" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<th>重复密码</th>
					<td><input type="password" class="easyui-validatebox" data-options="required:true,validType:'eqPwd[\'#pwd\']'" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>