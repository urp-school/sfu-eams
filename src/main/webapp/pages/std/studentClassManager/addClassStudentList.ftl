<#include "/templates/head.ftl"/>
<#macro getAdminClassListNames(beanList)><#list beanList as bean>&nbsp;${bean.name}<#if bean_has_next><br></#if></#list></#macro>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
<table id="stdListBar" ></table>
  <table width="100%" align="left" class="listTable">
  <form name="stdListForm" method="post" onsubmit="return false;">
    <tr bgcolor="#ffffff" onKeyDown="javascript:enterQuery()">
      <td align="center" width="3%" >
        <img src="${static_base}/images/action/search.gif"  align="top" onClick="query()" alt="<@bean.message key="info.filterInResult"/>"/>
      </td>
      <td><input style="width:100%" type="text" name="student.code"  value="${RequestParameters['student.code']?if_exists}"/></td>
      <td><input style="width:100%" type="text" name="student.name"  value="${RequestParameters['student.name']?if_exists}"/></td>
      <td></td>
      <td><input style="width:100%" type="text" name="student.enrollYear" value="${RequestParameters['student.enrollYear']?if_exists}"/></td>
      <td><input style="width:100%" type="text" name="student.department.name" value="${RequestParameters['student.department.name']?if_exists}"/></td>      
      <td><input style="width:100%" type="text" name="student.firstMajor.name" value="${RequestParameters['student.firstMajor.name']?if_exists}"/></td>    
      <td><input style="width:100%" type="text" name="adminClass.name" value="${RequestParameters['adminClass.name']?if_exists}"/></td>    
    </tr>
	   <tr align="center" class="darkColumn">
	     <td width="2%"  align="center">
	     <input type="checkbox" onClick="toggleCheckBox(document.getElementsByName('stdNo'),event);"></td>
	     <td width="8%"><@bean.message key="attr.stdNo"/></td>
	     <td width="6%"><@msg.message key="attr.personName"/></td>
	     <td width="5%"><@bean.message key="entity.gender"/></td>
	     <td width="6%"><@msg.message key="attr.enrollTurn"/></td>
	     <td width="15%"><@bean.message key="entity.college"/></td>
	     <td width="15%"><@bean.message key="entity.speciality"/></td>
	     <td width="15%"><@bean.message key="entity.adminClass"/></td>
	   </tr>
	   <#list studentList as std>
	   <#if std_index%2==1 ><#assign class="grayStyle" ></#if>
	   <#if std_index%2==0 ><#assign class="brightStyle" ></#if>
	   <tr class="${class}" onmouseover="swapOverTR(this,this.className)"
	    onmouseout="swapOutTR(this)" align="center"
	    onclick="onRowChange(event)">
	    <td align="center" class="select">
    	    <input type="checkBox" name="stdNo" value="${std.code}">
    	</td>
	    <td>${std.code}</td>
        <td>${std.name}</td>
        <td><@i18nName std.basicInfo?if_exists.gender?if_exists/></td>
        <td>${std.enrollYear}</td>
        <td><@i18nName std.department/></td>
        <td><@i18nName std.firstMajor?if_exists/></td>
        <td align="left"><@getAdminClassListNames std.adminClasses/></td> 
	   </tr>
	   </#list>
        <#include "/templates/newPageBar.ftl"/>
     </table>
  </form>
  <script>
    function pageGoWithSize(pageNo,pageSize){
       query(pageNo,pageSize);
    }
    function query(pageNo,pageSize){
       var form = document.stdListForm;
       form.action="adminClassManager.do?method=addClassStudentList";
       if(null!=pageNo)
          form.action+="&pageNo="+pageNo;
       if(null!=pageSize)
          form.action+="&pageSize="+pageSize;
       form.submit();
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
    function addSelected(){
       var stdNos = getCheckBoxValue(document.getElementsByName("stdNo"));
       if(stdNos==""){
          alert("请选择学生");return;
       }
       parent.addStdNos(stdNos);
    }
   var bar = new ToolBar('stdListBar','查询学号添加',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("单号","javascript:selectStdNoOf(true)",'update.gif');
   bar.addItem("双号","javascript:selectStdNoOf(false)",'update.gif');
   bar.addItem("添加学号","javascript:addSelected()",'new.gif',"添加学号");    
  </script>
 </body>
<#include "/templates/foot.ftl"/>