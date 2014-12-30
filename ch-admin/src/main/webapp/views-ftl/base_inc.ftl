<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<#assign basePath = request.getScheme()+"://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()>
<#assign contextPath = request.getContextPath()>


<#assign cookieMap = {}>
<#list request.getCookies() as cookie>
	<#assign cookieMap = cookieMap + {cookie.name:cookie}/>
</#list>
<#assign easyuiTheme = "bootstrap">
<#if cookieMap["easyuiTheme"]??>
	<#assign cookie = cookieMap["easyuiTheme"]>
	<#assign easyuiTheme = cookie.value>
</#if>

<script type="text/javascript">
var ch = ch || {};
ch.contextPath = '${contextPath}';
ch.basePath = '${basePath}';
ch.pixel_0 = '${contextPath}/style/images/pixel_0.gif';//0像素的背景，一般用于占位
</script>

<#-- 引入jQuery -->
<#-- IE9,chrome,firefox 
<script src='${contextPath}/jslib/jquery-2.1.3.min.js' type='text/javascript' charset='utf-8'></script>
-->
<#-- IE,IE7,IE8 -->
<script src='${contextPath}/jslib/jquery-1.11.2.min.js' type='text/javascript' charset='utf-8'></script>


<#-- 引入jquery扩展 -->
<script src="${contextPath}/jslib/chExtJquery.js" type="text/javascript" charset="utf-8"></script>

<#-- 引入EasyUI -->
<link id="easyuiTheme" rel="stylesheet" href="${contextPath}/jslib/jquery-easyui-1.4.1/themes/${easyuiTheme}/easyui.css" type="text/css">
<script type="text/javascript" src="${contextPath}/jslib/jquery-easyui-1.4.1/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${contextPath}/jslib/jquery-easyui-1.4.1/plugins/jquery.validatebox.fixed.js" charset="utf-8"></script>
<script type="text/javascript" src="${contextPath}/jslib/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>



<#-- 引入easyui扩展 -->
<script src="${contextPath}/jslib/chExtEasyUI.js" type="text/javascript" charset="utf-8"></script>

<#-- 引入扩展图标 -->
<link rel="stylesheet" href="${contextPath}/style/chExtIcon.css" type="text/css">

<#-- 引入自定义样式 -->
<link rel="stylesheet" href="${contextPath}/style/chExtCss.css" type="text/css">

<#-- 引入javascript扩展 -->
<script src="${contextPath}/jslib/chExtJavascript.js" type="text/javascript" charset="utf-8"></script>
