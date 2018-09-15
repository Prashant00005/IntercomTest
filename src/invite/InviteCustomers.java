package invite;

import java.io.*;
import java.io.FileReader; 
import java.util.Map;  
import org.json.simple.JSONObject; 
import org.json.simple.parser.*; 
import java.util.HashMap;
import java.util.TreeMap;

class InviteCustomers
{
	public static void main (String[] args) throws java.lang.Exception
	{

        BufferedReader br = null;
        JSONParser parser = new JSONParser();
		

        try {

			String readCustomer;
			
			br = new BufferedReader(new FileReader("/Users/WolfDen/Downloads/customer.txt"));  //Enter path of customer.txt file
			Map<Integer, String> customerData = new HashMap<Integer, String>();
			
            while ((readCustomer = br.readLine()) != null) {

                try {
                		Object obj = parser.parse(readCustomer);
                    JSONObject jsonObject = (JSONObject) obj;
                   
					String name = (String) jsonObject.get("name");
					int user_id = (int) (long) jsonObject.get("user_id");
					Double latitude = Double.valueOf((String) jsonObject.get("latitude"));
					Double longitude = Double.valueOf((String) jsonObject.get("longitude"));
					Double officeLat = Double.valueOf("53.339428");
					Double officeLong = Double.valueOf("-6.257664");
					
					Double dist = distanceCalculator(officeLat, officeLong, latitude, longitude);
					
					if(dist<100)
					customerData.put(user_id, name); 
	

                } catch (ParseException e) {
                    
                    e.printStackTrace();
                }
            }
            //Sort customerData hashmap as per question requirement
            Map<Integer,String> sortedCustomerData = new TreeMap<Integer,String>(customerData);
            displayCustomerData(sortedCustomerData); //Display relevant user id and customer name
			
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        } 
        finally 
        		{
	            try 
	            {
	                if (br != null)
	                	br.close();
	            } 
	            catch (IOException ex) 
		            {
		                ex.printStackTrace();
		            }
        		}
        
	}
	
	private static double distanceCalculator(double latitudePoint1, double longitudePoint1, double latitudePoint2, double longitudePoint2) {
		double theta = longitudePoint1 - longitudePoint2; //absolute difference
		double distance = Math.sin(degreeToRadian(latitudePoint1)) * Math.sin(degreeToRadian(latitudePoint2)) + Math.cos(degreeToRadian(latitudePoint1)) * Math.cos(degreeToRadian(latitudePoint2)) * Math.cos(degreeToRadian(theta));
		distance = Math.acos(distance);
		distance = radianToDegree(distance);
		distance = distance * 60 * 1.1515;
		distance = distance * 1.609344;
		return distance;
	}


	private static double degreeToRadian(double degree) {
		return (degree * Math.PI / 180.0);
	}

	private static double radianToDegree(double radians) {
		return (radians * 180 / Math.PI);
	}
	
	private static void displayCustomerData(Map<Integer,String> sortedCustomerData) {
		
		for (Map.Entry<Integer, String> entry : sortedCustomerData.entrySet()) {
            System.out.println("User ID: " + entry.getKey() + " Customer Name: " + entry.getValue());
        }
		
	}
	
}