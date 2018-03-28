package com.jfservice.sys.core;

import java.util.List;

public interface IBaseService<T> {
	
	/**
	 * 获取个数
	 * @param model
	 * @return
	 */
	public int countOf(T model);
	/**
	 * 根据id找到指定的数据记录
	 * @param id
	 * @return
	 */
	public T findById(int id);
	/**
	 * 列出所有的记录
	 * @return
	 */
	public List<T> findAll();
	/**
	 * 根据实体类找到记录
	 * @return
	 */
	public List<T> find(T model);
	/**
	 * 插入,并且返回主键的id
	 * @param model
	 * @return
	 */
	public int insert(T model);
	/**
	 * 根据id修改某个实体类
	 * @param id
	 * @return
	 */
	public int updateById(int id);
	/**
	 * 修改实体类的内容
	 * @param model
	 * @return
	 */
	public int update(T model);
	/**
	 * 批量修改,并且返回最后一条修改的主键id
	 * @param models
	 * @return
	 */
	public int batchUpdate(List<Integer> models);
	/**
	 * 删除某个实体类,并且返回主键
	 * @param model
	 * @return
	 */
	public int delete(T model);
	/**
	 * 根据id删除某个实体类
	 * @param id
	 * @return
	 */
	public int deleteById(int id);
	/**
	 * 批量删除
	 * @param models
	 * @return
	 */
	public int batchDelete(List<Integer> models);
}
