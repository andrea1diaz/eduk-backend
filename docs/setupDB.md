# How to Set-Up the Local Database

This is a step-by-step guide on how to setup the local database in order for the backend to connect with it. In order for it to work you only need to have a `postgres` user, or any other user with `su` privileges in your `psql`.

To enter as the root user of postgres just use:

```
psql --u postgres
```

## Create the eduk User

To create the user enter the following commands in order:

```
createuser --interactive --pwprompt
```

For the username and password just write `eduk`.

## Permissions of the new user

The new user shall not have permission to create databases or new roles, so to both prompts just write `n`.

## Creating the new Database

To create the new database use the following command.

```
createdb -O postgres eduk_db
```

## Try connecting user to Database

To connect to the "eduk\_db" database with the eduk user, run:

```
psql eduk_db -U eduk
```

If you get:
``` 
"FATAL: Peer authentication failed for user "eduk""
```

Go to file "pg\_hba.conf" and change
```
local   all              all                                     peer
```

to

```
local   all              all                                     trust
