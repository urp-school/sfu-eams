<#include "/templates/head.ftl"/>
<body > 
<table id="bar1"></table>
<#include "${RequestParameters['productType']}Info.ftl"/>
<table id="bar2"></table>
<@table.table   width="100%" id="listTable" sortable="true">
	<@table.thead>
      <@table.selectAllTd id="studyAwardId"/>
      <@table.td  name="attr.name"/>
      <@table.td  text="成果名称" />
      <@table.td  text="获奖名称" />
      <@table.td  text="获奖等级"/>
      <@table.td  text="获奖时间"/>
      <@table.td  text="颁奖单位" />
      <@table.td  text="是否通过审核"/>
    </@>
    <@table.tbody datas=studyProduct.awards;studyAward>
      <@table.selectTd id="studyAwardId" value="${studyAward.id}"/>
  		<td>${studyAward.student?if_exists.name?if_exists}</td>
  		<td>${studyAward.studyProduct.name?if_exists}</td>
  		<td>${studyAward.awardName?if_exists}</td>
  		<td>${studyAward.type?if_exists.name?if_exists}</td>
  		<td>${studyAward.awardedOn?string("yyyy-MM-dd")}</td>
        <td>${studyAward.departmentName?if_exists}</td>
        <td><#if studyAward.isPassCheck==true>通过审核<#else>未通过</#if></td>
      </@>
 </@>
 <form name="actionForm" method="post" action="" onsubmit="return false;">
   <input name="studyProductId" value="${studyProduct.id}" type="hidden"/>
   <input type="hidden" name="productType" value="${RequestParameters['productType']}"/>
 </form>
 <script>
    var bar = new ToolBar("bar1","科研成果信息",null,true,true);
    bar.addPrint("<@msg.message key="action.print"/>");
    bar.addBack();
    var awardBar = new ToolBar("bar2","获奖信息",null,true,true);
    awardBar.addItem("<@msg.message key="action.add"/>","addAwardInfo()","new.gif");
    awardBar.addItem("<@msg.message key="action.edit"/>","editAwardInfo()");
    var action="studyProduct.do";
    //表明该页面是学生访问的子页面
    if(parent.action.indexOf("Std")!=-1){
       var action="studyProductStd.do";
       //alert(action);
    }
    var form=document.actionForm;
    function addAwardInfo(){
    	form.action =action+"?method=editAward";
    	setSearchParams(parent.document.searchForm,form);
    	form.submit();
    }
    function editAwardInfo(){
       form.action =action+"?method=editAward";
       setSearchParams(parent.document.searchForm,form);
       submitId(form,"studyAwardId",false);
    }
 </script>
</body>
<#include "/templates/foot.ftl"/>