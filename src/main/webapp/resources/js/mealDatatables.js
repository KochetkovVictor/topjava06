var ajaxUrl = 'ajax/profile/meals/';
var datatableApi;

function updateTable() {
    $.get(ajaxUrl, function (data) {
        updateTableByData(data);
    });
}
function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + 'filter',
        data: $('#filter').serialize(),
        success: function (data) {
            updateTableByData(data);
        }
    });
    return false;
}
$(function () {
    datatableApi = $('#datatable').DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },

            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ],
        "createdRow": function (row, data, dataIndex) {
            $(row).addClass(data.exceed ? 'exceeded' : 'normal');
        },

        "initComplete": function () {
            $('#filter').submit(function () {
                updateTable();
                return false;
            });
            makeEditable();
        }
    });
})
;