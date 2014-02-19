package com.codari.apicore.item.assetmaster;

public class ExcessAssetException extends Exception {

	private static final long serialVersionUID = 4104717540315323882L;
	
	public ExcessAssetException() {
		super();
	}
	
	public ExcessAssetException(String issue) {
		super(issue);
	}
}
