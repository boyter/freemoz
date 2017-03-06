import sqlite3


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

    print 'Fetching all topics...'
    for row in cursor.execute('''SELECT DISTINCT topic FROM content'''):
        topic = row[0]
        topics.append(topic)

    return topics


def insert_topics(topics):
    db = sqlite3.connect('freemoz-content.sqlite')
    cursor = db.cursor()

    for i, topic in enumerate(topics):
        cursor.execute('''INSERT INTO "main"."structure" ("parentid","topic") VALUES (?,?)''', (-1, topic))
        db.commit()

        if i % 10000 == 0:
            print i, topic
            db.commit()
    db.commit()


def find_parent(path):
    db = sqlite3.connect('freemoz-content.sqlite')

    cursor = db.cursor()
    haveparent = False

    for row in cursor.execute('''SELECT id FROM structure WHERE topic = ?''', (path,)):
        parent = row[0]
        return parent
    return None

def update_parent(topic, parentid):
    db = sqlite3.connect('freemoz-content.sqlite')
    cursor = db.cursor()

    cursor.execute('''UPDATE structure SET parentid = ? WHERE topic = ?''', (parentid, topic))
    db.commit()


def build_relations(topics):
    for topic in topics:
        depth = len(topic.split('/'))
        parent = None

        if depth != 3:
            for x in range(depth-1, 0, -1):
                root = '/'.join(topic.split('/')[:x])
                parent = find_parent(root)

                if parent:
                    break

        update_parent(topic=topic, parentid=parent)
        print 'Parent for ', topic, ' is ', parent


create_table()
topics = get_all_topics()
insert_topics(topics=topics)
build_relations(topics)
