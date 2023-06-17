$(document).ready(function () {
    const turmasTableId = "#turmas-table"
    const alunoSelectId = '#aluno-select'
    const alunosTableId = "#alunos-table"
    const addAlunoButtonId = "#add-aluno-button"
    const createTurmaForm = '#create-turma-form'
    const editTurmaForm = '#edit-turma-form'
    const removeTurmaModalId = '#turma-remove-modal'
    const removeTurmaFormId = '#turma-remove-form'

    $(turmasTableId).DataTable({
        order: [],
        aaSorting: [],
        searching: false,
        lengthChange: false,
        orderable: false,
        paging: true,
        pageLength: 20,
        language: dataTableTranslation
    })

    $(alunosTableId).on('click', '.remove-aluno', function () {
        alunosTable
            .row($(this).parents('tr'))
            .remove()
            .draw();
    });

     $('#curso').on('change', function () {
        const cursoId = $(this).val();
        if (cursoId) {
            $.ajax({
                url: '/usuarios/obter?cursoId=' + cursoId,
                type: "GET",
                dataType: "json",
                success: function (alunos) {
                    $(alunoSelectId).empty();
                    $(alunoSelectId).append(`<option value='undefined' selected>Selecione aqui</option>`);
                    $.each(alunos, function (_, aluno) {
                        $(alunoSelectId).append(`<option value='${JSON.stringify(aluno)}'>${aluno.nome}</option>`);
                    });
                    $(alunoSelectId).removeAttr('disabled');
                    $(addAlunoButtonId).removeAttr('disabled');
                }
            });
            alunosTable.clear().draw();
        } else {
            $(alunoSelectId).empty();
            $(alunoSelectId).setAttribute('disabled', 'disabled');
        }
     })

    $(addAlunoButtonId).on('click', function () {
        const selectedAluno = JSON.parse($(alunoSelectId).val())
        $(alunoSelectId).val('undefined')
        alunosTable.row.add(selectedAluno).draw(false);
    })

    $(createTurmaForm).submit(function (event) {
        event.preventDefault()
        const data = new FormData($(this)[0]);
        const payload = formDataToObject(data)
        payload.alunosIds = alunosTable.data().toArray().map(aluno => aluno.id)

        console.log(payload)

        $.ajax({
            url: '/turmas',
            type: 'POST',
            data: JSON.stringify(payload),
            contentType: 'application/json',
            success: function () {
                window.location.href = '/turmas'
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

    $(editTurmaForm).submit(function (event) {
        event.preventDefault()
        const turmaId = $('#turmaId').val()
        const data = new FormData($(this)[0]);
        const payload = formDataToObject(data)
        payload.alunosIds = alunosTable.data().toArray().map(aluno => aluno.id)
        console.log(alunosTable.data())

        $.ajax({
            url: '/turmas/' + turmaId,
            type: 'PUT',
            data: JSON.stringify(payload),
            contentType: 'application/json',
            success: function () {
                window.location.href = '/turmas'
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

    let turmaId = undefined

    $('.modal').on('hidden.bs.modal', function() {
        turmaId = undefined
    });

    $(removeTurmaModalId).on('shown.bs.modal', function(event) {
        turmaId = event.relatedTarget.attributes.getNamedItem('value').value;
        $.ajax({
            url: '/turmas/' + turmaId + '/obter',
            type: 'GET',
            success: function (response) {
                $(`${removeTurmaModalId} p span`).html(response.nome)
            },
            error: function (error) {
                console.error(error)
            }
        })
    });

    $(removeTurmaFormId).submit(function(event) {
        event.preventDefault()

        $.ajax({
            url: '/turmas/' + turmaId,
            type: 'DELETE',
            processData: false,
            contentType: false,
            success: function () {
                closeModal(removeTurmaModalId)
                showSuccessMessage("Removido com sucesso!", "A turma foi devidamente removido de nosso banco de dados", true)
            },
            error: function (error) {
                if(error.status === 422) {
                    const errors = error.responseJSON.errors;
                    showErrors(errors)
                    return
                }

                showErrorMessage("Erro ao remover usuário", "Ocorreu um erro ao remover a turma. Por favor, tente novamente mais tarde.")
            }
        })
    })
})