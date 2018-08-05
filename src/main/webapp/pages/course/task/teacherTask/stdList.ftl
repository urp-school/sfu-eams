<#include "/templates/head.ftl"/>
<body  LEFTMARGIN="0" TOPMARGIN="0">
 <table id="stdListBar"></table>
 <script>
   var bar = new ToolBar("stdListBar","<@i18nName task.course/> 学生名单(${task.teachClass.courseTakes?size}人)",null,true,true);
   bar.addItem("发送消息","sendMessage()","send.gif");
   bar.addBack("<@msg.message key="action.back"/>");
   function sendMessage(){
     var stdCodes = getSelectIds("stdCode");
     if(""==stdCodes){alert("请选择一个或多个");return;}
     //alert(stdIds);
     window.open("systemMessage.do?method=quickSend&receiptorIds="+stdCodes+"&who=std");
   }
 </script>
 <table width="100%" border="0" class="listTable" >
    <tr align="center" class="darkColumn">
      <td align="center" width="2%">
        <input type="checkbox"  onClick="toggleCheckBox(document.getElementsByName('stdId'),event);"/>
      </td>
      <td width="8%"><@msg.message key="attr.stdNo"/></td>
      <td width="7%"><@msg.message key="attr.personName"/></td>
      <td width="8%"><@msg.message key="entity.studentType"/></td>
      <td width="4%"><@msg.message key="attr.credit"/></td>
      <td width="6%">修读类别</td>
      <td width="15%"><@msg.message key="entity.speciality"/></td>
      <td width="20%"><@msg.message key="entity.specialityAspect"/></td>
      <td width="20%">班级</td>
    </tr>
    <#list task.teachClass.courseTakes?sort_by(["student","code"]) as take>
   	  <#if take_index%2==1 ><#assign class="grayStyle" ></#if>
	  <#if take_index%2==0 ><#assign class="brightStyle" ></#if>
     <tr class="${class}" align="center" 
       onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)"
       onclick="onRowChange(event)" >
      <td width="2%" class="select">
        <input type="checkbox" name="stdCode" value="${take.student.code}">
      </td>
      <td>${take.student.code}</td>
      <td>${take.student.name}</td>
      <td><@i18nName take.student.type/></td>
      <td>${task.course.credits}</td>
      <td><@i18nName take.courseTakeType/></td>
      <td><@i18nName take.student.firstMajor?if_exists/></td>
      <td><@i18nName take.student.firstAspect?if_exists/></td>
      <td>${take.student.firstMajorClass?if_exists.name?if_exists}</td>
    </tr>
	</#list>
	</table>
	<p>一共${task.teachClass.courseTakes?size}名学生</p>
</body> 
<#include "/templates/foot.ftl"/> 