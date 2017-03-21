var SubmissionModel = {
    currentlyloading: false,
    url: '',
    title: '',
    description: '',
    tags: '',

    getNextSubmission: function() {
        SubmissionModel.currentlyloading = true;
        m.redraw();

        m.request({method: 'GET', url: '/api/v1/submission/'}).then(function(e) {
            SubmissionModel.currentlyloading = false;
            SubmissionModel.url = e.url;
            SubmissionModel.title = e.title;
            SubmissionModel.description = e.description;
            SubmissionModel.tags = e.tags;
        }).then( function(e) {
        });
    },
};

// Main component that does everything
var MainComponent = {
    controller: function() {
        return {
            getNextSubmission: function() {
                SubmissionModel.getNextSubmission();
            }
        }
    },
    view: function(ctrl) {
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
                        'value': SubmissionModel.url, 
                        'onkeydown': function (e) { 
                            m.withAttr('value', SubmissionModel.url);
                        }
                    }, ''),
                    m('p.help-block', 'Is this URL already in the database? Is it HTTPS?')
                ]),
                m('div.form-group', [
                    m('label', 'Submission Title'),
                    m('input.form-control', {'value': SubmissionModel.title}, ''),
                    m('p.help-block', 'Is the title meaningful? Short and descriptive?')
                ]),
                m('div.form-group', [
                    m('label', 'Submission Description'),
                    m('textarea.form-control', SubmissionModel.description),
                    m('p.help-block', 'Is the derscription accurate? Is it brief and meaningful?')
                ]),
                m('div.form-group', [
                    m('label', 'Submission Tags'),
                    m('input.form-control', {'value': SubmissionModel.tags}, ''),
                    m('p.help-block', 'Are the tags applicable to this submission? Are there too many?')
                ]) 
            ]);
    }
};

m.mount(document.getElementsByClassName('submissions')[0], MainComponent);
SubmissionModel.getNextSubmission()