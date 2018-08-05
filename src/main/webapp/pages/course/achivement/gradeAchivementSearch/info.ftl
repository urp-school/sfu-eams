<#include "/templates/head.ftl"/>
 <style  type="text/css">
 .content{
	vertical-align: middle;
	font-size: 12px;
	padding-left: 2px;
	padding-right: 2px;
}
.title{
	width: 100px;
	vertical-align: middle;
	font-weight: 700;
	font-size: 12px;
	background-color: #EEEEEE;
	text-indent: 6px;
	border-bottom-width: 2px;
	padding-left: 4px;
	padding-right: 4px;	
}
 </style>
<body  LEFTMARGIN="0" TOPMARGIN="0" >
	<table id="backBar"></table>
     <table class="infoTable">
       <tr>
	     <td class="title">学号</td>
	     <td class="content" width="20%"> ${gradeAchivement.std.code}</td>
	     <td class="title">姓名:</td>
	     <td class="content">${gradeAchivement.std.name}</td>
	     <td class="title">年级:</td>
	     <td class="content">${gradeAchivement.grade}</td>
	   </tr>
	   <tr>
	     <td class="title">学院</td>
	     <td class="content" width="20%">${gradeAchivement.department.name}</td>
	     <td class="title">专业:</td>
	     <td class="content">${(gradeAchivement.major.name)!}</td>
	     <td class="title">班级:</td>
	     <td class="content">${(gradeAchivement.adminClass.name)!}</td>
	   </tr>
	   <tr>
	     <td class="title">德育分数:</td>
	     <td class="content">${gradeAchivement.moralScore!}</td>
	     <td class="title">智育分数:</td>
         <td class="content">${gradeAchivement.ieScore!}
          <#if (gradeAchivement.ieScore?default(0)>0)>${gradeAchivement.iePassed?string("全部及格","有不及格")}</#if>
         </td>
	     <td class="title">体育分数:</td>
         <td class="content">${gradeAchivement.peScore!}
          <#if (gradeAchivement.peScore?default(0)>0)>${gradeAchivement.pePassed?string("全部及格","有不及格")}</#if>
         </td>
	   </tr>
	   <tr>
	     <td class="title">总分:</td>
         <td class="content"><span style="font-size:15pt">${gradeAchivement.score!}</span></td>
	     <td class="title">绩点:</td>
         <td class="content">${(gradeAchivement.gpa?string("0.###"))!}</td>
	     <td class="title">英语四级:</td>
	     <td class="content">${gradeAchivement.cet4Score!}
	      <#if gradeAchivement.cet4Score?exists>${gradeAchivement.cet4Passed?string("","没通过")}</#if>
	     </td>
	   </tr>
	   <tr>
	     <td class="title">明细:</td>
	     <td class="content" colspan="4">
	     <#if (gradeAchivement.unpassed!0)!=0><font color="red">有${gradeAchivement.unpassed} 门不及格</font></#if>
	     <pre>${gradeAchivement.remark!}</pre>
	     </td>
	     <td>
	     <#if gradeAchivement.confirmed>结果已经冻结<#else>可以再行统计更新</#if>
	     </td>
	   </tr>
	   <tr>
	   	 <td class="title">统计时间:</td>
         <td class="content" colspan="5">${(gradeAchivement.updatedAt?string("yyyy-MM-dd HH:mm:ss"))!}</td>
       </tr>
      </table>
	<script>
		var bar = new ToolBar('backBar','综合测评详情',null,true,true);
		bar.setMessage('<@getMessage/>');
		bar.addBackOrClose("返回", "关闭");
	</script>
</body>
<#include "/templates/foot.ftl"/>