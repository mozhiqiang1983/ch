<#if !Session["sessionInfo"]??>
	<#include "login.ftl">
<#else>
	<#include "main.ftl">
</#if>