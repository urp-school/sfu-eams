<script src='dwr/interface/departmentDAO.js'></script>
<script src='dwr/interface/specialityDAO.js'></script>
<script src='dwr/interface/specialityAspectDAO.js'></script>

<script>
    <#if result?if_exists.stdTypeList?exists>
    <#assign stdTypeList=result.stdTypeList>
    </#if>
    <#if result?if_exists.departmentList?exists>
    <#assign departmentList=result.departmentList>
    </#if>
    var stdTypeSelectId='stdTypeOfSpeciality';
    var departSelectId='department';
    var specialitySelectId='speciality';
    var aspectSelectId="specialityAspect"
   
    var specialityNotNull=false;
   
    var defaultStdTypeId="";
    var defaultDepartId="";
    var defaultSpecialityId="";
    
    function initStdTypeSelect(){
        defaultStdTypeId=document.getElementById(stdTypeSelectId).value;
        DWRUtil.removeAllOptions(stdTypeSelectId);
        DWRUtil.addOptions(stdTypeSelectId,[<#list stdTypeList as stdType>{'id':'${stdType.id?if_exists}','name':'<@i18nName stdType/>'},</#list>{'':'please selector'}],'id','name');
        if(defaultStdTypeId!="")
           setSelected(document.getElementById(stdTypeSelectId),defaultStdTypeId);
        else{
           DWRUtil.addOptions(stdTypeSelectId,[{'id':'','name':'请选择..'}],'id','name');
           setSelected(document.getElementById(stdTypeSelectId),"");
        }
        if(null != document.getElementById(departSelectId)&&null != document.getElementById(specialitySelectId)){
	       document.getElementById(stdTypeSelectId).onchange=notifySpecialityChange;
	    }
    }
    
    function initDepartSelect(){
        defaultDepartId=document.getElementById(departSelectId).value;
        DWRUtil.removeAllOptions(departSelectId);
	    DWRUtil.addOptions(departSelectId,[<#list departmentList as depart>{'id':'${depart.id?if_exists}','name':'<@i18nName depart/>'},</#list>{'':'please selector'}],'id','name');
	    if(defaultDepartId!="")
           setSelected(document.getElementById(departSelectId),defaultDepartId);
        else{        
           DWRUtil.addOptions(departSelectId,[{'id':'','name':'请选择..'}],'id','name');
           setSelected(document.getElementById(departSelectId),"");
        }
	    document.getElementById(departSelectId).onchange=notifySpecialityChange;
    }
    
    function initSpecialitySelect(){
       defaultSpecialityId=document.getElementById(specialitySelectId).value;
       DWRUtil.removeAllOptions(specialitySelectId);
       
       var std= document.getElementById(stdTypeSelectId);
       var d = document.getElementById(departSelectId);       
       if(!specialityNotNull)
           DWRUtil.addOptions(specialitySelectId,[{'id':'','name':'请选择..'}],'id','name');
       if(std.value!=""&&d.value!=""){
       		specialityDAO.getSpecialityNames(setSpecialityOptions,d.value,std.value);       
       }
    }
    
    function notifySpecialityChange(){
       defaultSpecialityId="";
       var s= document.getElementById(stdTypeSelectId);
       var d = document.getElementById(departSelectId);
       DWRUtil.removeAllOptions(specialitySelectId);
       if(!specialityNotNull)
           DWRUtil.addOptions(specialitySelectId,[{'id':'','name':'请选择..'}],'id','name');
       if(s.value!=""&&d.value!=""){
       		specialityDAO.getSpecialityNames(setSpecialityOptions,d.value,s.value);       
       }
    }
    function setSpecialityOptions(data){
       for(var i=0;i<data.length;i++)
           DWRUtil.addOptions(specialitySelectId,[{'id':data[i][0],'name':data[i][1]}],'id','name');
       if(defaultSpecialityId!="")
           setSelected(document.getElementById(specialitySelectId),defaultSpecialityId);
    }
    
    function initDefault(){
      if( null!=document.getElementById(stdTypeSelectId))
          initStdTypeSelect();
      if(null != document.getElementById(departSelectId)&&null != document.getElementById(specialitySelectId)){
          initDepartSelect();
          setTimeout(initSpecialitySelect,200);
      }
    }
    initDefault();    
</script>