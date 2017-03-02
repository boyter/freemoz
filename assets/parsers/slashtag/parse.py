'''
Parses out the slashtag data for the data from the following source
https://github.com/blekko/slashtag-data

TODO have this import into the database in a structured way
'''

import json

json_data = open('slastag.json')
data = json.load(json_data)

for key in data:
    title = key.split('/')[2].split('-')

    newtitle = '-'.join([word.title() for word in title])
    print newtitle

    if 'tags' in data[key]:
        for tag in data[key]['tags']:
            print tag

    if 'urls' in data[key]:
        for url in data[key]['urls']:
            print url
