package org.mocktail
scenario "Verify recording for search query",{
	given "data is already available in the db",{
	}
	
	when "search with same parameters again", {
	}
	
	then "data should be retrieved from recording", {
	}
}

scenario "Insert the record in database",{
    when "data is inserted in the database", {
    }
    
    then "nothing happens in the database", {
    }
}

scenario "Update the record in database",{
    when "data is updated in the database", {
    }
    
    then "nothing happens in the database", {
    }
}

scenario "delete the record in database",{
    when "record is deleted in the database", {
    }
    
    then "nothing happens in the database", {
    }
}

scenario "database schema changes which should break the tests",{
    given "record a test case",{
    }
    
    when "change the schema", {
    }
    
    then "test should break", {
    }
}