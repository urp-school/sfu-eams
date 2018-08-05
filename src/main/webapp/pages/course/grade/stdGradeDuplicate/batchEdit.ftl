<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" > 
<table id="myBar"></table>
<table width="100%" align="center" class="formTable">
  <form name="gradeForm" action="stdGradeDuplicate.do?method=saveBatchEdit" method="post" onsubmit="return false;">
   <input type="hidden" name="params" value="${RequestParameters['params']}">
   <input type="hidden" name="courseGradeIds" value="${RequestParameters['courseGradeIds']}">
    <tr>
  	 <td class="title" width="20%"><input type="checkbox" onclick="changeState(this,'course.code')">&nbsp;课程代码：</td>
     <td  colspan="3"><input name="course.code" maxlength="32" value="请输入课程代码" onfocus="this.value=''" disabled/></td>
    </tr>
    <tr>
  	 <td class="title" width="20%"><input type="checkbox" onclick="changeState(this,'courseType.id')">&nbsp;课程类别：</td>
     <td><@htm.i18nSelect datas=courseTypeList?sort_by("name") selected="" name="courseType.id" style="width:150px" disabled="true"/>
     </td>
  	     <td class="title" width="20%"><input type="checkbox" onclick="changeState(this,'credit')">&nbsp;学分：</td>
     <td >
       <input id="credit" name="credit" value="请输入学分(正实数)" maxlength="3" onfocus="this.value=''" disabled/>
     </td>
    </tr>
</form> 
 </table>
<@table.table width="100%" id="listTable">
   <@table.thead>
     <@table.td class="selectTd" text="序号"/>
     <@table.td width="10%" name="attr.stdNo"/>
     <@table.td width="10%"  name="attr.personName"/>
     <@table.td width="8%"  name="attr.courseNo"/>
     <@table.td width="20%" name="entity.course"/>
     <@table.td width="15%" name="entity.courseType"/>
     <@table.td width="5%" text="成绩"/>
     <@table.td width="5%" name="attr.credit"/>
	 <@table.td width="5%" text="绩点"/>
	 <@table.td width="15%" text="学年学期"/>
   </@>
   <@table.tbody datas=grades;grade,grade_index>
    <td>${grade_index+1}</td>
    <td>${grade.std.code}</td>
    <td><@i18nName grade.std/></td>
    <td>${grade.course.code}</td>
    <td><@i18nName grade.course?if_exists/></td>
    <td><@i18nName grade.courseType?if_exists/></td>
    <td><#if grade.isPass>${(grade.score?string("#.##"))?if_exists}<#else><font color="red">${(grade.score?string("#.##"))?if_exists}</font></#if></td>
    <td>${grade.credit}</td>
    <td><#if grade.isPass>${(grade.GP?string("#.##"))?if_exists}<#else><font color="red">${(grade.GP?string("#.##"))?if_exists}</font></#if></td>
    <td>${grade.calendar.year} ${grade.calendar.term}</td>
   </@>
 </@>
</body>
<script>
   var bar = new ToolBar("myBar","批量修改实践课成绩（${grades?size}）",null,true,true);
   bar.addItem("提交修改","save()");
   bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
   var form= document.gradeForm; 
   function save() {
      if (!form['course.code'].disabled && form['course.code'].value == "") {
      	alert("请填写课程代码.");
      	return;
      }
      if (!form['credit'].disabled && !/^\d*\.?\d*$/.test(form['credit'].value)) {
      	alert("请正确填写学分.");
      	return;
      }
      if (confirm("确定要对这些成绩进行选定的修改吗?")) {
         form.submit();
      }
   }
	var infos=new Object();
	infos['course.code']='请输入课程代码';
	infos['credit']='请输入学分(正实数)';
    function changeState(check, name) { 
       form[name].disabled =! check.checked;
       if (form[name].type == "text") {
          form[name].value = infos[name];
       }
    }
</script>
<#include "/templates/foot.ftl"/>