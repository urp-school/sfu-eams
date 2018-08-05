<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
<#include "../signUpSetting/settingInfoList.ftl"/> 
<#if signUpSettings?size==0>
<table id="bar" width="100%"></table>
 <script>
    var bar = new ToolBar("bar",'管理员还没有进行报名设置',null,true,true);
    bar.addBack("<@msg.message key="action.back"/>");
 </script>
<#else>
<#list signUpSettings as setting>
 <#if setting.canSignUp(std,GPA)>
 <@displaySetting setting/>
     <script>
        var bar = new ToolBar("bar${setting.id}",'报名信息',null,true,true);
        <#if setting.isOpen&&setting.isDateSuitable()>
        bar.addItem("进入报名","signUp(${setting.id})");
        </#if>
        bar.addBack("<@msg.message key="action.back"/>");
     </script>
     <br>
 </#if>
</#list>
</#if>
<form name="actionForm" method="post" action="" onsubmit="return false;">
   <input type="hidden" name="signUpStd.GPA" value="${GPA}"/>
</form>
<p>
  你的平均绩点是${GPA},如果界面上没有可以进行报名的信息，说明绩点不够或者你不在允许的报名对象范围内。
</p>
<script>
  var action="speciality2ndSignUp.do";
  var form=document.actionForm;
  function signUp(id){
     form.action=action+"?method=signUp&signUpSettingId="+id;
     form.submit();
  }
</script>
</body>
<#include "/templates/foot.ftl"/> 
