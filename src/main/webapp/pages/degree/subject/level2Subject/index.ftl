<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
    <table id="leve1SubjectBar"></table>
     <@table.table width="100%" id="listTable">
     <form name='level2SubjectForm' method="post" action="" onsubmit="return false;">
       <tr align="center" class="darkColumn" bgcolor="#ffffff" onKeyDown="javascript:enterQuery()">
	     <td><img src="${static_base}/images/action/search.png"  align="top" onClick="query()" alt="在结果中过滤..."/></td> 
	     <td><input type="text" name="level2Subject.level1Subject.name" maxlength="30" value="" style="width:100%"></td>
	     <td><input type="text" name="level2Subject.speciality.name" value="" maxlength="25" style="width:100%"></td>
	     <td>
	     	<select name="level2Subject.speciality.department.id" style="width:100%">
	     	    <option value="">...</option>
	     		<#list departmentList as department>
     				<option value="${department.id}" <#if RequestParameters['level2Subject.speciality.department.id']?default('')==department.id?string>selected </#if>>${department.name}</option>
	     		</#list>
	     	</select>
	     </td>
	     <td>
	        <select name="level2Subject.isSpecial" style="width:100%">
	      	  <option value="">...</option>
	      	  <option value="0">不是</option>
	      	  <option value="1">是</option>
		    </select>
	     </td>
	     <td>
	        <select name="level2Subject.forDoctor" style="width:100%">
	      	  <option value="">...</option>
	      	  <option value="0">无</option>
	      	  <option value="1">有</option>
		    </select>
	     </td>
	     <td>
	        <select name="level2Subject.isSelfForDoctor" style="width:100%">
	      	  <option value="">...</option>
	      	  <option value="0">不是</option>
	      	  <option value="1">是</option>
		    </select>
	     </td>
         <td>
	        <select name="level2Subject.forMaster" style="width:100%">
	      	  <option value="">...</option>
	      	  <option value="0">无</option>
	      	  <option value="1">有</option>
		    </select>
         </td>
         <td>
	        <select name="level2Subject.isSelfForMaster" style="width:100%">
	      	  <option value="">...</option>
	      	  <option value="0">不是</option>
	      	  <option value="1">是</option>
		    </select>
	     </td>
	   </tr>
	   </form>
       <@table.thead>
	     <@table.selectAllTd id="subjectId"/>
         <@table.td width="15%" text="一级学科"/>
	     <@table.td width="15%" text="学科点名称"/>
	     <@table.td width="15%" text="学科所在部门"/>
	     <@table.td width="10%" text="是否专业学位"/>
         <@table.td width="10%" text="博士学位授予权"/>
         <@table.td width="10%" text="自主设置博士点"/>
         <@table.td width="10%" text="硕士学位授予权"/>
         <@table.td width="10%" text="自主设置硕士点"/>
	   </@>
	   <@table.tbody datas=level2Subjects;subject>
    	<@table.selectTd id="subjectId" value=subject.id/>
    	<td><@i18nName (subject.level1Subject)?if_exists/>
    	<td><a href="level2Subject.do?method=info&level2Subject.id=${subject.id}"><@i18nName subject.speciality?if_exists/></td>
	    <td><@i18nName (subject.speciality.department)?if_exists/></td>
	    <td>${subject.isSpecial?string("是","不是")}</td>
	    <td>${subject.forDoctor?string("有","无")}</td>
	    <td>${subject.isSelfForDoctor?string("是","不是")}</td>	
	    <td>${subject.forMaster?string("有","无")}</td>
	    <td>${subject.isSelfForMaster?string("是","不是")}</td>
	   </@>
	</@>
    <script>
	    var bar = new ToolBar("leve1SubjectBar","二级学科点管理",null,true,true);
	    bar.setMessage('<@getMessage/>');
	    bar.addItem("学科点院系分布",'subjectOfCollege()');
	    bar.addItem("<@msg.message key="action.new"/>",'add()');
	    bar.addItem("<@msg.message key="action.edit"/>","edit()");
	    bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
	    
         var form = document.level2SubjectForm;
	    function add(){
	      form.action="level2Subject.do?method=edit";
	      form.submit();
	    }
	    function edit(){
	       	 submitId(form, "subjectId", false, "level2Subject.do?method=edit");
	    }
	    function enterQuery() {
	       if (window.event.keyCode == 13) {
	           query();
	       }
	    }
	    function query(){
	        form.action="level2Subject.do?method=index";
	    	form.submit();
	    }
	    function stat(){
	        level2SubjectForm.action="level2Subject.do?method=stat";
	    	level2SubjectForm.submit();
	    }
	    function subjectOfCollege(){
	    	form.action="level2Subject.do?method=statByCollege";
	    	form.submit();
	    }
    </script>
 </body> 
<#include "/templates/foot.ftl"/>