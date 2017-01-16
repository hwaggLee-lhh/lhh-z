package com.lhh.z.base.textends.printOrder;

import junit.framework.TestCase;

/**
 * 继承-运行打印顺序
 * @author hwaggLee
 * @createDate 2017年1月9日
 */
public class Test extends TestCase{

	/**
	 * 实例化对现实时的打印顺序
	 */
	public void testCreatePrintOrder(){
		Son son = new Son();
		son.printOverride();
	}
}
