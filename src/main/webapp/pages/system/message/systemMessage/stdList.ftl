<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0" onload="f_frameStyleResize(parent)">
     <table id="myBar"></table>
     <@table.table width="100%" sortable="true" headIndex="1" onkeypress="DWRUtil.onReturn(event,query)" id="sortTable">
     <form name="actionForm" method="post" action="systemMessage.do?method=stdList" onsubmit="return false;">
       <tr>
           <td><img src="${static_base}/images/action/search.png"  align="top" onClick="query()" alt="<@bean.message key="info.filterInResult"/>"/></td>
           <td><input style="width:100%" type="text" name="std.code" maxlength="32" value="${RequestParameters['std.code']?if_exists}"></td>
           <td><input style="width:100%" type="text" name="std.name" maxlength="20" value="${RequestParameters['std.name']?if_exists}"></td>
           <td><input style="width:100%" type="text" name="std.enrollYear" maxlength="7" value="${RequestParameters['std.enrollYear']?if_exists}"></td>
           <td><input style="width:100%" type="text" name="std.type.name" maxlength="50" value="${RequestParameters['std.type.name']?if_exists}"></td>
           <td><input style="width:100%" type="text" name="std.department.name" maxlength="50" value="${RequestParameters['std.department.name']?if_exists}"></td>
           <td><input style="width:100%" type="text" name="std.firstMajor.name" maxlength="50" value="${RequestParameters['std.firstMajor.name']?if_exists}"></td>
           <td><input style="width:100%" type="text" name="adminClass.name" maxlength="50" value="${RequestParameters['adminClass.name']?if_exists}"></td>
       </tr>
      </form>
	   <@table.thead>
	     <@table.selectAllTd id='stdCode'/>
	     <@table.sortTd id="std.code" width="10%" name="attr.stdNo"/>
	     <@table.sortTd id="std.name" name="attr.personName"/>
	     <@table.sortTd id="std.enrollYear"name="attr.enrollTurn"/>
	     <@table.sortTd id="std.type.name" name="entity.studentType"/>
	     <@table.sortTd id="std.department.name" name="entity.department"/>
	     <@table.sortTd id="std.firstMajor.name" name="entity.speciality"/>
	     <@table.sortTd id="adminClass.name" width="25%"name="entity.adminClass"/>
	   </@>
	   <@table.tbody datas=stds;std>
	    <@table.selectTd id="stdCode" value="${std.code}"/>
	    <td>${std.code}</td>
	    <td>${std.name}</td>
	    <td>${std.enrollYear}</td>
	    <td><@i18nName std.type?if_exists/></td>
	    <td><@i18nName std.department?if_exists/></td>
	    <td><@i18nName std.firstMajor?if_exists/></td>
	    <td><@i18nName std.firstMajorClass?if_exists/></td>
	   </@>
     </@>
 <script language="JavaScript" type="text/JavaScript" src="scripts/system/SendMessage.js"></script>
 <script>
   function getIds(){
       return(getCheckBoxValue(document.getElementsByName("stdCode")));
    }
    <#list stds as std>
	detailArray['${std.code}'] = {'name':'${(std.name)?js_string}'};
	</#list>
    function addAll(){
       <#if (totalSize>500)>
         alert("一次批量发送的上限人数是500,现在查询出${totalSize}人.\n请确保发送的对象是否正确。");
         return;
       </#if>
        var form =document.actionForm;
        addInput(form,"pageSize",500);
        query();
    }
    <#if RequestParameters['selectAll']?exists>
     var checkboxes=  document.getElementsByName('stdCode');
     for(var i=0; i<checkboxes.length;i++){
       checkboxes[i].checked=true;
     }
     addSelected();
    </#if>
    var bar= new ToolBar("myBar","学生列表",null,true,true);
    bar.addItem("选中添加","addSelected()");
    bar.addItem("显示所有","addAll()");
 </script>
 </body>
<#include "/templates/foot.ftl"/>