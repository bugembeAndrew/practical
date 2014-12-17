<?php

//load and connect to MySQL database stuff
require("config.inc.php");

if (!empty($_POST)) {
	//initial query
	$query = "INSERT INTO unit ( unit_id, unit_name ) VALUES ( '', :name ) ";

    //Update query
    $query_params = array(
        ':name' => $_POST['name']
    );
  
	//execute query
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        // For testing, you could use a die and message. 
        //die("Failed to run query: " . $ex->getMessage());
        
        //or just use this use this one:
        $response["success"] = 0;
        $response["message"] = "Database Error. Couldn't add unit!";
        die(json_encode($response));
    }

    $response["success"] = 1;
    $response["message"] = "Unit of sale successfully added!";
    echo json_encode($response);
   
} else {
    ?>
        <html>
            <head>
                <title>Submit Current Commodity Prices</title>
                <link rel="stylesheet" type="text/css" href="style.css"/>
            </head>
            <body>
                <div id="main_container">
            		<h3>Add a unit of sale</h3> 
            		<form action="add_unit.php" method="post" align="center"> 
            		    <input type="text" name="name" placeholder="Unit of sale" /> 
            		    <br /><br />
            		    <input type="submit" value="Save" /> 
            		</form>
                </dv>
            </body>
        </html>
	<?php
}

?> 
