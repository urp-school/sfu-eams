<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table id="courseTakeBar"></table>
 <#include "../courseTakeSearch/courseTakeList.ftl"/>
  <form name="actionForm" method="post" action="" onsubmit="return false;">
    <#--下面两行代码为显示课程详细信息而设，请勿更改或者同名-->
    <input type="hidden" name="type" value="course"/>
    <input type="hidden" name="id" value=""/>

  	<input name="calendar.id" type="hidden" value="${RequestParameters['courseTake.task.calendar.id']}"/>
  </form>
<script language="JavaScript" type="text/JavaScript" src="scripts/course/CourseTake.js"></script>
<script>
    var byWhat='${RequestParameters['orderBy']?default("null")}';
    var bar = new ToolBar("courseTakeBar","上课名单列表",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addItem("<@msg.message key="action.export"/>","exportData('courseTake', ${totalSize})");
    
    var menu =bar.addMenu("修改修读类别...");
    <#list courseTakeTypes as type>
    menu.addItem("<@i18nName type/>","editCourseTakeType(${type.id})");
    </#list>
    <#if courseTakes?size!=0>
    bar.addItem("选退课信息","submitId(document.actionForm,'courseTakeId',false,'courseTake.do?method=electWithdrawInfo')");
    </#if>
    <#if searchStd?exists>
    var menu =bar.addMenu("为 ${searchStd.name}[${searchStd.code}]加课","add(${searchStd.id})");
    menu.addItem("${searchStd.name}的选退课信息","document.actionForm.action='courseTake.do?method=electWithdrawInfo&stdId=${searchStd.id}&courseTake.task.calendar.id=${RequestParameters['courseTake.task.calendar.id']}'; document.actionForm.submit()");
    </#if>
    bar.addItem("退课","withdraw()","delete.gif","减少教学班实际人数,降低学生已选学分,更新选中状态");
    bar.addItem("发送消息","sendMessage()","inbox.gif");
    var form =document.actionForm;
    function add(stdId){
       var seqNo= prompt("请输入要添加的课程序号:","");
       if(null==seqNo||""==seqNo){
          return;
       }
       form.action="courseTake.do?method=add";
       addInput(form,"stdId",stdId,"hidden");
       addInput(form,"seqNo",seqNo,"hidden");
       addInput(form,"calendarId",'${RequestParameters['courseTake.task.calendar.id']}',"hidden");
       addParamsInput(form,getInputParams(parent.document.courseTakeForm));
       form.submit();
    }
    
    function courseInfo(selectId, courseId) {
       if (null == courseId || "" == courseId || isMultiId(selectId) == true) {
           alert("请选择一条要操作的记录。");
           return;
       }
       form.action = "courseSearch.do?method=info";
       form[selectId].value = courseId;
       form.submit();
    }
</script>
</body>
<#include "/templates/foot.ftl"/>


