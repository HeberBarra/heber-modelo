package org.modelador.feedback;

import org.modelador.banco.GenericDAO;

public class FeedbackDAO extends GenericDAO<Feedback> {

    public FeedbackDAO() {
        super(Feedback.class);
    }
}
