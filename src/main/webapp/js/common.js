requirejs.config({
    // baseUrl: '.',
    waitSeconds: 30,
    shim: {
        'bootstrap': {deps: ['jquery']},
        'sha256': {deps: [], exports: 'sha256'}
    },
    paths: {
        'jquery': '../vendor/jquery/dist/jquery',
        'bootstrap': '../vendor/bootstrap/dist/js/bootstrap',
        'datatables.net': '../vendor/datatables.net/js/jquery.dataTables',
        'datatables.net-bs': '../vendor/datatables.net-bs/js/dataTables.bootstrap',
        'datatables.net-responsive': '../vendor/datatables.net-responsive/js/dataTables.responsive',
        'moment': '../vendor/moment/moment',
        'bootstrap-datetimepicker': '../vendor/eonasdan-bootstrap-datetimepicker/src/js/bootstrap-datetimepicker',
        'sha256': '../vendor/js-sha256/src/sha256'
    }
});
