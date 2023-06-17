$(document).ready(function () {
    let cursoId = undefined
    const cursosTableId = "#cursos-table"

    $(cursosTableId).DataTable({
        order: [[ 0, "asc" ]],
        searching: false,
        lengthChange: false,
        orderable: false,
        paging: true,
        pageLength: 20,
        language: dataTableTranslation
    })

    $('.modal').on('hidden.bs.modal', function() {
        resetErrors()
        cursoId = undefined
    });

    $('#curso-create-form').submit(function(event) {
        event.preventDefault()
        const data = new FormData($(this)[0]);

        $.ajax({
            url: '/cursos',
            type: 'POST',
            data: data,
            processData: false,
            contentType: false,
            success: function () {
                closeModal('#curso-create-modal')
                resetForm('#curso-create-form')
                showSuccessMessage("Salvo com sucesso!", "O curso foi devidamente registrado em nosso banco de dados", true)
            },
            error: function (error) {
                if(error.status === 422) {
                    const errors = error.responseJSON.errors;
                    showErrors(errors)
                    return
                }

                showErrorMessage("Erro ao cadastrar curso", "Ocorreu um erro ao cadastrar o curso. Por favor, tente novamente mais tarde.")
            }
        })
    })

    const editCursoModalId = '#curso-edit-modal'
    $(editCursoModalId).on('shown.bs.modal', function(event) {
        $(editCursoModalId).trigger('reset')
        cursoId = event.relatedTarget.attributes.getNamedItem('value').value;

        $.ajax({
            url: '/cursos/' + cursoId,
            type: 'GET',
            success: function (response) {
                $(`${editCursoModalId} input[name='nome']`).val(response.nome)
                $(`${editCursoModalId} select[name='cursoTipo']`).val(response.cursoTipo)
            },
            error: function (error) {
                console.error(error)
            }
        })
    });

    const editCursoFormId = '#curso-edit-form'
    $(editCursoFormId).submit(function(event) {
        event.preventDefault()
        const data = new FormData($(this)[0]);

        $.ajax({
            url: '/cursos/' + cursoId,
            type: 'PUT',
            data: data,
            processData: false,
            contentType: false,
            success: function () {
                closeModal(editCursoModalId)
                resetForm(editCursoFormId)
                showSuccessMessage("Salvo com sucesso!", "O curso foi devidamente atualizado em nosso banco de dados", true)
            },
            error: function (error) {
                if(error.status === 422) {
                    const errors = error.responseJSON.errors;
                    showErrors(errors)
                    return
                }

                showErrorMessage("Erro ao atualizar curso", "Ocorreu um erro ao atualizar o curso. Por favor, tente novamente mais tarde.")
            }
        })
    })

    const removeCursoModalId = '#curso-remove-modal'
    $(removeCursoModalId).on('shown.bs.modal', function(event) {
        cursoId = event.relatedTarget.attributes.getNamedItem('value').value;

        $.ajax({
            url: '/cursos/' + cursoId,
            type: 'GET',
            success: function (response) {
                $(`${removeCursoModalId} p span`).html(response.nome)
            },
            error: function (error) {
                console.error(error)
            }
        })
    });

    const removeCursoFormId = '#curso-remove-form'
    $(removeCursoFormId).submit(function(event) {
        event.preventDefault()

        $.ajax({
            url: '/cursos/' + cursoId,
            type: 'DELETE',
            processData: false,
            contentType: false,
            success: function () {
                closeModal(removeCursoModalId)
                showSuccessMessage("Removido com sucesso!", "O curso foi devidamente removido de nosso banco de dados", true)
            },
            error: function (error) {
                if(error.status === 422) {
                    const errors = error.responseJSON.errors;
                    showErrors(errors)
                    return
                }

                showErrorMessage("Erro ao remover curso", "Ocorreu um erro ao remover o curso. Por favor, tente novamente mais tarde.")
            }
        })
    })
})