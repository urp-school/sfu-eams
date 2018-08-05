<script src='dwr/interface/departmentDAO.js'></script>
<script src='dwr/interface/specialityDAO.js'></script>
<script src='dwr/interface/specialityAspectDAO.js'></script>
<script src='scripts/stdTypeDepart3Select.js?ver=20150706'></script>
<script>
    <#if result?if_exists.stdTypeList?exists>
    <#assign stdTypeList=result.stdTypeList>
    </#if>
    <#if result?if_exists.departmentList?exists>
    <#assign departmentList=result.departmentList>
    </#if>
    var stdTypeArray = new Array();
    <#list stdTypeList as stdType>
    stdTypeArray[${stdType_index}]={'id':'${stdType.id?if_exists}','name':'<@i18nName stdType/>'};
    </#list>
    var departArray = new Array();
    <#list departmentList as depart>
    departArray[${depart_index}]={'id':'${depart.id?if_exists}','name':'<@i18nName depart/>'};
    </#list>
    <#if !(stdTypeNullable?exists)>
      <#assign stdTypeNullable=false>
    </#if>
    var sds = new StdTypeDepart3Select("stdTypeOfSpeciality","department","speciality","specialityAspect",${stdTypeNullable?string},true,true,true);
    sds.init(stdTypeArray,departArray);
</script>