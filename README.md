Here is the endpoints the system supports:  

**New confirmed cases**
----
Get number of confirmed cases by country that fits to a specific date.

* **Method + URL**

   **```GET```** ```/confirmed/daily```

* **URL Params**

    *Required*  
    **```country```** = ```Country name starts with a capital letter```  
    **```date```** = ```A valid date in format 'dd-mm-yyyy'```

* **Success Response**

    **Status:** 200 OK  
    **Content:**  
    A number that fits to the given parameters.

* **Error Response**

    **Status:** 400 BAD REQUEST  
    If the given country OR the date is invalid.

* **Example**

    **```GET```** ```/confirmed/daily?country=Germany&date=13-05-2021```



**Compare between countries**
----
Calculate the daily difference between the percentages of the population confirmed cases.

* **Method + URL**

   **```GET```** ```/confirmed/compare```

* **URL Params**

    *Required*  
    **```sourceCountry```** = ```Country name starts with a capital letter```  
    **```targetcountry```** = ```Country name starts with a capital letter```  
    **```from```** = ```A valid date in format 'dd-mm-yyyy'```  
    **```to```** = ```A valid date in format 'dd-mm-yyyy'```

* **Success Response**

    **Status:** 200 OK  
    **Content:**  
    A list contains all the  differences in percentage between the source and the target country.

* **Error Response**

    **Status:** 400 BAD REQUEST  
    If at least one of the given countries OR the dates is invalid.

* **Example**

    **```GET```** ```/confirmed/compare?sourceCountry=Germany&targetcountry=France&from=12-05-2021&to=13-05-2021```



**Dockerize**
----
There are two ways to test this project:  
1. **Pull it directly from Docker Hub**  
    * login to your docker account by type the following:
    ```
    docker login
    ```
    * Open a command line and type the following command:
    ```
    docker pull nisandalva/covid19daily
    ```
    * Next, type this command:
    ```
    docker run -p PORT:8080 nisandalva/covid19daily
    ```
    Feel free to choose the PORT to whatever you want.


2. **Build and run it in your machine**
    * Clone this repository by typing the following:
    ```
    git clone https://github.com/NisanDalva/Covid19Daily.git
    ```
    
    * Open a command line and change the working directory to the folder contains all the project resources.

    * Type:
    ```
    docker build -t covid19daily .
    ```
    (Pay attention to the dot at the end of command)
    
    * Finally run the following:
    ```
    docker run -p PORT:8080 covid19daily
    ```
    Feel free to choose the PORT to whatever you want.
