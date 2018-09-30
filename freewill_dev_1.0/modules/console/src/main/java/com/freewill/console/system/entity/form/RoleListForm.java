/**
 * 
 */
package com.freewill.console.system.entity.form;


import com.freewill.console.common.BasePageForm;
import com.freewill.console.common.validation.SelectGroup;

import javax.validation.constraints.NotNull;

/**
 * @author fyden
 *
 */
public class RoleListForm extends BasePageForm {

	/**
	 * 角色的类型
	 */
	@NotNull(groups = { SelectGroup.class }, message = "角色类型不能为空")
	private Integer type;

	private String keyword;
	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword
	 *            the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	
}
