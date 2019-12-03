import java.io.FileReader;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mongodb.*;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import com.mongodb.client.model.Sorts;
import java.util.Arrays;
import org.bson.Document;
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

/**
 * Servlet implementation class Schedule
 */
public class Schedule extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Schedule() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ConnectionString connString = new ConnectionString(
			"mongodb+srv://password:username@cluster0-v2kcb.gcp.mongodb.net/test?retryWrites=true&w=majority"
		);
		MongoClientSettings settings = MongoClientSettings.builder()
		    .applyConnectionString(connString)
		    .retryWrites(true)
		    .build();
		MongoClient mongoClient = MongoClients.create(settings);
		MongoDatabase database = mongoClient.getDatabase("ViterbiSchedule");
		MongoCollection<Document> collection = database.getCollection("Majors");
		Document user = collection.find(eq("_id", request.getParameter("major"))).first();
		session.setAttribute("user", user.toJson());
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/schedule.jsp");
        dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		ConnectionString connString = new ConnectionString(
			"mongodb+srv://password:username@cluster0-v2kcb.gcp.mongodb.net/test?retryWrites=true&w=majority"
		);
		MongoClientSettings settings = MongoClientSettings.builder()
		    .applyConnectionString(connString)
		    .retryWrites(true)
		    .build();
		MongoClient mongoClient = MongoClients.create(settings);
		MongoDatabase database = mongoClient.getDatabase("ViterbiSchedule");
		MongoCollection<Document> collection = database.getCollection("Users");
		String username = "";
		try {
			JSONObject jo = (JSONObject) new JSONParser().parse((String) session.getAttribute("user"));
			username = (String) jo.get("_id");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		username = "nneven@usc.edu";
		String schedule = request.getParameter("schedule");
		BasicDBObject test = new BasicDBObject().parse(schedule);
		collection.updateOne(eq("_id", username), new Document("$set", new Document(test.toMap())));
		Document user = collection.find(eq("_id", username)).first();
		session.setAttribute("user", user.toJson());
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/schedule.jsp");
        dispatch.forward(request, response);
	}

}
