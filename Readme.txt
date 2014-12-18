This application is composed of a server side implemented in PHP and a client side implemented in android.
The server side feeds the client with predefined lists of commodities, markets and unit of sale. These are entered through a website based back-end
and the client can retrieve them on starting.

The database has 4 tables: submissions (sub_id, vendor_name, market_id, commodity_id, price, unit_id), market (market_id, name), 
unit(unit_id, name) and commodity (commodity_id, name).
They keep track of the market, commodity and unit lists and also the vendor's submissions.

The client-side has two android activities: Submit.java which retrieves the markets, commodities and units of sale and the creates
the interface that lets vendors add submissions of current prices of their commodities.
The second activity is Submissions.java which allows vendors to view submissions of current prices of each commodity per market given a unit
of sale for each vendor who has submitted. 

The android activities are supported by 4 other classes: Market.java, Unit.java and Commodity.java which are data transfer objects
and JSONParser.java which parses the json strings from the server side.
