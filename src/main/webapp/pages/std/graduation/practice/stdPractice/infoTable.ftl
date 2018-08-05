<table class="infoTable" width="100%" align="center">
    <tr>
    	<td class="title" width="15%">学生学号：</td>
    	<td width="30%">&nbsp;${student.code}</td>
    	<td class="title" width="15%">学生姓名：</td>
    	<td id="stdName" width="30%">&nbsp;${student.name}</td>
    </tr>
  	<tr>
     	<td class="title" id="f_company">实习单位:</td>
     	<td>&nbsp;${graduatePractice.practiceCompany?default("")}</td>
    	<td class="title" width="15%">实习方式：</td>
    	<td width="30%">&nbsp;<@i18nName graduatePractice.practiceSource/></td>
  	</tr>
    <tr>
    	<td class="title" width="15%">是否实习基地：</td>
    	<td width="30%">&nbsp;${graduatePractice.isPractictBase?string("是","否")}</td>
    	<td class="title" width="15%">指导教师：</td>
    	<td width="30%">&nbsp;${(student.teacher.name)?default("-")}</td>
    </tr>
   	<tr>
	    <td class="title" id="f_practiceDesc">实习描述： </td>
	    <td colspan="3">&nbsp;${graduatePractice.practiceDesc?default("")}</td>
   	</tr>
</table>