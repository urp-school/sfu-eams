<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table id="stdBar" width="100%"></table>
  <#assign courseTakes = courseTakes?sort_by(["student","code"])>
  <#include "../courseTakeSearch/courseTakeList.ftl"/>
  <script>
     var bar=new ToolBar('stdBar','排课冲突学生名单(${courseTakes?size})',null,true,false);
     bar.setMessage('<@getMessage/>');
     bar.addItem("强制保存","save()");
     bar.addItem("发送消息","sendMessage()","send.gif");
     bar.addItem("<@msg.message key="action.print"/>","print()");
     bar.addBack();
     function save(){
       var form =document.queryForm;
       form.action="manualArrange.do?method=saveActivities&detectCollision=0";
       if(confirm("确认强制保存操作吗?")){
         form['params'].value="${RequestParameters['params']?default()}";
         form.submit();
       }
    }
    function sendMessage(){
       window.open("systemMessage.do?method=quickSend&receiptorIds=<#list courseTakes as take>${take.student.code}<#if take_has_next>,</#if></#list>&who=std");
    }
</script>
 </body>
<#include "/templates/foot.ftl"/>

