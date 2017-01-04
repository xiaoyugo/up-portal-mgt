package com.changhong.base.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import com.google.gson.annotations.Expose;


/**
 * 实体主键生成策略，所有的实体类都必须继承该类
 * 采用hibernate uuid.hex策略，具有普遍通用性，适合所有数据库
 * 所以实体的主键属性名必须是id，对应的数据库字段名称必须是ID
 * @author wanghao
 * 2009-03-15晚
 */
@MappedSuperclass
public abstract class IDEntity{
	@Expose
	private String id;
	
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid",strategy="uuid.hex")
	@Column(name = "ID", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public IDEntity(){
		
	}
    public IDEntity(String id){
		this.id=id;
	}
}
