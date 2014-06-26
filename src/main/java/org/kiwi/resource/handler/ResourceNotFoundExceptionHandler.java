package org.kiwi.resource.handler;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ResourceNotFoundExceptionHandler implements ExceptionMapper<ResourceNotFoundException> {
    @Override
    public Response toResponse(ResourceNotFoundException exception) {
        return Response.status(404).build();
    }
}
