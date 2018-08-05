<#include "/templates/head.ftl"/>
<body >
 <table id="gradeAchivementListBar"></table>
  <@table.table  width="100%" id="listTable" sortable="true">
    <@table.thead>
      	<@table.selectAllTd width="5%" id="gradeAchivementId"/>
      	<@table.sortTd width="10%" text="学号" id="gradeAchivement.std.code"/>
        <@table.sortTd width="7%" text="姓名" id="gradeAchivement.std.name"/>
        <@table.sortTd width="7%" text="年级" id="gradeAchivement.grade"/>
        <@table.sortTd width="14%" text="院系" id="gradeAchivement.department.name"/>
        <@table.sortTd width="14%" text="专业" id="gradeAchivement.major.name"/>
        <@table.sortTd width="10%" text="班级" id="gradeAchivement.adminClass.name"/>
        <@table.sortTd width="6%" text="智育" id="gradeAchivement.ieScore"/>
        <@table.sortTd width="6%" text="体育" id="gradeAchivement.peScore"/>
        <@table.sortTd width="6%" text="德育" id="gradeAchivement.moralScore"/>
        <@table.sortTd width="6%" text="综合" id="gradeAchivement.score"/>
        <@table.td width="5%" text="全部<br>及格" id="gradeAchivement.gradePassed" />
        <@table.sortTd width="5%" text="四级<br>通过" id="gradeAchivement.cet4Passed"/>
    </@>
    <@table.tbody datas=gradeAchivements;gradeAchivement>
    	<@table.selectTd type="checkbox" value=gradeAchivement.id id="gradeAchivementId"/>
      	<td>${(gradeAchivement.std.code)!}</td>
      	<td>${(gradeAchivement.std.name)!}</td>
      	<td>${(gradeAchivement.grade)!}</td>
      	<td>${(gradeAchivement.department.name)!}</td>
        <td>${(gradeAchivement.major.name)!}</td>
        <td>${(gradeAchivement.adminClass.name)!}</td>
      	<td>${(gradeAchivement.ieScore)!}</td>
      	<td>${(gradeAchivement.peScore)!}</td>
      	<td>${(gradeAchivement.moralScore)!}</td>
      	<td>${(gradeAchivement.score)!}</td>
      	<td><#if gradeAchivement.ieScore?exists>${(gradeAchivement.gradePassed)?string("是","<font color='red'>否</font>")}</#if></td>
      	<td>${(gradeAchivement.cet4Passed)?string("是","<font color='red'>否</font>")}</td>
    </@>
  </@>
  <form name="actionForm" target="_self" method="post" action="" onsubmit="return false;">
     <input type="hidden" name="setting.id" value="${RequestParameters['setting.id']}"/>
     <input type="hidden" name="params" value="<#list RequestParameters?keys as key><#if !key?contains("method")>&${key}=${RequestParameters[key]}</#if></#list>"/>
  	 <input type="hidden" name="keys" value="toSemester.year,std.code,std.name,grade,major.name,adminClass.name,moralScore,ieScore,peScore,score,gpa,gradePassed,cet4Passed"/>
  	 <input type="hidden" name="titles" value="学年度,学号,姓名,年级,专业,班级,德育,智育,体育,综合测评,绩点,全部及格,四级通过"/>
  </form>
  <script>
  	var bar=new ToolBar("gradeAchivementListBar","综合测评成绩列表",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addItem("查看", "info()");
    bar.addItem("统计", "stat()");
    bar.addItem("删除", "remove()");
    bar.addItem("<@msg.message key="action.export"/>", "exportList()");
    
    function exportList() {
    	var form = document.actionForm;
    	form.action = "gradeAchivementStat.do?method=export";
    	var gradeAchivementIds = getCheckBoxValue(document.getElementsByName("gradeAchivement.id"));
    	if (null != gradeAchivementIds && '' != gradeAchivementIds){
    		form.action = form.action + "&gradeAchivementIds=" + gradeAchivementIds;
    	}
		addHiddens(form, queryStr);
		form.submit();
	}
	
    function stat() {
    	var form = document.actionForm;
    	form.action = "gradeAchivementStat.do?method=stat";
    	var gradeAchivementIds = getCheckBoxValue(document.getElementsByName("gradeAchivementId"));
        if (null != gradeAchivementIds && '' != gradeAchivementIds){
           submitId(document.actionForm, "gradeAchivementId", true, "gradeAchivementStat.do?method=stat", "确定要重新统计吗？");
        }else{
           if(confirm("确认统计所有查询记录")){
             addHiddens(form, queryStr);
             form.submit();
           }
        }
	}
	function remove() {
    	var form = document.actionForm;
    	form.action = "gradeAchivementSearch.do?method=remove";
    	submitId(document.actionForm, "gradeAchivementId", true, "gradeAchivementStat.do?method=remove", "确定删除吗？");
	}
	function info() {
    	var form = document.actionForm;
    	form.action = "gradeAchivementSearch.do?method=info";
    	submitId(document.actionForm, "gradeAchivementId", false, "gradeAchivementStat.do?method=info");
	}
  </script>
</body> 
<#include "/templates/foot.ftl"/> 