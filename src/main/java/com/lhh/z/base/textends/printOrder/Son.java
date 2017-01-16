package com.lhh.z.base.textends.printOrder;

/**
 * 子类
 * @author hwaggLee
 * @createDate 2017年1月9日
 */
public class Son extends My{

	public Son(){
		System.out.println("Son extends My...");
	}
	

	public void printOverride(){
		System.out.println("Son extends My printOverride...");
	}


	@Override
	public String toString() {
		return "Son [getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
}
