// Main component that does everything
var MainComponent = {
    view: function(ctrl) {
        return m("div", [
                m('input', {'value': SubmissionModel.url}, ''),
                m('input', {'value': SubmissionModel.title}, ''),
                m('textarea', SubmissionModel.description),
                m('input', {'value': SubmissionModel.tags}, ''),
            ]);
    }
};


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
            // Do something?
        });
    },
};

m.mount(document.getElementsByClassName('submissions')[0], MainComponent);
SubmissionModel.getNextSubmission()