<script language="JavaScript" type="text/JavaScript">
 //下拉列表框初始化
 var departmentList= new Array();//院系下拉框数组
 var specialityList= new Array();//专业下拉框数组
 var aspectList = new Array();//专业方向下拉框数组
 var depSize=0;//院系下拉框长度
 var speSize=0;//专业下拉框长度 
 var aspctSize=0;//专业方向下拉框长度
 <#if departmentList?exists> 
 <#else>
 <#assign departmentList = result.departmentList />
 </#if>
 
 //院系下拉框数组初始化
 <#list departmentList as department>
   departmentList[depSize]=new Array(2);
   departmentList[depSize][0]=null;
   departmentList[depSize][1]="${department.id?if_exists}";
   departmentList[depSize][2]="<@i18nName department/>"; 
   
   //专业框数组初始化 
   <#list department.specialitis as speciality> 
	 	specialityList[speSize]=new Array(2);
		specialityList[speSize][0]="${department.id?if_exists}";
		specialityList[speSize][1]="${speciality.id}";
		specialityList[speSize][2]="<@i18nName speciality/>"; 
  		//专业方向下拉框数组初始化
		<#list speciality.aspects as aspect>
		    aspectList[aspctSize]=new Array(2);
		    aspectList[aspctSize][0]="${speciality.id}";
		    aspectList[aspctSize][1]="${aspect.id}";
		    aspectList[aspctSize][2]="<@i18nName aspect/>"; 
		    aspctSize = aspctSize + 1;
		</#list>
		speSize=speSize+1;
   </#list>
   depSize=depSize+1;
 </#list>
 initSelect(departmentList,"department",null,"speciality"); 
 initSelect(specialityList,"speciality","department","specialityAspect");
 initSelect(aspectList,"specialityAspect","speciality",null);
</script>