var SubmissionModel = {
    currentlyloading: false,
    dirty: false,
    url: '',
    title: '',
    description: '',
    tags: '',

    updateUrl: function(value) {
        SubmissionModel.dirty = true; 
        SubmissionModel.url = value;
    },
    updateTitle: function(value) {
        SubmissionModel.dirty = true; 
        SubmissionModel.title = value;
    },
    updateDescription: function(value) {
        SubmissionModel.dirty = true; 
        SubmissionModel.description = value;
    },
    getNextSubmission: function() {
        SubmissionModel.currentlyloading = true;
        m.redraw();

        m.request({method: 'GET', url: '/api/v1/submission/'}).then(function(e) {
            SubmissionModel.currentlyloading = false;
            SubmissionModel.url = e.url;
            SubmissionModel.title = e.title;
            SubmissionModel.description = e.description;
            SubmissionModel.tags = e.tags;
            SubmissionModel.dirty = false;
        }).then(function(e) {
        });
    },
    getWordCount: function(value) {
        var count = value.split(' ').length;
        return  count === 1 ? count + ' word' : count + ' words';
    }
};

// Main component that does everything
var MainComponent = {
    oninit: SubmissionModel.getNextSubmission,
    view: function() {
        return m('div', [
                m('div', [
                    m('h4', 'Sample Display'),
                    m('dl', [
                        m('dt', [
                            m('span.glyphicon glyphicon-list-alt'),
                            m('a', {
                                'href': SubmissionModel.url, 
                                'target': '_new'
                            }, ' ' + SubmissionModel.title)
                        ]),
                        m('dd', SubmissionModel.description)
                    ])
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
                    m('p.help-block', 'Is the title meaningful? Short and descriptive? Is the spelling and grammer correct?')
                ]),
                m('div.form-group', [
                    m('label', 'Submission Description (~' +  SubmissionModel.getWordCount(SubmissionModel.description) + ')'),
                    m('textarea.form-control', {
                        oninput: m.withAttr('value', function(value) { SubmissionModel.updateDescription(value); })
                    }, SubmissionModel.description),
                    m('p.help-block', 'About 30 words? Is the description accurate? Is it brief and meaningful? Is the spelling and grammer correct?')
                ]),
                m('div.form-group', [
                    m('label', 'Submission Tags'),
                    m('input.form-control', {
                        value: SubmissionModel.tags,
                        maxlength: '255',
                    }, ''),
                    m('p.help-block', 'Are the tags applicable to this submission? Are there too many? Is the spelling and grammer correct?')
                ]),
                m('input.btn.btn-primary', {'type': 'submit', 'value': SubmissionModel.dirty ? 'Submit for Review' : 'Approve' }, '')
            ]);
    }
};

m.mount(document.getElementsByClassName('submissions')[0], MainComponent);
