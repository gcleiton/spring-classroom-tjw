function sendAlert(header, content, type, forceReload = false){
    Swal.fire({
        title: header,
        text: content,
        type: type,
        confirmButtonText: "OK",
        closeOnClickOutside: false,
        allowOutsideClick: false
    }).then((result) => {
        console.log(result)
        console.log(forceReload)
        if(result.isConfirmed && forceReload)
            window.location.reload()
    })
}

function showErrorMessage(header, description, forceReload = false) {
    sendAlert(header, description,"error", forceReload);
}

function showSuccessMessage(header, description, forceReload = false) {
    sendAlert(header, description,"success", forceReload);
}

function closeModal(modalId) {
    $(modalId).modal('hide')
}

function resetForm(formId) {
    $(formId)[0].reset();
}

function showErrors(errors){
    $('.alert-danger').removeClass("d-none");
    $(".alert-danger").html('');
    $.each(errors, function(key, value) {
        $(".alert-danger").append("<li>" + value + "</li>");
    });
}

function resetErrors(){
    $('.alert-danger').addClass("d-none");
    $(".alert-danger").html('');
}

const dataTableTranslation = {
    "processing": '<div>Carregando dados...</div>',
    "emptyTable": "Nenhum registro encontrado no banco de dados",
    "info": "Mostrando registros de _START_ até _END_ de um total de _TOTAL_ registros",
    "infoEmpty": "Mostrando registros de 0 até 0 de um total de 0 registros",
    "paginate": {
        "previous": "Anterior",
        "next": "Próximo"
    },
}

function formDataToObject(formData) {
    var obj = {};
    for (var pair of formData.entries()) {
        obj[pair[0]] = pair[1] === '' ? null : pair[1]
    }
    return obj;
}
