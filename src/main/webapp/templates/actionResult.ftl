<#include "/templates/head.ftl"/>
<body>
<table  width="100%" cellpadding="0" cellspacing="0">
    <tr>
      <td  class="infoTitle" style="height:22px;" >
       <img src="${static_base}/images/action/info.gif" align="top"/><B>
          <B>操作提示</B>
      </td>
    </tr>
    <tr>
      <td colspan="4" style="font-size:0px" >
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
      </td>
   </tr>
   <tr>
     <td id="errorTD"><font color="green">
      <@html.errors />
      <#if RequestParameters.messages?exists>
      <#list RequestParameters.messages?split(",") as message>
         <#if (message?length>2)><@bean.message key="${message}"/></#if>&nbsp;
      </#list>
      </#if>
      </font>
      </td>
   </tr>
  </table>
<body>
<script>
    if(null != self.name && "" != self.name){
		window.resizeTo(300,200);
		window.moveTo(300,200);
		setTimeout("window.close()",2000);
	}
</script>
<#include "/templates/foot.ftl"/>