package com.chinacscs.platform.search.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author:  liusong
 * @date:    2019年1月14日
 * @email:   359852326@qq.com
 * @version: 
 * @describe: //TODO
 */
public class MathUtils {

	public static void main(String[] args) {
		mem();
		tps();
	}
	
	public static void tps() {
		long totalSize=153789458;
		long totalTime=51689432;
		System.out.println("tps:"+divide(totalSize, totalTime)*1000);
	}
	
	public static void mem() {
		long usedForHeap=2653807120l;
		long usedForNonHeap=156200216l;
		long totalHeap=14962655232l;
		System.out.println("mem1:"+divide(usedForHeap+usedForNonHeap, totalHeap));
		
		usedForHeap=2080821296l;
		usedForNonHeap=161109448l;
		totalHeap=14962655232l;
		System.out.println("mem2:"+divide(usedForHeap+usedForNonHeap, totalHeap));
	}
 
	
	public static double divide(long num1,long num2) {
		BigDecimal num1Bd=new BigDecimal(String.valueOf(num1));
		BigDecimal num2Bd=new BigDecimal(String.valueOf(num2));
		return num1Bd.divide(num2Bd, 4, RoundingMode.HALF_EVEN).doubleValue();
	}
}
