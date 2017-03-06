import sqlite3
import json


def create_table():
    db = sqlite3.connect('freemoz-content.sqlite')
    cursor = db.cursor()
    cursor.execute('''DROP TABLE "main"."structure"''')
    db.commit()
    cursor.execute('''CREATE  TABLE "main"."structure" ("id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , "parentid" INTEGER, "topic" VARCHAR)''')
    db.commit()


def get_all_topics():
    db = sqlite3.connect('freemoz-content.sqlite')
    cursor = db.cursor()

    topics = []

    for row in cursor.execute('SELECT DISTINCT topic FROM content'):

        topic = row[0]
        topics.append(topic)

    return topics


def insert_topics(topics):
    db = sqlite3.connect('freemoz-content.sqlite')
    cursor = db.cursor()

    for i, topic in enumerate(topics):
        cursor.execute('''INSERT INTO "main"."structure" ("parentid","topic") VALUES (?,?)''', (-1, topic))
        db.commit()

        if i % 100 == 0:
            db.commit()
    db.commit()


create_table()
topics = get_all_topics()
insert_topics(topics=topics)

# depth = len(topic.split('/'))
# root = '/'.join(topic.split('/')[:len(topic.split('/')) - 1])
