<#macro cycleTypeSelect name cycleType  extra...>
    <select name="${name}"<#if (extra?size!=0)><#list extra?keys as attr> ${attr}="${extra[attr]?html}"</#list></#if>>
        <option value="1" <#if cycleType == 1>selected</#if>>天</option>
        <option value="2" <#if cycleType == 2>selected</#if>>周</option>
        <option value="4" <#if cycleType == 4>selected</#if>>月</option>
    </select>
</#macro>

<#assign cycleNames={'1':'天','2':'周','4':'月'}/>
<#macro cycleValue count type>每<#if count!=1>${count}</#if>${cycleNames[(type?string)?default('1')]}</#macro>
