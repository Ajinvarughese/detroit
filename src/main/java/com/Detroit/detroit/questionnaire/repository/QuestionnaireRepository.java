package com.Detroit.detroit.questionnaire.repository;

import com.Detroit.detroit.enums.LoanCategory;
import com.Detroit.detroit.enums.QuestionnaireType;
import com.Detroit.detroit.questionnaire.entity.Questionnaire;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {
    Optional<Questionnaire> findByFormUrlId(String formUrlId);
    List<Questionnaire> findByLoanCategory(LoanCategory loanCategory);

    @Modifying
    @Query("UPDATE Questionnaire q SET q.questionnaireType = :normalType WHERE q.loanCategory = :loanCategory AND q.id <> :id")
    @Transactional
    void setAllExceptToNormalInCategory(@Param("id") Long id,
                                        @Param("loanCategory") LoanCategory loanCategory,
                                        @Param("normalType") QuestionnaireType normalType);


}

