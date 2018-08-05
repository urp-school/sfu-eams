<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="stdListBar" ></table>

  <@table.table width="100%" align="left" sortable="true" id="listTable" headIndex="1">
    <tr bgcolor="#ffffff" onKeyDown="javascript:enterQuery()">
      <form name="stdListForm" method="post" onsubmit="return false;" action="graduatePractice.do?method=stdList">
  		<input name="graduatePractice.calendar.id" value="${RequestParameters['graduatePractice.calendar.id']}" type="hidden"/>
      <td align="center" width="3%" >
        <img src="${static_base}/images/action/search.gif" align="top" onClick="query()" alt="<@bean.message key="info.filterInResult"/>"/>
      </td>
      <td><input style="width:100%" type="text" name="std.code" maxlength="32" value="${RequestParameters['std.code']?if_exists}"/></td>
      <td><input style="width:100%" type="text" name="std.name" maxlength="20" value="${RequestParameters['std.name']?if_exists}"/></td>
      <td></td>
      <td><input style="width:100%" type="text" name="std.enrollYear" maxlength="7" value="${RequestParameters['std.enrollYear']?if_exists}"/></td>
      <td><input style="width:100%" type="text" name="std.department.name" maxlength="20" value="${RequestParameters['std.department']?if_exists.name?if_exists}"/></td>      
      <td><input style="width:100%" type="text" name="std.firstMajor.name" maxlength="20" value="${RequestParameters['std.firstMajor']?if_exists.name?if_exists}"/></td>    
      <td><input style="width:100%" type="text" name="adminClass.name" maxlength="20" value="${RequestParameters['adminClassName']?if_exists}"/></td>    
    </tr>
    <@table.thead>
        <@table.selectAllTd id="stdId"/>
	    <td width="8%"><@bean.message key="attr.stdNo"/></td>
	    <td width="6%"><@msg.message key="attr.personName"/></td>
	    <td width="5%"><@bean.message key="entity.gender"/></td>
	    <td width="6%"><@msg.message key="attr.enrollTurn"/></td>
	    <td width="15%"><@bean.message key="entity.college"/></td>
	    <td width="15%"><@bean.message key="entity.speciality"/></td>
	    <td width="15%"><@bean.message key="entity.adminClass"/></td>
    </@>
    <@table.tbody datas=students;std>
	  <@table.selectTd id="stdId" value=std.id/>
	    <td width="8%">${std.code}</td>
	    <td width="6%">${std.name}</td>
	    <td width="5%"><@i18nName std.basicInfo?if_exists.gender?if_exists/></td>
	    <td width="6%">${std.enrollYear}</td>
	    <td width="15%"><@i18nName std.department/></td>
	    <td width="15%"><@i18nName std.firstMajor?if_exists/></td>
	    <td width="15%"><@getBeanListNames std.adminClasses/></td>
     </@>
       </form>
  </@>
  <script>
   	var bar = new ToolBar('stdListBar','批量添加查找学生(1)',null,true,true);
   	bar.setMessage('<@getMessage/>');
   	bar.addItem("选中添加","javascript:batchAdd()",'new.gif');
   	
    var form = document.stdListForm;
   
    function query(pageNo,pageSize){
       goToPage(form,pageNo,pageSize);
    }
    function enterQuery() {
            if (window.event.keyCode == 13)
                query();
    }
    function selectStdNoOf(isOdd){
        var stdNos = document.getElementsByName("stdNo");
        for(var i=0;i<stdNos.length;i++){
           var number = new Number(stdNos[i].value.substring(stdNos[i].value.length-1))
           if(number%2!=0&&isOdd){
              stdNos[i].checked=true;
           }
           else if(number%2==0&&!isOdd){
              stdNos[i].checked=true;              
           }
        }
    }
    function batchAdd(){
       var stdIds = getCheckBoxValue(document.getElementsByName("stdId"));
       if(stdIds==""){
          alert("请选择学生");
          return;
       }
       addInput(form,"stdIds",stdIds);
       form.action = "graduatePractice.do?method=batchAdd";
       form.submit();
    }
  </script>
 </body>
<#include "/templates/foot.ftl"/>