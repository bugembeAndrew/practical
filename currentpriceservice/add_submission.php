<?php

#connection information
require("config.inc.php");
#market, commodity and unit information
include 'get_market.php';
include 'get_commodity.php';
include 'get_unit.php';
#clean any output
ob_clean();

//if posted data is not empty
if (!empty($_POST)) {
    //If the username or password is empty when the user submits
    //the form, the page will die.
    //Using die isn't a very good practice, you may want to look into
    //displaying an error message within the form instead.  
    //We could also do front-end form validation from within our Android App,
    //but it is good to have a have the back-end code do a double check.
    if (empty($_POST['name']) || empty($_POST['markets']) || empty($_POST['commodities']) || empty($_POST['price']) || empty($_POST['units'])) {
        
        
        // Create some data that will be the JSON response 
        $response["success"] = 0;
        $response["message"] = "Please note that all fields are required";
        
        //die will kill the page and not execute any code below, it will also
        //display the parameter... in this case the JSON data our Android
        //app will parse
        die(json_encode($response));
    }
    
    //If we have made it here without dying, then we are in the clear to 
    //create a new user.  Let's setup our new query to create a user.  
    //Again, to protect against sql injects, user tokens such as :user and :pass
    $query = "INSERT INTO submissions ( sub_id, vendor_name, market_id, commodity_id, price, unit_id ) VALUES (  '', :name, :market, :commodity, :price, :unit ) ";
    
    //Again, we need to update our tokens with the actual data:
    $query_params = array(
        ':name' => $_POST['name'],
        ':market' => $_POST['markets'],
        ':commodity' => $_POST['commodities'],
        ':price' => $_POST['price'],
        ':unit' => $_POST['units']
    );
    
    //time to run our query, and create the user
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        // For testing, you could use a die and message. 
        //die("Failed to run query: " . $ex->getMessage());
        
        //or just use this use this one:
        $response["success"] = 0;
        $response["message"] = "Database Error. Please Try Again!";
        die(json_encode($response));
    }
    
    //If we have made it this far without dying, we have successfully added
    //a new user to our database.  We could do a few things here, such as 
    //redirect to the login page.  Instead we are going to echo out some
    //json data that will be read by the Android application, which will login
    //the user (or redirect to a different activity, I'm not sure yet..)
    $response["success"] = 1;
    $response["message"] = "Your submission was successfully added!";
    echo json_encode($response);
    
    //for a php webservice you could do a simple redirect and die.
    //header("Location: login.php"); 
    //die("Redirecting to login.php");
    
    
} else {
    ?>
        <html>
            <head>
                <title>Submit Current Commodity Prices</title>
                <link rel="stylesheet" type="text/css" href="style.css"/>
            </head>
            <body>
                <div id="main_container">
                	<h3>Update Commodity Prices</h3> 
                	<form action="add_submission.php" method="post" align="center"> 
                	    <input type="text" name="name" value="" placeholder="Vendor's name"/> 
                	    <br /><br /> 
                	    <?php 
                            echo 'Vendor\'s market: <select name="markets">';
                            foreach( (array) $response["markets"] as $mkts ){
                                echo '<option value = "'.$mkts['market_id'].'" selected = "selected">'.$mkts['market_name'].'</option>';
                            }
                            echo '</select><br/>';  
                        ?>
                	    <br /><br /> 
                        <?php 
                            echo 'Commodity for sale: <select name="commodities">';
                            foreach( (array) $response["commodities"] as $cmds ){
                                echo '<option value = "'.$cmds['commodity_id'].'" selected = "selected">'.$cmds['commodity_name'].'</option>';
                            }
                            echo '</select><br/>'; 
                        ?>
                        <br /><br />
                        <input type="text" name="price" value="" placeholder="Current price"/> 
                        <br /><br />
                        <?php 
                            echo 'Unit of sale: <select name="units">';
                            foreach( (array) $response["units"] as $units ){
                                echo '<option value = "'.$units['unit_id'].'" selected = "selected"> per '.$units['unit_name'].'</option>';
                            }
                            echo '</select><br/>';
                        ?>
                        <br /><br />
                	    <input type="submit" value="Submit update" /> 
                	</form>
                </dv>
            </body>
        </html>
	<?php
}

?>
