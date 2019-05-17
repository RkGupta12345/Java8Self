package com.nit.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/Employees")

public class EmployeeResource {
	private static List<Employee> list=new ArrayList<>();
	
	@GET
	
	public List<Employee> getEmployess() {
		
	return	list;
	}
	@POST
	public String insertEmployee(Employee employee) {
		list.add(employee);
		
		return "Employe Added With Id: "+employee.getEmpno();
		
	}
	
	@Path("/{id}")
	@DELETE
	public String deleteEmployee(@PathParam("id")int id) {
		for(int i=0;i<list.size();i++) {
			System.out.println(list.get(i).getEmpno());
			System.out.println(id);
			if(list.get(i).getEmpno()==id) {
				list.remove(i);
			}
		}
		//list.remove(id);
		return "Employee "+id+ "Removed";
	}
	
}  
	  
	 /* @GET
	 //@Path("/getEmployeebyDesig")
	  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	  @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	  public Employee getEmployeeByDesig(@QueryParam("desig") String desig){ 
		  Employee e1 =new Employee(); 
		  if(desig!=null && desig.equals("SSE")){
	 * e1.setEmpno(102); e1.setEmpname("Kiran"); e1.setEmpdesig("SSE"); return
	 * e1; // return Response.ok(e1).status(201).build(); } else{ //return
	 * Response.status(400).build(); return null; }
	 * 
	 * }
	 */
/*@GET
@Path("{name}")
public Employee getEmployeeByName(@PathParam("name") String name) {
	if (name != null && name.equals("Sai")) {
		Employee e = new Employee(101, name, "SE");
		e.setEmpno(101);
		e.setEmpname(name);
		e.setEmpdesig("SE");
		return e;
	
	} else {
		return null;
	
	}
}*/

