package com.flaster.blog.exception;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -5476560638623879570L;

	public ResourceNotFoundException(String message) {
        super(message);
    }
}
