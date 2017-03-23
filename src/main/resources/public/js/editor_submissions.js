var VectorSpace = {
    cache: {},
    buildConcordance: function(string) {
        if (VectorSpace.cache[string] !== undefined) {
            return VectorSpace.cache[string];
        }

        string = string.toLowerCase();
        string = string.replace(/\W+/g, ' ');
        string = string.replace(/\s+/g, ' ');

        var concordance = {};

        var split = string.split(' ');

        for (var i = 0; i < split.length; i++) {
            if (concordance[split[i]] === undefined) {
                concordance[split[i]] = 1;
            }
            else {
                concordance[split[i]] = concordance[split[i]] + 1;
            }
        }

        VectorSpace.cache[string] = concordance;

        return concordance;
    },
    relation: function(con1, con2) {
        var magnitude = function(concordance) {
            var total = 0;

            for (var key in concordance) {
                total += Math.pow(concordance[key], 2);
            }

            return Math.sqrt(total);
        };

        var topvalue = 0;
        var mag = magnitude(con1) * magnitude(con2);


        for (var k in con1) {
            if (con2[k] !== undefined) {
                topvalue = topvalue + (con1[k] * con2[k])
            }
        }

        if (mag != 0) {
            return topvalue / mag;
        }

        return 0;
    }
}


var SearchModel = {
    resultsUrl: [],
    resultsTitle: [],
    resultsDescription: [],
    getSimilarByUrl: _.debounce(function(searchterm) {
        searchterm = searchterm.replace(/\W+/g, ' ');
        m.request({method: 'GET', url: '/api/v1/searchurl/?q=' + encodeURIComponent(searchterm)}).then(function(e) {
            if (e && e.results) {
                SearchModel.resultsUrl = e.results;
            }
        });
    }, 300),
    getSimilarByTitle: _.debounce(function(searchterm) {
        searchterm = searchterm.replace(/\W+/g, ' ');
        m.request({method: 'GET', url: '/api/v1/search/?q=' + encodeURIComponent(searchterm)}).then(function(e) {
            if (e && e.results) {
                SearchModel.resultsTitle = e.results;
            }
        });
    }, 300),
    getSimilarByDescription: _.debounce(function(searchterm) {
        searchterm = searchterm.replace(/\W+/g, ' ');
        m.request({method: 'GET', url: '/api/v1/searchdescription/?q=' + encodeURIComponent(searchterm)}).then(function(e) {
            if (e && e.results) {
                SearchModel.resultsDescription = e.results;
            }
        });
    }, 300),
    getPossibleDuplicates: function() {
        var result = _.first(SearchModel.resultsTitle, 5).concat(
            _.first(SearchModel.resultsUrl, 5)
        ).concat(
            _.first(SearchModel.resultsDescription, 5)
        );

        result = _.uniq(result);

        result.sort(
            function(x, y) {
                var con1 = VectorSpace.buildConcordance([x.url, x.title, x.description].join(' '))
                var con2 = VectorSpace.buildConcordance([y.url, y.title, y.description].join(' '))
                var con3 = VectorSpace.buildConcordance([SubmissionModel.url, SubmissionModel.title, SubmissionModel.description].join(' '))

                return VectorSpace.relation(con2, con3) - VectorSpace.relation(con1, con3);
            }
        );

        return result;
    }
};

var SubmissionModel = {
    currentlyloading: false,
    dirty: false,
    url: '',
    title: '',
    description: '',
    tags: '',
    interval: null,
    countdown: 300,

    updateUrl: function(value) {
        SubmissionModel.dirty = true; 
        SubmissionModel.url = value;
        SearchModel.getSimilarByUrl(SubmissionModel.url);
    },
    updateTitle: function(value) {
        SubmissionModel.dirty = true; 
        SubmissionModel.title = value;
        SearchModel.getSimilarByTitle(SubmissionModel.title);
    },
    updateDescription: function(value) {
        SubmissionModel.dirty = true; 
        SubmissionModel.description = value;
        SearchModel.getSimilarByDescription(SubmissionModel.description);
    },
    updateTags: function(value) {
        SubmissionModel.dirty = true; 
        SubmissionModel.tags = value;
    },
    getNextSubmission: function() {
        SubmissionModel.currentlyloading = true;
        clearInterval(SubmissionModel.interval);
        m.redraw();

        m.request({method: 'GET', url: '/api/v1/submission/'}).then(function(e) {
            SubmissionModel.currentlyloading = false;
            SubmissionModel.url = e.url;
            SearchModel.getSimilarByUrl(e.url);
            SubmissionModel.title = e.title;
            SearchModel.getSimilarByTitle(e.title);
            SubmissionModel.description = e.description;
            SearchModel.getSimilarByDescription(SubmissionModel.description);
            SubmissionModel.tags = e.tags;
            SubmissionModel.dirty = false;
            SubmissionModel.countdown = 300;

            SubmissionModel.interval = setInterval(function() { 
                if (SubmissionModel.countdown > 0) {
                    SubmissionModel.countdown--;
                    m.redraw();
                }
            }, 1000);


        }).then(function(e) {
        });
    },
    getWordCount: function(value) {
        var count = value.split(' ').length;
        return  count === 1 ? count + ' word' : count + ' words';
    }
};

