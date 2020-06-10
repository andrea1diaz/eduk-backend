# Add the list of institutions to db

got to the file `instituciones_table.csv` location on the terminal and put the next command
```
	realpath instituciones_table.csv
```
and copy the output, next enter to the database
```
	psql eduk -u eduk
```
inside type the next command
```
	copy institutions(id, name) from 'PATH' delimiter ',' csv header;
```

example 
```
	eduk_db=# copy institutions(id, name) from '/Users/andreadiaz/Library/Mobile Documents/com~apple~CloudDocs/utec/software/eduk-backend/docs/instituciones_table.csv' delimiter ',' csv header;
```
