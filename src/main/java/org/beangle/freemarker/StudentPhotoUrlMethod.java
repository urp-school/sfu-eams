package org.beangle.freemarker;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class StudentPhotoUrlMethod implements TemplateMethodModel{

	@Override
	public Object exec(List arg0) throws TemplateModelException {
		if(arg0.size() != 1 ){
			return "";
		}
		return "http://service.urp.sfu.edu.cn/sns/photo/" + DigestUtils.md5Hex(arg0.get(0).toString()+"@sfu.edu.cn") + ".jpg";
	}
	
	

}
