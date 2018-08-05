<script language="JavaScript" type="text/JavaScript">
	//下拉列表框初始化
	var districtList= new Array();//校区下拉框数组
	var buildingList= new Array();//教学楼下拉框数组
	var distictSize=0;//校区下拉框长度
	var buildingSize=0;//教学楼下拉框长度 
	 
	//校区下拉框数组初始化
	<#--
	districtList[distictSize]=new Array(1);
	districtList[distictSize][0]=null;
	districtList[distictSize][1]="";
	districtList[distictSize++][2]="<@bean.message key="common.all"/>"; 
	-->
	<#list districtList as district>
		districtList[distictSize]=new Array(1);
		districtList[distictSize][0]=null;
		districtList[distictSize][1]="${district.id}";
		districtList[distictSize++][2]="<@i18nName district/>";
	   
		//教学楼框数组初始化 
		buildingList[buildingSize] = new Array(1);
		buildingList[buildingSize][0] = "${district.id}";
		buildingList[buildingSize][1] = "";
		buildingList[buildingSize++][2]="全部";
	   	<#list district.buildings as building>
			buildingList[buildingSize]=new Array(1);
			buildingList[buildingSize][0]="${district.id}";
			buildingList[buildingSize][1]="${building.id}";
			buildingList[buildingSize++][2]="<@i18nName building/>";
	   	</#list>
	</#list>
	initSelect(districtList,"district",null,"building"); 
	initSelect(buildingList,"building","district",null); 
	tip="";
</script>
