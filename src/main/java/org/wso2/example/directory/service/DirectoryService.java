/*
 * Copyright (c) 2016, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.example.directory.service;

import io.swagger.annotations.*;
import org.wso2.example.directory.service.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the Microservice resource class.
 * See <a href="https://github.com/wso2/msf4j#getting-started">https://github.com/wso2/msf4j#getting-started</a>
 * for the usage of annotations.
 *
 * @since 0.1-SNAPSHOT
 */
@Api(value = "DirectoryService")
@SwaggerDefinition(
        info=@Info(
                title = "Directory Service Definition",version = "1.0.SNAPSHOT",
                license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0"),
                contact = @Contact(
                        name = "Ajanthan Bala",
                        email = "ajanthan@wso2.com",
                        url = "http://wso2.com"
                ))
)
@Path("/service/user")
public class DirectoryService {
    private Map<String,User> userDAO;

    public DirectoryService() {
        userDAO= new HashMap<>();
        userDAO.put("001",new User("001","john.methon","john","methon","Manager"));
        userDAO.put("002",new User("002","grant.thomson","grant","thomson","Sales Rep"));
        userDAO.put("003",new User("003","edward.silva","edward","silva","Accountant"));
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    @ApiOperation(value = "Get User by ID",response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "valid user found"),
            @ApiResponse(code = 404, message = "valid user is not found")
    })
    public Response get(@ApiParam(value = "id",example = "001",required = true)@PathParam("id")String id) {
        System.out.println("GET user by ID="+id);
        User user=userDAO.get(id);
        if (user==null){
         return Response.status(404).build();
        }
        return Response.ok(user).build();
    }

    @POST
    @Path("/")
    @Consumes("application/json")
    @ApiOperation(value = "Add an User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User has been added"),
            @ApiResponse(code = 500, message = "Error in adding user")
    })
    public Response post(@ApiParam(value = "user",required = true,example = "{}") User user) {
        userDAO.put(user.getId(),user);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    @ApiOperation(value = "Edit an User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User has been edited"),
            @ApiResponse(code = 500, message = "Error in editing user")
    })
    public Response put(@ApiParam(value = "user",required = true,example = "{}") User user,@ApiParam(value = "id",required = true)@PathParam("id") String id) {
        userDAO.put(user.getId(),user);
        return Response.accepted().build();
    }

    @DELETE
    @Path("/{id}")
    @ApiOperation(value = "Delete an User by id")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "User has been deleted"),
            @ApiResponse(code = 500, message = "Error in deleting user")
    })
    public Response delete(@ApiParam(value = "id",required = true,example = "001")@PathParam("id")String id) {
        // TODO: Implementation for HTTP DELETE request
        userDAO.remove(id);
        return Response.accepted().build();
    }
}
