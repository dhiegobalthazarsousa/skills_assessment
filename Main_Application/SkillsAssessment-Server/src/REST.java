



import static spark.Spark.get;
import static spark.Spark.post;


import java.io.UnsupportedEncodingException;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import spark.Request;
import spark.Response;
import spark.Route;


public class REST{
	
	private Model model;
	
	
	public REST(Model store){
		this.model = store;
	}
	
	
	public void getLogin(){
		
		get("/login/:username/:password", new Route() {
			@Override
            public Object handle(final Request request, final Response response){
	        	
	        	 
	        	 
	        	response.header("Access-Control-Allow-Origin", "*");
	        	 
	            
	            
	            try {
	            	Student student = model.login(request.params(":username"), request.params(":password"));
	            	
	            	if(student != null){
	            		
	            		JSONArray jsonResult = new JSONArray();
		         	    JSONObject jsonObj = new JSONObject();

		        		jsonObj.put("ra", student.getRa());
		        		jsonObj.put("completed", student.getCompleted());
		        		jsonObj.put("question", student.getQuestion());
		        		
		             	jsonResult.put(jsonObj);
		             	
		             	return jsonResult;
	            		
	            	} else {
	            		
	            		
	            		
	            	}
	            	
	            	
	             	
	        		} catch (JSONException e) {
	        				
	        			//e.printStackTrace();

	        		}
	         	    	
	
	            JSONArray jsonResult = new JSONArray();
         	    JSONObject jsonObj = new JSONObject();

        		jsonObj.put("ra", 0);
        		
        		
             	jsonResult.put(jsonObj);
             	
             	return jsonResult;
	            
	     	     
	         }
	         
	      });
			

	}
	
	
	public void getStudentCompetencies(){
		
		
	
		get("/competencies/:ra", new Route() {
			@Override
            public Object handle(final Request request, final Response response){
	        	
	        	 
	        	 
	        	response.header("Access-Control-Allow-Origin", "*");
	        	 
	            Integer ra = Integer.parseInt(request.params(":ra"));
	        	
	            
	            try {
	            	Student student = model.searchStudentbyRA(ra);
	            	
	            	JSONArray jsonResult = new JSONArray();
	         	    JSONObject jsonObj = new JSONObject();

	         	    //jsonObj.put("userName", student.getUserName());
	         	    //jsonObj.put("password", student.getPassword());
	        		jsonObj.put("name", student.getName());
	        		jsonObj.put("ra", student.getRa());
	        		//jsonObj.put("institution", student.getInstitution());
	        		//jsonObj.put("course", student.getCourse());
	        		//jsonObj.put("year", student.getYear());
	        		//jsonObj.put("period", student.getPeriod());
	        		jsonObj.put("competencies", student.getCompetencies());
	        		//jsonObj.put("question", student.getQuestion());
	        		//jsonObj.put("completed", student.getCompleted());
	        		//jsonObj.put("psychologistComment", student.getPsychologistComment());
	        		//jsonObj.put("statusComment", student.getStatusComment());
	        		
	             	jsonResult.put(jsonObj);
	             	
	             	return jsonResult;
	             	
	        		} catch (JSONException e) {
	        				
	        			e.printStackTrace();
	        		}
	         	    	
	
	     	    return null;
	     	     
	         }
	         
	      });

	         
	}
	
	public void getStudentsQuestionbyRA(){
		
		get("/user/:ra", new Route() {
			@Override
            public Object handle(final Request request, final Response response){
	        	
	        	 
	        	 
	        	response.header("Access-Control-Allow-Origin", "*");
	        	 
	        	Integer ra = Integer.parseInt(request.params(":ra"));
	            
	            try {
	            	Student student = model.searchStudentbyRA(ra);
	            	
	            	JSONArray jsonResult = new JSONArray();
	         	    JSONObject jsonObj = new JSONObject();

	        		jsonObj.put("question", student.getQuestion());
	        		jsonObj.put("completed", student.getCompleted());
	        		
	             	jsonResult.put(jsonObj);
	             	
	             	return jsonResult;
	             	
	        		} catch (JSONException e) {
	        				
	        			e.printStackTrace();
	        		}
	         	    	
	
	     	    return null;
	     	     
	         }
	         
	      });
		
		
	}
	
