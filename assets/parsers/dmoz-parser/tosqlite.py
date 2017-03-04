import sqlite3
import json

db = sqlite3.connect('freemoz-content.sqlite')

cursor = db.cursor()
cursor.execute('''CREATE TABLE IF NOT EXISTS "main"."content" ("topic" VARCHAR, "title" VARCHAR, "description" TEXT, "url" VARCHAR, "data" TEXT);''')
db.commit()


count = 0
for line in open('parsed.json'):
    count = count + 1
    cursor = db.cursor()

    content = json.loads(line)

    if 'topic' in content and 'd:Title' in content and 'd:Description' in content and 'url' in content:
        cursor.execute('''INSERT INTO "main"."content" ("topic","title","description","url","data") VALUES (?,?,?,?,?);''', (content['topic'], content['d:Title'], content['d:Description'], content['url'], '{}'))
    else:
        print 'nope'

    if count % 1000 == 0:
        db.commit()
db.commit()
