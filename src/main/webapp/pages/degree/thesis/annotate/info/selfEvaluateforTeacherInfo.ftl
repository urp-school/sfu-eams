<table width="100%" align="center" class="infoTable">
    <caption>
      <B>专家对学位论文创新点及不足的评价</B>
    </caption>
    <tr align="center" class="title">
      <td width="20%">创新点评价</td>
      <td>A(很好)</td>
      <td>B(较好)</td>
      <td>C(一般)</td>
      <td>D(差)</td>
    </tr>
    <tr align="center">
      <td class="title">创新点(一):</td>
      <td><#if annotateBook?if_exists.innovateOne?exists &&annotateBook?if_exists.innovateOne?string=='A'>很好</#if></td>
      <td> <#if annotateBook?if_exists.innovateOne?exists &&annotateBook?if_exists.innovateOne?string=='B'>较好</#if></td>
      <td>  <#if annotateBook?if_exists.innovateOne?exists &&annotateBook?if_exists.innovateOne?string=='C'>一般</#if></td>
      <td>  <#if annotateBook?if_exists.innovateOne?exists &&annotateBook?if_exists.innovateOne?string=='D'>差</#if></td>
    </tr>
    <tr align="center">
      <td class="title" >创新点(二):</td>
      <td>  <#if annotateBook?if_exists.innovateTwo?exists && annotateBook?if_exists.innovateTwo?string=='A'>很好</#if></td>
      <td>  <#if annotateBook?if_exists.innovateTwo?exists && annotateBook?if_exists.innovateTwo?string=='B'>较好</#if></td>
      <td>  <#if annotateBook?if_exists.innovateTwo?exists && annotateBook?if_exists.innovateTwo?string=='C'>一般</#if></td>
      <td>  <#if annotateBook?if_exists.innovateTwo?exists && annotateBook?if_exists.innovateTwo?string=='D'>差</#if></td>
    </tr>
    <tr align="center">
      <td class="title">创新点(三):</td>
      <td> <#if annotateBook?if_exists.innovateThree?exists &&annotateBook?if_exists.innovateThree?string=='A'>很好</#if></td>
      <td> <#if annotateBook?if_exists.innovateThree?exists &&annotateBook?if_exists.innovateThree?string=='B'>较好</#if></td>
      <td> <#if annotateBook?if_exists.innovateThree?exists &&annotateBook?if_exists.innovateThree?string=='C'>一般</#if></td>
      <td> <#if annotateBook?if_exists.innovateThree?exists &&annotateBook?if_exists.innovateThree?string=='D'>差</#if></td>
    </tr>
    <tr align="center">
      <td class="title" >不足之处:</td>
      <td><#if annotateBook?if_exists.lack?exists && annotateBook?if_exists.lack?string=='A'>确切</#if></td>
      <td><#if annotateBook?if_exists.lack?exists && annotateBook?if_exists.lack?string=='B'>较确切</#if></td>
      <td><#if annotateBook?if_exists.lack?exists && annotateBook?if_exists.lack?string=='C'>一般</#if></td>
      <td><#if annotateBook?if_exists.lack?exists && annotateBook?if_exists.lack?string=='D'>不确切</#if></td>
    </tr>
  </table>