<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
<table id="stdListBar" ></table>
  <@table.table width="100%" >
  <form name="stdListForm" method="post" action="" onsubmit="return false;">
       <input type="hidden" name="params" value="${RequestParameters['params']}">
       <input type="hidden" name="stdIds" value=""/>
	<#list RequestParameters['params']?if_exists?split("&") as param>
	    <#if (param?length>2)>
	    <input type="hidden" name="${param[0..param?index_of('=')-1]}" value="${param[param?index_of('=')+1..param?length-1]}"/>
	    </#if>
	</#list>
  </form>
	   <@table.thead>
	     <@table.selectAllTd id="stdId"/>
	     <@table.td width="8%" name="attr.student.code"/>
	     <@table.td width="6%" name="attr.personName"/>
	     <@table.td width="5%" name="entity.gender"/>
	     <@table.td width="6%" name="attr.enrollTurn"/>
	     <@table.td width="15%" name="entity.college"/>
	     <@table.td width="15%" name="entity.speciality"/>
	     <@table.td width="15%" name="entity.adminClass"/>
	   </@>
	   <@table.tbody datas=stds;std>
	    <@table.selectTd id="stdId"  value=std.id/>
	    <td >${std.code}</td>
        <td ><@i18nName std/></td>
        <td ><@i18nName (std.basicInfo.gender)?if_exists/></td>
        <td >${std.enrollYear}</td>
        <td ><@i18nName std.department/></td>
        <td ><@i18nName std.firstMajor?if_exists/></td>
        <td ><@getBeanListNames std.adminClasses/></td> 
	   </@>
	 </@>
  <script>
    function pageGoWithSize(pageNo,pageSize){
       query(pageNo,pageSize);
    }
    function query(pageNo,pageSize){
       var form = document.stdListForm;
       form.action="creditConstraint.do?method=noCreditStdList";
       if(null!=pageNo)
          form.action+="&pageNo="+pageNo;
       if(null!=pageSize)
          form.action+="&pageSize="+pageSize;
       form.submit();
    }
    function addSelected(){
       	var stdIds = getSelectIds('stdId');
       	if(""==stdIds){
       	   alert("请选择学生进行设置.");return;
       	}
       	var msg="请为所有查询出的学生设置学分上限";
       	if(stdIds!=""){
          	msg="请为选定的学生设置学分上限";
       	}
       	var maxCredit = prompt(msg,"0");
       	if (maxCredit == null || maxCredit == "") {
       		alert("学生设置学分不能为空");
       		return;
       	} else if (!/^\d*\.?\d*$/.test(maxCredit)) {
       		alert("学分上限格式不对，应为正实数。");
       		return;
       	} else if (parseFloat(maxCredit) > 100) {
       		alert("学生设置学分不能超过100分。");
       		return;
       	}
       	if(null!=maxCredit){
	       var form = document.stdListForm;
	       form.action="creditConstraint.do?method=batchAddCredit&creditConstraint.minCredit=0&creditConstraint.maxCredit="+maxCredit;
	       addInput(form,"stdIds",stdIds);
	       form.submit();
       }
    }
   var bar = new ToolBar('stdListBar','没有学分上限的学生名单',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("添加上限","javascript:addSelected()",'new.gif');
   bar.addBack("<@msg.message key="action.back"/>");
  </script>
 </body>
<#include "/templates/foot.ftl"/>
