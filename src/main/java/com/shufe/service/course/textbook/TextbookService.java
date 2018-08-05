package com.shufe.service.course.textbook;

import java.util.List;

import com.ekingstar.commons.model.pojo.PojoExistException;
import com.shufe.model.course.textbook.Textbook;

/**
 * 教材服务接口
 * 
 * @author lzs
 * 
 */
public interface TextbookService {

	public List getTextbooks();

	public Textbook getTextbook(Long id);

	public void saveTextbook(Textbook textbook) throws PojoExistException;

}
