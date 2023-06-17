$(document).ready(function () {
    const usuariosTableId = '#usuarios-table'
    let usuarioId = undefined

    $(usuariosTableId).DataTable({
        order: [[ 0, "asc" ]],
        searching: false,
        lengthChange: false,
        orderable: false,
        paging: true,
        pageLength: 20,
        language: dataTableTranslation,
        fixedColumns: true,
        autoWidth: false
    })

    $('.modal').on('hidden.bs.modal', function() {
        usuarioId = undefined
    });

    const removeUsuarioModal = '#usuario-remove-modal'
    $(removeUsuarioModal).on('shown.bs.modal', function(event) {
        usuarioId = event.relatedTarget.attributes.getNamedItem('value').value;
        console.log("chegda")
        $.ajax({
            url: '/usuarios/' + usuarioId + '/obter',
            type: 'GET',
            success: function (response) {
                console.log(response)
                $(`${removeUsuarioModal} p span`).html(response.nome)
            },
            error: function (error) {
                console.error(error)
            }
        })
    });

    const removeUsuarioFormId = '#usuario-remove-form'
    $(removeUsuarioFormId).submit(function(event) {
        event.preventDefault()

        $.ajax({
            url: '/usuarios/' + usuarioId,
            type: 'DELETE',
            processData: false,
            contentType: false,
            success: function () {
                closeModal(removeUsuarioModal)
                showSuccessMessage("Removido com sucesso!", "O usuário foi devidamente removido de nosso banco de dados", true)
            },
            error: function (error) {
                if(error.status === 422) {
                    const errors = error.responseJSON.errors;
                    showErrors(errors)
                    return
                }

                showErrorMessage("Erro ao remover usuário", "Ocorreu um erro ao remover o usuário. Por favor, tente novamente mais tarde.")
            }
        })
    })
})