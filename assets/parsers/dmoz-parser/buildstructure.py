import sqlite3


'''
Builds on tosqlite.py by taking the database / table it builds
and works out the parents for most of the structure
'''

def create_table():
    db = sqlite3.connect('freemoz-content.sqlite')
    cursor = db.cursor()
    try:
        cursor.execute('''DROP TABLE "main"."structure"''')
        db.commit()
    except:
        pass
    cursor.execute('''CREATE  TABLE "main"."structure" ("id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , "parentid" INTEGER, "topic" VARCHAR)''')
    db.commit()


def get_all_topics_from_content():
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



find_parent_cache = {}
def find_parent(id, path, topics, fast=True):
    '''The fast option works by assuming that what we are looking for is around
    where the current one is in the database and trying there first'''

    if path in find_parent_cache:
        return find_parent_cache[path]

    start = 0
    if fast:
        start = id - 10000
        if start < 0:
            start = 0
    end = id + 10000

    parent = [x for x in topics[start:end] if x[2] == path]

    if len(parent) != 0:
        parent = parent[0]

    find_parent_cache[path] = parent
    return parent


def update_parents(toupdate):
    db = sqlite3.connect('freemoz-content.sqlite')
    cursor = db.cursor()

    for x in toupdate:
        cursor.execute('''UPDATE structure SET parentid = ? WHERE id = ?''', (x[1], x[0]))
    db.commit()


def build_relations(fast=True):
    # Get all the topics from the new tables
    db = sqlite3.connect('freemoz-content.sqlite')
    cursor = db.cursor()

    topics = []

    print 'Fetching all topics...'
    if fast:
        for row in cursor.execute('''SELECT id, parentid, topic FROM structure'''):
            topics.append(row)
    else:
        for row in cursor.execute('''SELECT id, parentid, topic FROM structure WHERE parentid = -1'''):
            topics.append(row)

    toupdate = []

    for topic in topics:
        depth = len(topic[2].split('/'))
        parent = None

        for x in range(depth-1, 0, -1):
            root = '/'.join(topic[2].split('/')[:x])
            parent = find_parent(topic[0], root, topics, fast)

            if parent:
                toupdate.append([topic[0], parent[0]])
                break

        if len(toupdate) == 10000:
            print 'Updating Parents...', topic[0]
            update_parents(toupdate)
            toupdate = []

    print 'Final Updating Parents...'
    update_parents(toupdate)


def get_all_roots(topic):
    roots = []
    depth = len(topic.split('/'))
    for x in range(depth-1, 0, -1):
        root = '/'.join(topic.split('/')[:x])
        roots.append(root)
    return list(set(roots))


def expand_topics(topics):
    print 'Expanding out topics...'
    newtopics = {}

    for i, topic in enumerate(topics):
        newtopics[topic] = 1

        for root in get_all_roots(topic):
            newtopics[root] = 1

        if i % 10000 == 0:
            print 'New Length', len(newtopics)

    newtopics = newtopics.keys()
    newtopics.sort()
    return newtopics


def update_content_parents(parentid, toupdate):
    db = sqlite3.connect('freemoz-content.sqlite')
    cursor = db.cursor()

    cursor.execute('''UPDATE content SET parentid = ? WHERE id = ?''', (parentid, toupdate))
    db.commit()


def update_content_topics():
    print 'Updating content...'

    db = sqlite3.connect('freemoz-content.sqlite')
    cursor = db.cursor()

    topics_dict = {}
    print 'Fetching all topics...'
    for row in cursor.execute('''SELECT id, parentid, topic FROM structure'''):
        topics_dict[row[2]] = row[0]

    content = []
    print 'Fetching all content...'
    for row in cursor.execute('''SELECT id, topic FROM content'''):
        content.append(row)

    for i, con in enumerate(content):

        if con[1] in topics_dict:
            update_content_parents(topics_dict[con[1]], con[0])

        if i % 1000 == 0:
            print 'Update', con

            # for i, topic in enumerate(topics):
            #     toupdate = [x for x in content if x[1] == topic[2]]

            #     if i % 1000 == 0:
            #         print 'Update', topic[2], 'children', len(toupdate)

            #     update_content_parents(topic[0], toupdate)


def create_indexes():
    print 'Creating indexes...'

    db = sqlite3.connect('freemoz-content.sqlite')
    cursor = db.cursor()

    cursor.execute('''CREATE INDEX structure_parentid_idx ON structure (parentid);''')
    db.commit()
    cursor.execute('''CREATE INDEX structure_topic_idx ON structure (topic);''')
    db.commit()
    cursor.execute('''CREATE INDEX content_parentid_idx ON content (parentid);''')
    db.commit()


create_table()
topics = get_all_topics_from_content()
topics = expand_topics(topics)
insert_topics(topics=topics)
build_relations()
build_relations(False)
update_content_topics()
create_indexes()
