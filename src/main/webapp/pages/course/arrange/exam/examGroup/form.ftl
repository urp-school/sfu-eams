<#include "/templates/head.ftl"/>
<body  LEFTMARGIN="0" TOPMARGIN="0">
<table id="examGroupBar"></table>
	<table width="90%" border="0" class="formTable" align="center">
	<form name="examGroupForm" method="post" action="" onsubmit="return false;">
	 <input name="examGroup.id" value="${examGroup.id?if_exists}" type="hidden"/>
	 <input name="examGroup.examType.id" value="${(examGroup.examType.id)?if_exists}" type="hidden"/>
	 <input name="examType.id" value="${(examGroup.examType.id)?if_exists}" type="hidden"/>
	 <input name="calendar.id" value="${RequestParameters['calendar.id']}" type="hidden"/>
	 <input name="calendar.studentType.id" value="${RequestParameters['calendar.studentType.id']}" type="hidden"/>
 	 <tr class="darkColumn">
      <td width="15%" class="grayStyle">排考类别<font color="red">*</font></td>
      <td class="brightStyle"><@i18nName examGroup.examType/></td>
     </tr>
 	 <tr class="darkColumn">
      <td width="15%" class="grayStyle">排考课程组 名称<font color="red">*</font></td>
      <td class="brightStyle"><input name="examGroup.name" value="<@i18nName examGroup?if_exists/>" maxlength="50"/></td>
     </tr>
    </table>
    </form>
 <script>
   var bar = new ToolBar('examGroupBar','排考课程组',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("添加任务","taskList()",'save.gif');
   bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
   
   function taskList(){
      var form=document.examGroupForm;
      if (form['examGroup.name'].value == "") {
      	alert("请填写课程组名称");
      	return;
      }
      form.action = "examGroup.do?method=taskList";
      form.submit();
   }
 </script>
</body>
<#include "/templates/foot.ftl"/>