	public void getQuestionByNumber(){
		
		get("/questions/:number", new Route() {
			@Override
            public Object handle(final Request request, final Response response) throws UnsupportedEncodingException{
	        	
	        	response.header("Access-Control-Allow-Origin", "*");
	        	 
	            Integer number = Integer.parseInt(request.params(":number"));
	        	
	            
	            try {
	            	Question question = model.searchQuestionByCode(number);
	            	
	            	JSONArray jsonResult = new JSONArray();
	         	    JSONObject jsonObjQuestion = new JSONObject();
	         	    
	         	    jsonObjQuestion.put("number", question.getNumber());
	        		jsonObjQuestion.put("introduction", java.net.URLDecoder.decode(question.getIntroduction(), "UTF-8"));
	        		jsonObjQuestion.put("introductionMediaType", question.getIntroductionMediaType());
	        		jsonObjQuestion.put("question", question.getQuestion());
	        		jsonObjQuestion.put("answer", question.getAnswers());
	        		
	        		jsonResult.put(jsonObjQuestion);

	        		
	        		
	             	return jsonResult;
	             	
	        		} catch (JSONException e) {
	        				
	        			e.printStackTrace();
	        		}
	         	    	
	
	     	    return null;
	     	     
	         }
	         
	      });
		
	}
	
	
	
