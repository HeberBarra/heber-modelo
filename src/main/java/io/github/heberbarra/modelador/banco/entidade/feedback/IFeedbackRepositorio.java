package io.github.heberbarra.modelador.banco.entidade.feedback;

import io.github.heberbarra.modelador.banco.entidade.atividade.Atividade;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFeedbackRepositorio extends JpaRepository<Feedback, Integer> {

    Feedback getFeedbackByCodigo(int codigo);

    List<Feedback> getFeedbacksByAtividade(Atividade atividade);
}
