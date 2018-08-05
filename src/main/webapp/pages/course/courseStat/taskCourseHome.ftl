<#include "/templates/head.ftl"/>
<body>
<table width="100%"  class="frameTable">
	<tr>
	<td width="30%" class="frameTable_view">
 	<#include "taskForm.ftl"/>
	</td>
	<td width="70%" valign="top">
     	<iframe name="queryFrame" id="queryFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe>
	</td>
	</tr>
	<script language="javascript">
	 var form =document.courseSearchForm;
	 var action="courseStat.do";
 	 function search(){
 		form.action=action+"?method=taskCourseList";
 		form.submit();
 	}
 	function stat(){
 		form.action=action+"?method=taskCourseStat";
 		form.submit();
 	}
 	search();
	</script>
</body>
<#include "/templates/foot.ftl"/>