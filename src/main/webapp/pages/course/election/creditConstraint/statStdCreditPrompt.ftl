<#include "/templates/head.ftl"/>
<body  LEFTMARGIN="0" TOPMARGIN="0">
<table id="updateStdCreditBar"></table>
	<table  width="100%" border="0" class="listTable" id="criteriaTable">
	<form name="creditConstraintForm" method="post" action="" onsubmit="return false;">
	<#list RequestParameters['params']?if_exists?split("&") as param>
	    <#if (param?length>2)>
	    <input  type="hidden" name="${param[0..param?index_of('=')-1]}" value="${param[param?index_of('=')+1..param?length-1]}"/>
	    </#if>
	</#list>
	<input type="hidden" name="params" value="${RequestParameters['params']}">
	<input type="hidden" name="stdContraintIdSeq" value="${RequestParameters['stdContraintIdSeq']}">
	<tr  class="darkColumn"  align="center">
	  <td colspan="2">学年度：${calendar.year},学期：${calendar.term}的学分计算参数</td>
	</tr>
 	<tr class="darkColumn">
 	   <td class="grayStyle"><input type="checkbox" name="params.initElectedCredit" checked/>计算学生已选学分</td>
 	</tr>
 	<tr>
 	   <td class="grayStyle"><input type="checkbox" name="params.initGPA"/>计算平均绩点</td>
 	</tr>
 	 <tr class="darkColumn">
 	   <td class="grayStyle">
 	   <input type="checkbox" name="params.initAwardCredit">
 	   依照<select id="stdType" name="preCalendar.studentType.id" style="width:100px"></select>
 	   <select id="year" name="preCalendar.year" style="width:100px"></select>
 	   <select id="term" name="preCalendar.term" style="width:80px"></select>
 	   的平均绩点计算奖励学分
 	   </td>
 	 </tr>
	<tr  class="darkColumn"  align="center">
	  <td colspan="2">&nbsp;</td>
	</tr>
     </form>
    </table>
 <script>
  function statStdCrditConstraint(isPre,calendarId){
       var form=document.creditConstraintForm;
       if(form['params.initAwardCredit'].checked==false &&
          form['params.initElectedCredit'].checked==false &&
          form['params.initGPA'].checked==false){
          alert("请选择要计算的项目");
          return;
       }
       if(form['params.initAwardCredit'].checked){
          if(form['preCalendar.studentType.id'].value=="" ||
             form['preCalendar.year'].value=="" ||
             form['preCalendar.term'].value==""){
               alert("参考的学年度学期数据不全");
               return;
          }
       }
       document.creditConstraintForm.action= "creditConstraint.do?method=statCreditConstraint";
       document.creditConstraintForm.submit();
  }
   var bar = new ToolBar('updateStdCreditBar','学分绩点计算设置',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("开始计算","javascript:statStdCrditConstraint()");
   bar.addBack("<@bean.message key="action.back"/>");
 </script>
<#include "/templates/calendarSelect.ftl"/>
</body>
<#include "/templates/foot.ftl"/>