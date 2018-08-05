<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<script src='dwr/interface/studentDAO.js'></script>
<body  LEFTMARGIN="0" TOPMARGIN="0">
<table id="creditConstraintBar"></table>
	<table  width="100%" class="formTable" id="criteriaTable">
	<tr class="darkColumn">
		<td colspan="4">学年度：${calendar.year},学期：${calendar.term}</td>
	</tr>
	  <form name="creditConstraintForm" method="post" action="" onsubmit="return false;">
	  <input type="hidden" name="constraint.calendar.id" value="${calendar.id}"/>
	  <input type="hidden" name="params" value="${RequestParameters['params']}"/>
	 <#if constraint?if_exists.id?exists>
 	 <tr>
 	   <input type="hidden" name="constraint.id" value="${constraint.id}"/>
 	   <input name="constraint.std.id" type="hidden" id="stdNo" value="${constraint.std.id}"/>
 	   <td class="grayStyle">&nbsp;<@msg.message key="attr.stdNo"/></td>
 	   <td>${constraint.std.code}</td>
 	   <td class="grayStyle">&nbsp;<@msg.message key="attr.personName"/></td>
 	   <td><@i18nName constraint.std?if_exists/></td>
 	 </tr>
     <#else>
 	 <tr>
 	   <td class="grayStyle">&nbsp;<@msg.message key="attr.stdNo"/></td>
 	   <td id="f_stdNo"><input name="constraint.std.code" id="stdNo" value="${constraint.std?if_exists.code?if_exists}" maxlength="32"/><input type="button" value="查找学生.." onclick="checkStd(stdNo.value)"></td>
 	   <td class="grayStyle">&nbsp;<@msg.message key="attr.personName"/></td>
 	   <td id="stdName"><@i18nName constraint.std?if_exists/></td>
 	 </tr>
 	 </#if>
 	 <tr>
 	   <td class="grayStyle" id="f_maxCredit">&nbsp;学分上限<font color="red">*</font></td>
 	   <td><input name="constraint.maxCredit" value="${constraint.maxCredit?if_exists}" maxlength="8"/></td>
 	   <td class="grayStyle" id="f_minCredit">&nbsp;学分下限<font color="red">*</font></td>
 	   <td><input name="constraint.minCredit" value="${constraint.minCredit?if_exists}" maxlength="8"/></td>
 	 </tr>
 	 <tr>
 	   <td class="grayStyle" id="f_electedCredit">&nbsp;已选学分<font color="red">*</font></td>
 	   <td><input type="hidden" name="constraint.electedCredit" value="${constraint.electedCredit?default("0")}" maxlength="4"/>${constraint.electedCredit?default("0")}</td>
 	   <td class="grayStyle" id="f_awardedCredit">&nbsp;奖励学分</td>
 	   <td><input name="constraint.awardedCredit" value="${constraint.awardedCredit?if_exists}" maxlength="4"/></td>
 	 </tr>
 	 <tr>
 	   <td class="grayStyle" id="f_GPA">&nbsp;平均绩点</td>
 	   <td><input name="constraint.GPA" value="${constraint.GPA?if_exists}" maxlength="8"/></td>
 	   <td class="grayStyle"></td>
 	   <td></td>
 	 </tr>
     </form>
    </table>
 <script>
  function saveConstraint(){
     var form=document.creditConstraintForm;
     if(form['constraint.std.code']!=null){
        if(form['constraint.std.code'].value==""){
           $("error").innerHTML="学号不应为空";
           return;
        }
     }
     var a_fields = {
         'constraint.maxCredit':{'l':'学分上限', 'r':true, 't':'f_maxCredit','f':'unsignedReal'},
         'constraint.minCredit':{'l':'学分下限', 'r':true, 't':'f_minCredit','f':'unsignedReal'},
         'constraint.electedCredit':{'l':'已选学分', 'r':true, 't':'f_electedCredit','f':'unsignedReal'},
         'constraint.awardedCredit':{'l':'奖励学分', 'r':false, 't':'f_awardedCredit','f':'unsignedReal'},
         'constraint.GPA':{'l':'平均绩点', 'r':false, 't':'f_GPA','f':'unsignedReal'}
     };
     var v = new validator(form , a_fields, null);
     if (v.exec()) {
     	if (form["constraint.minCredit"].value > form["constraint.maxCredit"].value) {
     		alert("学分下限不能超过学分上限！");
     		return;
     	}
        form.action="creditConstraint.do?method=saveStdCreditConstraint"
        form.submit();
     }
  }
  function checkStd(stdNo){
     if(""==stdNo){
       $("error").innerHTML="学号不应为空";
     }else{
       studentDAO.getBasicInfoName(setStdInfo,stdNo);
     }
  }
  function setStdInfo(data){
     if(null==data){
       $("error").innerHTML="该学生不存在";
     }else{
        $("stdName").innerHTML=data.name;
     }
  }
  function getCredit(id,calendarId,whichCalendar){
     var form=document.creditConstraintForm;
     form.action="creditConstraint.do?method=editStdCreditConstraint&whichCalendar="+whichCalendar;
     form.action+="&student.id="+id+"&calendar.id="+calendarId;
     //alert(form.action)
     form.submit();
  }
   var bar = new ToolBar('creditConstraintBar','学生个人学分管理',null,true,true);
   bar.setMessage('<@getMessage/>');
   <#if constraint.id?exists>
   bar.addItem("前个学期学分","javascript:getCredit('${constraint.std.id}','${calendar.id}','preCalendar')");
   bar.addItem("后个学期学分","javascript:getCredit('${constraint.std.id}','${calendar.id}','postCalendar')");
   </#if>
   bar.addItem("<@bean.message key="action.save"/>",saveConstraint);
   bar.addBack("<@bean.message key="action.back"/>");  
 </script>
</body>
<#include "/templates/foot.ftl"/>