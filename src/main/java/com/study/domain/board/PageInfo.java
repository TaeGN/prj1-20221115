package com.study.domain.board;

import lombok.Data;

@Data
public class PageInfo {
	
	private int currentPageNumber;
	private int lastPageNumber;
	private int leftPageNumber;
	private int rightPageNumber;
	
	private boolean hasPrevButton;
	private boolean hasNextButton;
	private int jumpPrevPageNumber;
	private int jumpNextPageNumber;
}
