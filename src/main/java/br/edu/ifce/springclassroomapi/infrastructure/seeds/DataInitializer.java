package br.edu.ifce.springclassroomapi.infrastructure.seeds;

import br.edu.ifce.springclassroomapi.domain.entities.Curso;
import br.edu.ifce.springclassroomapi.domain.entities.Turma;
import br.edu.ifce.springclassroomapi.domain.entities.Usuario;
import br.edu.ifce.springclassroomapi.domain.enums.CursoTipo;
import br.edu.ifce.springclassroomapi.domain.enums.IdentificacaoTipo;
import br.edu.ifce.springclassroomapi.infrastructure.repositories.CursoRepository;
import br.edu.ifce.springclassroomapi.infrastructure.repositories.TurmaRepository;
import br.edu.ifce.springclassroomapi.infrastructure.repositories.UsuarioRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class DataInitializer implements CommandLineRunner {
    private final CursoRepository cursoRepository;
    private final UsuarioRepository usuarioRepository;
    private final TurmaRepository turmaRepository;
    private final Faker faker;

    public DataInitializer(CursoRepository cursoRepository, UsuarioRepository usuarioRepository, TurmaRepository turmaRepository) {
        this.cursoRepository = cursoRepository;
        this.usuarioRepository = usuarioRepository;
        this.turmaRepository = turmaRepository;
        this.faker = new Faker(new Locale("pt-BR"));
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if(cursoRepository.count() == 0) {
            seed();
        }
    }

    public void seed() {
        var computacao = new Curso("Ciência da Computação", CursoTipo.Graduacao);
        var redesDeComputadores = new Curso("Redes de Computadores", CursoTipo.Tecnico);

        var cursos = new Curso[]{
                new Curso("Técnico em Automação Industrial", CursoTipo.Tecnico),
                new Curso("Técnico em Informática", CursoTipo.Tecnico),
                new Curso("Técnico em Meio Ambiente", CursoTipo.Tecnico),
                redesDeComputadores,
                new Curso("Integrado em Química", CursoTipo.Integrado),
                new Curso("Integrado em Mecânica", CursoTipo.Integrado),
                new Curso("Licenciatura em Matemática", CursoTipo.Graduacao),
                new Curso("Licenciatura em Química", CursoTipo.Graduacao),
                new Curso("Bacharelado em Engenharia Ambiental e Sanitária", CursoTipo.Graduacao),
                computacao,
                new Curso("Bacharelado em Engenharia Mecânica", CursoTipo.Graduacao),
                new Curso("Bacharelado em Engenharia de Controle e Automação", CursoTipo.Graduacao),
        };
        cursoRepository.saveAll(List.of(cursos));

        var corneli = new Usuario(
                "Corneli Gomes Furtado Júnior",
                "cjunior@ifce.edu.br",
                faker.phoneNumber().cellPhone(),
                faker.numerify("###########"),
                faker.numerify("#######"),
                IdentificacaoTipo.Professor,
                null
        );
        var ione = new Usuario(
                "Francisca Ione Chaves",
                "ione@ifce.edu.br",
                faker.phoneNumber().cellPhone(),
                faker.numerify("###########"),
                faker.numerify("#######"),
                IdentificacaoTipo.Professor,
                null
        );
        var igor = new Usuario(
                "Igor Rafael Silva Valente",
                "igor@ifce.edu.br",
                faker.phoneNumber().cellPhone(),
                faker.numerify("###########"),
                faker.numerify("#######"),
                IdentificacaoTipo.Professor,
                null
        );
        var inacio = new Usuario(
                "Inácio Cordeiro Alves",
                "inacioalves@ifce.edu.br",
                faker.phoneNumber().cellPhone(),
                faker.numerify("###########"),
                faker.numerify("#######"),
                IdentificacaoTipo.Professor,
                null
        );

        var professores = new Usuario[] {
                new Usuario(
                        "Amauri Holanda de Souza Júnior",
                        "amauriholanda@ifce.edu.br",
                        faker.phoneNumber().cellPhone(),
                        faker.numerify("###########"),
                        faker.numerify("#######"),
                        IdentificacaoTipo.Professor,
                        null
                ),
                corneli,
                new Usuario(
                        "Elder dos Santos Teixeira",
                        "teixeira.elder@gmail.com",
                        faker.phoneNumber().cellPhone(),
                        faker.numerify("###########"),
                        faker.numerify("#######"),
                        IdentificacaoTipo.Professor,
                        null
                ),
                ione,
                igor,
                inacio,
                new Usuario(
                        "Marcos Cirineu Aguiar Siqueira",
                        "mcirineu@ifce.edu.br",
                        faker.phoneNumber().cellPhone(),
                        faker.numerify("###########"),
                        faker.numerify("#######"),
                        IdentificacaoTipo.Professor,
                        null
                ),
                new Usuario(
                        "Otávio Alcântara de Lima Junior",
                        "otavio@ifce.edu.br",
                        faker.phoneNumber().cellPhone(),
                        faker.numerify("###########"),
                        faker.numerify("#######"),
                        IdentificacaoTipo.Professor,
                        null
                ),
                new Usuario(
                        "Thiago Alves Rocha",
                        "thiagoalves@ifce.edu.br",
                        faker.phoneNumber().cellPhone(),
                        faker.numerify("###########"),
                        faker.numerify("#######"),
                        IdentificacaoTipo.Professor,
                        null
                ),
                new Usuario(
                        "Sandro César Silveira Jucá",
                        "sandro@ifce.edu.br",
                        faker.phoneNumber().cellPhone(),
                        faker.numerify("###########"),
                        faker.numerify("#######"),
                        IdentificacaoTipo.Professor,
                        null
                ),
        };
        usuarioRepository.saveAll(List.of(professores));

        var alunos = new ArrayList<Usuario>();
        for (int i = 0; i < 600; i++) {
            var aluno = new Usuario(
                    faker.name().fullName(),
                    faker.internet().emailAddress(),
                    faker.phoneNumber().cellPhone(),
                    faker.numerify("###########"),
                    faker.numerify("##############"),
                    IdentificacaoTipo.Aluno,
                    cursos[faker.number().numberBetween(0, cursos.length - 1)]
            );
            alunos.add(aluno);
        }
        usuarioRepository.saveAll(alunos);

        var alunosArray = alunos.toArray(new Usuario[0]);
        var turmas = new Turma[] {
            new Turma(
                "Tópicos em Java para WEB",
                2023,
                1,
                computacao,
                corneli,
                Arrays.stream(alunosArray).filter(a -> a.getCurso().getId() == computacao.getId()).limit(12).collect(Collectors.toList())
            ),
            new Turma(
                "Cabeamento Estruturado",
                2018,
                2,
                redesDeComputadores,
                inacio,
                Arrays.stream(alunosArray).filter(a -> a.getCurso().getId() == redesDeComputadores.getId()).limit(6).collect(Collectors.toList())
            ),
            new Turma(
                "Administração para Ciência da Computação",
                2023,
                1,
                computacao,
                ione,
                Arrays.stream(alunosArray).filter(a -> a.getCurso().getId() == computacao.getId()).limit(20).collect(Collectors.toList())
            ),
            new Turma(
                "Sistemas Distribuídos",
                2023,
                1,
                computacao,
                corneli,
                Arrays.stream(alunosArray).filter(a -> a.getCurso().getId() == computacao.getId()).limit(8).collect(Collectors.toList())
            ),
            new Turma(
                "TCC II",
                2023,
                1,
                computacao,
                igor,
                Arrays.stream(alunosArray).filter(a -> a.getCurso().getId() == computacao.getId()).limit(3).collect(Collectors.toList())
            ),
            new Turma(
                "Laboratório de Redes",
                2017,
                2,
                redesDeComputadores,
                inacio,
                Arrays.stream(alunosArray).filter(a -> a.getCurso().getId() == redesDeComputadores.getId()).limit(15).collect(Collectors.toList())
            ),
            new Turma(
                "Administração de Servidores I",
                2018,
                1,
                redesDeComputadores,
                corneli,
                Arrays.stream(alunosArray).filter(a -> a.getCurso().getId() == redesDeComputadores.getId()).limit(11).collect(Collectors.toList())
            ),
            new Turma(
                "Programação Orientada a Objetos",
                2021,
                2,
                computacao,
                igor,
                Arrays.stream(alunosArray).filter(a -> a.getCurso().getId() == computacao.getId()).limit(12).collect(Collectors.toList())
            ),
        };
        turmaRepository.saveAll(List.of(turmas));
    }
}