//glyphicon glyphicon-time

// Main component that does everything
var MainComponent = {
    oninit: SubmissionModel.getNextSubmission,
    view: function() {
        return m('div', [
                m('div', [
                    m('h4', 'Preview of Submission'),
                    m('div.sample-display', [ 
                        m('dl', [
                            m('dt', [
                                m('span.glyphicon glyphicon-list-alt'),
                                m('a', {
                                    'href': SubmissionModel.url, 
                                    'target': '_new'
                                }, ' ' + SubmissionModel.title),
                                m('small', ' (' + SubmissionModel.url + ')')
                            ]),
                            m('dd', [
                                m('span', SubmissionModel.description),
                                m('br'),
                                m('small', SubmissionModel.tags)
                            ])
                        ]),
                        m('p', [
                            m('small', 'Possible Duplicates '),
                            _.map(SearchModel.getPossibleDuplicates(), function(res, ind) {
                                return m('small', [
                                    m('a', { href: res.url }, res.title + ' (' +  res.url + ')'),
                                    m('span', ' ')
                                ])
                            })
                        ]),
                    ])
                ]),
                m('div.alert.alert-info', {role: 'alert'}, [
                    m('span.glyphicon.glyphicon-time', ''),
                    m('span', ' You can edit/approve this submission for another ' + SubmissionModel.countdown + ' seconds before it is returned to the queue.')
                ]),
                m('div.form-group', [
                    m('label', 'Submission URL'),
                    m('input.form-control', {
                        value: SubmissionModel.url, 
                        maxlength: '1000',
                        oninput: m.withAttr('value', function(value) { SubmissionModel.updateUrl(value); })
                    }, ''),
                    m('p.help-block', 'Is this URL already in the database? Is it HTTPS?')
                ]),
                m('div.form-group', [
                    m('label', 'Submission Title'),
                    m('input.form-control', {
                        value: SubmissionModel.title,
                        maxlength: '255',
                        oninput: m.withAttr('value', function(value) { SubmissionModel.updateTitle(value); })
                    }, ''),
                    m('p.help-block', 'Is the title meaningful? Short and descriptive? Is the spelling and grammar correct?')
                ]),
                m('div.form-group', [
                    m('label', 'Submission Description (~' +  SubmissionModel.getWordCount(SubmissionModel.description) + ')'),
                    m('textarea.form-control', {
                        oninput: m.withAttr('value', function(value) { SubmissionModel.updateDescription(value); })
                    }, SubmissionModel.description),
                    m('p.help-block', 'About 30 words? Is the description accurate? Is it brief and meaningful? Is the spelling and grammar correct?')
                ]),
                m('div.form-group', [
                    m('label', 'Submission Tags'),
                    m('input.form-control', {
                        value: SubmissionModel.tags,
                        maxlength: '255',
                        oninput: m.withAttr('value', function(value) { SubmissionModel.updateTags(value); })
                    }, ''),
                    m('p.help-block', 'Are the tags applicable to this submission? Are there too many? Is the spelling and grammar correct?')
                ]),
                m('input.btn.btn-primary', {'type': 'submit', 'value': SubmissionModel.dirty ? 'Submit for Review' : 'Approve' }, ''),
                m('span', ' '),
                m('input.btn.btn-danger', {'type': 'submit', 'value': SubmissionModel.dirty ? 'Submit for Review' : 'Reject' }, ''),
                m('span', ' '),
                m('input.btn.btn-default', {'type': 'submit', 'value': 'Request Another Submission'}, '')
            ]);
    }
};

m.mount(document.getElementsByClassName('submissions')[0], MainComponent);
