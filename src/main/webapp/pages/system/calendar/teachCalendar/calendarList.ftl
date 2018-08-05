<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/Menu.js"></script>
<body>
  <table id="calendarBar"></table>
  <@table.table width="100%" sortable="true" id="listTable" >
   <@table.thead>
     <@table.td text="" width="2%"/>
     <@table.sortTd width="10%" name="attr.year2year" id="calendar.year"/>
     <@table.sortTd width="10%" name="attr.term" id="calendar.term"/>
     <@table.sortTd width="15%" text="起始日期" id="calendar.start"/>
     <@table.sortTd width="15%" text="结束日期" id="calendar.finish"/>
     <@table.sortTd width="7%" text="上课周数" id="calendar.weeks"/>
     <@table.sortTd width="5%" text="总周数" id="calendar.weekSpan"/>
   </@>
   <@table.tbody datas=calendars;calendar>
     <@table.selectTd type="radio" id="calendarId" value="${calendar.id}"/>
     <td><A href="calendar.do?method=calendarInfo&calendar.id=${calendar.id}">&nbsp;${calendar.year}</A></td>
     <td>&nbsp;${calendar.term}</td>
     <td>&nbsp;${calendar.start?string("yyyy-MM-dd")}</td>
     <td>&nbsp;${calendar.finish?string("yyyy-MM-dd")}</td>
     <td>&nbsp;${calendar.weeks}</td>
     <td>&nbsp;${calendar.weekSpan?default("")}</td>
   </@>
  </@>
  <form name="calendarListForm" method="post" action="calendar.do" onsubmit="return false;">
     <input type="hidden" name="scheme.id" value="${RequestParameters['scheme.id']}"/>
  </form>
<script>
　　　	var bar = new ToolBar("calendarBar",'${scheme.name}教学日历列表',null,true,true);
    bar.setMessage('<@getMessage/>');
    <#if calendars?size!=0>
    bar.addItem('添加下个学期',"javascript:add(true)",'new.gif');
    bar.addItem('添加上个学期',"javascript:add(false)",'new.gif'); 
    <#else>
    bar.addItem('添加新学期',"javascript:addNew()",'new.gif'); 
    </#if>
    bar.addItem('<@bean.message key="action.modify"/>',"javascript:operation('editCalendar',true)",'update.gif');
    bar.addItem('<@bean.message key="action.delete"/>',"javascript:operation('removeCalendar',true)",'delete.gif');
    bar.addItem('查看日历方案',"schemeInfo()", "detail.gif");
    bar.addItem('修改日历方案',"editSchemeForm()");
    bar.addItem('在校时间',"onCampusList()",'list.gif');
    
    var form = document.calendarListForm;
  function operation(method,needId){
    var calendarId ="";
    if(needId){
       calendarId = getRadioValue(document.getElementsByName('calendarId'));
       if(calendarId=="") {alert("请选择一个");return;}
    }
    form.action+="?method=" + method+ "&calendar.id=" + calendarId;
    if(method.indexOf('remove')!=-1 && !confirm("删除教学日历，确保没有其他基于此日历的数据，否则将不能删除！确认删除")) return;
    form.submit();
  }
  function add(isPre){
    var calendarId ="";
    calendarId = getRadioValue(document.getElementsByName('calendarId'));
    if(calendarId=="") {alert("请选择一个");return;}
    form.action+="?method=editCalendar";
    if(isPre)
        form.action+="&preCalendar.id="+ calendarId;
    else  
        form.action+="&nextCalendar.id="+ calendarId;
    form.submit();
  }
  function addNew(){
    form.action+="?method=editCalendar";
    form.submit();
  }
  function schemeInfo(){
    self.location="calendar.do?method=schemeInfo&scheme.id=${RequestParameters['scheme.id']}";
  }
  function onCampusList(){
    self.location="calendar.do?method=onCampusTimeList&studentType.id=${scheme.stdTypes?first.id}";
  }
	   	function editSchemeForm() {
	   		form.action = "calendar.do?method=editSchemeForm";
	   		form.submit();
	   	}
</script>
</body>
<#include "/templates/foot.ftl"/>