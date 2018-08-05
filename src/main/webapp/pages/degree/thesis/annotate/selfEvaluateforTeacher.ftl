<table width="100%" align="center" class="listTable">
    <caption>
      <B>专家对学位论文创新点及不足的评价</B>
    </caption>
    <tr align="center" class="darkColumn">
      <td width="20%" align="left">创新点评价</td>
      <td>A(很好)</td>
      <td>B(较好)</td>
      <td>C(一般)</td>
      <td>D(差)</td>
    </tr>
    <tr align="center">
      <td class="darkColumn" id="f_innovateOne${idSeq}" align="left">创新点(一)<#if flag?exists&&flag=="book"><font color="red">*</font></#if>:</td>
      <td><input type="radio" value="A" name='annotateBook${idSeq}.innovateOne' <#if annotateBook?if_exists.innovateOne?exists &&annotateBook?if_exists.innovateOne?string=='A'>checked</#if>>很好</td>
      <td><input type="radio" value="B" name='annotateBook${idSeq}.innovateOne' <#if annotateBook?if_exists.innovateOne?exists &&annotateBook?if_exists.innovateOne?string=='B'>checked</#if>>较好</td>
      <td><input type="radio" value="C" name='annotateBook${idSeq}.innovateOne' <#if annotateBook?if_exists.innovateOne?exists &&annotateBook?if_exists.innovateOne?string=='C'>checked</#if>>一般</td>
      <td><input type="radio" value="D" name='annotateBook${idSeq}.innovateOne' <#if annotateBook?if_exists.innovateOne?exists &&annotateBook?if_exists.innovateOne?string=='D'>checked</#if>>差</td>
    </tr>
    <tr align="center">
      <td class="darkColumn" id="f_innovateTwo${idSeq}" align="left">创新点(二)<#if flag?exists&&flag=="book"><font color="red">*</font></#if>:</td>
      <td><input type="radio" value="A" name='annotateBook${idSeq}.innovateTwo' <#if annotateBook?if_exists.innovateTwo?exists && annotateBook?if_exists.innovateTwo?string=='A'>checked</#if>>很好</td>
      <td><input type="radio" value="B" name='annotateBook${idSeq}.innovateTwo' <#if annotateBook?if_exists.innovateTwo?exists && annotateBook?if_exists.innovateTwo?string=='B'>checked</#if>>较好</td>
      <td><input type="radio" value="C" name='annotateBook${idSeq}.innovateTwo' <#if annotateBook?if_exists.innovateTwo?exists && annotateBook?if_exists.innovateTwo?string=='C'>checked</#if>>一般</td>
      <td><input type="radio" value="D" name='annotateBook${idSeq}.innovateTwo' <#if annotateBook?if_exists.innovateTwo?exists && annotateBook?if_exists.innovateTwo?string=='D'>checked</#if>>差</td>
    </tr>
    <tr align="center">
      <td class="darkColumn" id="f_innovateThree${idSeq}" align="left">创新点(三)<#if flag?exists&&flag=="book"><font color="red">*</font></#if>:</td>
      <td><input type="radio" value="A" name='annotateBook${idSeq}.innovateThree' <#if annotateBook?if_exists.innovateThree?exists &&annotateBook?if_exists.innovateThree?string=='A'>checked</#if>>很好</td>
      <td><input type="radio" value="B" name='annotateBook${idSeq}.innovateThree' <#if annotateBook?if_exists.innovateThree?exists &&annotateBook?if_exists.innovateThree?string=='B'>checked</#if>>较好</td>
      <td><input type="radio" value="C" name='annotateBook${idSeq}.innovateThree' <#if annotateBook?if_exists.innovateThree?exists &&annotateBook?if_exists.innovateThree?string=='C'>checked</#if>>一般</td>
      <td><input type="radio" value="D" name='annotateBook${idSeq}.innovateThree' <#if annotateBook?if_exists.innovateThree?exists &&annotateBook?if_exists.innovateThree?string=='D'>checked</#if>>差</td>
    </tr>
    <tr align="center">
      <td class="darkColumn" id="f_lack${idSeq}" align="left">不足之处<#if flag?exists&&flag=="book"><font color="red">*</font></#if>:</td>
      <td><input type="radio" value="A" name='annotateBook${idSeq}.lack' <#if annotateBook?if_exists.lack?exists && annotateBook?if_exists.lack?string=='A'>checked</#if>>确切</td>
      <td><input type="radio" value="B" name='annotateBook${idSeq}.lack' <#if annotateBook?if_exists.lack?exists && annotateBook?if_exists.lack?string=='B'>checked</#if>>较确切</td>
      <td><input type="radio" value="C" name='annotateBook${idSeq}.lack' <#if annotateBook?if_exists.lack?exists && annotateBook?if_exists.lack?string=='C'>checked</#if>>一般</td>
      <td><input type="radio" value="D" name='annotateBook${idSeq}.lack' <#if annotateBook?if_exists.lack?exists && annotateBook?if_exists.lack?string=='D'>checked</#if>>不确切</td>
    </tr>
  </table>