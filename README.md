# Taco Loco Order Total Webservice
#### Quick Start Guide
*If you already have Maven installed skip to step 3
1. Download and extract the latest version of [Maven](https://maven.apache.org/download.cgi)
2. Add the `bin` directory from the newly created apache-maven-3.8.6 directory to your PATH environment variable.

	**2.1 For Windows:**
  
		Open system properties, select the "Advanced" tab, and the "Environment Variables" button at the bottom. 
    
		Create a new variable called MVN_HOME with a value of C:/path/to/apache-maven-3.8.6/bin
    
		Edit the `Path` variable to include MVN_HOME
    
	**2.2 For Mac or Linux:**
  
		From a terminal or console, run the command : export PATH=/path/to/apache-maven-3.8.6/bin:$PATH
    
3. Clone the repository https://github.com/WILD3IV/TacoLoco.git to your local drive.
>The application.properties file sets the server.port=0 which will randomize the port it runs on. You can change it before compiling the jar or retrieve the assigned port from the console when running the jar.
4. Open Widows PowerShell or a terminal/console window, `cd` to the `../TacoLoco/order_total/` directory of the project on your computer.
5. Run the command `mvn package` to compile the jar. When complete the jar can be found it the project directory `../order_total/targets/`
6. `cd` to the `../order_total/targets/` directory and run `java -jar ./order_total-0.0.1-SNAPSHOT.jar`


### JSON Request Body format
```
{
    "orderItems":[
        {
            "itemType": "Veggie Taco",
            "itemQty": 5,
            "itemPrice": null
        },
        {
            "itemType": "Beef Taco",
            "itemQty": 5,
            "itemPrice": null
        },
        {
            "itemType": "Chicken Taco",
            "itemQty": 5,
            "itemPrice": null
        },
        {
            "itemType": "Chorizo Taco",
            "itemQty": 5,
            "itemPrice": null
        }
    ]
 
}
```
