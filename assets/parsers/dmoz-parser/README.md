The files in here are used to convert the DMOZ data dumps into something useful. They need to be run with
https://github.com/kremso/dmoz-parser 

To build the database you need to first do the following,

1. Download DMOZ data
2. Run the dmoz-parser against the content file to produce parsed.json
3. Run tosqlite.py 
4. Run buildstructure.py

The result will be a single sqlite file named freemoz-content.sqlite which will contain