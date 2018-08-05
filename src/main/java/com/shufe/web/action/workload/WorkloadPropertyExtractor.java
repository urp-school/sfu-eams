//$Id: PropertyExtractorWorkload.java,v 1.1 2007-4-12 15:48:54 Administrator Exp $
/*
 *
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved.
 * 
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended 
 * only for the use of KINGSTAR MEDIA application development. Reengineering, reproduction
 * arose from modification of the original source, or other redistribution of this source 
 * is not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 * 
 */
/********************************************************************************
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2007-4-12         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.workload;

import java.util.Locale;

import org.apache.struts.util.MessageResources;

import com.ekingstar.commons.mvc.struts.misc.StrutsMessageResource;
import com.ekingstar.commons.transfer.exporter.DefaultPropertyExtractor;
import com.ekingstar.eams.system.basecode.industry.CourseCategory;
import com.shufe.model.workload.course.TeachWorkload;

public class WorkloadPropertyExtractor extends DefaultPropertyExtractor {
	protected MessageResources resources;

	public WorkloadPropertyExtractor() {

	}

	public WorkloadPropertyExtractor(MessageResources resources, Locale locale) {
		this.resources = resources;
		this.locale = locale;
		this.setBuddle(new StrutsMessageResource(resources));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ekingstar.commons.transfer.exporter.DefaultPropertyExtractor#extract(java.lang.Object,
	 *      java.lang.String)
	 */
	protected Object extract(Object target, String property) throws Exception {
		if ("isGuaPai".equals(property)) {
			TeachWorkload teachWorkload = (TeachWorkload) target;
			if (null != teachWorkload.getTeachTask()) {
				if (Boolean.TRUE.equals(teachWorkload.getTeachTask()
						.getRequirement().getIsGuaPai())) {
					return resources.getMessage(locale, "common.yes");
				} else {
					return resources.getMessage(locale, "common.no");
				}
			} else {
				if (CourseCategory.GP.equals(teachWorkload.getCourseCategory()
						.getId())) {
					return resources.getMessage(locale, "common.yes");
				} else {
					return resources.getMessage(locale, "common.no");
				}
			}
		} else {
			return super.extract(target, property);
		}
	}
}
