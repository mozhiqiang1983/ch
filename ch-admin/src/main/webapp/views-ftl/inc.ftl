<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<#assign basePath = request.getScheme()+"://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()>
<#assign contextPath = request.getContextPath()>


<#assign cookieMap = {}>
<#list request.getCookies() as cookie>
	<#assign cookieMap = cookieMap + {cookie.name:cookie}/>
</#list>
<#assign easyuiTheme = "ui-cupertino">
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

<#-- 引入my97日期时间控件 -->
<script type="text/javascript" src="${contextPath}/jslib/My97DatePicker4.8Beta3/My97DatePicker/WdatePicker.js" charset="utf-8"></script>

<#-- 引入ueditor控件 -->
<script type="text/javascript" charset="utf-8">window.UEDITOR_HOME_URL = '${contextPath}/jslib/ueditor1_2_6_1-utf8-jsp/';</script>
<script src="${contextPath}/jslib/ueditor1_2_6_1-utf8-jsp/ueditor.config.js" type="text/javascript" charset="utf-8"></script>
<script src="${contextPath}/jslib/ueditor1_2_6_1-utf8-jsp/ueditor.all.min.js" type="text/javascript" charset="utf-8"></script>


<#-- 引入jQuery -->
<#-- IE9,chrome,firefox -->
<script src='${contextPath}/jslib/jquery-2.1.3.min.js' type='text/javascript' charset='utf-8'></script>
<#-- IE,IE7,IE8 -->
<script src='${contextPath}/jslib/jquery-1.11.2.min.js' type='text/javascript' charset='utf-8'></script>


<#-- 引入jquery扩展 -->
<script src="${contextPath}/jslib/cbExtJquery.js" type="text/javascript" charset="utf-8"></script>

<#-- 引入Highcharts -->
<script src="${contextPath}/jslib/Highcharts-3.0.6/js/highcharts.js" type="text/javascript" charset="utf-8"></script>
<script src="${contextPath}/jslib/Highcharts-3.0.6/js/modules/exporting.js" type="text/javascript" charset="utf-8"></script>
<#-- 引入Highcharts扩展 -->
<script src="${contextPath}/jslib/cbExtHighcharts.js" type="text/javascript" charset="utf-8"></script>

<#-- 引入plupload -->
<script type="text/javascript" src="${contextPath}/jslib/plupload/js/plupload.full.min.js"></script>
<script type="text/javascript" src="${contextPath}/jslib/plupload/js/i18n/zh_CN.js"></script>

<#-- 引入EasyUI -->
<link id="easyuiTheme" rel="stylesheet" href="${contextPath}/jslib/jquery-easyui-1.4.1/themes/${easyuiTheme}/easyui.css" type="text/css">
<script type="text/javascript" src="${contextPath}/jslib/jquery-easyui-1.4.1/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${contextPath}/jslib/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>

<#-- 引入EasyUI Portal插件 -->
<link rel="stylesheet" href="${contextPath}/jslib/jquery-easyui-portal/portal.css" type="text/css">
<script type="text/javascript" src="${contextPath}/jslib/jquery-easyui-portal/jquery.portal.js" charset="utf-8"></script>
<#-- 引入easyui扩展 -->
<script src="${contextPath}/jslib/cbExtEasyUI.js" type="text/javascript" charset="utf-8"></script>

<#-- 引入扩展图标 -->
<link rel="stylesheet" href="${contextPath}/style/cbExtIcon.css" type="text/css">

<#-- 引入自定义样式 -->
<link rel="stylesheet" href="${contextPath}/style/cbExtCss.css" type="text/css">

<#-- 引入javascript扩展 -->
<script src="${contextPath}/jslib/cbExtJavascript.js" type="text/javascript" charset="utf-8"></script>
