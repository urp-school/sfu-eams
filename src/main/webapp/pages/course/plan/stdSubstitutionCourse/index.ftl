<#include "/templates/head.ftl"/>
<body>
	<table id="stdSubstitutionCourseBar"></table>
	<table class="frameTable" width="100%">
	   	<tr>
	   		<td width="20%" class="frameTable_view"><#include "courseSearchForm.ftl"/></td>
	    	<td valign="top">
	 			<iframe src="#" id="stdSubstitutionCourseFrame" name="stdSubstitutionCourseFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0"  height="100%" width="100%"></iframe>
    		</td>
   		</tr>
  	</table>
  	<form name="stdSubstitutionCourseForm" method="post" action="" onsubmit="return false;">
	
  	</form>
	<script>
	  	var bar=new ToolBar("stdSubstitutionCourseBar","替代课程管理",null,true,true);
	  	bar.setMessage('<@getMessage />');
        bar.addHelp();
        
        function searchSubstitutionCourse() {
	    	var form = document.stdSubstitutionCourseForm;
	    	form.target = "stdSubstitutionCourseFrame";
	    	form.action = "stdSubstitutionCourse.do?method=search";
	    	form.submit();
	    }
	    
	    searchSubstitutionCourse();
	</script>  
</body>
<#include "/templates/foot.ftl"/>