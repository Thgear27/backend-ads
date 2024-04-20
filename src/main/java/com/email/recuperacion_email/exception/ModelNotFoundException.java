package com.email.recuperacion_email.exception;

public class ModelNotFoundException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8914591043508090375L;

	public ModelNotFoundException(String message){
		super(message);
	}

}
