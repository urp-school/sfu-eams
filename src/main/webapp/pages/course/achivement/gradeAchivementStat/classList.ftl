<#include "/templates/head.ftl"/>
<body >
 <table id="gradeAchivementListBar"></table>
  <@table.table  width="100%" id="listTable" sortable="true">
    <@table.thead>
      	<@table.selectAllTd width="5%" id="adminClassId"/>
      	<@table.td width="5%" text="序号"/>
        <@table.td width="7%" text="年级"/>
        <@table.td width="20%" text="名称"/>
        <@table.td width="20%" text="院系"/>
        <@table.td width="20%" text="专业" />
        <@table.td width="10%" text="是否冻结"/>
        <@table.td width="10%" text="人数"/>
    </@>
    <@table.tbody datas=classStats;stat,stat_index>
    	<@table.selectTd type="checkbox" value=stat.adminClass.id id="adminClassId"/>
    	<td>${stat_index+1}</td>
      	<td>${(stat.adminClass.enrollYear)!}</td>
      	<td>${(stat.adminClass.name)!}</td>
      	<td>${(stat.adminClass.department.name)!}</td>
      	<td>${(stat.adminClass.speciality.name)!}</td>
        <td>${stat.confirmed?string("是","否")}</td>
        <td>${(stat.count)}</td>
    </@>
  </@>
  <form name="actionForm" target="_self" method="post" action="" onsubmit="return false;">
     <input type="hidden" name="setting.id" value="${RequestParameters['setting.id']}"/>
     <input type="hidden" name="params" value="<#list RequestParameters?keys as key><#if !key?contains("method")>&${key}=${RequestParameters[key]}</#if></#list>"/>
  	 <input type="hidden" name="confirmed" value=""/>
  </form>
  <script>
  	var bar=new ToolBar("gradeAchivementListBar","综合测评班级成绩",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addItem("冻结", "toconfirm('1')");
    bar.addItem("激活", "toconfirm('0')");
    
    function toconfirm(rs) {
    	var form = document.actionForm;
    	form.action = "gradeAchivementStat.do?method=confirm";
    	form['confirmed'].value=rs;
    	submitId(document.actionForm, "adminClassId", true, "gradeAchivementStat.do?method=confirm", "确认操作吗？");
	}
  </script>
</body> 
<#include "/templates/foot.ftl"/> 