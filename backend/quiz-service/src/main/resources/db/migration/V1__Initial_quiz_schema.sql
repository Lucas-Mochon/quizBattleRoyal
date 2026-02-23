-- Quiz Service: Quiz, Questions, Sessions, Power-ups, and Achievements
CREATE TABLE IF NOT EXISTS quizs (
    id UUID PRIMARY KEY,
    owner_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    topic VARCHAR(150),
    picture BYTEA,
    difficulty INT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(owner_id, name)
);

CREATE TABLE IF NOT EXISTS quiz_questions (
    id UUID PRIMARY KEY,
    quiz_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    time TIME,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_quiz_questions_quiz_id FOREIGN KEY (quiz_id) REFERENCES quizs(id) ON DELETE CASCADE,
    UNIQUE(quiz_id, name)
);

CREATE TABLE IF NOT EXISTS question_propositions (
    id UUID PRIMARY KEY,
    quiz_question_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    is_correct BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_question_propositions_question_id FOREIGN KEY (quiz_question_id) REFERENCES quiz_questions(id) ON DELETE CASCADE,
    UNIQUE(quiz_question_id, name)
);

CREATE TABLE IF NOT EXISTS power_ups (
    id UUID PRIMARY KEY,
    name VARCHAR(150) NOT NULL UNIQUE,
    effect VARCHAR(255) NOT NULL,
    effect_duration TIME,
    picture BYTEA,
    reload_time TIME,
    rarity VARCHAR(50),
    max_per_game INT,
    is_passive BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS quiz_sessions (
    id UUID PRIMARY KEY,
    quiz_id UUID NOT NULL,
    started_at TIMESTAMP NOT NULL,
    ended_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_quiz_sessions_quiz_id FOREIGN KEY (quiz_id) REFERENCES quizs(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS quiz_session_participants (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    quiz_session_id UUID NOT NULL,
    is_winner BOOLEAN,
    score INT NOT NULL DEFAULT 0,
    CONSTRAINT fk_session_participants_session_id FOREIGN KEY (quiz_session_id) REFERENCES quiz_sessions(id) ON DELETE CASCADE,
    UNIQUE(user_id, quiz_session_id)
);

CREATE TABLE IF NOT EXISTS quiz_session_answers (
    id UUID PRIMARY KEY,
    quiz_session_id UUID NOT NULL,
    question_id UUID NOT NULL,
    proposition_id UUID NOT NULL,
    is_correct BOOLEAN NOT NULL,
    answered_at TIMESTAMP,
    CONSTRAINT fk_session_answers_session_id FOREIGN KEY (quiz_session_id) REFERENCES quiz_sessions(id) ON DELETE CASCADE,
    CONSTRAINT fk_session_answers_question_id FOREIGN KEY (question_id) REFERENCES quiz_questions(id) ON DELETE CASCADE,
    CONSTRAINT fk_session_answers_proposition_id FOREIGN KEY (proposition_id) REFERENCES question_propositions(id) ON DELETE CASCADE,
    UNIQUE(quiz_session_id, question_id)
);

CREATE TABLE IF NOT EXISTS quiz_session_power_ups (
    id UUID PRIMARY KEY,
    quiz_session_id UUID NOT NULL,
    power_up_id UUID NOT NULL,
    user_id UUID NOT NULL,
    used_at TIMESTAMP,
    CONSTRAINT fk_session_power_ups_session_id FOREIGN KEY (quiz_session_id) REFERENCES quiz_sessions(id) ON DELETE CASCADE,
    CONSTRAINT fk_session_power_ups_power_up_id FOREIGN KEY (power_up_id) REFERENCES power_ups(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS achievements (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255),
    picture BYTEA,
    rarity VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_quizs_owner_id ON quizs(owner_id);
CREATE INDEX idx_quiz_questions_quiz_id ON quiz_questions(quiz_id);
CREATE INDEX idx_question_propositions_quiz_question_id ON question_propositions(quiz_question_id);
CREATE INDEX idx_quiz_sessions_quiz_id ON quiz_sessions(quiz_id);
CREATE INDEX idx_quiz_session_participants_user_id ON quiz_session_participants(user_id);
CREATE INDEX idx_quiz_session_participants_session_id ON quiz_session_participants(quiz_session_id);
CREATE INDEX idx_quiz_session_answers_session_id ON quiz_session_answers(quiz_session_id);
CREATE INDEX idx_quiz_session_answers_question_id ON quiz_session_answers(question_id);
CREATE INDEX idx_quiz_session_power_ups_session_id ON quiz_session_power_ups(quiz_session_id);
