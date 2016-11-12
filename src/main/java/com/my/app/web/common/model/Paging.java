package com.my.app.web.common.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Paging {

	public static Paging getPaging(int page) {
		Paging paging = new Paging();
		
		int totalCount = 312;
		int listSize = 10;
		int pageSize = 10;
		int block = new BigDecimal(page).divide(new BigDecimal(pageSize), RoundingMode.CEILING).intValue();
		int totalPage = new BigDecimal(totalCount).divide(new BigDecimal(listSize), RoundingMode.CEILING).intValue();
		int totalBlock = new BigDecimal(totalPage).divide(new BigDecimal(pageSize), RoundingMode.CEILING).intValue();
		int startPage = new BigDecimal(block - 1).multiply(new BigDecimal(pageSize)).intValue() + 1;
		int endPage = startPage + pageSize - 1;
		
		System.out.println("paging: " + startPage + "~" + endPage + "/" + totalPage);
		System.out.println("block: " + block + "/" + totalBlock);
		
		// first
		if (block > 1) {
			System.out.println("[first :1]");
		}
		
		// prev
		if (block > 1) {
			System.out.println("[prev :" + (startPage - 1) + "]");
		}
		
		// paging
		for (int i = startPage; i <= endPage; i++) {
			System.out.println("[page :" + i + "]");
		}
		
		// next
		if (block < totalBlock) {
			System.out.println("[next :" + (endPage + 1) + "]");
		}
		
		// last
		if (block < totalBlock) {
			System.out.println("[last :" + totalPage + "]");
		}
		
		return paging;
	}
	
	public static void main(String[] args) {
		getPaging(5);
	}
	
}
