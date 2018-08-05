<#include "/templates/head.ftl"/>
<body  LEFTMARGIN="0" TOPMARGIN="0">
<#include "/pages/system/calendar/timeFunction.ftl">
<table id="examTurnBar" width="100%"></table>
	<table  width="100%" border="0" class="listTable">
	<form name="examTurnListForm" method="post" action="" onsubmit="return false;">
	 <input name="examTurn.stdType.id" value="${studentType?if_exists.id?if_exists}" type="hidden"/>
	 <input name="examTurn.id" value="" type="hidden"/>
 	 <tr class="darkColumn" align="center">
      <td align="center" width="3%"></td>
      <td width="6%"><@bean.message key="attr.index" /></td>
      <td width="10%">场次名称</td>
      <td width="10%">开始时间</td>
      <td width="10%">结束时间</td>
      </tr>
      
	 <#list examTurns?sort_by("beginTime") as examTurn> 
	   <#if examTurn_index%2==1 ><#assign class="grayStyle"/></#if>
	   <#if examTurn_index%2==0 ><#assign class="brightStyle"/></#if>
	 <tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)" onclick="onRowChange(event)">
	     <td width="2%" class="select">
	        <input type="radio" name="examTurnId" value="${examTurn.id}"/>
	     </td>
	     <td>${examTurn_index + 1}</td>
		 <td><@i18nName examTurn/></td> 
		 <td><@getTimeStr examTurn.beginTime/></td> 
         <td><@getTimeStr examTurn.endTime/></td>
	 </tr>
     </#list>
    </table>
    </form>
    <pre>
      说明:
         1.没有对应场次的学生类别,在排考中依次查找上级的可用排考场次.
         2.无须为每个学生类别制定场次.系统制定了<A class="buttonStyle" href="examTurn.do?method=list">默认场次设置.</A>
         3.考试场次的更改不会影响到已经排好的课程.
    </pre>
 <script>
   function remove(){
      var examTurnId = getSelectId("examTurnId");
      if(""==examTurnId){
         alert("请选择考试场次进行删除");return;
      }
      if(confirm("确定要删除考试场次?")){
	      var form = document.examTurnListForm;
	      form['examTurn.id'].value=examTurnId;
	      form.action="examTurn.do?method=remove";
	      form.submit();
      }
   }
   function newExamTurn(){
   	   var form = document.examTurnListForm;
	   form['examTurn.id'].value="";
	   form.action="examTurn.do?method=edit";
	   form.submit();
   }
   function edit(){
	   var examTurnId = getSelectId("examTurnId");
       if(""==examTurnId){
         alert("请选择考试场次进行修改");return;
       }
   	   var form = document.examTurnListForm;
	   form['examTurn.id'].value=examTurnId;
	   form.action="examTurn.do?method=edit";
	   form.submit();
   }
   var bar = new ToolBar('examTurnBar','<#if studentType?exists><@i18nName studentType/><#else>系统默认</#if> 考试场次列表',null,true,true);
   bar.setMessage('<@getMessage/>');
   <#assign inAuthority = true/>
   <#if studentType?exists>
   <#else>
      <#if !isAdminUser>
       <#assign inAuthority=false/>
      </#if>
   </#if>
   <#if inAuthority>
   bar.addItem("<@bean.message key="action.delete"/>",remove,"delete.gif");
   bar.addItem("<@bean.message key="action.modify"/>",edit,'update.gif');
   bar.addItem("<@bean.message key="action.add"/>",newExamTurn,'new.gif');
   </#if>
 </script>
</body>
<#include "/templates/foot.ftl"/>