	public void getStudentsbyInstitutionCourseYearPeriod(){
		
		get("/students/:institution/:course/:year/:period", new Route() {
			@Override
            public Object handle(final Request request, final Response response){
	        	
	        	response.header("Access-Control-Allow-Origin", "*");
	        	 
	        	
	        	
	        	String institution = request.params(":institution");
	        	String course = request.params(":course");
	        	Integer year = Integer.parseInt(request.params(":year"));
	            Integer period = Integer.parseInt(request.params(":period"));
	            
	            
	            try {
	            	List<Student> students = model.searchStudentsByInstitutionCourseYearPeriod(institution, course, year, period);
	            	
	            	JSONArray jsonResult = new JSONArray();
	         	    

	         	    for(Student student:students){
	         	    	JSONObject jsonObj = new JSONObject();
	         	    	jsonObj.put("name", student.getName());
	         	    	jsonObj.put("username", student.getUserName());
	         	    	jsonObj.put("ra", student.getRa());
	         	    	if(student.getCompleted()==false){
	         	    		jsonObj.put("status", 1);
	         	    	} else if(student.getCompleted()==true && student.getStatusComment()==false){
	         	    		jsonObj.put("status", 2);
	         	    	} else {
	         	    		jsonObj.put("status", 3);
	         	    	}
	         	    	jsonResult.put(jsonObj);
	         	    	
	         	    }

	             	
	             	return jsonResult;
	             	
	        		} catch (JSONException e) {
	        				
	        			e.printStackTrace();
	        		}
	         	    	
	
	     	    return null;
	     	     
	         }
	         
	      });

	         
	}
	
	
	public void setAnswerbyCode(){
		
		get("/answer/:ra/:questionNumber/:answerCode", new Route() {
			@Override
            public Object handle(final Request request, final Response response){
	        	
	        	response.header("Access-Control-Allow-Origin", "*");
	        	 
	        	
	        	
	        	Integer ra = Integer.parseInt(request.params(":ra"));
	        	Integer questionNumber = Integer.parseInt(request.params(":questionNumber"));
	        	Integer answerCode = Integer.parseInt(request.params(":answerCode"));
	            
	            
	            
	            try {
	            	
	            	int status = model.recordAnswer(ra, questionNumber, answerCode);
	            	
	            	if(status == 0){
	            		JSONArray jsonResult = new JSONArray();
		         	    
		         	    JSONObject jsonObj = new JSONObject();
		         	    	
		         	    jsonObj.put("status", "ok");
		         	    jsonResult.put(jsonObj);
		
		             	return jsonResult;
		             	
	            	} else {
	            		
	            		JSONArray jsonResult = new JSONArray();
		         	    
		         	    JSONObject jsonObj = new JSONObject();
		         	    	
		         	    jsonObj.put("status", "fim");
		         	    jsonResult.put(jsonObj);
		
		             	return jsonResult;
	            		
	            	}
	            	
	            	
	             	
	        		} catch (JSONException e) {
	        				
	        			e.printStackTrace();
	        		}
	         	    	
	
	     	    return null;
	     	     
	         }
	         
	      });

	         
	}
	
	
	public void getAllInstitutions(){
		
		get("/institutions", new Route() {
			@Override
            public Object handle(final Request request, final Response response){
	        	
	        	response.header("Access-Control-Allow-Origin", "*");

	        	JSONArray jsonResult = new JSONArray();
         	    
         	    
	        	
	            try {
	            	
	            	List<Institution> institutions = model.getAllInstitutions();
	            	
	            	for(Institution institution:institutions){
	            		
	            		JSONObject jsonObj = new JSONObject();
	            		jsonObj.put("institutionName", institution.getInstitutionName());
	            		jsonResult.put(jsonObj);
	            		
	            	}
	            	
	            	return jsonResult;
	            	
	            	
	             	
	        		} catch (JSONException e) {
	        				
	        			e.printStackTrace();
	        		}
	         	    	
	
	     	    return null;
	     	     
	         }
	         
	      });

	         
	}
	
	
	public void getCourses(){
		
		get("/courses/:institution", new Route() {
			@Override
            public Object handle(final Request request, final Response response){
	        	
	        	response.header("Access-Control-Allow-Origin", "*");

	        	
	        	String institutionName = request.params(":institution");
	        	
	        	JSONArray jsonResult = new JSONArray();
         	    
         	    
	        	
	            try {
	            	
	            	List<Course> courses = model.getCourses(institutionName);
	            	
	            	for(Course course:courses){
	            		
	            		JSONObject jsonObj = new JSONObject();
	            		jsonObj.put("courseName", course.getCourseName());
	            		jsonResult.put(jsonObj);
	            		
	            	}
	            	
	            	return jsonResult;
	            	
	            	
	             	
	        		} catch (JSONException e) {
	        				
	        			e.printStackTrace();
	        		}
	         	    	
	
	     	    return null;
	     	     
	         }
	         
	      });

	}
	
	
	public void setComments(){
			
			post("/comment/", new Route() {
				@Override
	            public Object handle(final Request request, final Response response){
		        	
		           response.header("Access-Control-Allow-Origin", "*");

		        	
		        	
		        	
		           JSONObject json = new JSONObject(request.body());
		        	
		           String comment = json.getString("comment");
		        	
		           int ra = Integer.parseInt(json.getString("ra"));
		        	
		           
	         	    
	         	   try {
		            	
		            	boolean status = model.setComment(ra, comment);;
		            	
		            	
		            	
		            	if(status==true){
		            		
		            		
		            		
		            		JSONArray jsonResult = new JSONArray();
		 	         	    JSONObject jsonObj = new JSONObject();
		     
			        		jsonObj.put("status", 1);
			        		
			        		
			             	jsonResult.put(jsonObj);
			             	
			             	
			             	
			             	return jsonResult;
		            		
		            	}
		            	
		            	
		             	
		        		} catch (JSONException e) {
		        				
		        			e.printStackTrace();
		        		}
	         	    
	         	    JSONArray jsonResult = new JSONArray();
	         	    JSONObject jsonObj = new JSONObject();
	         	   	
	         	    jsonObj.put("status", 0);
	        		
	        		
	             	jsonResult.put(jsonObj);
	             	
	             	return jsonResult;
	         	   
	         	   
		        	
			   }
			});     

	         
	}
	
	
	public void getAllCompetencies(){
		
		get("/competencies", new Route() {
			@Override
            public Object handle(final Request request, final Response response){
	        	
	        	response.header("Access-Control-Allow-Origin", "*");

	        	JSONArray jsonResult = new JSONArray();
         	    
         	    
	        	
	            try {
	            	
	            	List<Competency> competencies = model.getAllCompetencies();
	            	
	            	for(Competency competency:competencies){
	            		
	            		JSONObject jsonObj = new JSONObject();
	            		jsonObj.put("competency", competency.getName());
	            		jsonResult.put(jsonObj);
	            		
	            	}
	            	
	            	return jsonResult;
	            	
	            	
	             	
	        		} catch (JSONException e) {
	        				
	        			e.printStackTrace();
	        		}
	         	    	
	
	     	    return null;
	     	     
	         }
	         
	      });

	         
	}
	
	
	public void setQuestion(){
		
		post("/question/", new Route() {
			@Override
            public Object handle(final Request request, final Response response){
	        	
	           response.header("Access-Control-Allow-Origin", "*");

	        	
	        	
	        	
	           JSONObject json = new JSONObject(request.body());
	        	
	           String question = json.getString("question");
	        	
	           //int value1 = Integer.parseInt(json.getJSONArray("answer").get(0));
	        	
	           
         	    
         	   try {
	            	
	            	
	            		
	            		
	            		
	            		JSONArray jsonResult = new JSONArray();
	 	         	    JSONObject jsonObj = new JSONObject();
	     
		        		jsonObj.put("status", 1);
		        		
		        		
		             	jsonResult.put(jsonObj);
		             	
		             	
		             	
		             	return jsonResult;
	            		
	            	
	            	
	            	
	             	
	        		} catch (JSONException e) {
	        				
	        			e.printStackTrace();
	        		}
         	    
         	    JSONArray jsonResult = new JSONArray();
         	    JSONObject jsonObj = new JSONObject();
         	   	
         	    jsonObj.put("status", 0);
        		
        		
             	jsonResult.put(jsonObj);
             	
             	return jsonResult;
         	   
         	   
	        	
		   }
		});     

         
}
	
		
}
