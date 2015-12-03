define([], function () {
    var SampleData = {
        users: [{
            username: 'user 1',
            remark: 'user 1 remark',
            isAccountAdmin: true,
            isAppraisalAdmin: true
        }, {
            username: 'user 2',
            remark: 'user 2 remark',
            isAccountAdmin: false,
            isAppraisalAdmin: true
        }, {
            username: 'user 3',
            remark: 'user 3 remark',
            isAccountAdmin: true,
            isAppraisalAdmin: false
        }, {
            username: 'user 4',
            remark: 'user 4 remark',
            isAccountAdmin: false,
            isAppraisalAdmin: false
        }, {
            username: 'user 5',
            remark: 'user 5 remark',
            isAccountAdmin: false,
            isAppraisalAdmin: false
        }, {
            username: 'user 6',
            remark: 'user 6 remark',
            isAccountAdmin: false,
            isAppraisalAdmin: false
        }, {
            username: 'user 7',
            remark: 'user 7 remark',
            isAccountAdmin: false,
            isAppraisalAdmin: false
        }, {
            username: 'user 8',
            remark: 'user 8 remark',
            isAccountAdmin: false,
            isAppraisalAdmin: false
        }, {
            username: 'user 9',
            remark: 'user 9 remark',
            isAccountAdmin: false,
            isAppraisalAdmin: false
        }, {
            username: 'user 10',
            remark: 'user 10 remark',
            isAccountAdmin: false,
            isAppraisalAdmin: false
        }],

        indexes: [{
            id: 12,
            name: 'index 1',
            remark: 'index remark 1',
            point: 5,
            appraiser: 'user 1',
            appraisee: 'user 10'
        }, {
            id: 13,
            name: 'index 2',
            remark: 'index remark 2',
            point: 10,
            appraiser: 'user 2',
            appraisee: 'user 8'
        }, {
            id: 14,
            name: 'index 3',
            remark: 'index remark 3',
            point: 15,
            appraiser: 'user 3',
            appraisee: ''
        }],
        usedIndexes: [13, 14],

        plans: [{
            id: 1,
            name: 'plan 1',
            remark: 'plan remark 1',
            deadline: new Date(2049, 9, 30, 20, 0, 0),
            started: true
        }, {
            id: 2,
            name: 'plan 2',
            remark: 'plan remark 2',
            deadline: new Date(2020, 2, 22, 0, 0, 0),
            started: false
        }],

        plan_meta: {
            id: 50,
            name: 'plan 1',
            remark: 'plan 1 remark',
            deadline: new Date(2020, 5, 1)
        },
        plan_indexes: [{
            id: 12,
            weight: 2
        }, {
            id: 14,
            weight: 3
        }]
    };

    return SampleData;
});
